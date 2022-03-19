package client.scenes;


import client.utils.FileUtils;
import client.utils.ServerUtils;
import com.google.inject.Inject;

import commons.Person;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Pair;


import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class GamePageController implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;
    private String username;
    private final ServerUtils server;
    private final MainCtrl mainCtrl;


    private static final int TIME_TO_NEXT_ROUND = 3;

    @FXML
    private ImageView MenuButton;

    @FXML
    private ImageView Windmill;

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
    private Text Question_text;

    @FXML
    private ImageView emoji1;
    @FXML
    private ImageView emoji2;
    @FXML
    private ImageView emoji3;





    String[] names = {"foo", "bar", "test"};
    private ArrayList<Button> button_List = new ArrayList<>();


    @Inject
    public GamePageController(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
        this.username = FileUtils.readNickname();
    }


    /**
     * initializes user interface components
     *
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        InitImages();
        ArrayList<String> list = new ArrayList<>();
        //progressBar = (ProgressBar) mainCtrl.getCurrentScene().lookup("#progressBar");
        progressBar.setProgress(0);
        // Question_text = new Text("foo");
        button_List.add(button1);
        button_List.add(button2);
        button_List.add(button3);
        button_List.add(button4);

        Question_text.setText("foo");

        currentLeaderboard.getItems().addAll(names);
        currentLeaderboard.getItems().addAll(names);
        currentLeaderboard.getItems().addAll(names);
        currentLeaderboard.getItems().addAll(names);
        currentLeaderboard.getItems().addAll(names);
        currentLeaderboard.getItems().addAll(names);
        server.send("/app/chat", "foo");
        server.registerForMessages("/topic/chat", String.class, q -> {
            list.add(q);
        });
        server.registerForMessages("/emoji/receive", Person.class, v -> {
            System.out.println(v);
        });


    }

    public void InitImages(){
        Windmill.setImage(new Image("client/images/OIP.jpg"));
        MenuButton.setImage(new Image(("client/images/menu.png")));
        emoji1.setImage(new Image("client/images/emoji1.png"));
        emoji2.setImage(new Image("client/images/emoji2.png"));
        emoji3.setImage(new Image("client/images/emoji3.png"));

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
                        for (i = 0; i < TIME_TO_NEXT_ROUND * 100; i++) {
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

    public void emoji1Pressed(){
        Pair<String,Integer> emojiInfo = new Pair(username,1);
        server.send("/app/emoji", emojiInfo);
    }
    public void emoji2Pressed(){
        Pair<String,Integer> emojiInfo = new Pair(username,2);
        server.send("/app/emoji", emojiInfo);
    }
    public void emoji3Pressed(){
        Pair<String,Integer> emojiInfo = new Pair(username,3);
        server.send("/app/emoji", 3);
    }


}
