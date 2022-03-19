package client.scenes;

import client.MyFXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.util.Objects;

public class MainCtrl {

    private Stage primaryStage;
    private MyFXML FXML;

    private Scene gamePage;
    private GamePageController gamePageController;

    private Scene dummy;
    private DummyController dummyController;

    private Scene matchLeaderboardScn;
    private MatchLeaderboardCtrl matchLeaderboardCtrl;

    private SplashCtrl splashCtrl;
    private Scene splash;

    private SettingsCtrl settingsCtrl;
    private Scene settings;

    private ServerLeaderboardCtrl serverLeaderboardCtrl;
    private Scene serverLeaderboardScn;

    private AdminCtrl adminCtrl;
    private Scene adminPageScene;

    private LoadingController loadingCtrl;
    private Scene loading;

    private ServerJoinCtrl joinCtrl;
    private Scene joinScn;

    /**
     * Uses the main MyFXML object to load the specified controller and associated javafx resource.
     * This function can be used to dynamically load pages individually without the need to keep unused objects in memory all the time. Once the resources are loaded it's the caller's job to manage their lifetime.
     * @param ctrl Type (class name) of the desired controller
     * @param fxmlName Name of the .fxml file containing the associated UI
     * @return Pair of an instance of the controller and it's parent (associated fxml)
     */
    public <Ctrl> Pair<Ctrl, Parent> loadPage(Class<Ctrl> ctrl, String fxmlName) {
        return FXML.load(ctrl, "client", "scenes", fxmlName);
    }

    public void initialize(MyFXML fxml, Stage primaryStage, Pair<SplashCtrl, Parent> splashScreen, Pair<SettingsCtrl, Parent> settingsScreen, Pair<ServerLeaderboardCtrl, Parent> serverLeaderboard, Pair<GamePageController, Parent> GamePage,  Pair<DummyController, Parent> dummy, Pair<LoadingController, Parent> loadingScreen, Pair<MatchLeaderboardCtrl, Parent> matchLeaderboard, Pair<AdminCtrl, Parent> adminPage, Pair<ServerJoinCtrl, Parent> joinPage) {
        // primary stage
        this.primaryStage = primaryStage;
        this.primaryStage.setMinWidth(700);
        this.primaryStage.setMinHeight(450);
        this.FXML = fxml;

        // CSS
        String background = Objects.requireNonNull(this.getClass().getResource("../css/Background.css")).toExternalForm();

        // splash scene
        this.splashCtrl = splashScreen.getKey();
        this.splash = new Scene(splashScreen.getValue());
        this.splash.getStylesheets().addAll(Objects.requireNonNull(this.getClass().getResource("../css/Splash.css")).toExternalForm());

        // settings scene
        this.settingsCtrl = settingsScreen.getKey();
        this.settings = new Scene(settingsScreen.getValue());

        // Game scene
        this.gamePageController = GamePage.getKey();
        this.gamePage = new Scene(GamePage.getValue());

        // match leaderboard screen
        this.matchLeaderboardCtrl = matchLeaderboard.getKey();
        this.matchLeaderboardScn = new Scene(matchLeaderboard.getValue());
        matchLeaderboardScn.getStylesheets().addAll(background);

        // Dummy scene
        this.dummyController = dummy.getKey();
        this.dummy = new Scene(dummy.getValue());

        // server leaderboard scene
        this.serverLeaderboardCtrl = serverLeaderboard.getKey();
        this.serverLeaderboardScn = new Scene(serverLeaderboard.getValue());
        serverLeaderboardScn.getStylesheets().addAll(background);

        // Admin page scene
        this.adminCtrl = adminPage.getKey();
        this.adminPageScene = new Scene(adminPage.getValue());

        // Loading scene
        this.loadingCtrl = loadingScreen.getKey();
        this.loading = new Scene(loadingScreen.getValue());
        this.loading.getStylesheets().addAll(Objects.requireNonNull(this.getClass().getResource("../css/Loading.css")).toExternalForm());

        // Server join scene
        this.joinCtrl = joinPage.getKey();
        this.joinScn = new Scene(joinPage.getValue());

        showSplashScreen();
        primaryStage.show();
    }

    public void showSplashScreen() {
        splashCtrl.initTextField();
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

    public void showMatchLeaderboardScreen(String gameCode) {
        primaryStage.setScene(matchLeaderboardScn);
        matchLeaderboardCtrl.refresh(gameCode);
    }

    public Scene getCurrentScene() {
        return primaryStage.getScene();
    }

    public void showDummy() {
        primaryStage.setTitle("Quotes: Dummy");
        primaryStage.setScene(dummy);
      //  add.setOnKeyPressed(e -> addCtrl.keyPressed(e));
    }

    /**
     * Display game screen
     */
    public void showGamePage() {
        primaryStage.setTitle("gamePage");
        primaryStage.setScene(gamePage);
        //add.setOnKeyPressed(e -> addCtrl.keyPressed(e));
    }

    /**
     * Display loading screen
     */
    public void showLoadingScreen() {
        primaryStage.setTitle("Get Ready!");
        primaryStage.setScene(loading);

        loadingCtrl.init();
        loadingCtrl.countDown();
    }

    public Scene getSplashScreenScene() {
        return this.splash;
    }

    public void showAdmin() {
        primaryStage.setTitle("Admin page");
        primaryStage.setScene(adminPageScene);
    }

    public void showServerJoin() {
        primaryStage.setScene(joinScn);
    }

    public void showWaitingRoom(String gameID) {
        var room = loadPage(WaitingRoomController.class, "WaitingRoom.fxml");
        primaryStage.setScene(new Scene(room.getValue())); // also calls 'initialize' (I think)
    }
}