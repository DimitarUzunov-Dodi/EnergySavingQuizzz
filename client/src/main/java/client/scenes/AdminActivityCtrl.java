package client.scenes;

import client.communication.AdminCommunication;
import client.utils.ActivityBankUtils;
import client.utils.CorruptImageException;
import client.utils.ImageNotSupportedException;
import com.google.inject.Inject;
import commons.Activity;
import commons.ActivityImage;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import static client.scenes.UserAlert.userAlert;
import static client.utils.ActivityImageUtils.imageToByteArray;

public class AdminActivityCtrl implements Initializable {
    private final MainCtrl mainCtrl;
    private final AdminCommunication server;

    private ObservableList <Activity> data;

    @FXML
    private TableView<Activity> activityTable;
    @FXML
    private TableColumn<Activity, String> colActivityId;
    @FXML
    private TableColumn<Activity, String> colActivityText;
    @FXML
    private TableColumn<Activity, String> colActivityValue;

    @Inject
    public AdminActivityCtrl(MainCtrl mainCtrl, AdminCommunication server) {
        this.mainCtrl = mainCtrl;
        this.server = server;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colActivityId.setCellValueFactory(q -> new SimpleStringProperty(q.getValue().getActivityId()+""));
        colActivityText.setCellValueFactory(q -> new SimpleStringProperty(q.getValue().getActivityText()));
        colActivityValue.setCellValueFactory(q -> new SimpleStringProperty(q.getValue().getValue()+""));
    }

    /**
     * Function called by button when clicked. Switches to main admin panel.
     * @param event passed by JavaFX by default
     */
    public void switchToAdminPanel(ActionEvent event){
        mainCtrl.showAdmin();
    }

    /**
     * Method refreshes the list of activities in admin panel
     */
    public void refresh() {
        new Thread(() -> {
            System.out.println("Refreshing activities table...");
            try {
                data = FXCollections.observableList(AdminCommunication.getAllActivities());
                activityTable.setItems(data);
                System.out.println("Populated table with "+data.size()+" entries.");
            } catch (RuntimeException e) {
                userAlert("ERROR", "Connection failed", "Client was unable to connect to the server");
            }
        }).start();
    }

    /**
     * FOR MANUAL TESTING PURPOSES ONLY
     * (It's still available in the admin panel)
     * Adds a dummy activity with fixed values:
     *
     */
    public void add() {
        AdminCommunication.addTestingActivity();
        addImage();
    }

    /**
     * Deletes all available activities on the server
     */
    public void deleteAllActivities() {
        Stage thisStage = (Stage) activityTable.getScene().getWindow();
        Alert quitAlert = new Alert(Alert.AlertType.CONFIRMATION);
        quitAlert.setTitle("Quit");
        quitAlert.setHeaderText("Are you sure you want to delete all activities?");
        Optional<ButtonType> result = quitAlert.showAndWait();
        if(result.get() == ButtonType.OK) {
            try {
                AdminCommunication.deleteActivities();
            } catch (RuntimeException e) {
                userAlert("ERROR", "Connection failed", "Client was unable to connect to the server");
            }
        }
    }

    /**
     * Switches a window to editing mode, so you can change activity details
     */
    public void edit() {
        Activity selected = activityTable.getSelectionModel().getSelectedItem();
        mainCtrl.showAdminActivityDetails(selected);
    }

    /**
     * Loads activities from the archive based on jsons there
     */
    public void load(){
        new Thread(() -> {
            System.out.println("Loading activities...");
            try {
                ActivityBankUtils.unzipActivityBank();
            } catch (IOException exception) {
                exception.printStackTrace();
                // userAlert("ERROR", "Unable to load archive", "Error occurred while trying to read an archive");
            }
            try {
                ActivityBankUtils.jsonToActivityBankEntry();
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }).start();
    }

    // Temporary code
    public void addImage() {
        new Thread(() -> {
            try {
                String path = "C:/Users/Lenovo/Desktop/oopp/repository-template/client/src/main/Data/unzipped/01/ipad.jpg";
                AdminCommunication.addActivityImage(
                        new ActivityImage(0, imageToByteArray(path)));
            } catch (IOException | ImageNotSupportedException | CorruptImageException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
