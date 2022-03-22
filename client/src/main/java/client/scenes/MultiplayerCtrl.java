package client.scenes;

import client.MyFXML;
import client.utils.SceneController;
import client.utils.UserAlert;
import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import static client.scenes.MainCtrl.currentGameID;

public class MultiplayerCtrl extends SceneController {

    @FXML
    private TextField gameCodeField;

    /**
     * Basic constructor
     * @param myFXML handled by INJECTOR
     */
    @Inject
    private MultiplayerCtrl(MyFXML myFXML) {
        super(myFXML);
    }

    @Override
    public void show() {
        // noting to do here
    }

    /**
     * Called on user pressing 'Join Public Match' button.
     * Sends the user to the current public waiting room.
     */
    protected void onJoinPublic() {
        currentGameID = "get waiting public game id"; // TODO: Implement auto-gen games
        myFXML.showScene(WaitingRoomController.class);
    }

    /**
     * Called on user pressing 'Join Private Game'
     * Sends the user to the desired waiting room if it exists.
     */
    protected void onJoinPrivate() {
        // TODO: Check the game id first
        currentGameID = gameCodeField.getText();
        myFXML.showScene(WaitingRoomController.class);
    }

    /**
     * Called on user pressing 'Create Private Game' buttton.
     * Creates a new match and sends the player to it's waiting room.
     */
    protected void onCreatePrivate() {
        currentGameID = "generate new private game id";
        myFXML.showScene(WaitingRoomController.class);
        UserAlert.userAlert("INFO", "Game code: " + currentGameID,
                    "Share this with the people you want to play with.");
    }

    /**
     * Called on user pressing the 'Back' button.
     * Sends the user to the Splash Screen.
     */
    protected void onBackButton() {
        myFXML.showScene(SplashCtrl.class);
    }
}
