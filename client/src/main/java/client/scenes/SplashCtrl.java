package client.scenes;

import client.MyFXML;
import client.utils.SceneController;
import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import static client.scenes.MainCtrl.username;
import static client.utils.FileUtils.readNickname;
import static client.utils.FileUtils.writeNickname;

public class SplashCtrl extends SceneController {

    @FXML
    private Button quitButton;
    @FXML
    private Button usernameText;

    /**
     * Basic constructor
     * @param myFXML handled by INJECTOR
     */
    @Inject
    private SplashCtrl(MyFXML myFXML) {
        super(myFXML);
    }

    @Override
    public void show() {
        initTextField();
        showScene(); // display the scene on the primaryStage
    }

    /**
     * Function called by quit button when clicked. Quits the application
     */
    protected void quitAction() {
        Stage stage = (Stage)scene.getWindow(); // this is how you get the current Stage btw
        quitButton.getScene().getWindow().fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
    }

    /**
     * Function called by settings button when clicked. Changes scene to Settings scene.
     */
    protected void settingsAction() {
        //myFXML.showScene(SettingsCtrl.class);
    }

    /**
     * Called on user pressing 'Singleplayer' button, send user to GamePage
     */
    protected void singlePlayerAction() {
        //myFXML.showScene(GamePageController.class);
        /* left here for reference - delete later
        GamePageController.init(new User(null, nickname));
        mainCtrl.showGamePage(); */
    }

    /**
     * Function initializes nickname TextField in SplashScreen scene
     */
    private void initTextField() {
        username = readNickname();
        usernameText.setText(MainCtrl.username);
    }

    /**
     * Function saves nickname automatically when nickname TextField is changed
     * Nickname is stored according to pathToUserData
     */
    protected void saveNickname() {
        username = usernameText.getText();
        writeNickname(username);
    }

    /**
     * Called on user pressing 'Multiplayer' button, sends user to Multiplayer
     */
    protected void onMultiplayerPressed() {
        myFXML.showScene(MultiplayerCtrl.class);
    }

    /**
     * Function called by admin button when clicked. Changes scene to AdminPage scene.
     */
    protected void adminAction(){
        myFXML.showScene(AdminCtrl.class);
    }
}
