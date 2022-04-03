package client.scenes;

import client.MyFXML;
import client.communication.LeaderboardCommunication;
import client.utils.SceneController;
import com.google.inject.Inject;
import commons.ServerLeaderboardEntry;
import java.util.Comparator;
import java.util.List;
import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class ServerLeaderboardCtrl extends SceneController {

    private ObservableList<ServerLeaderboardEntry> data;

    @FXML
    private TableView<ServerLeaderboardEntry> table;
    @FXML
    private TableColumn<ServerLeaderboardEntry, String> colUsername;
    @FXML
    private TableColumn<ServerLeaderboardEntry, Integer> colGamesPlayed;
    @FXML
    private TableColumn<ServerLeaderboardEntry, Integer> colScore;

    /**
     * Constructor used by INJECTOR.
     * @param myFxml handled by INJECTOR
     */
    @Inject
    private ServerLeaderboardCtrl(MyFXML myFxml) {
        super(myFxml);
    }

    @Override
    public void show() {
        // load contents async
        new Thread(() -> {
            try {
                List<ServerLeaderboardEntry> list = LeaderboardCommunication.getServerLeaderboard();
                Platform.runLater(() -> {
                    colUsername.setCellValueFactory(q -> new SimpleStringProperty(
                            q.getValue().username));
                    colGamesPlayed.setCellValueFactory(q -> new SimpleIntegerProperty(
                            q.getValue().gamesPlayed).asObject());
                    colScore.setCellValueFactory(q -> new SimpleIntegerProperty(
                            q.getValue().score).asObject());
                    list.sort(Comparator.comparing(ServerLeaderboardEntry::getScore).reversed());
                    data = FXCollections.observableList(list);
                    table.setItems(data);
                });
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }).start();
        present();
    }

    /**
     * Called on user pressing 'Back' button, sends user to Splash.
     */
    @FXML
    private void onBackButton() {
        myFxml.showScene(SplashCtrl.class);
    }
}
