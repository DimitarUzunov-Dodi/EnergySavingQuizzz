package client.scenes;

import client.Main;
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

    // used by many scenes (maybe do getters/setters)
    public static String username = null;
    public static String currentGameID = null;

    /**
     * Normal constructor
     * @param INJECTOR The injector that handles the controllers
     */
    @Inject
    private MainCtrl(Injector INJECTOR) {
        myFXML = new MyFXML(INJECTOR);
    }

    /**
     * Start the application GUI
     */
    public void start() {
        // set quit pop-up
        Main.primaryStage.setOnCloseRequest(e -> {
            Alert quitAlert = new Alert(Alert.AlertType.CONFIRMATION);
            quitAlert.setTitle("Quit");
            quitAlert.setHeaderText("Are you sure you want to quit?");
            Optional<ButtonType> result = quitAlert.showAndWait();
            if(result.isPresent() && result.get() == ButtonType.OK)
                Main.primaryStage.close();
            else
                e.consume();
        });
        Main.primaryStage.setMinWidth(800);
        Main.primaryStage.setMinHeight(600);
        Main.primaryStage.show(); // make app window visible

        myFXML.showScene(SplashCtrl.class);
    }
}