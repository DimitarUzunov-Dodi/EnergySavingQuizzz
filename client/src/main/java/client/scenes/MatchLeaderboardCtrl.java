package client.scenes;

import static client.scenes.MainCtrl.currentGameID;

import client.Main;
import client.MyFXML;
import client.communication.WaitingRoomCommunication;
import client.utils.FileUtils;
import client.utils.SceneController;
import com.google.inject.Inject;
import commons.User;
import java.util.ArrayList;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.text.Text;

public class MatchLeaderboardCtrl extends SceneController {

    private final ObservableList<User> data;

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
    }

    /**
     * Shows the match leaderboard for 2 seconds and then switches back to GameScreen.
     * The transition at the end does NOT call GameScreenCtrl.show() !
     */
    @Override
    public void show() {
        scene.getStylesheets().removeAll();
        scene.getStylesheets().add(FileUtils.getTheme());
        new Thread(() -> {
            System.out.println(">>>" + currentGameID);
            var l = WaitingRoomCommunication.getAllUsers(currentGameID);
            if (l != null) {
                data.remove(0, data.size());
                data.addAll(l);
            }
        }).start();

        colUsername.setCellValueFactory(q -> new SimpleStringProperty(q.getValue().getUsername()));
        colScore.setCellValueFactory(q -> new SimpleStringProperty(
                q.getValue().getScore() + " points"));
        table.setItems(data);

        table.setVisible(true);
        readyText.setVisible(false);
        countDown();

        showScene();
    }

    private void countDown() {
        final Service<Integer> countDownThread = new Service<>() {
            @Override
            protected Task<Integer> createTask() {
                return new Task<Integer>() {
                    @Override
                    protected Integer call() {
                        int i;
                        for (i = 0; i < 225; i++) {
                            updateProgress(225 - i, 225);
                            if (i == 170) {
                                readyText.setVisible(true);
                                table.setVisible(false);
                            }
                            try {
                                Thread.sleep(16);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        return i;
                    }
                };
            }
        };

        // Next Round Transition
        countDownThread.setOnSucceeded(event -> {
            Main.primaryStage.setScene(myFxml.get(GameScreenCtrl.class).getScene());
        });

        // bind thread to progress bar and start it
        progressBar.progressProperty().bind(countDownThread.progressProperty());
        countDownThread.start();
    }

}
