package client;

import static com.google.inject.Guice.createInjector;

import java.util.Optional;

import com.google.inject.Injector;

import client.scenes.MainCtrl;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class Main extends Application {

    private static final Injector INJECTOR = createInjector(new MyModule());

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
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

        MainCtrl mainCtrl = INJECTOR.getInstance(MainCtrl.class);
        mainCtrl.start(INJECTOR); // give control to mainCtrl
    }

    /*
    private static final Injector INJECTOR = ;
    private static final MyFXML FXML = new MyFXML(INJECTOR);

    public static void main(String[] args) throws URISyntaxException, IOException {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        /*
        var mainCtrl = INJECTOR.getInstance(MainCtrl.class);

        var gamePage = FXML.load(GamePageController.class, "client", "scenes", "GameScreen.fxml");
        var dummyPage = FXML.load(DummyController.class, "client", "scenes", "DummyScene.fxml");

        var splash = FXML.load(SplashCtrl.class, "client", "scenes", "SplashScreen.fxml");
        var settings = FXML.load(SettingsCtrl.class, "client", "scenes", "SettingsScreen.fxml");
        var serverLeaderboard = FXML.load(ServerLeaderboardCtrl.class, "client", "scenes", "ServerLeaderboard.fxml");
        var adminPage = FXML.load(AdminCtrl.class, "client", "scenes", "AdminPage.fxml");
        var loading = FXML.load(LoadingController.class, "client", "scenes", "LoadingScene.fxml");
        var matchLeaderboard = FXML.load(MatchLeaderboardCtrl.class, "client", "scenes", "MatchLeaderboard.fxml");
        var joinPage = FXML.load(ServerJoinCtrl.class, "client", "scenes", "ServerJoin.fxml");
        var adminActivityPanel = FXML.load(AdminActivityCtrl.class, "client", "scenes", "AdminActivitiesScreen.fxml");
        var adminActivityDetails = FXML.load(AdminActivityDetailsCtrl.class, "client", "scenes", "AdminActivityDetails.fxml");
        var activityImage = FXML.load(ActivityImageCtrl.class,  "client", "scenes", "ActivityImageScreen.fxml");

        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent e) {
                Alert quitAlert = new Alert(Alert.AlertType.CONFIRMATION);
                quitAlert.setTitle("Quit");
                quitAlert.setHeaderText("Are you sure you want to quit?");
                Optional<ButtonType> result = quitAlert.showAndWait();
                if(result.get() == ButtonType.OK){
                    primaryStage.close();
                } else {
                    e.consume();
                }
            }
        });

        mainCtrl.initialize(FXML, primaryStage, splash, settings, serverLeaderboard, gamePage, dummyPage, loading, matchLeaderboard, adminPage, joinPage, adminActivityPanel, adminActivityDetails, activityImage);
    }
    */
}