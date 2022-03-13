package client.scenes;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.google.inject.Inject;

import client.utils.ServerUtils;

import commons.LeaderboardEntry;
import commons.ScoreRecord;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;

public class MatchLeaderboardCtrl implements Initializable {

    private final MainCtrl mainCtrl;
    private final ServerUtils server;

    private ObservableList<ScoreRecord> data;

    @FXML
    private TableView<ScoreRecord> table;
    @FXML
    private TableColumn<ScoreRecord, String> colUsername;
    @FXML
    private TableColumn<ScoreRecord, String> colScore;

    @Inject
    public MatchLeaderboardCtrl(MainCtrl mainCtrl, ServerUtils server) {
        this.mainCtrl = mainCtrl;
        this.server = server;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colUsername.setCellValueFactory(q -> new SimpleStringProperty(q.getValue().nickname));
        colScore.setCellValueFactory(q -> new SimpleStringProperty(q.getValue().score+" points"));
    }

    public void refresh(String gameCode) {
        new Thread(() -> {
            var data = server.getMatchLeaderboard(gameCode);
            if(data != null)
                table.setItems(FXCollections.observableList(data));
        }).start();
    }
}
