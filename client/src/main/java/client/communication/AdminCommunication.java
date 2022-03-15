package client.communication;

import commons.Activity;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.Response;
import org.glassfish.jersey.client.ClientConfig;

import java.util.List;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

public class AdminCommunication {

    private static final String SERVER = "http://localhost:8080/";

    /**
     * Send post request to start the restart procedure of the server
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
     * Send get request to the server to get list of available activities on the server
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
     * Send get request to the server to get list of available activities on the server
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
     * Send get request to the server to delete all activities on the server
     * @return response
     * @throws RuntimeException when unable to connect to the server
     */
    public static Response deleteActivities() throws RuntimeException {
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("api/admin/activity/deleteAll")
                .request(APPLICATION_JSON)
                .delete();
    }
}
