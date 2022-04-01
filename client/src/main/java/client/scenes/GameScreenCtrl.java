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

    private Instant roundStartTime;
    private Instant roundEndTime;
    private int qindex;
    private final ScheduledFuture<?>[] tasks = new ScheduledFuture<?>[3];
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
        qindex = 0;
    }

    /**
     * When the first emoji is clicked it is sent to the server and also by whom it has been sent.
     */
    @FXML
    private void emoji1Pressed() {
        EmojiMessage emojiInfo = new EmojiMessage(username, "emoji1");
        GameCommunication.send("/app/emoji/" + currentGameID
            + "/" + MainCtrl.username, emojiInfo);
    }

    /**
     * When the second emoji is clicked it is sent to the server and also by whom it has been sent.
     */
    @FXML
    private void emoji2Pressed() {
        EmojiMessage emojiInfo = new EmojiMessage(username, "emoji2");
        GameCommunication.send("/app/emoji/" + currentGameID
                + "/" + MainCtrl.username, emojiInfo);
    }

    /**
     * When the third emoji is clicked it is sent to the server and also by whom it has been sent.
     */
    @FXML
    private void emoji3Pressed() {
        EmojiMessage emojiInfo = new EmojiMessage(username, "emoji3");
        GameCommunication.send("/app/emoji/" + currentGameID
                + "/" + MainCtrl.username, emojiInfo);
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
            // ws setup
            setupWebSockets();
            // receive first question time
            GameCommunication.send("/app/time/get/" + currentGameID + "/" + qindex, "foo");
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
        //GameCommunication.send("/app/time/get/" + currentGameID + "/" + qindex, "foo");
        refreshQuestion();
        present();
    }

    // TODO: Replace with final ws implementation
    /**
     * Set up the WS connection and start listening for messages.
     */
    private void setupWebSockets() {
        // connect
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("currentGameID", currentGameID);
        properties.put("username", MainCtrl.username);
        GameCommunication.connect(Utils.serverAddress, properties);

        // configure the connection
        HashMap<String, Object> userProperties = new HashMap<>();
        userProperties.put("currentGameID", currentGameID);
        userProperties.put("username", MainCtrl.username);
        GameCommunication.send("/app/game/" + currentGameID + "/"
                + MainCtrl.username, userProperties);

        // save barTask (0 - barTask) (1 - transitionTask)


        // register to receive roundEndTime
        GameCommunication.registerForMessages("/time/get/receive/" + currentGameID,
            Long[].class, o -> {
                if (o == null) {
                    return;
                }

                roundStartTime = Instant.ofEpochMilli(o[0]);

                roundEndTime = Instant.ofEpochMilli(o[1]);
                if (tasks[0] != null) {
                    tasks[0].cancel(false);
                }
                tasks[0] = SceneController
                    .scheduleProgressBar(progressBar, roundStartTime, roundEndTime);


                System.out.println("new roundStartTime: " + roundStartTime);
                System.out.println("new roundEndTime: " + roundEndTime);

                // progress bar


                // transition to leaderboard
                if (tasks[1] != null) {
                    System.out.println("1 canceled");
                    tasks[1].cancel(false);
                }

                if (qindex == 0) {
                    tasks[1] = scheduler.scheduleAtInstant(() -> {
                        System.out.println("holy schmoop");
                        tasks[0].cancel(false);

                        Platform.runLater(() -> myFxml.showScene(MatchLeaderboardCtrl.class,
                            roundEndTime));




                    }, roundEndTime);
                }
                System.out.println("the index is: " + qindex);
                if (qindex != 0) {
                    tasks[1] = scheduler.scheduleAtInstant(() -> {
                        System.out.println("holy schdfsfoop");
                        //tasks[0].cancel(false);

                        Platform.runLater(() -> myFxml.showScene(MatchLeaderboardCtrl.class,
                            Instant.now().plusMillis(10).plusSeconds(6)));
                        System.out.println(qindex);




                    }, Instant.now().plusMillis(10));
                }


                //  System.out.println("qIndex" + qindex);
                qindex++;


                if (tasks[2] != null) {
                    System.out.println("1 canceled");
                    tasks[2].cancel(false);

                }

                tasks[2] = scheduler.scheduleAtInstant(() -> {
                    System.out.println("debug");
                    GameCommunication.send("/app/time/get/" + currentGameID
                          + "/" + qindex, "foo");


                }, roundEndTime);
            });

        // register for emojis
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
                                img.setImage(emojis.get(v.emojiID));
                                setGraphic(img);
                            }
                            setText(name);
                        }
                    }
                }));
    }

    /**
     * Get the question from the server and display it.
     */
    public void refreshQuestion() {
        Question activeQuestion = GameCommunication.getQuestion(currentGameID, qindex);
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
        int reward = GameCommunication.processAnswer(currentGameID, username, qindex,
                answer, Duration.between(roundStartTime, Instant.now()).toMillis());
        GameCommunication.send("/app/time/get/" + currentGameID + "/" + qindex, "foo");
    }
}
