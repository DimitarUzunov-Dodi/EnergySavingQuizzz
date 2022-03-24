package client.scenes;

import static client.scenes.MainCtrl.currentGameID;
import static client.scenes.MainCtrl.username;
import static client.utils.UserAlert.userAlert;

import client.MyFXML;
import client.communication.WaitingRoomCommunication;
import client.utils.SceneController;
import client.utils.UserAlert;
import com.google.inject.Inject;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.Response;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class MultiplayerCtrl extends SceneController {

    @FXML
    private TextField gameCodeField;

    /**
     * Basic constructor.
     * @param myFxml handled by INJECTOR
     */
    @Inject
    private MultiplayerCtrl(MyFXML myFxml) {
        super(myFxml);
    }

    @Override
    public void show() {
        showScene();
    }

    /**
     * Called on user pressing 'Join Public Match' button.
     * Sends the user to the current public waiting room.
     */
    @FXML
    private void onJoinPublic() {
        currentGameID = "get waiting public game id"; // TODO: Implement auto-gen games
        myFxml.showScene(WaitingRoomCtrl.class);
    }

    /**
     * Called on user pressing 'Join Private Game'.
     * Sends the user to the desired waiting room if it exists.
     */
    @FXML
    private void onJoinPrivate() {
        // TODO: Check the game id first
        currentGameID = gameCodeField.getText().trim();
        joinGame();
    }

    private void joinGame() {
        Response joinResponse = WaitingRoomCommunication.joinGame(currentGameID, username);
        int statusCode = joinResponse.getStatus();
        if (statusCode == 200) {
            myFxml.get(WaitingRoomCtrl.class).customShow(currentGameID);
        } else if(statusCode == 400){
            userAlert("ERROR", "Username is already taken", "Username already in use in this game!");
        } else if(statusCode == 404) {
            userAlert("ERROR", "No such game", "No game found with this game code!");
        }
    }

    /**
     * Called on user pressing 'Create Private Game' button.
     * Creates a new match and sends the player to it's waiting room.
     */
    @FXML
    private void onCreatePrivate() {
        currentGameID = WaitingRoomCommunication.createNewGame();
        joinGame();
    }

    /**
     * Called on user pressing the 'Back' button.
     * Sends the user to the Splash Screen.
     */
    @FXML
    private void onBackButton() {
        myFxml.showScene(SplashCtrl.class);
    }
}
