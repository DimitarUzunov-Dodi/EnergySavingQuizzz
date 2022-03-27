package client.scenes;

import client.MyFXML;
import client.utils.FileUtils;
import client.utils.SceneController;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.ServerLeaderboardEntry;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class ServerLeaderboardCtrl extends SceneController {

    private final ObservableList<ServerLeaderboardEntry> data;

    @FXML
    private TableView<ServerLeaderboardEntry> table;
    @FXML
    private TableColumn<ServerLeaderboardEntry, String> colUsername;
    @FXML
    private TableColumn<ServerLeaderboardEntry, String> colGamesPlayed;
    @FXML
    private TableColumn<ServerLeaderboardEntry, String> colScore;

    /**
     * Constructor used by INJECTOR.
     * @param myFxml handled by INJECTOR
     */
    @Inject
    private ServerLeaderboardCtrl(MyFXML myFxml) {
        super(myFxml);
        colUsername.setCellValueFactory(q -> new SimpleStringProperty(q.getValue().username));
        colGamesPlayed.setCellValueFactory(q -> new SimpleStringProperty(
                q.getValue().gamesPlayed.toString() + " games played"));
        colScore.setCellValueFactory(q -> new SimpleStringProperty(
                q.getValue().score.toString() + " points"));
        data = FXCollections.observableList(new ArrayList<>());
        table.setItems(data);
    }

    @Override
    public void show() {
        scene.getStylesheets().removeAll();
        scene.getStylesheets().add(FileUtils.getTheme());
        // load contents async
        new Thread(() -> {
            try {
                List<ServerLeaderboardEntry> list = ServerUtils.getServerLeaderboard();
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
     * Called on user pressing 'Back' button, sends user to Splash.
     */
    @FXML
    private void onBackButton() {
        myFxml.showScene(SplashCtrl.class);
    }
}
