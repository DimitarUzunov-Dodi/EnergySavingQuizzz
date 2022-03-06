package client.scenes;

import java.net.URL;
import java.util.ResourceBundle;

import com.google.inject.Inject;

import client.utils.ServerUtils;
import commons.LeaderboardEntry;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
    }

    public void refresh() {
        new Thread(() -> {
            System.out.println("Refreshing server leaderboard table...");
            data = FXCollections.observableList(server.getServerLeaderboard());
            table.setItems(data);
            System.out.println("Populated table with "+data.size()+" entries.");
        }).start();
    }

    public void onBackButton() {
        mainCtrl.showSplashScreen();
    }
}
