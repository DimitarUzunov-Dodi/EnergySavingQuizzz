package client.scenes;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.util.Objects;

public class MainCtrl {

    private Stage primaryStage;

    private ServerLeaderboardCtrl serverLeaderboardCtrl;
    private Scene serverLeaderboardScn;

    public void initialize(Stage primaryStage, Pair<ServerLeaderboardCtrl, Parent> serverLeaderboard) {
        this.primaryStage = primaryStage;
        this.primaryStage.setMinWidth(700); // TODO: decide on a min value tht works for all scenes
        this.primaryStage.setMinHeight(450);

        this.serverLeaderboardCtrl = serverLeaderboard.getKey();
        this.serverLeaderboardScn = new Scene(serverLeaderboard.getValue());
        serverLeaderboardScn.getStylesheets().addAll(Objects.requireNonNull(this.getClass().getResource("../css/ServerLeaderboard.css")).toExternalForm());

        showServerLeaderboard(); // for testing only
        primaryStage.show();
    }

    /**
     * Display server leaderboard and refresh table
     */
    public void showServerLeaderboard() {
        primaryStage.setTitle("Server Leaderboard");
        primaryStage.setScene(serverLeaderboardScn);
        serverLeaderboardCtrl.refresh();
    }

    public Scene getCurrentScene() {
        return primaryStage.getScene();
    }
}