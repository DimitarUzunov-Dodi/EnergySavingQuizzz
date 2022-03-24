package client.utils;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

import commons.ScoreRecord;
import commons.ServerLeaderboardEntry;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.core.GenericType;
import java.util.List;
import org.glassfish.jersey.client.ClientConfig;

public class ServerUtils {

    public static String serverAddress;

    /**
     * Retrieve the all-time leaderboard from the server.
     * @return List of server leaderboard entries
     */
    public static List<ServerLeaderboardEntry> getServerLeaderboard() throws RuntimeException {
        try {
            return ClientBuilder.newClient(new ClientConfig())
                    .target(serverAddress).path("/api/leaderboard")
                    .request(APPLICATION_JSON)
                    .get(new GenericType<>() {});
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("Cannot get server leaderboard!");
        }

    }

    /**
     * Retrieve a match leaderboard from the server.
     * @return List of player score entries
     */
    public static List<ScoreRecord> getMatchLeaderboard(String gameCode) throws RuntimeException {
        try {
            return ClientBuilder.newClient(new ClientConfig())
                    .target(serverAddress).path("/api/user/score/" + gameCode)
                    .request(APPLICATION_JSON)
                    .get(new GenericType<>() {});
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Cannot get server leaderboard!");
        }
    }
}
