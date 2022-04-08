package client.utils;

import static client.scenes.MainCtrl.scheduler;

import client.Main;
import client.MyFXML;
import com.google.inject.Inject;
import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ScheduledFuture;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.stage.Stage;

/**
 * Abstract class that forms the basis of a scene controller.
 */
public abstract class SceneController {
    protected Scene scene = null;
    protected static MyFXML myFxml;

    /**
     * Basic constructor.
     * @param myFxml handled by INJECTOR
     */
    @Inject
    protected SceneController(MyFXML myFxml) {
        SceneController.myFxml = myFxml;
    }

    /**
     * Function that is called by other classes to hand control flow to this controller.
     * If you override this function make sure to call present() to actually show
     *      the scene on the current stage.
     */
    public void show() {
        present();
    }

    /**
     * Function that is called by other classes to hand control flow to this controller.
     * Override this function to refresh scene content and add extra functionality.
     * @param args Variable arguments that can be used for custom controllers.
     */
    public void show(Object... args) {
        show();
    }

    /**
     * Get the scene that this controller belongs to.
     * @return the scene
     */
    public Scene getScene() {
        return scene;
    }

    /**
     * Set the scene that this controller belongs to.
     * @param scene the scene
     */
    public void setScene(Scene scene) {
        this.scene = scene;
    }

    /**
     * Shows this controller's scene on the primary stage.
     */
    protected void present() {
        present(Main.primaryStage);
    }

    /**
     * Shows this controller's scene on the specified stage.
     */
    protected void present(Stage stage) {
        stage.setScene(scene);
        /* depricated stage resizing
        double posX = stage.getX();
        double posY = stage.getY();
        double sizeX = stage.getWidth();
        double sizeY = stage.getHeight();

        stage.setWidth(sizeX);
        stage.setHeight(sizeY);
        stage.setX(posX);
        stage.setY(posY);
        */
    }

    /**
     * Schedule a recurrent task that continuously adjusts the progress bar value.
     * @param bar Progress bar to be updated
     * @param endTime The moment in time the progress reaches 0
     * @return A reference to the scheduled task that can be used to cancel it
     */
    protected static ScheduledFuture<?> scheduleProgressBar(ProgressBar bar, Instant endTime) {
        System.out.println(endTime);
        bar.setUserData(bar.getProgress() * 32d
                / Duration.between(Instant.now(), endTime).toMillis()); // progress step
        System.out.println("-- step: " + bar.getUserData());
        System.out.println("progress: " + bar.getProgress());

        return scheduler.scheduleAtFixedRate(
                () -> bar.setProgress(Math.max(0,
                        bar.getProgress() - (Double) bar.getUserData())),
                32L);
    }
}
