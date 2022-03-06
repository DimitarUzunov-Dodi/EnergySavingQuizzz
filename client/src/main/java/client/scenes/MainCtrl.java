package client.scenes;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.util.Objects;

public class MainCtrl {

    private Stage primaryStage;




    private Scene gamePage;
    private Scene dummy;
    private GamePageController gamePageController;
    private DummyController dummyController;


   
    private SplashCtrl splashCtrl;
    private Scene splash;

    private SettingsCtrl settingsCtrl;
    private Scene settings;

    private ServerLeaderboardCtrl serverLeaderboardCtrl;
    private Scene serverLeaderboardScn;

    public void initialize(Stage primaryStage, Pair<SplashCtrl, Parent> splashScreen, Pair<SettingsCtrl, Parent> settingsScreen, Pair<ServerLeaderboardCtrl, Parent> serverLeaderboard, Pair<GamePageController, Parent> GamePage,  Pair<DummyController, Parent> dummy) {
        // primary stage
        this.primaryStage = primaryStage;
        this.primaryStage.setMinWidth(700);
        this.primaryStage.setMinHeight(450);

        // splash scene
        this.splashCtrl = splashScreen.getKey();
        this.splash = new Scene(splashScreen.getValue());

        // settings scene
        this.settingsCtrl = settingsScreen.getKey();
        this.settings = new Scene(settingsScreen.getValue());
        
        // Game scene
        this.gamePageController = GamePage.getKey();
        this.gamePage = new Scene(GamePage.getValue());

        // Dummy scene
        this.dummyController = dummy.getKey();
        this.dummy = new Scene(dummy.getValue());
        
        // server leaderboard scene
        this.serverLeaderboardCtrl = serverLeaderboard.getKey();
        this.serverLeaderboardScn = new Scene(serverLeaderboard.getValue());
        serverLeaderboardScn.getStylesheets().addAll(Objects.requireNonNull(this.getClass().getResource("../css/ServerLeaderboard.css")).toExternalForm());

        showSplashScreen();
        //showServerLeaderboard(); // for testing only

        primaryStage.show();
    }

    public void showSplashScreen() {
        splashCtrl.initTextField(splash);
        primaryStage.setTitle("Splash Screen");
        primaryStage.setScene(splash);
    }

    public void showSettingsScreen() {
        primaryStage.setTitle("Settings Screen");
        primaryStage.setScene(settings);
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
    public void showDummy() {
        primaryStage.setTitle("Quotes: Dummy");
        primaryStage.setScene(dummy);
      //  add.setOnKeyPressed(e -> addCtrl.keyPressed(e));
    }



    public void showGamePage() {
        primaryStage.setTitle("gamePage");
        primaryStage.setScene(gamePage);
        //add.setOnKeyPressed(e -> addCtrl.keyPressed(e));
    }
    public Scene getSplashScreenScene() {
        return this.splash;

    }
}