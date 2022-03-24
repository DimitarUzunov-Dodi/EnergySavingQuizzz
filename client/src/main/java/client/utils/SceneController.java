package client.utils;

import client.Main;
import client.MyFXML;
import com.google.inject.Inject;
import javafx.scene.Scene;

/**
 * Abstract class that forms the basis of a scene controller
 */
public abstract class SceneController {
    protected Scene scene = null;
    protected final MyFXML myFXML;

    /**
     * Basic constructor
     * @param myFXML handled by INJECTOR
     */
    @Inject
    protected SceneController(MyFXML myFXML) {
        this.myFXML = myFXML;
    }

    /**
     * Function that is called by other classes to hand control flow to this controller.
     * Override this function to refresh scene content and add extra functionality.
     * Make sure to call showScene() to actually show the scene on the current stage.
     */
    public abstract void show();

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
     * Shows this controller's scene on the stage it currently sits.
     */
    protected void showScene() {
        Main.primaryStage.setScene(scene);
    }
}
