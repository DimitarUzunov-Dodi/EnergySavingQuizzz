package client.scenes;

import client.Main;
import client.MyFXML;
import client.communication.WaitingRoomCommunication;
import com.google.inject.Inject;
import com.google.inject.Injector;
import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/**
 * Contains the entry point for the user interface.
 */
public final class MainCtrl {
    private final MyFXML myFxml;

    // used by many scenes (maybe do getters/setters)
    public static String username = null;
    public static String currentGameID = null;

    /**
     * Normal constructor.
     * @param injector The injector that handles the controllers
     */
    @Inject
    private MainCtrl(Injector injector) {
        myFxml = new MyFXML(injector);
    }

    /**
     * Start the application GUI.
     */
    public void start() {
        // set quit pop-up
        Main.primaryStage.setOnCloseRequest(e -> {
            Alert quitAlert = new Alert(Alert.AlertType.CONFIRMATION);
            quitAlert.setTitle("Quit");
            quitAlert.setHeaderText("Are you sure you want to quit?");
            Optional<ButtonType> result = quitAlert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                WaitingRoomCommunication.leaveGame(currentGameID, username);
                WaitingRoomCtrl.pollingThread.cancel();
                Main.primaryStage.close();
            } else {
                e.consume();
            }
        });
        Main.primaryStage.setMinWidth(800);
        Main.primaryStage.setMinHeight(600);

        myFxml.showScene(SplashCtrl.class);
        Main.primaryStage.show(); // make app window visible
    }
}