package client.scenes;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.google.inject.Inject;

import client.utils.ServerUtils;
import commons.LeaderboardEntry;

import commons.ScoreRecord;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

public class TransitionScreenCtrl implements Initializable {

    private final MainCtrl mainCtrl;
    private final ServerUtils server;

    private ObservableList<ScoreRecord> data;

    @FXML
    private ListView<ScoreRecord> list;

    @Inject
    public TransitionScreenCtrl(MainCtrl mainCtrl, ServerUtils server) {
        this.mainCtrl = mainCtrl;
        this.server = server;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        list.setCellFactory(new cellFactory());
        list.getItems().add(new ScoreRecord("name", 20));
    }

    public void refresh(String gameCode) {
        new Thread(() -> {
            List<ScoreRecord> scores = server.getMatchLeaderboard(gameCode);
            if(scores == null)
                System.err.println("Could not refresh match leaderboard!");
            else {
                data = FXCollections.observableList(scores);
                list.setItems(data);
                list.refresh();
            }
        }).start();
    }

    // TODO: Use a table view instead
    private static class cellFactory implements Callback<ListView<ScoreRecord>, ListCell<ScoreRecord>> {
        @Override
        public ListCell<ScoreRecord> call(ListView<ScoreRecord> param) {
            return new ListCell<ScoreRecord>(){
                @Override
                public void updateItem(ScoreRecord entry, boolean empty) {
                    super.updateItem(entry, empty);
                    if(!empty) {
                        setText(entry.nickname+"\t\t\t"+entry.score);
                    }
                }
            };
        }
    }
}
