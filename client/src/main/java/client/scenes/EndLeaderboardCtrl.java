package client.scenes;

import static client.scenes.MainCtrl.currentGameID;
import static client.scenes.MainCtrl.scheduler;
import static client.scenes.MainCtrl.username;
import static client.utils.UserAlert.userAlert;

import client.MyFXML;
import client.communication.CommunicationUtils;
import client.communication.GameCommunication;
import client.communication.LeaderboardCommunication;
import client.communication.WaitingRoomCommunication;
import client.utils.SceneController;
import client.utils.UserAlert;
import com.google.inject.Inject;
import commons.User;
import jakarta.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Displays the final screen at the end of the game.
 */
public class EndLeaderboardCtrl extends SceneController {

    @FXML
    private BarChart<String, Integer> chart;
    @FXML
    private ImageView backButtonImg;

    /**
     * Constructor used by INJECTOR.
     * @param myFxml handled by INJECTOR
     */
    @Inject
    protected EndLeaderboardCtrl(MyFXML myFxml) {
        super(myFxml);
    }

    /**
     * Loads the Screen with the data of the match.
     */
    @Override
    public void show() {
        cleanGraph();
        backButtonImg.setImage(new Image(("client/images/exit_icon.jpg")));
        scheduler.execute(() -> {
            Optional<List<User>> l = CommunicationUtils.getAllUsers(currentGameID);

            if (l.isPresent()) {
                //Sets the graph smaller if it is one player playing
                if (l.get().size() == 1) {
                    chart.lookup(".chart").setStyle("-fx-max-width: 60");
                }
                //Sorts the list for a proper display
                User[] array = l.get().toArray(new User[l.get().size()]);
                ArrayList<User> sortedList = new ArrayList<>(Arrays.asList(array));
                List<User> newList = sortedList.stream()
                        .sorted(Comparator.comparingInt(User::getScore))
                        .collect(Collectors.toList());
                Collections.reverse(newList);

                var series = new XYChart.Series<String, Integer>();
                series.setName("Points");
                newList.forEach(user -> {
                    var data = new XYChart.Data<>(user.getUsername(), user.getScore());
                    series.getData().add(data);
                });
                Platform.runLater(() -> chart.getData().add(series));
                GameCommunication.endGame(currentGameID); // end current game
            }
        });
        scheduler.execute(() -> LeaderboardCommunication.updateServerLeaderboard(username, 1,
                CommunicationUtils.getAllUsers(currentGameID)
                        .orElse(new ArrayList<>(0))
                        .stream()
                        .filter(o -> ((User)o).getUsername().equals(username))
                        .collect(Collectors.toList())
                        .get(0).getScore()));
        present();
    }

    /**
     * Send a user to a new public waiting room.
     */
    @FXML
    private void nextGame() {
        cleanGraph();
        try {
            currentGameID = WaitingRoomCommunication.getPublicCode();
        } catch (RuntimeException e) {
            e.printStackTrace();
            UserAlert.userAlert("WARN", "Cannot connect to server",
                    "Check your connection and try again.");
        }
        try {
            joinGame();
        } catch (RuntimeException e) {
            e.printStackTrace();
            UserAlert.userAlert("WARN", "Cannot connect to server",
                    "Check your connection and try again.");
        }
    }

    private void joinGame() throws RuntimeException {
        Response joinResponse = WaitingRoomCommunication.joinGame(currentGameID, username);
        int statusCode = joinResponse.getStatus();
        if (statusCode == 200) {
            myFxml.showScene(WaitingRoomCtrl.class);
        } else if (statusCode == 400) {
            userAlert(
                    "ERROR",
                    "Username is already taken",
                    "Username already in use in this game!");
        } else if (statusCode == 404 || statusCode == 418) {
            Alert quitAlert = new Alert(Alert.AlertType.CONFIRMATION);
            quitAlert.setTitle("Oops");
            quitAlert.setHeaderText(
                    "It is no longer possible to join this room. "
                            + "Would you like to join a public game?");
            Optional<ButtonType> result = quitAlert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                nextGame();
            }
        }
    }

    /**
     * returns back to SplashScreen.
     */
    @FXML
    private void onBackButton() {
        myFxml.showScene(SplashCtrl.class);
    }

    /**
     * fixes the data in on the graph.
     */
    private void cleanGraph() {
        chart.lookup(".chart").setStyle("-fx-max-width: 10000");
        chart.getData().clear();
    }
}
