package client.communication;

import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.core.Response;
import org.glassfish.jersey.client.ClientConfig;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

public class GameCommunication {
    private static final String SERVER = "http://localhost:8080/";

    /**
     * Send post request to start the restart procedure of the server
     * @return
     */
    public Response connectTest() throws RuntimeException {
        WebSocketClient client = new StandardWebSocketClient();
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("/game")
                .request(APPLICATION_JSON)
                .get();

    }
}
