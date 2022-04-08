package client.communication;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

import commons.User;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.core.GenericType;
import java.util.List;
import java.util.Optional;
import org.glassfish.jersey.client.ClientConfig;

public class CommunicationUtils {
    public static String serverAddress = "";

    /**
     * Send GET request to the server to get a list of all users in an ongoing game.
     * Prints the exception stacktrace if the request fails. (HTTP error)
     * @return Optional list of Users
     */
    public static Optional<List<User>> getAllUsers(String gameCode) {
        try {
            if (gameCode == null) {
                throw new IllegalArgumentException("'gameCode' may not be null!");
            }
            return Optional.ofNullable(ClientBuilder.newClient(new ClientConfig())
                    .target(serverAddress).path("/api/game/getUsers/" + gameCode)
                    .request(APPLICATION_JSON)
                    .get(new GenericType<List<User>>() { // do not remove the explicit type!
                    }));
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }
}
