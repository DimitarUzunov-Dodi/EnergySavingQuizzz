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
     * Basic constructor
     * @param myFXML handled by INJECTOR
     */
    @Inject
    private WaitingRoomCtrl(MyFXML myFXML) {
        super(myFXML);
    }

    @Override
    public void show() {
        listView.setItems(playerList);
        // TODO: init websockets connection and get player list
        listView.refresh(); // may not be necessary depending on how we update playerList
        showScene();
    }

    /**
     * Called on user pressing 'Back' button, sends user to Multiplayer
     */
    protected void onBackButton() {
        myFXML.showScene(MultiplayerCtrl.class);
    }

    /**
     * Called on user pressing 'Start' button, sends user to GamePage
     */
    protected void onStartButton() {
        // TODO: start match ( either here or in GamePageController.show() )
        myFXML.showScene(GamePageController.class);
    }
    
}
