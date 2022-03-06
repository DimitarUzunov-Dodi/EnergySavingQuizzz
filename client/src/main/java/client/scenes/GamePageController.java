package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GamePageController implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;
    private final ServerUtils server;
    private final MainCtrl mainCtrl;




    @FXML
    private ListView<String> currentLeaderboard;



    String[] names = {"foo", "bar", "test"};

    @Inject
    public GamePageController(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        currentLeaderboard.getItems().addAll(names);
        currentLeaderboard.getItems().addAll(names);
        currentLeaderboard.getItems().addAll(names);
        currentLeaderboard.getItems().addAll(names);
        currentLeaderboard.getItems().addAll(names);
        currentLeaderboard.getItems().addAll(names);
    }







}
