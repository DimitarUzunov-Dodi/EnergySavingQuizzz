package client.scenes;

import com.google.inject.Inject;
import commons.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import static client.utils.FileUtils.readNickname;
import static client.utils.FileUtils.writeNickname;


public class SplashCtrl {

    private final MainCtrl mainCtrl;
    private String nickname;

    @FXML
    private Button singlePlayerGameButton;

    @Inject
    public SplashCtrl(MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
        this.nickname = "";
    }

    /**
     * Function called by quit button when clicked. Quits the application
     * @param event passed by JavaFX by default
     */
    public void quitAction(ActionEvent event) {
        Stage stage = (Stage)(((Button)event.getSource()).getScene().getWindow());
        stage.fireEvent(
        new WindowEvent(
                stage,
                WindowEvent.WINDOW_CLOSE_REQUEST
        ));
    }

    /**
     * Function called by settings button when clicked. Changes scene to Settings scene.
     * @param event passed by JavaFX by default
     */
    public void settingsAction(ActionEvent event) {
        mainCtrl.showSettingsScreen();
    }

    public void singlePlayerAction(ActionEvent event) {
        GamePageController.init(new User(null, nickname));
        mainCtrl.showGamePage();
    }

    /**
     * Function initializes nickname TextField in SplashScreen scene
     */
    public void initTextField() {
        TextField usernameField = (TextField) mainCtrl.getSplashScreenScene().lookup("#nicknameField");

        String username = readNickname();
        usernameField.setText(username);
        this.setNickname(username);
    }

    /**
     * Function saves nickname automatically when nickname TextField is changed
     * Nickname is stored according to pathToUserData
     */
    public void saveNickname() {
        TextField usernameField = (TextField) mainCtrl.getSplashScreenScene().lookup("#nicknameField");
        String nicknameString = usernameField.getText();

        writeNickname(nicknameString);
    }

    /**
     * Get method for nickname attribute, so nickname can be accessed everywhere by every client function
     * Subject to change, because to the best thing to import SplashCtrl everytime. Maybe we should add function somewhere in common class
     * @return String representing the nickname
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * Set method for nickname attribute
     * @param nickname String value which is going to be assigned to nickname class attribute but will not be saved in file
     */
    private void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void onMultiplayerPressed() {
        mainCtrl.showServerJoin();
    }

    /**
     * Function called by admin button when clicked. Changes scene to AdminPage scene.
     * @param event passed by JavaFX by default
     */
    public void adminAction(ActionEvent event){
        mainCtrl.showAdmin();
    }
}
