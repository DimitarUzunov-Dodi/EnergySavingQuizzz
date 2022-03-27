package client.scenes;

import static client.utils.UserAlert.userAlert;

import client.MyFXML;
import client.communication.AdminCommunication;
import client.utils.SceneController;
import com.google.inject.Inject;
import javafx.fxml.FXML;

public class AdminCtrl extends SceneController {

    /**
     * Basic constructor.
     * @param myFxml handled by INJECTOR
     */
    @Inject
    private AdminCtrl(MyFXML myFxml) {
        super(myFxml);
    }

    @Override
    public void show() {
        showScene();

    }

    /**
     * Function called by button when clicked. Invokes server's restart.
     */
    @FXML
    private void restartAction() {
        try {
            AdminCommunication.invokeServerRestart();
        } catch (RuntimeException e) {
            userAlert("ERROR", "Connection failed", "Client was unable to connect to the server");
        }
    }

    /**
     * Function called by button when clicked. Returns to the Splashscreen.
     */
    @FXML
    private void backToSplashAction() {
        myFxml.showScene(SplashCtrl.class);
    }

    /**
     * Function called by button when clicked. Switches to admin activity panel.
     */
    @FXML
    private void switchToAdminActivityPanel() {
        myFxml.showScene(AdminActivityCtrl.class);
    }
}
