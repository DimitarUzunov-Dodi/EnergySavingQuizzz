package client.communication;

import commons.ActivityImage;
import jakarta.ws.rs.client.ClientBuilder;
import javafx.scene.image.Image;
import org.glassfish.jersey.client.ClientConfig;


import java.util.Objects;

import static client.utils.ActivityImageUtils.imageFromByteArray;
import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

public class ActivityImageCommunication {

    public static final String SERVER = "http://localhost:8080/";

    /**
     * Get Image for ImageView from imageId
     * @param imageId - image id from activity
     * @return Image for ImageView(JavaFX)
     */
    public static Image getImageFromId(long imageId) {

        try {
            byte[] imageData = getActivityImage(imageId).getImageData();

            return imageFromByteArray(imageData);
        }
        catch (RuntimeException e) {
            e.printStackTrace();
            return new Image(Objects.requireNonNull(ActivityImageCommunication.class.getResource("../images/default-image.png")).toExternalForm());
        }
    }

    /**
     * Get ActivityImage by id
     * @param imageId - image id from activity
     * @return ActivityImage by id
     * @throws RuntimeException  when unable to connect to the server
     */
    private static ActivityImage getActivityImage(long imageId) throws RuntimeException {
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("api/images/getImage/" + Long.toString(imageId))
                .request(APPLICATION_JSON)
                .get(ActivityImage.class);
    }
}
