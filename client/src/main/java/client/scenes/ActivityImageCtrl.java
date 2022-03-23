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

    @Override
    public void show() {
    }

    public void customShow(Activity selected) {
        showImage(selected.getImageId());
        showScene();
    }

    public void showImage(long imageId) {
        imageView.setImage(ActivityImageCommunication.getImageFromId(imageId));
    }

    protected void switchToActivityPanel(ActionEvent event){
        myFXML.showScene(AdminCtrl.class);
    }
}
