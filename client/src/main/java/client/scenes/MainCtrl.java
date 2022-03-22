package client.scenes;

import client.MyFXML;
import client.utils.SceneController;
import com.google.inject.Inject;
import com.google.inject.Injector;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.util.Optional;

/**
 * Contains the entry point for the user interface.
 */
public final class MainCtrl {
    private final MyFXML myFXML;
    private final Stage primaryStage;

    // used by many scenes (maybe do getters/setters)
    public static String username = null;
    public static String currentGameID = null;

    /**
     * Normal constructor
     * @param primaryStage The primary stage of the application
     * @param INJECTOR The injector that handles the controllers
     */
    @Inject
    private MainCtrl(Stage primaryStage, Injector INJECTOR) {
        this.primaryStage = primaryStage;
        myFXML = new MyFXML(INJECTOR);
    }

    /**
     * Start the application GUI
     */
    public void start() {
        // set quit pop-up
        primaryStage.setOnCloseRequest(e -> {
            Alert quitAlert = new Alert(Alert.AlertType.CONFIRMATION);
            quitAlert.setTitle("Quit");
            quitAlert.setHeaderText("Are you sure you want to quit?");
            Optional<ButtonType> result = quitAlert.showAndWait();
            if(result.isPresent() && result.get() == ButtonType.OK)
                primaryStage.close();
            else
                e.consume();
        });
        primaryStage.show(); // make app window visible
        myFXML.showScene(SplashCtrl.class); // same as myFXML.get(SplashCtrl.class).showScene();
    }

/*
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

    private AdminActivityCtrl adminActivityCtrl;
    private Scene adminActivityPanelScreen;

    private AdminActivityDetailsCtrl adminActivityDetailsCtrl;
    private Scene adminActivityDetailsScene;

    private ActivityImageCtrl imageCtrl;
    private Scene imageScene;

    public void initialize(MyFXML myFxml, Stage primaryStage, Pair<SplashCtrl, Parent> splashScreen, Pair<SettingsCtrl, Parent> settingsScreen, Pair<ServerLeaderboardCtrl, Parent> serverLeaderboard, Pair<GamePageController, Parent> GamePage,  Pair<DummyController, Parent> dummy, Pair<LoadingController, Parent> loadingScreen, Pair<MatchLeaderboardCtrl, Parent> matchLeaderboard, Pair<AdminCtrl, Parent> adminPage, Pair<ServerJoinCtrl, Parent> joinPage, Pair<AdminActivityCtrl, Parent> adminActivityPanel,  Pair<AdminActivityDetailsCtrl, Parent> adminActivityDetails, Pair<ActivityImageCtrl, Parent> activityImage) {
        // primary stage
        this.primaryStage = primaryStage;
        this.primaryStage.setMinWidth(700);
        this.primaryStage.setMinHeight(450);
        FXML = myFxml;

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
        this.gamePage.getStylesheets().addAll(Objects.requireNonNull(this.getClass().getResource("../css/GamePage.css")).toExternalForm());

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

        // Server join scene
        this.joinCtrl = joinPage.getKey();
        this.joinScn = new Scene(joinPage.getValue());

        // Admin activity details
        this.adminActivityDetailsCtrl = adminActivityDetails.getKey();
        this.adminActivityDetailsScene = new Scene(adminActivityDetails.getValue());

        this.imageCtrl = activityImage.getKey();
        this.imageScene = new Scene(activityImage.getValue());

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

    public void showGamePage() {
        primaryStage.setTitle("gamePage");
        primaryStage.setScene(gamePage);
        gamePageController.countDown();

        //add.setOnKeyPressed(e -> addCtrl.keyPressed(e));
    }

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

    public void showAdminActivityPanel() {
        primaryStage.setTitle("Activity panel");
        primaryStage.setScene(adminActivityPanelScreen);
        adminActivityCtrl.refresh();
    }

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

    public void showActivityImage(Activity selected) {
        primaryStage.setTitle("Activity Image");
        primaryStage.setScene(imageScene);
        this.imageCtrl.showImage(selected.getImageId());
    }
*/
}