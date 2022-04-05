package client.communication;

import static client.communication.CommunicationUtils.serverAddress;
import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.Response;
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
                .get(new GenericType<>() {});
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
                .get(new GenericType<>() {});
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

    /**
     * GET request that checks if game has started.
     * 418 - Started
     * Any other(404 or 400) - Not started
     * @param gameCode code of teh game to check
     * @return response
     */
    public static Response isStarted(String gameCode) {
        return ClientBuilder.newClient(new ClientConfig())
                .target(serverAddress).path("/api/user/start/" + gameCode + "/started")
                .request(APPLICATION_JSON)
                .get();
    }
}
