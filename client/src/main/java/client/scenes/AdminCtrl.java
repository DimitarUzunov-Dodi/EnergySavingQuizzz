package client.scenes;

import client.MyFXML;
import client.communication.AdminCommunication;
import client.utils.SceneController;
import com.google.inject.Inject;
import javafx.fxml.FXML;

import static client.utils.UserAlert.userAlert;

public class AdminCtrl extends SceneController {

    /**
     * Basic constructor
     * @param myFXML handled by INJECTOR
     */
    @Inject
    private AdminCtrl(MyFXML myFXML) {
        super(myFXML);
    }

    @Override
    public void show() {
        showScene();
    }

    /**
     * Function called by button when clicked. Invokes server's restart
     */
    @FXML
    protected void restartAction(){
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
    protected void backToSplashAction(){
        myFXML.showScene(SplashCtrl.class);
    }

    /**
     * Function called by button when clicked. Switches to admin activity panel.
     */
    @FXML
    protected void switchToAdminActivityPanel(){
        myFXML.showScene(AdminActivityCtrl.class);
    }
}
