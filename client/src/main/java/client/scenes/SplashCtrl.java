package client.scenes;

import static client.scenes.MainCtrl.currentGameID;
import static client.scenes.MainCtrl.username;
import static client.utils.FileUtils.readNickname;
import static client.utils.FileUtils.writeNickname;
import static client.utils.UserAlert.userAlert;

import client.Main;
import client.MyFXML;
import client.communication.LeaderboardCommunication;
import client.communication.WaitingRoomCommunication;
import client.utils.SceneController;
import client.utils.UserAlert;
import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class SplashCtrl extends SceneController {

    @FXML
    private Button quitButton;
    @FXML
    private TextField usernameText;

    /**
     * Basic constructor.
     * @param myFxml handled by INJECTOR
     */
    @Inject
    private SplashCtrl(MyFXML myFxml) {
        super(myFxml);
    }

    @Override
    public void show() {
        initTextField();
        present(); // display the scene on the primaryStage
    }

    /**
     * Function called by quit button when clicked. Quits the application.
     */
    @FXML
    private void quitAction() {
        Stage stage = (Stage)scene.getWindow(); // this is how you get the current Stage btw
        quitButton.getScene().getWindow().fireEvent(
                new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
    }

    /**
     * Function called by settings button when clicked. Changes scene to Settings scene.
     */
    @FXML
    private void settingsAction() {
        myFxml.showScene(SettingsCtrl.class);
    }

    /**
     * Called on user pressing 'Singleplayer' button, send user to GamePage.
     */
    @FXML
    private void singlePlayerAction() {
        try {
            currentGameID = WaitingRoomCommunication.createNewGame();
        } catch (RuntimeException e) {
            e.printStackTrace();
            UserAlert.userAlert("WARN", "Cannot connect to server",
                    "Check your connection and try again.");
        }
        // may throw exceptions, but it's unlikely
        WaitingRoomCommunication.joinGame(currentGameID, username);
        myFxml.showScene(GameScreenCtrl.class, true);
    }

    /**
     * Function initializes nickname TextField in SplashScreen scene.
     */
    private void initTextField() {
        username = readNickname();
        usernameText.setText(username);
        Main.primaryStage.setTitle(username);
    }

    /**
     * Function saves nickname automatically when nickname TextField is changed.
     * Nickname is stored according to pathToUserData.
     */
    @FXML
    private void saveNickname() {
        username = usernameText.getText().strip();
        try {
            writeNickname(username);
        } catch (IllegalArgumentException e) {
            username = username.substring(0, 20);
            usernameText.setText(username);
            userAlert(
                    "WARN",
                    "Username is too long",
                    "Username can be less no more than 20 characters");
        }
        Main.primaryStage.setTitle(username);
    }

    /**
     * Called on user pressing 'Multiplayer' button, sends user to Multiplayer.
     */
    @FXML
    private void onMultiplayerPressed() {
        myFxml.showScene(MultiplayerCtrl.class);
    }

    /**
     * Function called by admin button when clicked. Changes scene to AdminPage scene.
     */
    @FXML
    private void adminAction() {
        myFxml.showScene(AdminCtrl.class);
    }

    /**
     * Function called by highScoresButton when clicked. Changes scene to ServerLeaderboard scene.
     */
    @FXML
    private void onHighScoresButton() {
        myFxml.showScene(ServerLeaderboardCtrl.class);
    }
}
