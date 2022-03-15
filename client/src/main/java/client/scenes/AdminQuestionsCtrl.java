package client.scenes;

import client.communication.AdminCommunication;
import com.google.inject.Inject;
import javafx.event.ActionEvent;

public class AdminQuestionsCtrl {
    private final MainCtrl mainCtrl;
    private final AdminCommunication server;

    @Inject
    public AdminQuestionsCtrl(MainCtrl mainCtrl, AdminCommunication server) {
        this.mainCtrl = mainCtrl;
        this.server = server;
    }

    /**
     * Function called by button when clicked. Switches to main admin panel.
     * @param event passed by JavaFX by default
     */
    public void switchToAdminPanel(ActionEvent event){
        mainCtrl.showAdmin();
    }
}
