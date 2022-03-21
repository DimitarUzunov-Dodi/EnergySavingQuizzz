package client.utils;

import javafx.scene.Scene;

/**
 * Abstract class that forms the basis of a scene controller
 */
public abstract class SceneController {
    private Scene scene;

    /**
     * Constructor that initializes the scene member.
     * Super this when making more complex constructors.
     * @param scene The associated scene
     */
    public SceneController(Scene scene) {
        this.scene = scene;
    }

    /**
     * Function that is called whenever this controller is given control.
     * e.g.: when the scene is shown on the primary stage
     */
    public abstract void onShow();

    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }
}
