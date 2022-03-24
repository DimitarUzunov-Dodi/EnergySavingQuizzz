package client;

import static com.google.inject.Guice.createInjector;

import client.utils.ServerUtils;
import com.google.inject.Injector;

import client.scenes.MainCtrl;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    private static final Injector INJECTOR = createInjector(new MyModule());
    public static Stage primaryStage;

    /**
     * Program entry point
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Start the JavaFX application
     * @param primaryStage the primary stage (window) of the application
     */
    @Override
    public void start(Stage primaryStage) {
        Main.primaryStage = primaryStage;
        ServerUtils.serverAddress = getParameters().getNamed().getOrDefault("server", "http://localhost:8080");
        MainCtrl mainCtrl = INJECTOR.getInstance(MainCtrl.class);
        mainCtrl.start(); // give control to mainCtrl
    }
}