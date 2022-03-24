package client.scenes;

import client.MyFXML;
import client.communication.AdminCommunication;
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
import java.util.List;

import static client.utils.UserAlert.userAlert;

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
            try {
                List<LeaderboardEntry> list = ServerUtils.getServerLeaderboard();
                data.removeAll();
                data.addAll(list);
                table.refresh(); // might be removed
            } catch (Exception ex) {
                ex.printStackTrace();
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
