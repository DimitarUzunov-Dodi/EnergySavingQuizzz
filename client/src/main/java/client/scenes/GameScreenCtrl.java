package client.scenes;

import static client.scenes.MainCtrl.currentGameID;
import static client.scenes.MainCtrl.scheduler;
import static java.util.Map.entry;

import client.Main;
import client.MyFXML;
import client.communication.GameCommunication;
import client.communication.Utils;
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
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;
import java.util.stream.Collectors;
import javafx.application.Platform;
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

public class GameScreenCtrl extends SceneController {

    private String username;

    private static int qIndex;
    private Question activeQuestion;

    private static final Long TIME_TO_NEXT_ROUND = 8L;

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

    private Instant currentTime = Instant.now();

    private int questionNumber = 0;
    private final Map<String, Image> emojis = Map.ofEntries(
            entry("emoji1", new Image("client/images/emoji1.png")),
            entry("emoji2", new Image("client/images/emoji2.png")),
            entry("emoji3", new Image("client/images/emoji3.png")));

    @Inject
    public GameScreenCtrl(MyFXML myFxml) {
        super(myFxml);
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
        System.out.println("the real real current time is " + currentTime);
        Long threadSleepTime = 10L;
        Double remainder = (((TIME_TO_NEXT_ROUND * 100D)
            - ((Instant.now().toEpochMilli()- currentTime.toEpochMilli()) / threadSleepTime)));
        progressBar.setProgress(remainder/(TIME_TO_NEXT_ROUND * 100D));
        final ScheduledFuture<?> barTask = scheduler.scheduleAtFixedRate( () ->{

            progressBar.setProgress(progressBar.getProgress() -(threadSleepTime/TIME_TO_NEXT_ROUND * 100D));
        }, threadSleepTime);

       final ScheduledFuture<?> cancellation =  scheduler.scheduleAtInstant(() -> {
            barTask.cancel(false); // stop progressBar
        }, (Instant) (currentTime.plusMillis(TIME_TO_NEXT_ROUND * 1000L)));



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
        System.out.println("the real current time is " + currentTime);

        activeQuestion = client.communication.GameCommunication
            .getQuestion(currentGameID, qIndex);
        //System.out.println(GameCommunication.getAnswer(currentGameID, qIndex));
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
        present();

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
            Utils.getAllUsers(currentGameID).orElse(new ArrayList<>(0))
                    .stream().map(User::getUsername).collect(Collectors.toList())
        ));

        // register websockets events on receiving messages
        GameCommunication.registerForMessages("/emoji/receive/" + currentGameID, Person.class,
            v -> currentLeaderboard.setCellFactory(param -> new ListCell<>() {
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
            }));
        GameCommunication.registerForMessages("/time/get/receive/" + currentGameID,
            Instant.class, o -> {
                System.out.println("REFRESH");
                // if(o != null){
                //  refreshQuestion();

                currentTime = o;

                //  System.out.println(o);
                // System.out.println("time/get/receive");
                //}


            });
        HashMap<String, Object> userProperties = new HashMap<>();
        userProperties.put("currentGameID", currentGameID);
        userProperties.put("username", MainCtrl.username);



        GameCommunication.registerForMessages("/game/receive/" + currentGameID + "/" + username,
            Map.class, o -> {
                System.out.println(o.toString());
                System.out.println("foo");
            });
        GameCommunication.send("/app/game/" + MainCtrl.currentGameID + "/"
            + MainCtrl.username, userProperties);

        GameCommunication.send("/app/time/" + MainCtrl.currentGameID
            + "/" + questionNumber, "foo");
        GameCommunication.send("/app/time/get/" + MainCtrl.currentGameID
            + "/" + questionNumber, "foo");
    }

    @Override
    public void show() {

        HashMap<String, Object> properties = new HashMap<>();
        properties.put("currentGameID", currentGameID);
        properties.put("username", MainCtrl.username);
        // connect via websockets
        GameCommunication.connect(Utils.serverAddress, properties);

        System.out.println("/time/get/receive/" + currentGameID.toString());



        initImages();

        setupPlayerList();

        qIndex = 0;
        System.out.println(currentGameID);




        System.out.println("the time is " + currentTime);


        //refreshQuestion();
    }

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
        int reward = GameCommunication.processAnswer(currentGameID, MainCtrl.username,
                qIndex, answer, getTimeLeft());
        System.out.println(reward);
        myFxml.showScene(MatchLeaderboardCtrl.class, Instant.now().plusSeconds(8));
    }

    private int getTimeLeft() {
        return (int) Math.round(progressBar.getProgress() * 100);
    }
}
