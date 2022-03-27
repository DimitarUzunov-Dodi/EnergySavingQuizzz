package client.scenes;


import client.MyFXML;
import client.communication.ActivityImageCommunication;
import client.communication.GameCommunication;
import client.utils.FileUtils;
import client.utils.SceneController;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Person;
import commons.QuestionTypeA;
import commons.User;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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


public class GameScreenCtrl extends SceneController {

    private String username;


    private static String gameCode;
    private static User user;
    private static int qIndex;
    private static QuestionTypeA activeQuestion;

    private static final double TIME_TO_NEXT_ROUND = 5;

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

    private long currentTime = 0;
    private int questionNumber = -1;

    private int correctAnswer;

    private final Image emojiHappy = new Image("client/images/emoji1.png");
    private final Image emojiSad = new Image("client/images/emoji2.png");
    private final Image emojiAngry = new Image("client/images/emoji3.png");

    /*image array to load all images at a time*/
    private final Image[] imagesArray = {emojiHappy, emojiSad, emojiAngry};

    //    GameScreenLeaderboardEntry[] names = {new GameScreenLeaderboardEntry("Dodi"),
    //    new GameScreenLeaderboardEntry("John"),new GameScreenLeaderboardEntry("boom")};
    private ArrayList<Button> buttonList = new ArrayList<>();

    String[] names = {"foo", "bar", "test"};

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
            .getQuestion(MainCtrl.currentGameID, qIndex);
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
        //progressBar.setProgress(1000);
        countDown();


    }




    /**
     * When the first emoji is clicked it is sent to the server and also by whom it has been sent.
     */
    public void emoji1Pressed() {
        username = FileUtils.readNickname();
        Person emojiInfo = new Person(username, "emoji1");
        GameCommunication.send("/app/emoji/" + MainCtrl.currentGameID
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
        GameCommunication.send("/app/emoji/" + MainCtrl.currentGameID
            +
            "/" + MainCtrl.username, emojiInfo);
    }

    /**
     * When the third emoji is clicked it is sent to the server and also by whom it has been sent.
     */
    public void emoji3Pressed() {
        username = FileUtils.readNickname();
        Person emojiInfo = new Person(username, "emoji3");
        GameCommunication.send("/app/emoji/" + MainCtrl.currentGameID + "/"
            + MainCtrl.username, emojiInfo);
    }


    @Override
    public void show() {

        HashMap<String, Object> properties = new HashMap<>();
        properties.put("currentGameID", MainCtrl.currentGameID);
        properties.put("username", MainCtrl.username);
        // connect via websockets
        GameCommunication.connect(ServerUtils.serverAddress, properties);

        GameCommunication.registerForMessages("/time/get/receive/" + MainCtrl.currentGameID,
            long.class, o -> {
                currentTime = o;
                System.out.println(o);
                System.out.println("time/get/receive");

            });



        /* adding items to the list view */
        ObservableList<String> elements = FXCollections.observableArrayList("Fist ", "Second ",
                "Dodi");
        currentLeaderboard.setItems(elements);

        initImages();
        buttonList.add(button1);
        buttonList.add(button2);
        buttonList.add(button3);
        buttonList.add(button4);

        // gameCode = client.communication.GameCommunication.startSinglePlayerGame();

        qIndex = 0;

        currentLeaderboard.getItems().addAll(names);
        currentLeaderboard.getItems().addAll(names);
        currentLeaderboard.getItems().addAll(names);
        currentLeaderboard.getItems().addAll(names);
        currentLeaderboard.getItems().addAll(names);
        currentLeaderboard.getItems().addAll(names);

        ArrayList<String> list = new ArrayList<>();
        GameCommunication.registerForMessages("/emoji/receive/"
            + MainCtrl.currentGameID, Person.class, v -> {

                Image newEmoji = null;
                System.out.println("communication");
                switch (v.lastName) {

                    case "emoji1":
                        newEmoji = imagesArray[0];
                        break;
                    case "emoji2":
                        newEmoji = imagesArray[1];
                        break;
                    case "emoji3":
                        newEmoji = imagesArray[2];
                        break;

                    default:
                        break;

                }

                final Image emoji = newEmoji;

                currentLeaderboard.setCellFactory(param -> new ListCell<String>() {
                    /*view the image class to display the image*/
                    private ImageView displayImage = new ImageView();


                    @Override
                public void updateItem(String name, boolean empty) {
                        super.updateItem(name, empty);
                        if (empty) {
                            setText(null);
                            setGraphic(null);
                        }   else {
                            if (name.equals(v.firstName)) {
                                displayImage.setFitHeight(20);
                                displayImage.setFitWidth(20);
                                displayImage.setImage(emoji);
                                //  displayImage.setFitWidth(0.1);


                            }
                            setText(name);
                            setGraphic(displayImage);
                        }
                    }
                });
                ImageView displayImage = new ImageView();
                displayImage.setImage(imagesArray[2]);
                System.out.println(v);
            });

        HashMap<String, Object> userProperties = new HashMap<String, Object>();
        userProperties.put("currentGameID", MainCtrl.currentGameID);
        userProperties.put("username", MainCtrl.username);
        GameCommunication.send("/app/game/" + MainCtrl.currentGameID + "/"
            + MainCtrl.username, userProperties);
        System.out.println("the time is " + currentTime);
        refreshQuestion();

        showScene();
    }
}
