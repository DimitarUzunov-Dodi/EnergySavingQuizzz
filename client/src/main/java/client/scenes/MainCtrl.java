package client.scenes;

import client.MyFXML;
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
}