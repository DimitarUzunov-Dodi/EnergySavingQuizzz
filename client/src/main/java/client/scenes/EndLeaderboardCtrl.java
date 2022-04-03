package client.scenes;

import static client.scenes.MainCtrl.currentGameID;
import static client.scenes.MainCtrl.scheduler;

import client.MyFXML;
import client.communication.Utils;
import client.communication.WaitingRoomCommunication;
import client.utils.SceneController;
import client.utils.UserAlert;
import com.google.inject.Inject;
import commons.User;

import java.util.*;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Displays the final screen at the end of the game
 */
public class EndLeaderboardCtrl extends SceneController {



    @FXML
    private BarChart chart;
    @FXML
    private CategoryAxis usernames;
    @FXML
    private NumberAxis points;





    /**
     * Constructor used by INJECTOR.
     * @param myFxml handled by INJECTOR
     */
    @Inject
    protected EndLeaderboardCtrl(MyFXML myFxml) {
        super(myFxml);

    }

    /**
     * Loads the Screen with the data of the match
     */
    @Override
    public void show() {
        chart.lookup(".chart-plot-background").setStyle("-fx-background-color: transparent;");
         //get player list from server
        scheduler.execute(() -> {
            Optional<List<User>> l = Utils.getAllUsers(currentGameID);



            if (l.isPresent()) {
                 //Sorts the list for a proper display
                var sortedList = l.stream().toList().get(0);

                List<User> newList = sortedList.stream().sorted((x,y)-> x.getScore() - y.getScore()).collect(Collectors.toList());
                Collections.reverse(newList);

                for (User user:newList
                     ) {
                    System.out.println(user.getUsername());
                }

                var series = new XYChart.Series<String, Integer>();
                series.setName("Points");
                newList.forEach(user -> {
                    var data = new XYChart.Data<String,Integer>(user.getUsername(), user.getScore());
                    series.getData().add(data);
                });
                System.out.println(series);
                chart.getData().add(series);


            }
        });
        present();
    }


    /**
     * Send a user to a new waiting lobby with the same game ID
     */
    @FXML
    private void nextGame() {
        myFxml.showScene(WaitingRoomCtrl.class, currentGameID);
    }

    /**
     * returns back to SplashScreen
     */
    @FXML
    private void onBackButton() {
        myFxml.showScene(SplashCtrl.class);
    }
}