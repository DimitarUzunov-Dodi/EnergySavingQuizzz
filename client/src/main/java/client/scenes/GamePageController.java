package client.scenes;


import client.Entities.GameScreenLeaderboardEntry;
import client.utils.FileUtils;
import client.utils.ServerUtils;
import com.google.inject.Inject;

import commons.Person;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;


import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
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
    private ListView<GameScreenLeaderboardEntry> currentLeaderboard;


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




    private final Image cabinetImage  = new Image("client/images/OIP.jpg");
    private final Image docIconImage  = new Image("client/images/OIP.jpg");
    private final Image homeCabImage  = new Image("client/images/OIP.jpg");
    private final Image searchIconImage = new Image("client/images/OIP.jpg");
    /*image array to load all images at a time*/
    private Image[] imagesArray = {cabinetImage, docIconImage, homeCabImage, searchIconImage};

    GameScreenLeaderboardEntry[] names = {new GameScreenLeaderboardEntry("Dodi"),new GameScreenLeaderboardEntry("John"),new GameScreenLeaderboardEntry("boom")};
    private ArrayList<Button> button_List = new ArrayList<>();


    @Inject
    public GamePageController(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;

    }

    /**
     * initializes user interface components
     *
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        /* create list object */

        /* adding items to the list view */
        ObservableList<String> elements = FXCollections.observableArrayList("Fist Image", "Second Image", "Third Image",
                "Fourth Image");
        currentLeaderboard.setItems(elements);
        /*setting each image to corresponding array index*/
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
                    if (name.equals("Fist Image"))
                        displayImage.setImage(imagesArray[0]); /*setting array image to First Image*/
                    else if (name.equals("Second Image"))
                        displayImage.setImage(imagesArray[1]);/*setting array image to Second Image*/
                    else if (name.equals("Third Image"))
                        displayImage.setImage(imagesArray[2]);/*setting array image to Third Image*/
                    else if (name.equals("Fourth Image"))
                        displayImage.setImage(imagesArray[3]);/*setting array image to Fourth Image*/
                    setText(name);
                    setGraphic(displayImage);
                }
            }
        });
        /* creating vertical box to add item objects */
       // VBox vBox = new VBox(currentLeaderboard);
        /* creating scene */

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
        server.send("/app/chat", "foo");
        server.registerForMessages("/topic/chat", String.class, q -> {
            list.add(q);
        });
        server.registerForMessages("/emoji/receive", Person.class, v -> {
            ImageView newEmoji = new ImageView("client/images/emoji1.png");
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

    /**
     * When the first emoji is clicked it is sent to the server and also by whom it has been sent
     */
    public void emoji1Pressed(){
        username = FileUtils.readNickname();
        Person emojiInfo = new Person(username,"emoji1");
        server.send("/app/emoji", emojiInfo);
    }
    /**
     * When the second emoji is clicked it is sent to the server and also by whom it has been sent
     */
    public void emoji2Pressed(){
        username = FileUtils.readNickname();
        Person emojiInfo = new Person(username,"emoji2");
        server.send("/app/emoji", emojiInfo);
    }
    /**
     * When the third emoji is clicked it is sent to the server and also by whom it has been sent
     */
    public void emoji3Pressed(){
        username = FileUtils.readNickname();
        Person emojiInfo = new Person(username,"emoji3");
        server.send("/app/emoji", emojiInfo);
    }


}
