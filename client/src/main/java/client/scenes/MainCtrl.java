package client.scenes;

import client.Main;
import client.MyFXML;
import client.communication.WaitingRoomCommunication;
import client.utils.TaskScheduler;
import com.google.inject.Inject;
import com.google.inject.Injector;
import javafx.stage.WindowEvent;
import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/**
 * Contains the entry point for the user interface.
 */
public final class MainCtrl {
    private final MyFXML myFxml;

    // used to schedule and execute all sorts of stuff (e.g. end the current round)
    public static final TaskScheduler scheduler = new TaskScheduler(2);

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
        scheduler.setRemoveOnCancelPolicy(false);
        scheduler.setExecuteExistingDelayedTasksAfterShutdownPolicy(false);
    }

    /**
     * Start the application GUI.
     */
    public void start() {
        // set quit pop-up
        Main.primaryStage.setOnCloseRequest(this::onApplicationClose);
        Main.primaryStage.setMinWidth(800);
        Main.primaryStage.setMinHeight(600);

        myFxml.showScene(SplashCtrl.class);
        Main.primaryStage.show(); // make app window visible
    }

    private void onApplicationClose(WindowEvent e) {
        // alert the user
        Alert quitAlert = new Alert(Alert.AlertType.CONFIRMATION);
        quitAlert.setTitle("Quit");
        quitAlert.setHeaderText("Are you sure you want to quit?");
        Optional<ButtonType> result = quitAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            WaitingRoomCommunication.leaveGame(currentGameID, username);
            try {
                WaitingRoomCtrl.pollingThread.cancel();
            } catch (NullPointerException exception) {
                System.out.println("Blame Yehor for this stupid catch");
            }
            scheduler.shutdown();
            Main.primaryStage.close();
        } else {
            e.consume();
        }
    }
}