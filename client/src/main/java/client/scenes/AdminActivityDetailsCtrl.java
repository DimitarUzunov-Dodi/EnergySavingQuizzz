package client.scenes;

import static client.utils.UserAlert.userAlert;

import client.MyFXML;
import client.communication.AdminCommunication;
import client.utils.FileUtils;
import client.utils.SceneController;
import com.google.inject.Inject;
import commons.Activity;
import java.util.Objects;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class AdminActivityDetailsCtrl extends SceneController {
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
    public AdminActivityDetailsCtrl(MyFXML myFxml) {
        super(myFxml);
    }

    /**
     * Please use customShow(Activity selected) method to switch to this scene.
     */
    @Override
    public void show() {
        scene.getStylesheets().removeAll();
        scene.getStylesheets().add(FileUtils.getTheme());
    }

    /**
     * Method that shows the scene with details about selected activity.
     * @param selected activity which details will be shown
     */
    public void customShow(Activity selected) {
        setActivity(selected);
        showScene();
    }

    /**
     * Function called by button when clicked. Switches to main admin question panel.
     * @param event passed by JavaFX by default
     */
    @FXML
    private void switchToActivityPanel(ActionEvent event) {
        myFxml.showScene(AdminActivityCtrl.class);
    }

    /**
     * Set selected activity.
     * @param selected Activity that was selected in table view earlier
     */
    private void setActivity(Activity selected) throws NullPointerException {
        if (!Objects.isNull(selected)) {
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
     * Function called when confirm button pressed.
     * It checks if activity was changed and if so sends a PUT request to the server
     * to change and existing record of that activity
     */
    @FXML
    private void confirmAction(ActionEvent event) {
        Activity constructed;
        try {
            constructed = new Activity(
                    Long.parseLong(idField.getText()),
                    activityTextField.getText(),
                    Long.parseLong(valueField.getText()),
                    sourceField.getText(),
                    Long.parseLong(imageIdField.getText())
            );
        } catch (NumberFormatException exception) {
            userAlert(
                    "ERROR",
                    "Bad format",
                    "Text fields with numbers are inputted incorrectly.");
            return;
        }
        if (!this.selectedActivity.equals(constructed)) {
            try {
                AdminCommunication.editActivity(selectedActivity.getActivityId(), constructed);
            } catch (RuntimeException e) {
                userAlert(
                        "ERROR",
                        "Connection failed",
                        "Client was unable to connect to the server");
                return;
            }
        }
        myFxml.showScene(AdminActivityCtrl.class);
    }
}
