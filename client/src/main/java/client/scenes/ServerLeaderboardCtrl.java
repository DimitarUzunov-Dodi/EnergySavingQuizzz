package client.scenes;

import java.net.URL;
import java.util.ResourceBundle;

import com.google.inject.Inject;

import client.utils.ServerUtils;
import commons.LeaderboardEntry;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class ServerLeaderboardCtrl implements Initializable {

    private final MainCtrl mainCtrl;
	private final ServerUtils server;

	private ObservableList<LeaderboardEntry> data;

    @FXML
    private TableView<LeaderboardEntry> table;
    @FXML
    private TableColumn<LeaderboardEntry, String> colUsername;
    @FXML
    private TableColumn<LeaderboardEntry, String> colGamesPlayed;
    @FXML
    private TableColumn<LeaderboardEntry, String> colScore;

    @Inject
    public ServerLeaderboardCtrl(MainCtrl mainCtrl, ServerUtils server) {
        this.mainCtrl = mainCtrl;
        this.server = server;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colUsername.setCellValueFactory(q -> new SimpleStringProperty(q.getValue().username));
        colGamesPlayed.setCellValueFactory(q -> new SimpleStringProperty(q.getValue().gamesPlayed.toString()+" games played"));
        colScore.setCellValueFactory(q -> new SimpleStringProperty(q.getValue().score.toString()+" points"));
		refresh();
    }

    public void refresh() {
        // TODO: implement server.getLeaderboard();
        System.out.println("Refreshing server leaderboard table...");
		data = FXCollections.observableList(server.getLeaderboard());
		table.setItems(data);
    }

    public void onBackButton() {
        //mainCtrl.showMultiplayerScreen();
        System.out.println("User pressed the back button.");
    }
}
