package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ResourceBundle;

public class GamePageController implements Initializable {

    /*
    private final ServerUtils server;
    private final MainCtrl mainCtrl;
*/
    @FXML
    private ListView<String> Leaderboard_list;

    @FXML
    private Label Leaderboard_label;

    String[] names = {"foo", "bar", "test"};

  /*  @Inject
    public GamePageCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }
   */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Leaderboard_list.getItems().addAll(names);
    }


}
