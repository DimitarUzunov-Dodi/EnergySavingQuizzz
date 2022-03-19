package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

public class WaitingRoomController {

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

    public void onBackButton() {
        // TODO: close websockets connection
    }
    
}
