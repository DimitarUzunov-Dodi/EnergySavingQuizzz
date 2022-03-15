package client.scenes;

import client.communication.AdminCommunication;
import com.google.inject.Inject;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

public class ViewQuestionCtrl implements Initializable {
    private final MainCtrl mainCtrl;
    private final AdminCommunication server;

    @FXML
    private TextField idField;
    @FXML
    private TextField activityTextField;
    @FXML
    private TextField valueField;
    @FXML
    private TextField sourceField;
    @FXML
    private TextField imageIdField;

    @Inject
    public ViewQuestionCtrl(MainCtrl mainCtrl, AdminCommunication server) {
        this.mainCtrl = mainCtrl;
        this.server = server;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    /**
     * Function called by button when clicked. Switches to main admin question panel.
     * @param event passed by JavaFX by default
     */
    public void switchToQuestionPanel(ActionEvent event){
        mainCtrl.showAdminQuestionPanel();
    }
}
