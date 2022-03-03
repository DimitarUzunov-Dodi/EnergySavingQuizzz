package client.scenes;

import com.google.inject.Inject;
import commons.ServerLeaderboardEntry;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

public class ServerLeaderboardCtrl implements Initializable {

    private final MainCtrl mainCtrl;

    @FXML
    private TableView<ServerLeaderboardEntry> table;
    @FXML
    private TableColumn<ServerLeaderboardEntry, String> colUsername;
    @FXML
    private TableColumn<ServerLeaderboardEntry, String> colGamesPlayed;
    @FXML
    private TableColumn<ServerLeaderboardEntry, String> colScore;

    @Inject
    public ServerLeaderboardCtrl(MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colUsername.setCellValueFactory(q -> new SimpleStringProperty(q.getValue().username));
        colGamesPlayed.setCellValueFactory(q -> new SimpleStringProperty(q.getValue().gamesPlayed.toString()+" games played"));
        colScore.setCellValueFactory(q -> new SimpleStringProperty(q.getValue().score.toString()+" points"));
    }

    public void refresh() {
        // TODO: refresh table data
        System.out.println("Refreshing table...");
    }

    public void onBackButton() {
        //mainCtrl.showSplash();
        System.out.println("User pressed the back button.");
    }
}
