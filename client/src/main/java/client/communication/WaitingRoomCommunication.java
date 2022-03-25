package client.communication;

import static client.utils.ServerUtils.serverAddress;
import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

import commons.User;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import org.glassfish.jersey.client.ClientConfig;

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
     * Send GET request to the server to get a list of all users in a current game.
     * @return String that contains six-digit game code.
     * @throws RuntimeException when unable to connect to the server
     */
    public static List<User> getAllUsers(String gameCode) throws RuntimeException {
        return ClientBuilder.newClient(new ClientConfig())
                .target(serverAddress).path("/api/game/getUsers/" + gameCode)
                .request(APPLICATION_JSON)
                .get(new GenericType<List<User>>() {});
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

    private static final ExecutorService EXEC = Executors.newSingleThreadExecutor();

    /**
     * Send GET request to the server to create a new game.
     * @return String that contains six-digit game code.
     * @throws RuntimeException when unable to connect to the server
     */
    public static void registerForUserListUpdates(String gameCode, Consumer<User> consumer)
            throws RuntimeException {
        EXEC.submit(() -> {
            while (!Thread.interrupted()) {
                System.out.println("Polling goes on");
                var res = ClientBuilder.newClient(new ClientConfig())
                        .target(serverAddress).path("/api/user/updates/" + gameCode)
                        .request(APPLICATION_JSON)
                        .accept(APPLICATION_JSON)
                        .get(Response.class);
                if (res.getStatus() == 204) {
                    continue;
                }
                var u = res.readEntity(User.class);
                consumer.accept(u);
            }
        });
    }

    public static void stop() {
        EXEC.shutdownNow();
    }

}
