package client.communication;

import static client.scenes.MainCtrl.username;
import static client.utils.ServerUtils.serverAddress;
import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

import commons.User;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.Objects;
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
     * Send PUT request to the server to initiate joining to certain game.
     * @return response from the server
     * @throws RuntimeException when unable to connect to the server
     */
    public static Response joinGame(String gameCode, String username) throws RuntimeException {
        return ClientBuilder.newClient(new ClientConfig())
                .target(serverAddress).path("/api/user/join/" + gameCode + "/" + username)
                .request(APPLICATION_JSON)
                .put(Entity.json(""));
    }

    /**
     * Send PUT request to the server to initiate joining to public game.
     * @return response from the server
     * @throws RuntimeException when unable to connect to the server
     */
    public static String getPublicCode() throws RuntimeException {
        return ClientBuilder.newClient(new ClientConfig())
                .target(serverAddress).path("/api/game/get/public")
                .request(APPLICATION_JSON)
                .get(new GenericType<String>() {});
    }

    private static ExecutorService pollingThread = Executors.newSingleThreadExecutor();
    private static String currentGameID;

    /**
     * Send GET request to the server to create a new game.
     * @throws RuntimeException when unable to connect to the server
     */
    public static void registerForUserListUpdates(String username, String gameCode,
                                                  Consumer<User> adder, Consumer<User> remover,
                                                  Consumer<Object> startHandler)
            throws RuntimeException {
        currentGameID = gameCode;
        pollingThread.execute(() -> {
            while (Objects.equals(currentGameID, gameCode)) {
                var res = ClientBuilder.newClient(new ClientConfig())
                        .target(serverAddress).path("/api/user/updates/" + gameCode)
                        .request(APPLICATION_JSON)
                        .accept(APPLICATION_JSON)
                        .get(Response.class);
                if (res.getStatus() == 204) {
                    continue;
                }
                if (res.getStatus() == 418) {
                    startHandler.accept(null);
                    continue;
                }
                var u = res.readEntity(User.class);
                if (res.getStatus() == 410) {
                    remover.accept(u);
                    continue;
                } else {
                    if (!u.getUsername().equals(username)) {
                        adder.accept(u);
                    }
                }
            }
        });
    }

    /**
     * Method called when application is closing.
     * So we make sure to leave a game, if user was in it and stop the polling
     */
    public static void stop() {
        try {
            if (currentGameID != null) {
                WaitingRoomCommunication.leaveGame(currentGameID, username);
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        currentGameID = "";
        pollingThread.shutdownNow();
    }

    /**
     * Send DELETE request to the server to leave the game, if user was connected to it.
     * @return server responser
     * @throws RuntimeException when unable to connect to the server
     */
    public static Response leaveGame(String gameCode, String username) throws RuntimeException {
        return ClientBuilder.newClient(new ClientConfig())
                .target(serverAddress).path("/api/user/leave/" + gameCode + "/" + username)
                .request(APPLICATION_JSON)
                .delete();
    }

    /**
     * Send POST request to the server to leave the game, if user was connected to it.
     * @return server responser
     * @throws RuntimeException when unable to connect to the server
     */
    public static Response startGame(String gameCode) throws RuntimeException {
        return ClientBuilder.newClient(new ClientConfig())
                .target(serverAddress).path("/api/user/start/" + gameCode)
                .request(APPLICATION_JSON)
                .post(Entity.json(""));
    }

}
