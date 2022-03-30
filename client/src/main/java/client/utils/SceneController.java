package client.utils;

import client.Main;
import client.MyFXML;
import com.google.inject.Inject;
import javafx.scene.Scene;
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
        Main.primaryStage.setScene(scene);
    }

    /**
     * Shows this controller's scene on the specified stage.
     */
    protected void present(Stage stage) {
        stage.setScene(scene);
    }
}
