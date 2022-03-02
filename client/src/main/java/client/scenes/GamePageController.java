package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ResourceBundle;

public class GamePageController implements Initializable {


    private final ServerUtils server;
    private final MainCtrl mainCtrl;
    private int playerScore;

    //an example to use until a proper point calculation is implemented
    private int placeholderPoints = 10;

    @FXML
    private ListView<String> Leaderboard_list;

    @FXML
    private Label Leaderboard_label;

    String[] names = {"foo", "bar", "test"};

    @Inject
    public GamePageController(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Leaderboard_list.getItems().addAll(names);
    }

    public void UpdatePlayerScore(int points) {
        playerScore += points;
    }


}
