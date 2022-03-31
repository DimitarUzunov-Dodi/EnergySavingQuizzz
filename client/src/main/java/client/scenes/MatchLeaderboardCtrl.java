package client.scenes;

import static client.scenes.MainCtrl.currentGameID;
import static client.scenes.MainCtrl.scheduler;

import client.MyFXML;
import client.communication.Utils;
import client.utils.SceneController;
import com.google.inject.Inject;
import commons.User;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ScheduledFuture;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.text.Text;

public class MatchLeaderboardCtrl extends SceneController {

    private final ObservableList<User> data;
    private boolean first; // check for initial table setup

    @FXML
    private TableView<User> table;
    @FXML
    private TableColumn<User, String> colUsername;
    @FXML
    private TableColumn<User, String> colScore;
    @FXML
    private ProgressBar progressBar;
    @FXML
    private Text readyText;

    /**
     * Basic constructor.
     * @param myFxml handled by INJECTOR
     */
    @Inject
    protected MatchLeaderboardCtrl(MyFXML myFxml) {
        super(myFxml);
        data = FXCollections.observableList(new ArrayList<>());
        first = true;
    }

    /**
     * Use show(Object... args) instead!
     */
    @Override
    public void show() {
        throw new IllegalArgumentException("Missing variadic argument: <instant>");
    }

    /**
     * Shows the match leaderboard until the specified timestamp,
     *      after which it transitions back to GameScreen.
     * @param args Takes only 1 argument of type `Instant` that
     *      represents the moment of transition to GameScreen
     */
    @Override
    public void show(Object... args) throws IllegalArgumentException {
        // initial setup (only done once)
        if (first) {
            setUp();
        }

        // handle args
        if (args.length != 1 || args[0].getClass() != Instant.class) {
            throw new IllegalArgumentException("only ONE argument of type `Instant` is accepted");
        }
        if (((Instant) args[0]).isBefore(Instant.now())) {
            throw new IllegalArgumentException("args[0] may not represent a past moment");
        }
        final Instant endTime = (Instant) args[0];

        // get player list from server
        scheduler.execute(() -> {
            Optional<List<User>> l = Utils.getAllUsers(currentGameID);
            if (l.isPresent()) {
                data.clear();
                data.addAll(l.get());
            }
        });

        // progress bar
        progressBar.setProgress(1d);
        ScheduledFuture<?> barTask = SceneController.scheduleProgressBar(progressBar, endTime);

        // show scene
        table.setVisible(true);
        readyText.setVisible(false);
        present();

        // transition screen
        scheduler.scheduleAtInstant(() -> {
            readyText.setVisible(true);
            table.setVisible(false);
        }, endTime.minusSeconds(2));

        // back to GameScreen
        scheduler.scheduleAtInstant(() -> {
            barTask.cancel(false); // stop progressBar
            Platform.runLater(() -> myFxml.showScene(GameScreenCtrl.class));
        }, endTime);
    }

    /**
     * Set up the table.
     * Only needs to be called once.
     */
    private void setUp() {
        colUsername.setCellValueFactory(q -> new SimpleStringProperty(q.getValue().getUsername()));
        colScore.setCellValueFactory(q -> new SimpleStringProperty(
                q.getValue().getScore() + " points"));
        table.setItems(data);
        first = false;
    }
}
