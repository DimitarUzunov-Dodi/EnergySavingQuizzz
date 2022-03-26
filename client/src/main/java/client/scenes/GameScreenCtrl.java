package client.scenes;

import static client.scenes.MainCtrl.currentGameID;
import static java.util.Map.entry;

import client.MyFXML;
import client.communication.ActivityImageCommunication;
import client.communication.GameCommunication;
import client.communication.LeaderboardCommunication;
import client.communication.WaitingRoomCommunication;
import client.utils.FileUtils;
import client.utils.SceneController;
import com.google.inject.Inject;
import commons.Person;
import commons.QuestionTypeA;
import commons.User;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javafx.collections.FXCollections;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

// TODO: this whole controller needs to be reworked bc it's a mess and it's very hard to work with
public class GameScreenCtrl extends SceneController {

    private String username;

    private static User user;
    private static int qIndex;
    private static QuestionTypeA activeQuestion;

    private static final int TIME_TO_NEXT_ROUND = 3;

    @FXML
    private ImageView menuButton;

    @FXML
    private ImageView image1;

    @FXML
    private ImageView image2;


    @FXML
    private ImageView image3;



    @FXML
    private ProgressBar progressBar;

    @FXML
    private Text activityText1;

    @FXML
    private Text activityText2;

    @FXML
    private Text activityText3;

    @FXML
    private ListView<String> currentLeaderboard;


    @FXML
    private Button button1;

    @FXML
    private Button button2;

    @FXML
    private Button button3;

    @FXML
    private Button button4;

    @FXML
    private Text questionText;

    @FXML
    private ImageView emoji1;
    @FXML
    private ImageView emoji2;
    @FXML
    private ImageView emoji3;

    private int correctAnswer;

    private final Map<String, Image> emojis = Map.ofEntries(
            entry("emoji1", new Image("client/images/emoji1.png")),
            entry("emoji2", new Image("client/images/emoji2.png")),
            entry("emoji3", new Image("client/images/emoji3.png"))
    // TODO: change emoji names to be more descriptive (and maybe 1 add more)
    );

    // GameScreenLeaderboardEntry[] names = {new GameScreenLeaderboardEntry("Dodi"),
    // new GameScreenLeaderboardEntry("John"),new GameScreenLeaderboardEntry("boom")};
    private ArrayList<Button> buttonList = new ArrayList<>();

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
        final Service<Integer> countDownThread = new Service<>() {
            @Override
            protected Task<Integer> createTask() {
                return new Task<Integer>() {
                    @Override
                    protected Integer call() {
                        int i;
                        for (i = TIME_TO_NEXT_ROUND * 100; i > 0; i--) {
                            updateProgress(i, TIME_TO_NEXT_ROUND * 100);
                            try {
                                Thread.sleep(10);
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
        // TODO: place this in the right place when answer checking is implemented
        myFxml.showScene(MatchLeaderboardCtrl.class);
        activeQuestion = client.communication.GameCommunication
            .getQuestion(currentGameID, qIndex);
        qIndex++;
        questionText.setText(activeQuestion.displayText());
        activityText1.setText(activeQuestion.getActivity1().getActivityText());
        activityText2.setText(activeQuestion.getActivity2().getActivityText());
        activityText3.setText(activeQuestion.getActivity3().getActivityText());
        long energyConsumption1 = activeQuestion.getActivity1().getValue();
        long energyConsumption2 = activeQuestion.getActivity2().getValue();
        long energyConsumption3 = activeQuestion.getActivity3().getValue();
        final long[] consumptions = {energyConsumption1, energyConsumption2, energyConsumption3};
        image1.setImage(ActivityImageCommunication.getImageFromId(
            activeQuestion.getActivity1().getImageId()));
        image2.setImage(ActivityImageCommunication.getImageFromId(
            activeQuestion.getActivity2().getImageId()));
        image3.setImage(ActivityImageCommunication.getImageFromId(
            activeQuestion.getActivity3().getImageId()));
        int i = -1;
        long biggest = -1;
        for (long consumption: consumptions) {
            i++;
            if (consumption > biggest) {
                biggest = consumption;
                correctAnswer = i;
            }
        }
        countDown();


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
     * method to call when answer A is pressed.
     */
    public void answerApressed() {
        if (correctAnswer == 0) {
            awardPoints();
        } else {
            System.out.print("dumbass");
        }
    }

    /**
     * method to call when answer B is pressed.
     */
    public void answerBpressed() {
        if (correctAnswer == 1) {
            awardPoints();
        } else {
            System.out.print("dumbass");
        }
    }

    /**
     * method to call when answer C is pressed.
     */
    public void answerCpressed() {
        if (correctAnswer == 2) {
            awardPoints();
        } else {
            System.out.print("dumbass");
        }
    }

    /**
     * method to award points to client.
     */
    public void awardPoints() {
        // TODO award points


        refreshQuestion();
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
                    .stream().map(User::getUsername).toList()
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

        initImages();
        //progressBar = (ProgressBar) mainCtrl.getCurrentScene().lookup("#progressBar");
        //progressBar.setProgress(1F);
        // Question_text = new Text("foo");
        buttonList.add(button1);
        buttonList.add(button2);
        buttonList.add(button3);
        buttonList.add(button4);

        setupPlayerList();

        qIndex = 0;

        GameCommunication.registerForMessages("/game/receive/" + currentGameID,
            Map.class,o -> {
                System.out.println(o.toString());
                System.out.println("foo");
            });
        HashMap<String, Object> userProperties = new HashMap<String, Object>();
        userProperties.put("currentGameID", currentGameID);
        userProperties.put("username", MainCtrl.username);
        GameCommunication.send("/app/game/" + currentGameID + "/"
            + MainCtrl.username, userProperties);
        refreshQuestion();

        showScene();
    }
}
