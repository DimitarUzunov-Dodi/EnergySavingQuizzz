package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminCtrl implements Initializable {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @Inject
    public AdminCtrl(MainCtrl mainCtrl, ServerUtils server) {
        this.mainCtrl = mainCtrl;
        this.server = server;
    }

    /**
     * Function called by button when clicked. Invokes server's restart
     * @param event passed by JavaFX by default
     */
    public void restartAction(ActionEvent event){
        server.invokeServerRestart();
    }

    /**
     * Function called by button when clicked. Returns to the Splashscreen.
     * @param event passed by JavaFX by default
     */
    public void backToSplashAction(ActionEvent event){
        mainCtrl.showSplashScreen();
    }
}
