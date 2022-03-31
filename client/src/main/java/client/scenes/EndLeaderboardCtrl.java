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

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

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

    @Override
    public void show() {

         //get player list from server
        scheduler.execute(() -> {
            Optional<List<User>> l = Utils.getAllUsers(currentGameID);


            if (l.isPresent()) {


                var series = new XYChart.Series<String, Integer>();
                series.setName("Points");
                l.get().forEach(user -> {
                    var data = new XYChart.Data<String,Integer>(user.getUsername(), user.getScore());
                    series.getData().add(data);
                });
                //series.getData().stream().sorted((a,b)->a.getYValue().compareTo(b.getYValue())).collect(Collectors.toList());
                System.out.println(series);

                chart.getData().add(series);


            }
        });
        present();


    }




    @FXML
    private void onBackButton() {
        myFxml.showScene(SplashCtrl.class);
    }
}

