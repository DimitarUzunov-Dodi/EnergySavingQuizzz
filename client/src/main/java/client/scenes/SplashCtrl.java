package client.scenes;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import com.google.inject.Inject;

import static client.utils.FileUtils.*;


public class SplashCtrl {

    private final MainCtrl mainCtrl;
    private String nickname;

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
        ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
    }

    /**
     * Function called by settings button when clicked. Changes scene to Settings scene.
     * @param event passed by JavaFX by default
     */
    public void settingsAction(ActionEvent event) {
        mainCtrl.showSettingsScreen();
    }

    public void singlePlayerAction(ActionEvent event) {
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
     * @return
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

}
