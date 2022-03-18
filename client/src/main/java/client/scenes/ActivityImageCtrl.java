package client.scenes;

import client.communication.ActivityImageCommunication;
import com.google.inject.Inject;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


import static client.utils.ActivityImageUtils.imageFromByteArray;

public class ActivityImageCtrl {
    private final MainCtrl mainCtrl;

    private ImageView imageView;
    private Button button;


    @Inject
    public ActivityImageCtrl(MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
    }


    public void showImage(long imageId) {
        imageView = (ImageView) mainCtrl.getCurrentScene().lookup("#imageView");

        byte[] imageData = ActivityImageCommunication.getImage(imageId).getImageData();
        Image fxImage = imageFromByteArray(imageData);

        imageView.setImage(fxImage);
    }
}
