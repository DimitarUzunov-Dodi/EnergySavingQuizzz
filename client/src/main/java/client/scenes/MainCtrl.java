package client.scenes;

import commons.Activity;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.util.Objects;

import static client.scenes.UserAlert.userAlert;

public class MainCtrl {

    private Stage primaryStage;

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

    private AdminActivityCtrl adminActivityCtrl;
    private Scene adminActivityPanelScreen;

    private AdminActivityDetailsCtrl adminActivityDetailsCtrl;
    private Scene adminActivityDetailsScene;

    public void initialize(Stage primaryStage, Pair<SplashCtrl, Parent> splashScreen, Pair<SettingsCtrl, Parent> settingsScreen, Pair<ServerLeaderboardCtrl, Parent> serverLeaderboard, Pair<GamePageController, Parent> GamePage,  Pair<DummyController, Parent> dummy, Pair<LoadingController, Parent> loadingScreen, Pair<MatchLeaderboardCtrl, Parent> matchLeaderboard, Pair<AdminCtrl, Parent> adminPage, Pair<AdminActivityCtrl, Parent> adminActivityPanel,  Pair<AdminActivityDetailsCtrl, Parent> adminActivityDetails) {
        // primary stage
        this.primaryStage = primaryStage;
        this.primaryStage.setMinWidth(700);
        this.primaryStage.setMinHeight(450);

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

        // Admin activities panel
        this.adminActivityCtrl = adminActivityPanel.getKey();
        this.adminActivityPanelScreen = new Scene(adminActivityPanel.getValue());

        // Loading scene
        this.loadingCtrl = loadingScreen.getKey();
        this.loading = new Scene(loadingScreen.getValue());
        this.loading.getStylesheets().addAll(Objects.requireNonNull(this.getClass().getResource("../css/Loading.css")).toExternalForm());

        // Admin activity details
        this.adminActivityDetailsCtrl = adminActivityDetails.getKey();
        this.adminActivityDetailsScene = new Scene(adminActivityDetails.getValue());

        //showServerLeaderboard(); // for testing only
        //showGamePage();
        showSplashScreen();
        //showLoadingScreen();
        //showMatchLeaderboardScreen("ID");
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

    /**
     * Display admin panel screen
     */
    public void showAdmin() {
        primaryStage.setTitle("Admin page");
        primaryStage.setScene(adminPageScene);
    }

    /**
     * Display admin panel for managing the activities
     */
    public void showAdminActivityPanel() {
        primaryStage.setTitle("Activity panel");
        primaryStage.setScene(adminActivityPanelScreen);
        adminActivityCtrl.refresh();
    }

    /**
     *  Display admin panel for details od the selected activity
     */
    public void showAdminActivityDetails(Activity selected) {
        primaryStage.setTitle("Activity details");
        try {
            this.adminActivityDetailsCtrl.setActivity(selected);
        } catch (NullPointerException e){
            userAlert("WARN", "No activity selected", "Please select an activity");
            return;
        }
        primaryStage.setScene(adminActivityDetailsScene);
    }
}