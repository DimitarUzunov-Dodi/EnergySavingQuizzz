package client.scenes;

import static client.scenes.MainCtrl.currentGameID;
import static java.util.Map.entry;

import client.MyFXML;
import client.communication.GameCommunication;
import client.communication.LeaderboardCommunication;
import client.communication.WaitingRoomCommunication;
import client.utils.FileUtils;
import client.utils.SceneController;
import com.google.inject.Inject;
import commons.Person;
import commons.Question;
import commons.QuestionTypeA;
import commons.QuestionTypeB;
import commons.QuestionTypeC;
import commons.QuestionTypeD;
import commons.User;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

// TODO: this whole controller needs to be reworked bc it's a mess and it's very hard to work with
public class GameScreenCtrl extends SceneController {

    private String username;

    private static User user;
    private static int qIndex;
    private Question activeQuestion;

    private static final double TIME_TO_NEXT_ROUND = 5;

    @FXML
    private ImageView menuButton;


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

    private Long currentTime = 0L;

    private int questionNumber = -1;
    private final Map<String, Image> emojis = Map.ofEntries(
            entry("emoji1", new Image("client/images/emoji1.png")),
            entry("emoji2", new Image("client/images/emoji2.png")),
            entry("emoji3", new Image("client/images/emoji3.png")));

    @Inject
    public GameScreenCtrl(MyFXML myFxml) {
        super(myFxml);
    }

    public static void init(User user1) {
        user = user1;
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


    /**
     * Start a time for TIME_TO_NEXT_ROUND seconds and bind to progressbar.
     */
    public void countDown() {
        System.out.println("the real real currentime is " + currentTime);
        Double threadSleepTime = 10D;
        Long difference = currentTime - new Date().getTime();

        final Service<Integer> countDownThread = new Service<>() {

            @Override
            protected Task<Integer> createTask() {
                return new Task<Integer>() {
                    @Override
                    protected Integer call() {

                        Double remainder = (((TIME_TO_NEXT_ROUND * 100D)
                            - ((new Date().getTime() - currentTime) / threadSleepTime)));
                        System.out.println("the remainder is " + remainder);
                        System.out.println(new Date().getTime() - currentTime + "subtract");

                        int i = remainder.intValue();

                        while (i >= 0) {

                            System.out.println(progressBar.getProgress() + "foo");
                            updateProgress(i, TIME_TO_NEXT_ROUND * 100D);
                            try {

                                i--;
                                //System.out.println(i);
                                Thread.sleep(threadSleepTime.intValue());
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        return i;
                    }
                };
            }
        };


        progressBar.progressProperty().bind(countDownThread.progressProperty());
        countDownThread.start();

    }

    /**
     * refreshes the question.
     */
    public void refreshQuestion() {
        questionNumber++;
        GameCommunication.send("/app/time/" + MainCtrl.currentGameID
            + "/" + questionNumber, "foo");
        GameCommunication.send("/app/time/get/" + MainCtrl.currentGameID
            + "/" + questionNumber, "foo");
        System.out.println("the real currentime is " + currentTime);
        // TODO: place this in the right place when answer checking is implemented
        myFxml.showScene(MatchLeaderboardCtrl.class);

        activeQuestion = client.communication.GameCommunication
            .getQuestion(currentGameID, qIndex);
        qIndex++;

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
        countDown();

    }

    /**
     * method to award points to client.
     */
    public void awardPoints() {
        // TODO award points


        refreshQuestion();
    }

    /**
     * When the first emoji is clicked it is sent to the server and also by whom it has been sent.
     */
    public void emoji1Pressed() {
        username = FileUtils.readNickname();
        Person emojiInfo = new Person(username, "emoji1");
        GameCommunication.send("/app/emoji/" + currentGameID
            +
            "/" + MainCtrl.username, emojiInfo);
    }

    /**
     * When the second emoji is clicked it is sent to the server and also by whom it has been sent.
     */
    public void emoji2Pressed() {
        username = FileUtils.readNickname();
        Person emojiInfo = new Person(username, "emoji2");
        GameCommunication.send("/app/emoji/" + currentGameID
            +
            "/" + MainCtrl.username, emojiInfo);
    }

    /**
     * When the third emoji is clicked it is sent to the server and also by whom it has been sent.
     */
    public void emoji3Pressed() {
        username = FileUtils.readNickname();
        Person emojiInfo = new Person(username, "emoji3");
        GameCommunication.send("/app/emoji/" + currentGameID + "/"
            + MainCtrl.username, emojiInfo);
    }

    /**
     * Called when the menuButton is pressed.
     */
    @FXML
    private void onMenuButton() {
        myFxml.showScene(SettingsCtrl.class);
    }

    private void setupPlayerList() {
        // init the player list (cells)
        currentLeaderboard.setItems(FXCollections.observableList(
            WaitingRoomCommunication.getAllUsers(currentGameID)
                    .stream().map(User::getUsername).collect(Collectors.toList())
        ));

        // register websockets events on receiving messages
        GameCommunication.registerForMessages("/emoji/receive/" + currentGameID, Person.class,
            v -> {
                currentLeaderboard.setCellFactory(param -> new ListCell<>() {
                    @Override
                    public void updateItem(String name, boolean empty) {
                        super.updateItem(name, empty);
                        if (empty) {
                            setGraphic(null);
                            setText(null);
                        } else {
                            if (name.equals(v.firstName)) {
                                ImageView img = new ImageView();
                                img.setFitHeight(20);
                                img.setFitWidth(20);
                                img.setImage(emojis.get(v.lastName)); // just why?
                                //  displayImage.setFitWidth(0.1);
                                setGraphic(img);
                            }
                            setText(name);
                        }
                    }
                });
            });
    }

    @Override
    public void show() {

        HashMap<String, Object> properties = new HashMap<>();
        properties.put("currentGameID", currentGameID);
        properties.put("username", MainCtrl.username);
        // connect via websockets
        GameCommunication.connect(LeaderboardCommunication.serverAddress, properties);

        GameCommunication.registerForMessages("/time/get/receive/" + MainCtrl.currentGameID,
            long.class, o -> {
                currentTime = o;
                System.out.println(o);
                System.out.println("time/get/receive");

            });





        setupPlayerList();

        qIndex = 0;

        GameCommunication.registerForMessages("/game/receive/" + currentGameID,
                Map.class, o -> {
                    System.out.println(o.toString());
                    System.out.println("foo");
                });
        HashMap<String, Object> userProperties = new HashMap<String, Object>();
        userProperties.put("currentGameID", currentGameID);
        userProperties.put("username", MainCtrl.username);
        GameCommunication.send("/app/game/" + MainCtrl.currentGameID + "/"
            + MainCtrl.username, userProperties);
        System.out.println("the time is " + currentTime);
        refreshQuestion();

        showScene();
    }

    public void showQuestion(Node node) {
        questionHolder.getChildren().setAll(node);
    }
}
