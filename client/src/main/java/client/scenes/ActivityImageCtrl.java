package client.scenes;

import client.MyFXML;
import client.communication.ActivityImageCommunication;
import client.utils.SceneController;
import com.google.inject.Inject;
import commons.Activity;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;


public class ActivityImageCtrl extends SceneController {

    @FXML
    private ImageView imageView;

    /**
     * Basic constructor
     * @param myFXML handled by INJECTOR
     */
    @Inject
    private ActivityImageCtrl(MyFXML myFXML) {
        super(myFXML);
    }

    /**
     * Please use customShow(Activity selected) method to switch to this scene
     */
    @Override
    public void show() {
    }

    /**
     * Method that shows the scene with details about selected activity
     * @param selected activity which details will be shown
     */
    @FXML
    public void customShow(Activity selected) {
        showImage(selected.getImageId());
        showScene();
    }

    /**
     * Method that shows the scene with image with the given imageId
     * @param imageId id of the image that corresponds to the database record
     */
    @FXML
    private void showImage(long imageId) {
        imageView.setImage(ActivityImageCommunication.getImageFromId(imageId));
    }

    /**
     * Function called by button when clicked. Switches to main admin question panel.
     * @param event passed by JavaFX by default
     */
    @FXML
    private void switchToActivityPanel(ActionEvent event){
        myFxml.showScene(AdminCtrl.class);
    }
}
