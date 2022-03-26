package client.scenes;

import client.MyFXML;
import client.communication.ActivityImageCommunication;
import client.communication.GameCommunication;
import client.utils.FileUtils;
import client.utils.SceneController;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.*;

import java.util.ArrayList;
import java.util.HashMap;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;


public class GameScreenCtrl extends SceneController {

    private String username;


    private static String gameCode;
    private static User user;
    private static int qIndex;
    private Question activeQuestion;

    private static final int TIME_TO_NEXT_ROUND = 3;

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

    private final Image emojiHappy = new Image("client/images/emoji1.png");
    private final Image emojiSad = new Image("client/images/emoji2.png");
    private final Image emojiAngry = new Image("client/images/emoji3.png");

    /*image array to load all images at a time*/
    private final Image[] imagesArray = {emojiHappy, emojiSad, emojiAngry};

    //    GameScreenLeaderboardEntry[] names = {new GameScreenLeaderboardEntry("Dodi"),
    //    new GameScreenLeaderboardEntry("John"),new GameScreenLeaderboardEntry("boom")};

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
        activeQuestion = client.communication.GameCommunication.getQuestion(gameCode, qIndex);
        qIndex++;

        switch (activeQuestion.getQuestionType()) {
            case 0:
                myFxml.get(QuestionTypeAComponentCtrl.class).loadComponent((QuestionTypeA) activeQuestion);

                break;
            case 1:
                myFxml.get(QuestionTypeBComponentCtrl.class).loadComponent((QuestionTypeB) activeQuestion);

                break;
            default:

                break;
        }


    }




    /**
     * When the first emoji is clicked it is sent to the server and also by whom it has been sent.
     */
    public void emoji1Pressed() {
        username = FileUtils.readNickname();
        Person emojiInfo = new Person(username, "emoji1");
        GameCommunication.send("/app/emoji", emojiInfo);
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
        GameCommunication.send("/app/emoji", emojiInfo);
    }

    /**
     * When the third emoji is clicked it is sent to the server and also by whom it has been sent.
     */
    public void emoji3Pressed() {
        username = FileUtils.readNickname();
        Person emojiInfo = new Person(username, "emoji3");
        GameCommunication.send("/app/emoji", emojiInfo);
    }


    @Override
    public void show() {
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("gameID", -1); // should be MainCtrl.currentGameID once it is not null
        // connect via websockets
        GameCommunication.connect(ServerUtils.serverAddress, properties);

        /* create list object */

        /* adding items to the list view */
        ObservableList<String> elements = FXCollections.observableArrayList("Fist ", "Second ",
                "Dodi");
        currentLeaderboard.setItems(elements);
        /*setting each image to corresponding array index*/

        /* creating vertical box to add item objects */
        // VBox vBox = new VBox(currentLeaderboard);
        /* creating scene */

        initImages();
        //progressBar = (ProgressBar) mainCtrl.getCurrentScene().lookup("#progressBar");
        progressBar.setProgress(1F);
        // Question_text = new Text("foo");

        gameCode = client.communication.GameCommunication.startSinglePlayerGame();

        qIndex = 0;



        currentLeaderboard.getItems().addAll(names);
        currentLeaderboard.getItems().addAll(names);
        currentLeaderboard.getItems().addAll(names);
        currentLeaderboard.getItems().addAll(names);
        currentLeaderboard.getItems().addAll(names);
        currentLeaderboard.getItems().addAll(names);

        ArrayList<String> list = new ArrayList<>();
        GameCommunication.send("/app/chat", "foo");
        GameCommunication.registerForMessages("/topic/chat", String.class, q -> {
            list.add(q);
        });
        /*
        GameCommunication.registerForMessages("game/receive", Game.class, o -> {
            questionText.setText("Which one consumes the most amount of energy?");
            for (Question question : o.getActiveQuestionList()) {
                QuestionTypeA foo = (QuestionTypeA) question;
                activityText1.setText(foo.getActivity1().getActivityText());
                activityText2.setText(foo.getActivity2().getActivityText());
                activityText3.setText(foo.getActivity3().getActivityText());

            }

        });
        */
        GameCommunication.registerForMessages("/emoji/receive", Person.class, v -> {
            Image newEmoji = null;
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
                    } else {
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
        refreshQuestion();
        countDown();
        showScene();
    }

    public void showQuestion(Node node) {
        questionHolder.getChildren().setAll(node);
    }

}
