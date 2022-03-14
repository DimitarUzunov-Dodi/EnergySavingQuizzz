package client.communication;

import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.core.Response;
import org.glassfish.jersey.client.ClientConfig;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

public class AdminCommunication {

    private static final String SERVER = "http://localhost:8080/";

    /**
     * Send post request to start the restart procedure of the server
     * @return
     */
    public Response invokeServerRestart() throws RuntimeException {
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("api/admin/restart")
                .request(APPLICATION_JSON)
                .post(null);
    }

}
