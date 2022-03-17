package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ResourceBundle;

public class WaitingRoomController implements Initializable {

    private final MainCtrl mainCtrl;
    private final ServerUtils server;

    @FXML
    private Button backButton;
    @FXML
    private Button startButton;
    @FXML
    private ListView<String> listView;
    private ObservableList<String> playerList;

    @Inject
    public WaitingRoomController(MainCtrl mainCtrl, ServerUtils server) {
        this.mainCtrl = mainCtrl;
        this.server = server;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        listView.setItems(playerList);
    }
    
}
