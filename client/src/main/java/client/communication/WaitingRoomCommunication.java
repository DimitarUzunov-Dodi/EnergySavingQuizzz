package client.communication;

import static client.utils.ServerUtils.serverAddress;
import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

import commons.Activity;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.GenericType;
import java.util.List;

import jakarta.ws.rs.core.Response;
import org.glassfish.jersey.client.ClientConfig;
import org.springframework.http.ResponseEntity;

public class WaitingRoomCommunication {

    /**
     * Send GET request to the server to create a new game.
     * @return String that contains six-digit game code.
     * @throws RuntimeException when unable to connect to the server
     */
    public static String createNewGame() throws RuntimeException {
        return ClientBuilder.newClient(new ClientConfig())
                .target(serverAddress).path("/api/game/new")
                .request(APPLICATION_JSON)
                .get(new GenericType<String>() {});
    }

    /**
     * Send PUT request to the server to get list of available activities on the server.
     * @return list of Activity entities retrieved from the server
     * @throws RuntimeException when unable to connect to the server
     */
    public static Response joinGame(String gameCode, String username) throws RuntimeException {
        return ClientBuilder.newClient(new ClientConfig())
                .target(serverAddress).path("/api/user/join/" + gameCode + "/" + username)
                .request(APPLICATION_JSON)
                .put(Entity.json(""));
    }
}
