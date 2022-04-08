package client.scenes;

import static client.utils.UserAlert.userAlert;

import client.MyFXML;
import client.communication.AdminCommunication;
import client.utils.ActivityBankUtils;
import client.utils.SceneController;
import com.google.inject.Inject;
import commons.Activity;
import java.io.IOException;
import java.util.Optional;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class AdminActivityCtrl extends SceneController {

    private ObservableList<Activity> data;

    @FXML
    private TableView<Activity> activityTable;
    @FXML
    private TableColumn<Activity, String> colActivityId;
    @FXML
    private TableColumn<Activity, String> colActivityText;
    @FXML
    private TableColumn<Activity, String> colActivityValue;

    @Inject
    private AdminActivityCtrl(MyFXML myFxml) {
        super(myFxml);
    }

    @Override
    public void show() {

        colActivityId.setCellValueFactory(
                q -> new SimpleStringProperty(q.getValue().getActivityId() + "")
        );
        colActivityText.setCellValueFactory(
                q -> new SimpleStringProperty(q.getValue().getActivityText())
        );
        colActivityValue.setCellValueFactory(
                q -> new SimpleStringProperty(q.getValue().getValue() + "")
        );
        refresh();
        present();
    }

    /**
     * Function called by button when clicked. Switches to main admin panel.
     * @param event passed by JavaFX by default
     */
    @FXML
    private void switchToAdminPanel(ActionEvent event) {
        myFxml.showScene(AdminCtrl.class);
    }

    /**
     * Method refreshes the list of activities in admin panel.
     */
    @FXML
    private void refresh() {
        new Thread(() -> {
            System.out.println("Refreshing activities table...");
            try {
                data = FXCollections.observableList(AdminCommunication.getAllActivities());
                Platform.runLater(() -> activityTable.setItems(data));
                System.out.println("Populated table with " + data.size() + " entries.");
            } catch (RuntimeException e) {
                Platform.runLater(
                        () -> userAlert(
                                "ERROR",
                                "Connection failed",
                                "Client was unable to connect to the server"));
            }
        }).start();
    }

    /**
     * Deletes all available activities on the server.
     */
    @FXML
    private void deleteAllActivities() {
        Alert quitAlert = new Alert(Alert.AlertType.CONFIRMATION);
        quitAlert.setTitle("Quit");
        quitAlert.setHeaderText("Are you sure you want to delete all activities?");
        Optional<ButtonType> result = quitAlert.showAndWait();
        if (result.get() == ButtonType.OK) {
            try {
                new Thread(AdminCommunication::deleteActivities).start();
            } catch (RuntimeException e) {
                Platform.runLater(
                        () -> userAlert(
                                "ERROR",
                                "Connection failed",
                                "Client was unable to connect to the server"));
            }
        }
    }

    /**
     * Switches a window to editing mode, so you can change activity details.
     */
    @FXML
    private void edit() {
        Activity selected = activityTable.getSelectionModel().getSelectedItem();
        myFxml.get(AdminActivityDetailsCtrl.class).customShow(selected);
    }

    /**
     * Switches a window to imageview mode, so you can check the image associated with activity.
     */
    @FXML
    private void image() {
        Activity selected = activityTable.getSelectionModel().getSelectedItem();
        myFxml.get(ActivityImageCtrl.class).customShow(selected);
    }

    /**
     * Loads activities from the archive based on json there.
     */
    @FXML
    private void load() {
        new Thread(() -> {
            System.out.println("Loading activities...");
            try {
                ActivityBankUtils.unzipActivityBank();
            } catch (IOException exception) {
                exception.printStackTrace();
                Platform.runLater(
                        () -> userAlert(
                            "ERROR",
                            "Unable to load archive",
                            "Error occurred while trying to read an archive"));
            }
            try {
                ActivityBankUtils.jsonToActivityBankEntry();
            } catch (IOException exception) {
                Platform.runLater(
                        () -> userAlert(
                                "ERROR",
                                "Unable to load data from json",
                                "Error occurred while trying to read a json"));
                exception.printStackTrace();
            }
        }).start();
    }
}
