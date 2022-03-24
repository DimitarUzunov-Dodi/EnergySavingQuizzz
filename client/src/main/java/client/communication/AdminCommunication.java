package client.communication;

import commons.Activity;
import commons.ActivityBankEntry;
import commons.ActivityImage;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.Response;
import org.glassfish.jersey.client.ClientConfig;

import java.util.List;

import static client.utils.ServerUtils.serverAddress;
import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

public class AdminCommunication {

    /**
     * Send POST request to start the restart procedure of the server
     * @return Response from the server if it was successful
     * @throws RuntimeException when unable to connect to the server
     */
    public static Response invokeServerRestart() throws RuntimeException {
        return ClientBuilder.newClient(new ClientConfig())
                .target(serverAddress).path("/api/admin/restart")
                .request(APPLICATION_JSON)
                .post(null);
    }

    /**
     * Send GET request to the server to get list of available activities on the server
     * @return list of Activity entities retrieved from the server
     * @throws RuntimeException when unable to connect to the server
     */
    public static List<Activity> getAllActivities() throws RuntimeException {
        return ClientBuilder.newClient(new ClientConfig())
                .target(serverAddress).path("/api/admin/activity/all")
                .request(APPLICATION_JSON)
                .get(new GenericType<>() {});
    }

    /**
     * Send POST request to the server to add a dummy activity. Used for manual testing
     * @return list of Activity entities retrieved from the server
     * @throws RuntimeException when unable to connect to the server
     */
    /*
    THIS WAS USED FOR TESTING MANUALLY ADDING ACTIVITIES
    PATH FOR ADDING ACTIVITIES WAS CHANGED SO JUST UNCOMMENTING THIS METHOD WON`T WORK
    LEFT AS A REFERENCE IN CASE OF EMERGENCY MANUAL TESTING
    
    public static Response addTestingActivity() throws RuntimeException {
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("api/admin/activity/add")
                .request(APPLICATION_JSON)
                .post(null);
    }*/

    /**
     * Send DELETE request to the server to delete all activities on the server
     * @return Response from the server if it was successful
     * @throws RuntimeException when unable to connect to the server
     */
    public static Response deleteActivities() throws RuntimeException {
        return ClientBuilder.newClient(new ClientConfig())
                .target(serverAddress).path("/api/admin/activity/delete/all")
                .request(APPLICATION_JSON)
                .delete();
    }

    /**
     * Sends PUT request to modify an activity that is already in the database
     * @param activityId id of activity
     * @param constructed new activity that is going to replace an old version
     * @return Response from the server
     * @throws RuntimeException when unable to connect to the server
     */
    public static Response editActivity(long activityId, Activity constructed) throws RuntimeException {
        System.out.println("Sending entity");
        return ClientBuilder.newClient(new ClientConfig())
                .target(serverAddress).path("/api/admin/activity/edit/" + activityId)
                .request(APPLICATION_JSON)
                .put(Entity.entity(constructed, APPLICATION_JSON));
    }

    /**
     * Sends POST request with activity bank entry, so activity is added to the database
     * @param constructed object of a class ActivityBankEntry that will be transformed to the proper Activity on the server
     * @param imageId id of the image that will be associated with this activity
     * @throws RuntimeException when unable to connect to the server
     */

    public static Response addActivityBankEntry(ActivityBankEntry constructed, long imageId) throws RuntimeException {
        System.out.println("Sending entity");
        return ClientBuilder.newClient(new ClientConfig())
                .target(serverAddress).path("/api/admin/activity/add")
                .queryParam("imageId", Long.toString(imageId))
                .request(APPLICATION_JSON)
                .post(Entity.entity(constructed, APPLICATION_JSON));
    }

    /**
     * Sends POST request to add an image to the activity
     * @param activityImage image of activity
     * @return Response from the server
     * @throws RuntimeException when unable to connect to the server
     */
    public static Response addActivityImage(ActivityImage activityImage) throws RuntimeException {
        System.out.println("Sending an image");
        return ClientBuilder.newClient(new ClientConfig())
                .target(serverAddress).path("/api/admin/activity/add/image")
                .request(APPLICATION_JSON)
                .post(Entity.entity(activityImage, APPLICATION_JSON));
    }
}
