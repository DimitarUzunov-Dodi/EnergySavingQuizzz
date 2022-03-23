package client.scenes;

import client.MyFXML;
import client.utils.SceneController;
import com.google.inject.Inject;

import client.utils.ServerUtils;

import commons.ScoreRecord;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.ArrayList;

import static client.scenes.MainCtrl.currentGameID;

public class MatchLeaderboardCtrl extends SceneController {

    private final ObservableList<ScoreRecord> data;

    @FXML
    private TableView<ScoreRecord> table;
    @FXML
    private TableColumn<ScoreRecord, String> colUsername;
    @FXML
    private TableColumn<ScoreRecord, String> colScore;

    /**
     * Basic constructor
     * @param myFXML handled by INJECTOR
     */
    @Inject
    protected MatchLeaderboardCtrl(MyFXML myFXML) {
        super(myFXML);
        colUsername.setCellValueFactory(q -> new SimpleStringProperty(q.getValue().nickname));
        colScore.setCellValueFactory(q -> new SimpleStringProperty(q.getValue().score+" points"));
        data = FXCollections.observableList(new ArrayList<>());
        table.setItems(data);
    }

    @Override
    public void show() {
        new Thread(() -> {
            var l = ServerUtils.getMatchLeaderboard(currentGameID);
            if(l == null)
                System.out.println("WARNING: null ScoreRecord list fetched from the server");
            else {
                data.removeAll();
                data.addAll(l);
                table.refresh();
            }
        }).start();
        showScene();
    }
}
