package client.communication;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

import commons.User;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.core.GenericType;
import java.util.List;
import org.glassfish.jersey.client.ClientConfig;

public class Utils {
    public static String serverAddress;

    /**
     * Send GET request to the server to get a list of all users in a current game.
     * @return String that contains six-digit game code.
     * @throws RuntimeException when unable to connect to the server
     */
    public static List<User> getAllUsers(String gameCode) throws RuntimeException {
        return ClientBuilder.newClient(new ClientConfig())
                .target(serverAddress).path("/api/game/getUsers/" + gameCode)
                .request(APPLICATION_JSON)
                .get(new GenericType<>() {});
    }
}
