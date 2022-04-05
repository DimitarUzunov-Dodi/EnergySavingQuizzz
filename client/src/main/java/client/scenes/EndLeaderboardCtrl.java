package client.scenes;

import static client.scenes.MainCtrl.currentGameID;
import static client.scenes.MainCtrl.scheduler;
import static client.scenes.MainCtrl.username;
import static client.utils.UserAlert.userAlert;

import client.MyFXML;
import client.communication.CommunicationUtils;
import client.communication.GameCommunication;
import client.communication.WaitingRoomCommunication;
import client.utils.SceneController;
import client.utils.UserAlert;
import com.google.inject.Inject;
import commons.User;
import jakarta.ws.rs.core.Response;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ScheduledFuture;
import java.util.stream.Collectors;
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


    private final ScheduledFuture<?>[] tasks = new ScheduledFuture<?>[1];
    @FXML
    private BarChart chart;
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
        tasks[0] = scheduler.scheduleAtInstant(() -> {
            GameCommunication.endGame(currentGameID);
        }, Instant.now().plusSeconds(2));
        chart.lookup(".chart-plot-background").setStyle("-fx-background-color: transparent;");
        //get player list from server
        scheduler.execute(() -> {
            Optional<List<User>> l = CommunicationUtils.getAllUsers(currentGameID);

            if (l.isPresent()) {
                //Sorts the list for a proper display

                //var sortedList = l.stream().toList().get(0);
                User[] array = l.get().toArray(new User[l.get().size()]);
                ArrayList<User> sortedList = new ArrayList<>();
                for (User a: array) {
                    sortedList.add(a);
                }
                List<User> newList = sortedList.stream()
                        .sorted((x,y) -> x.getScore() - y.getScore()).collect(Collectors.toList());
                Collections.reverse(newList);

                for (User user:newList
                     ) {
                    System.out.println(user.getUsername());
                }

                var series = new XYChart.Series<String, Integer>();
                series.setName("Points");
                newList.forEach(user -> {
                    var data = new XYChart.Data<String,Integer>(user.getUsername(),user.getScore());
                    series.getData().add(data);
                });
                System.out.println(series);
                chart.getData().add(series);


            }
        });
        initImages();
        present();
    }


    /**
     * Send a user to a new public waiting room.
     */
    @FXML
    private void nextGame() {
        try {
            currentGameID = WaitingRoomCommunication.getPublicCode();
        } catch (RuntimeException e) {
            e.printStackTrace();
            UserAlert.userAlert("WARN", "Cannot connect ot server",
                    "Check your connection and try again.");
        }
        try {
            joinGame();
        } catch (RuntimeException e) {
            e.printStackTrace();
            UserAlert.userAlert("WARN", "Cannot connect ot server",
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
     * Initialises the images for the game screen.
     */
    public void initImages() {
        //windmill.setImage(new Image("client/images/OIP.jpg"));
        backButtonImg.setImage(new Image(("client/images/exit_icon.png")));
    }
}
