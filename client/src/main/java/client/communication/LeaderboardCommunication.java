package client.communication;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

import commons.ServerLeaderboardEntry;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.GenericType;
import java.util.List;
import org.glassfish.jersey.client.ClientConfig;

public class LeaderboardCommunication {

    public static String serverAddress;

    /**
     * Retrieve the all-time leaderboard from the server.
     * @return List of server leaderboard entries
     */
    public static List<ServerLeaderboardEntry> getServerLeaderboard() throws RuntimeException {
        try {
            return ClientBuilder.newClient(new ClientConfig())
                    .target(serverAddress).path("/api/leaderboard/")
                    .request(APPLICATION_JSON)
                    .get(new GenericType<>() {});
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("Cannot get server leaderboard!");
        }

    }

    /**
     * Add/Update a leaderboard record.
     * @param username player username
     * @param games number of games played since last update from the db
     * @param score score earned since last update from the db
     * @throws RuntimeException see stacktrace for details
     */
    public static void updateServerLeaderboard(String username, int games, int score)
            throws RuntimeException {
        System.out.println("+ <" + username + ", " + games + ", " + score + ">");
        if (username == null || username.isEmpty()) {
            throw new RuntimeException("Invalid username");
        }
        try {
            ServerLeaderboardEntry entry = new ServerLeaderboardEntry(username, games, score);
            ClientBuilder.newClient(new ClientConfig())
                    .target(serverAddress).path("/api/leaderboard/" + username)
                    .request(APPLICATION_JSON)
                    .put(Entity.entity(entry, APPLICATION_JSON));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
