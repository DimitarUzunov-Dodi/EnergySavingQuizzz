package client.scenes;

import client.communication.AdminCommunication;
import com.google.inject.Inject;
import commons.Activity;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminQuestionsCtrl implements Initializable {
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
    public AdminQuestionsCtrl(MainCtrl mainCtrl, AdminCommunication server) {
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
     * Method refreshes the activity list available in admin panel
     */
    public void refresh() {
        new Thread(() -> {
            System.out.println("Refreshing activities table...");
            data = FXCollections.observableList(AdminCommunication.getAllActivities());
            activityTable.setItems(data);
            System.out.println("Populated table with "+data.size()+" entries.");
        }).start();
    }

    public void add() {
        AdminCommunication.addTestingActivity();
    }

    public void deleteAllActivities() {
        AdminCommunication.deleteActivities();
    }

    public void edit() {

    }
}
