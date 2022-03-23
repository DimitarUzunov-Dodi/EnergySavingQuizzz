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

public class ServerLeaderboardCtrl extends SceneController {

    private final ServerUtils serverUtil;
	private ObservableList<LeaderboardEntry> data;

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
     * @param serverUtil handled by INJECTOR
     */
    private ServerLeaderboardCtrl(MyFXML myFXML, ServerUtils serverUtil) {
        super(myFXML);
        this.serverUtil = serverUtil;
        colUsername.setCellValueFactory(q -> new SimpleStringProperty(q.getValue().username));
        colGamesPlayed.setCellValueFactory(q -> new SimpleStringProperty(q.getValue().gamesPlayed.toString()+" games played"));
        colScore.setCellValueFactory(q -> new SimpleStringProperty(q.getValue().score.toString()+" points"));
    }

    @Override
    public void show() {
        // load contents async
        new Thread(() -> {
            data = FXCollections.observableList(serverUtil.getServerLeaderboard());
            table.setItems(data);
        }).start();
        showScene();
    }

    /**
     * Called on user pressing 'Back' button, sends user to Splash
     */
    protected void onBackButton() {
        myFXML.showScene(SplashCtrl.class);
    }
}
