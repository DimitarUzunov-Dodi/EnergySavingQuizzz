package client.scenes;

import client.communication.AdminCommunication;
import com.google.inject.Inject;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

import static client.scenes.UserAlert.userAlert;

public class AdminCtrl implements Initializable {

    private final AdminCommunication server;
    private final MainCtrl mainCtrl;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @Inject
    public AdminCtrl(MainCtrl mainCtrl, AdminCommunication server) {
        this.mainCtrl = mainCtrl;
        this.server = server;
    }

    /**
     * Function called by button when clicked. Invokes server's restart
     * @param event passed by JavaFX by default
     */
    public void restartAction(ActionEvent event){
        try {
            server.invokeServerRestart();
        } catch (RuntimeException e) {
            userAlert("ERROR", "Connection failed", "Client was unable to connect to the server");
        }
    }

    /**
     * Function called by button when clicked. Returns to the Splashscreen.
     * @param event passed by JavaFX by default
     */
    public void backToSplashAction(ActionEvent event){
        mainCtrl.showSplashScreen();
    }
}
