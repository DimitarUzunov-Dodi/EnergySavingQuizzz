package client.utils;

import commons.ScoreRecord;
import commons.ServerLeaderboardEntry;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.core.GenericType;
import org.glassfish.jersey.client.ClientConfig;

import java.util.List;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

public class ServerUtils {

    public static String serverAddress;

    /**
     * Retrieve the all-time leaderboard from the server
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
     * Retrieve a match leaderboard from the server
     * @return List of player score entries
     */
    public static List<ScoreRecord> getMatchLeaderboard(String gameCode)  throws RuntimeException{
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

/* LEFT HERE FOR REFERENCE
    public void getQuotesTheHardWay() throws IOException {
        var url = new URL("http://localhost:8080/api/quotes");
        var is = url.openConnection().getInputStream();
        var br = new BufferedReader(new InputStreamReader(is));
        String line;
        while ((line = br.readLine()) != null) {
            System.out.println(line);
        }
    }

    public Quote addQuote(Quote quote) {
        return ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("api/quotes") //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .post(Entity.entity(quote, APPLICATION_JSON), Quote.class);
    }
*/
}
