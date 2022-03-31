package client.scenes;

import static client.scenes.MainCtrl.currentGameID;
import static client.scenes.MainCtrl.scheduler;
import static client.scenes.MainCtrl.username;
import static java.util.Map.entry;

import client.MyFXML;
import client.communication.GameCommunication;
import client.communication.Utils;
import client.utils.SceneController;
import com.google.inject.Inject;
import commons.EmojiMessage;
import commons.Question;
import commons.QuestionTypeA;
import commons.QuestionTypeB;
import commons.QuestionTypeC;
import commons.QuestionTypeD;
import commons.User;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;
import java.util.stream.Collectors;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public class GameScreenCtrl extends SceneController {

    private Instant roundStartTime, roundEndTime;
    private int qIndex;

    @FXML
    private StackPane questionHolder;
    @FXML
    private ProgressBar progressBar;
    @FXML
    private ListView<String> currentLeaderboard;

    // TODO: maybe replace with UTF-8 chars
    private final Map<String, Image> emojis = Map.ofEntries(
            entry("emoji1", new Image("client/images/emoji1.png")),
            entry("emoji2", new Image("client/images/emoji2.png")),
            entry("emoji3", new Image("client/images/emoji3.png")));

    @Inject
    public GameScreenCtrl(MyFXML myFxml) {
        super(myFxml);
        qIndex = -1;
    }

    /**
     * When the first emoji is clicked it is sent to the server and also by whom it has been sent.
     */
    @FXML
    private void emoji1Pressed() {
        EmojiMessage emojiInfo = new EmojiMessage(username, "emoji1");
        GameCommunication.send("/app/emoji/" + currentGameID
            + "/" + MainCtrl.username, emojiInfo);
        GameCommunication.send("/app/time/get/" + currentGameID + "/" + qIndex, "foo");
    }

    /**
     * When the second emoji is clicked it is sent to the server and also by whom it has been sent.
     */
    @FXML
    private void emoji2Pressed() {
        EmojiMessage emojiInfo = new EmojiMessage(username, "emoji2");
        GameCommunication.send("/app/emoji/" + currentGameID
                + "/" + MainCtrl.username, emojiInfo);
        GameCommunication.send("/app/time/get/" + currentGameID + "/" + qIndex, "foo");
    }

    /**
     * When the third emoji is clicked it is sent to the server and also by whom it has been sent.
     */
    @FXML
    private void emoji3Pressed() {
        EmojiMessage emojiInfo = new EmojiMessage(username, "emoji3");
        GameCommunication.send("/app/emoji/" + currentGameID
                + "/" + MainCtrl.username, emojiInfo);
        GameCommunication.send("/app/time/get/" + currentGameID + "/" + qIndex, "foo");
    }

    /**
     * Called when the menuButton is pressed.
     */
    @FXML
    private void onMenuButton() {
        myFxml.showScene(SettingsCtrl.class);
    }

    /**
     * Sets up the ws connection and inits some UI elements, then calls {@link #show()}.
     * @param args Only accepts one argument of type {@code Boolean}, which determines
     *             if the extra initialisation takes place or not.
     */
    @Override
    public void show(Object... args) {
        // handle varargs
        if (args.length != 1 || !args[0].getClass().equals(Boolean.class)) {
            throw new IllegalArgumentException("Expected only one Boolean argument");
        }

        if ((Boolean) args[0]) {
            // TODO: init whatever else needs it
            // ws
            setupWebSockets();
        }
        show();
    }

    /**
     * Displays the game scene and triggers all the necessary refreshes and async tasks.
     * Make sure to have working ws connection and initialized scene elements!
     * You can do that by calling {@code show(true)}.
     */
    @Override
    public void show() {

        // refresh player list
        currentLeaderboard.setItems(FXCollections.observableList(
                Utils.getAllUsers(currentGameID).orElse(new ArrayList<>(0))
                        .stream().map(User::getUsername).collect(Collectors.toList())
        ));

        // other UI stuff
        progressBar.setProgress(1d);
        refreshQuestion();
        present();

        // TODO: schedule these where you handle incoming ws messages
        roundEndTime = Instant.now().plusSeconds(22);
        roundStartTime = Instant.now().minusMillis(10);
        // progress bar
        final ScheduledFuture<?> barTask = SceneController.scheduleProgressBar(progressBar, roundEndTime);
        // round end transition
        ScheduledFuture<?> endRoundTask = scheduler.scheduleAtInstant(
                () -> {
                    barTask.cancel(false);
                    Platform.runLater(() -> myFxml.showScene(MatchLeaderboardCtrl.class,
                                    roundEndTime.plusSeconds(6)));
                },
                roundEndTime);
    }

    // TODO: Replace with final ws implementation
    /**
     * Set up the WS connection and start listening for messages.
     */
    private void setupWebSockets() {
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("currentGameID", currentGameID);
        properties.put("username", MainCtrl.username);

        GameCommunication.connect(Utils.serverAddress, properties);
        GameCommunication.registerForMessages("/time/get/receive/" + currentGameID,
            long.class, o -> {

            roundEndTime = Instant.ofEpochMilli(o);
                System.out.println(roundEndTime);
            refreshQuestion();
                scheduler.scheduleAtInstant(
                    () -> myFxml.showScene(MatchLeaderboardCtrl.class, roundEndTime.plusSeconds(6)),
                    roundEndTime);
                // progress bar
                final ScheduledFuture<?> barTask = SceneController.scheduleProgressBar(progressBar, roundEndTime);
        });
        GameCommunication.registerForMessages("/emoji/receive/" + currentGameID, EmojiMessage.class,
                v -> currentLeaderboard.setCellFactory(param -> new ListCell<>() {
                    @Override
                    public void updateItem(String name, boolean empty) {
                        super.updateItem(name, empty);
                        if (empty) {
                            setGraphic(null);
                            setText(null);
                        } else {
                            if (name.equals(v.username)) {
                                ImageView img = new ImageView();
                                img.setFitHeight(20);
                                img.setFitWidth(20);
                                img.setImage(emojis.get(v.emojiID)); // just why?
                                //  displayImage.setFitWidth(0.1);
                                setGraphic(img);
                            }
                            setText(name);
                        }
                    }
                }));
        GameCommunication.registerForMessages("/game/receive/" + currentGameID + "/" + MainCtrl.username,
                Map.class, o -> {
                    System.out.println(o.toString());
                    System.out.println("floop");
                });
        HashMap<String, Object> userProperties = new HashMap<>();
        userProperties.put("currentGameID", currentGameID);
        userProperties.put("username", MainCtrl.username);
        GameCommunication.send("/app/game/" + currentGameID + "/"
                + MainCtrl.username, userProperties);
        System.out.println("foo");
    }

    /**
     * Get the question from the server and display it.
     */
    public void refreshQuestion() {
        qIndex++;
        Question activeQuestion = GameCommunication.getQuestion(currentGameID, qIndex);
        switch (activeQuestion.getQuestionType()) {
            case 0:
                myFxml.get(QuestionTypeAComponentCtrl.class)
                        .loadComponent((QuestionTypeA) activeQuestion);
                break;
            case 1:
                myFxml.get(QuestionTypeBComponentCtrl.class)
                        .loadComponent((QuestionTypeB) activeQuestion);
                break;
            case 2:
                myFxml.get(QuestionTypeCComponentCtrl.class)
                        .loadComponent((QuestionTypeC) activeQuestion);
                break;
            case 3:
                myFxml.get(QuestionTypeDComponentCtrl.class)
                        .loadComponent((QuestionTypeD) activeQuestion);
                break;
            default:
                break;
        }


       // GameCommunication.send("/app/time/" + currentGameID + "/" + qIndex, "foo");
        GameCommunication.send("/app/time/get/" + currentGameID + "/" + qIndex, "foo");


    }

    /**
     * Displays a question to the correct node in the Scene.
     * @param node - where to put the new question element
     */
    public void showQuestion(Node node) {
        questionHolder.getChildren().setAll(node);
    }

    /**
     * Sends answer to the server.
     * Gets reward - bonus for answering a question
     * reward is in range [0, 100]
     *
     * @param answer - answer from the user
     */
    public void sendAnswer(long answer) {
        int reward = GameCommunication.processAnswer(currentGameID, username, qIndex,
                answer, Duration.between(roundStartTime, Instant.now()).toMillis());
        myFxml.showScene(MatchLeaderboardCtrl.class, Instant.now().plusSeconds(8));
    }
}
