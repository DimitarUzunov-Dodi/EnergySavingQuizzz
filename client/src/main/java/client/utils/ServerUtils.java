/*
 * Copyright 2021 Delft University of Technology
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package client.utils;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompSession;
import commons.LeaderboardEntry;
import commons.ScoreRecord;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.core.GenericType;
import org.glassfish.jersey.client.ClientConfig;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.util.List;
import java.util.concurrent.ExecutionException;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

public class ServerUtils {

    private static final String SERVER = "http://localhost:8080/";

    /**
     * Retrieve the all-time leaderboard from the server
     * @return List of server leaderboard entries
     */
    public List<LeaderboardEntry> getServerLeaderboard() {
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("api/leaderboard")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .get(new GenericType<List<LeaderboardEntry>>() {});
    }

    private StompSession session = connect("ws://localhost:8080/websocket");

    private StompSession connect(String s) {
        var client = new StandardWebSocketClient();
        var stomp = new WebSocketStompClient(client);
        stomp.setMessageConverter(new MappingJackson2MessageConverter());
        try {
            return stomp.connect(SERVER, new StompSessionHandlerAdapter() {}).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        throw new IllegalStateException();

    }


    /**
     * Retrieve a match leaderboard from the server
     * @return List of player score entries
     */
    public List<ScoreRecord> getMatchLeaderboard(String gameCode) {
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