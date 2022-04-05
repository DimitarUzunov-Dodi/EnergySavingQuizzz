package client.scenes;

import static client.scenes.MainCtrl.currentGameID;
import static client.scenes.MainCtrl.scheduler;
import static java.util.Map.entry;

import client.MyFXML;
import client.communication.CommunicationUtils;
import client.communication.GameCommunication;
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
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public class GameScreenCtrl extends SceneController {

    private Instant roundStartTime;
    private Instant roundEndTime;
    private int questionIndex;
    private final ScheduledFuture<?>[] tasks = new ScheduledFuture<?>[3];
    private Question activeQuestion;
    private int reward;

    private final Map<String, Image> emojis = Map.ofEntries(
            entry("emoji1", new Image("client/images/emoji1.png")),
            entry("emoji2", new Image("client/images/emoji2.png")),
            entry("emoji3", new Image("client/images/emoji3.png")));

    @FXML
    private StackPane questionHolder;
    @FXML
    private ProgressBar progressBar;
    @FXML
    private ListView<String> currentLeaderboard;
    @FXML
    private ImageView emoji1;
    @FXML
    private ImageView emoji2;
    @FXML
    private ImageView emoji3;
    @FXML
    private ImageView menuButton;
    @FXML
    private Label rewardLabel;

    @Inject
    public GameScreenCtrl(MyFXML myFxml) {
        super(myFxml);
        questionIndex = 0;
    }

    /**
     * When the first emoji is clicked it is sent to the server and also by whom it has been sent.
     */
    @FXML
    private void emoji1Pressed() {
        EmojiMessage emojiInfo = new EmojiMessage(MainCtrl.username, "emoji1");
        GameCommunication.send("/app/emoji/" + currentGameID
            + "/" + MainCtrl.username, emojiInfo);
    }

    /**
     * When the second emoji is clicked it is sent to the server and also by whom it has been sent.
     */
    @FXML
    private void emoji2Pressed() {
        EmojiMessage emojiInfo = new EmojiMessage(MainCtrl.username, "emoji2");
        GameCommunication.send("/app/emoji/" + currentGameID
                + "/" + MainCtrl.username, emojiInfo);
    }

    /**
     * When the third emoji is clicked it is sent to the server and also by whom it has been sent.
     */
    @FXML
    private void emoji3Pressed() {
        EmojiMessage emojiInfo = new EmojiMessage(MainCtrl.username, "emoji3");
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
        System.out.println(currentGameID);
        // handle varargs
        if (args.length != 1 || !args[0].getClass().equals(Boolean.class)) {
            throw new IllegalArgumentException("Expected only one Boolean argument");
        }

        if ((Boolean) args[0]) {
            // ws setup
            setupWebSockets();
            // receive first question time
            GameCommunication.send("/app/time/get/" + currentGameID + "/" + questionIndex, "foo");
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
                CommunicationUtils.getAllUsers(currentGameID).orElse(new ArrayList<>(0))
                        .stream().map(User::getUsername).collect(Collectors.toList())
        ));

        // UI stuff
        progressBar.setProgress(1d);
        refreshQuestion();
        present();
        initImages();
    }

    /**
     * Set up the WS connection and start listening for messages.
     */
    private void setupWebSockets() {
        // connect
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("currentGameID", currentGameID);
        properties.put("username", MainCtrl.username);
        GameCommunication.connect(CommunicationUtils.serverAddress, properties);

        // configure the connection
        HashMap<String, Object> userProperties = new HashMap<>();
        userProperties.put("currentGameID", currentGameID);
        userProperties.put("username", MainCtrl.username);
        GameCommunication.send("/app/game/" + currentGameID + "/"
                + MainCtrl.username, userProperties);

        // register to receive roundEndTime
        GameCommunication.registerForMessages("/time/get/receive/" + currentGameID,
            Long[].class, o -> {
                if (o == null) {
                    return;
                }

                roundStartTime = Instant.ofEpochMilli(o[0]);

                roundEndTime = Instant.ofEpochMilli(o[1]);
                if (tasks[0] != null) {
                    tasks[0].cancel(true);
                }
                tasks[0] = SceneController
                    .scheduleProgressBar(progressBar, roundEndTime);
                System.out.println("-- step: " + progressBar.getUserData());

                // transition to leaderboard
                if (tasks[1] != null) {
                    System.out.println("1 canceled");
                    tasks[1].cancel(false);
                }

                System.out.println("the index is: " + questionIndex);
                if (questionIndex == 0) {
                    tasks[1] = scheduler.scheduleAtInstant(() -> {
                        tasks[0].cancel(false);
                        System.out.println("the real index = " + questionIndex);
                        Platform.runLater(() -> myFxml.showScene(MatchLeaderboardCtrl.class,
                                roundEndTime));
                    }, roundEndTime);
                }
                if (questionIndex != 0) {
                    showCorrectAnswer();
                    System.out.println("the real index = " + questionIndex);
                }
                questionIndex++;

                if (tasks[2] != null) {
                    System.out.println("1 canceled");
                    tasks[2].cancel(false);

                }
                tasks[2] = scheduler.scheduleAtInstant(() -> {
                    System.out.println("debug");
                    GameCommunication.send("/app/time/get/" + currentGameID
                          + "/" + questionIndex, "foo");
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

                                if (Duration.between(Instant.now(), roundEndTime).toSeconds() > 3) {
                                    scheduler.scheduleAtInstant(() -> {
                                        img.setFitWidth(0.1);
                                        img.setImage(null);
                                        setGraphic(img);
                                    },Instant.now().plusSeconds(3));
                                } else {
                                    scheduler.scheduleAtInstant(() -> {
                                        img.setFitWidth(0.1);
                                        img.setImage(null);
                                        setGraphic(img);
                                    }, roundEndTime);
                                }
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
        activeQuestion = GameCommunication.getQuestion(currentGameID, questionIndex);
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
        rewardLabel.setVisible(false);
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
        System.out.print("sending answer");
        System.out.println(answer);
        reward = GameCommunication.processAnswer(currentGameID, MainCtrl.username,
                questionIndex, answer, getTimeLeft());
        GameCommunication.send("/app/time/get/" + currentGameID + "/" + questionIndex, "foo");
    }

    private void showCorrectAnswer() {
        System.out.println("showing correct answer");
        if (reward != 0) {
            Platform.runLater(() -> {
                rewardLabel.setText("+" + reward + " points");
                rewardLabel.setVisible(true);
            });
        }

        long correctAnswer = GameCommunication.getAnswer(currentGameID, questionIndex - 1);
        System.out.println(correctAnswer);
        Platform.runLater(() ->  showAnswerInComponent(correctAnswer));


        scheduler.schedule(
                () -> Platform.runLater(
                        () -> myFxml.showScene(MatchLeaderboardCtrl.class,
                                Instant.now().plusSeconds(6))), 2000);


    }

    private void showAnswerInComponent(long correctAnswer) {
        System.out.println(activeQuestion.getQuestionType() + "    That was the active question");
        switch (activeQuestion.getQuestionType()) {
            case 0:
                myFxml.get(QuestionTypeAComponentCtrl.class).showCorrectAnswer(correctAnswer);
                break;
            case 1:
                myFxml.get(QuestionTypeBComponentCtrl.class).showCorrectAnswer(correctAnswer);
                break;
            case 2:
                myFxml.get(QuestionTypeCComponentCtrl.class).showCorrectAnswer(correctAnswer);
                break;
            case 3:
                myFxml.get(QuestionTypeDComponentCtrl.class).showCorrectAnswer(correctAnswer);
                break;
            default:
                break;
        }




    }

    /**
     * Initialises the images for the game screen.
     */
    public void initImages() {
        //windmill.setImage(new Image("client/images/OIP.jpg"));
        menuButton.setImage(new Image(("client/images/menu.png")));
        emoji1.setImage(new Image("client/images/emoji1.png"));
        emoji2.setImage(new Image("client/images/emoji2.png"));
        emoji3.setImage(new Image("client/images/emoji3.png"));
    }

    // This is stupid, why not use the ACTUAL time left?
    private int getTimeLeft() {
        return (int) Math.round(progressBar.getProgress() * 100);
    }
}
