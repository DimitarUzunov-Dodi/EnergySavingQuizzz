package client.scenes;

import client.MyFXML;
import client.utils.SceneController;
import com.google.inject.Inject;
import commons.Activity;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;

public class WaitingRoomCtrl extends SceneController {

    private String gameCode;
    private ObservableList<String> playerList;
    @FXML
    private ListView<String> listView;
    @FXML
    private Text gameCodeLabel;

    /**
     * Basic constructor.
     * @param myFxml handled by INJECTOR
     */
    @Inject
    private WaitingRoomCtrl(MyFXML myFxml) {
        super(myFxml);
    }

    /**
     * Please use customShow(Activity selected) method to switch to this scene.
     */
    @Override
    public void show() {
        listView.setItems(playerList);
        // TODO: init websockets connection and get player list
        listView.refresh(); // may not be necessary depending on how we update playerList
        showScene();
    }

    /**
     * Method that shows the scene with details about selected game.
     * @param gameCode game code of te selected game
     */
    public void customShow(String gameCode) {
        setGameCode(gameCode);
        show();
    }

    /**
     * Method sets game code and changes label in the scene,
     * so players can see game code.
     * @param gameCode game code of te selected game
     */
    private void setGameCode(String gameCode){
        this.gameCode = gameCode;
        gameCodeLabel.setText("Game code: " + gameCode);
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
