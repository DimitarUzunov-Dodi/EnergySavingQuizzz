package client.utils;

import commons.LeaderboardEntry;
import commons.ScoreRecord;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.core.GenericType;
import org.glassfish.jersey.client.ClientConfig;

import java.util.List;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

public class ServerUtils {

    private static final String SERVER = "http://localhost:8080/";

    /**
     * Retrieve the all-time leaderboard from the server
     * @return List of server leaderboard entries
     */
    public static List<LeaderboardEntry> getServerLeaderboard() {
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("api/leaderboard")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .get(new GenericType<List<LeaderboardEntry>>() {});
    }




    /**
     * Retrieve a match leaderboard from the server
     * @return List of player score entries
     */
    public static List<ScoreRecord> getMatchLeaderboard(String gameCode) {
        try {
            return ClientBuilder.newClient(new ClientConfig())
                    .target(SERVER).path("api/user/score/"+gameCode)
                    .request(APPLICATION_JSON)
                    .accept(APPLICATION_JSON)
                    .get(new GenericType<List<ScoreRecord>>() {});
        } catch (Exception e) {
            System.err.println("getMatchLeaderboard: "+e);
            return null;
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
