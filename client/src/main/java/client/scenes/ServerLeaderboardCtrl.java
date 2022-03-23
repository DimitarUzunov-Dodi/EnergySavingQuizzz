package client.scenes;

import client.MyFXML;
import client.utils.SceneController;

import client.utils.ServerUtils;
import commons.LeaderboardEntry;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.ArrayList;

public class ServerLeaderboardCtrl extends SceneController {

	private final ObservableList<LeaderboardEntry> data;

    @FXML
    private TableView<LeaderboardEntry> table;
    @FXML
    private TableColumn<LeaderboardEntry, String> colUsername;
    @FXML
    private TableColumn<LeaderboardEntry, String> colGamesPlayed;
    @FXML
    private TableColumn<LeaderboardEntry, String> colScore;

    /**
     * Constructor used by INJECTOR
     * @param myFXML handled by INJECTOR
     */
    private ServerLeaderboardCtrl(MyFXML myFXML) {
        super(myFXML);
        colUsername.setCellValueFactory(q -> new SimpleStringProperty(q.getValue().username));
        colGamesPlayed.setCellValueFactory(q -> new SimpleStringProperty(q.getValue().gamesPlayed.toString()+" games played"));
        colScore.setCellValueFactory(q -> new SimpleStringProperty(q.getValue().score.toString()+" points"));
        data = FXCollections.observableList(new ArrayList<>());
        table.setItems(data);
    }

    @Override
    public void show() {
        // load contents async
        new Thread(() -> {
            var l = ServerUtils.getServerLeaderboard();
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

    /**
     * Called on user pressing 'Back' button, sends user to Splash
     */
    @FXML
    private void onBackButton() {
        myFXML.showScene(SplashCtrl.class);
    }
}
