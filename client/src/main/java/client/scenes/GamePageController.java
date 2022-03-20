package client.scenes;

import client.communication.GameCommunication;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.QuestionTypeA;
import commons.User;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;



public class GamePageController implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;
    private final ServerUtils server;
    private final GameCommunication gameCommunication;
    private final MainCtrl mainCtrl;

    private static String gameCode;
    private static User user;
    private static int qIndex;
    private static QuestionTypeA activeQuestion;

    private static final int TIME_TO_NEXT_ROUND = 3;

    @FXML
    private ProgressBar progressBar;


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
    private Text QuestionText;
    @FXML
    private Text ActivityText1;
    @FXML
    private Text ActivityText2;
    @FXML
    private Text ActivityText3;


    String[] names = {"foo", "bar", "test"};
    private final ArrayList<Button> button_List = new ArrayList<>();


    @Inject
    public GamePageController(ServerUtils server, MainCtrl mainCtrl, GameCommunication gameCommunication) {
        this.server = server;
        this.mainCtrl = mainCtrl;
        this.gameCommunication = gameCommunication;
    }

    /**
     * initializes user interface components
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //progressBar = (ProgressBar) mainCtrl.getCurrentScene().lookup("#progressBar");
        progressBar.setProgress(0);
       // Question_text = new Text("foo");
        button_List.add(button1);
        button_List.add(button2);
        button_List.add(button3);
        button_List.add(button4);

        gameCode = gameCommunication.startSinglePlayerGame();

        qIndex = 0;

        refreshQuestion();

        currentLeaderboard.getItems().addAll(names);
        currentLeaderboard.getItems().addAll(names);
        currentLeaderboard.getItems().addAll(names);
        currentLeaderboard.getItems().addAll(names);
        currentLeaderboard.getItems().addAll(names);
        currentLeaderboard.getItems().addAll(names);
    }

    public static void init(User user1) {
        user = user1;
    }

    /**
     * Start a time for TIME_TO_NEXT_ROUND seconds and bind to progressbar
     */
    public void countDown() {
        final Service<Integer> countDownThread = new Service<>() {
            @Override
            protected Task<Integer> createTask() {
                return new Task<Integer>() {
                    @Override
                    protected Integer call() {
                        int i;
                        for(i = 0; i < TIME_TO_NEXT_ROUND * 100; i++) {
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
/*
        // Next Round Transition
        countDownThread.setOnSucceeded(event -> {
            userAlert("INFO", "Next Round", "Next Round Start");
        });
*/
        // bind thread to progress bar and start it
        progressBar.progressProperty().bind(countDownThread.progressProperty());
        countDownThread.start();

        // TODO "uses unchecked or unsafe operations"

    }

    public void refreshQuestion() {
        activeQuestion = gameCommunication.getQuestion(gameCode, qIndex);
        QuestionText.setText(activeQuestion.displayText());
    }

}
