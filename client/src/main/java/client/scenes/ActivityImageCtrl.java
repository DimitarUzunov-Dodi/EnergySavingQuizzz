package client.scenes;

import client.communication.ActivityImageCommunication;
import com.google.inject.Inject;
import javafx.event.ActionEvent;
import javafx.scene.image.ImageView;


public class ActivityImageCtrl {
    private final MainCtrl mainCtrl;

    private ImageView imageView;

    @Inject
    public ActivityImageCtrl(MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
    }


    public void showImage(long imageId) {
        imageView = (ImageView) mainCtrl.getCurrentScene().lookup("#imageView");

        imageView.setImage(ActivityImageCommunication.getImageFromId(imageId));
    }

    public void switchToActivityPanel(ActionEvent event){
        mainCtrl.showAdminActivityPanel();
    }
}
