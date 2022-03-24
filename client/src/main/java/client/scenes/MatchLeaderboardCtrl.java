package client.scenes;

import static client.scenes.MainCtrl.currentGameID;

import client.MyFXML;
import client.utils.SceneController;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.ScoreRecord;
import java.util.ArrayList;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class MatchLeaderboardCtrl extends SceneController {

    private final ObservableList<ScoreRecord> data;

    @FXML
    private TableView<ScoreRecord> table;
    @FXML
    private TableColumn<ScoreRecord, String> colUsername;
    @FXML
    private TableColumn<ScoreRecord, String> colScore;

    /**
     * Basic constructor.
     * @param myFxml handled by INJECTOR
     */
    @Inject
    protected MatchLeaderboardCtrl(MyFXML myFxml) {
        super(myFxml);
        colUsername.setCellValueFactory(q -> new SimpleStringProperty(q.getValue().nickname));
        colScore.setCellValueFactory(q -> new SimpleStringProperty(q.getValue().score + " points"));
        data = FXCollections.observableList(new ArrayList<>());
        table.setItems(data);
    }

    @Override
    public void show() {
        new Thread(() -> {
            var l = ServerUtils.getMatchLeaderboard(currentGameID);
            if (l == null) {
                System.out.println("WARNING: null ScoreRecord list fetched from the server");
            } else {
                data.removeAll();
                data.addAll(l);
                table.refresh();
            }
        }).start();
        showScene();
    }
}
