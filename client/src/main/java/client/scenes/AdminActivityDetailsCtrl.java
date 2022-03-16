package client.scenes;

import client.communication.AdminCommunication;
import com.google.inject.Inject;
import commons.Activity;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

import static client.scenes.UserAlert.userAlert;

public class AdminActivityDetailsCtrl implements Initializable {
    private final MainCtrl mainCtrl;
    private final AdminCommunication server;
    private Activity selectedActivity;

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
    public AdminActivityDetailsCtrl(MainCtrl mainCtrl, AdminCommunication server) {
        this.mainCtrl = mainCtrl;
        this.server = server;
        this.selectedActivity = null;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    /**
     * Function called by button when clicked. Switches to main admin question panel.
     * @param event passed by JavaFX by default
     */
    public void switchToActivityPanel(ActionEvent event){
        mainCtrl.showAdminActivityPanel();
    }

    /**
     * Set selected activity
     * @param selected Activity that was selected in table view earlier
     */
    public void setActivity(Activity selected) throws NullPointerException{
        if(!Objects.isNull(selected)) {
            this.selectedActivity = selected;
            idField.setText(selected.getActivityId() + "");
            activityTextField.setText(selected.getActivityText());
            valueField.setText(selected.getValue() + "");
            sourceField.setText(selected.getSource());
            imageIdField.setText(selected.getImageId() + "");
        } else {
            throw new NullPointerException();
        }
    }

    /**
     * Function called when confirm button pressed
     */
    public void confirmAction(ActionEvent e) {
        Activity constructed;
        try {
            constructed = new Activity(Long.parseLong(idField.getText()), activityTextField.getText(), Integer.parseInt(valueField.getText()), sourceField.getText(), Long.parseLong(imageIdField.getText()));
        } catch (NumberFormatException exception){
            userAlert("ERROR", "Bad format", "Text fields with numbers are inputted incorrectly.");
            return;
        }
        if(this.selectedActivity.equals(constructed)) {
            mainCtrl.showAdminActivityPanel();
        } else {
            AdminCommunication.editActivity(selectedActivity.getActivityId(), constructed);
            mainCtrl.showAdminActivityPanel();
        }
    }
}
