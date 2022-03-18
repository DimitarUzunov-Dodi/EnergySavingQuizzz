package client.communication;

import commons.Activity;
import commons.ActivityImage;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.Response;
import org.glassfish.jersey.client.ClientConfig;

import java.util.List;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

public class ActivityImageCommunication {

    private static final String SERVER = "http://localhost:8080/";

    public static ActivityImage getImage(long imageId) throws RuntimeException {
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("api/images/getImage/" + Long.toString(imageId))
                .request(APPLICATION_JSON)
                .get(ActivityImage.class);
    }
}
