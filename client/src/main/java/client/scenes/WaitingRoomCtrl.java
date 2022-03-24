package client.scenes;

import client.MyFXML;
import client.utils.SceneController;
import com.google.inject.Inject;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class WaitingRoomCtrl extends SceneController {

    private ObservableList<String> playerList;
    @FXML
    private ListView<String> listView;

    /**
     * Basic constructor.
     * @param myFxml handled by INJECTOR
     */
    @Inject
    private WaitingRoomCtrl(MyFXML myFxml) {
        super(myFxml);
    }

    @Override
    public void show() {
        listView.setItems(playerList);
        // TODO: init websockets connection and get player list
        listView.refresh(); // may not be necessary depending on how we update playerList
        showScene();
    }

    /**
     * Called on user pressing 'Back' button, sends user to Multiplayer.
     */
    @FXML
    private void onBackButton() {
        myFxml.showScene(MultiplayerCtrl.class);
    }

    /**
     * Called on user pressing 'Start' button, sends user to GamePage.
     */
    @FXML
    private void onStartButton() {
        // TODO: start match ( either here or in GamePageController.show() )
        myFxml.showScene(GameScreenCtrl.class);
    }
    
}
