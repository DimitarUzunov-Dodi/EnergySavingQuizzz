package client.communication;

import commons.Activity;
import commons.ActivityBankEntry;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.Response;
import org.glassfish.jersey.client.ClientConfig;

import java.util.List;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

public class AdminCommunication {

    private static final String SERVER = "http://localhost:8080/";

    /**
     * Send POST request to start the restart procedure of the server
     * @return
     * @throws RuntimeException when unable to connect to the server
     */
    public static Response invokeServerRestart() throws RuntimeException {
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("api/admin/restart")
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
                .target(SERVER).path("api/admin/activity")
                .request(APPLICATION_JSON)
                .get(new GenericType<List<Activity>>() {});
    }

    /**
     * Send POST request to the server to add a dummy activity. Used for manual testing
     * @return list of Activity entities retrieved from the server
     * @throws RuntimeException when unable to connect to the server
     */
    public static Response addTestingActivity() throws RuntimeException {
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("api/admin/activity/add")
                .request(APPLICATION_JSON)
                .post(null);
    }

    /**
     * Send DELETE request to the server to delete all activities on the server
     * @return response
     * @throws RuntimeException when unable to connect to the server
     */
    public static Response deleteActivities() throws RuntimeException {
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("api/admin/activity/deleteAll")
                .request(APPLICATION_JSON)
                .delete();
    }

    /**
     * Sends POST request to modify an activity that is already in the database
     * @param activityId id of activity
     * @param constructed new activity that is going to replace an old version
     * @return Response from the server
     */
    public static Response editActivity(long activityId, Activity constructed) throws RuntimeException {
        System.out.println("Sending entity");
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("api/admin/activity/edit/" + Long.toString(activityId))
                .request(APPLICATION_JSON)
                .post(Entity.entity(constructed, APPLICATION_JSON));
    }

    /**
     * Sends POST request with activity bank
     */

    public static Response addActivityBankEntry(ActivityBankEntry constructed) throws RuntimeException {
        System.out.println("Sending entity");
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("api/admin/activitybank/add/")
                .request(APPLICATION_JSON)
                .post(Entity.entity(constructed, APPLICATION_JSON));
    }
}
