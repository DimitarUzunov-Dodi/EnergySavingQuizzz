package client.communication;

import static client.communication.CommunicationUtils.serverAddress;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import commons.ServerLeaderboardEntry;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LeaderboardCommunicationTest {
    public static HttpServer httpServer;
    public static String username = "1";
    public static int score = 1337;
    public static int games = 5;



    /**
     * Setups http server with similar mapping to game server
     * in order to simulate server responses.
     */
    @BeforeEach
    public void setup() {
        httpServer = null;
        try {
            httpServer = HttpServer.create(new InetSocketAddress(8080), 0);
            httpServer.createContext("/api/leaderboard/",
                    new HttpHandler() {
                        public void handle(HttpExchange exchange) throws IOException {
                            byte[] response = "".getBytes();
                            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK,
                                    response.length);
                            exchange.getResponseBody().write(response);
                            exchange.close();
                        }
                    }
            );
            httpServer.createContext("/api/leaderboard/" + username,
                    new HttpHandler() {
                        public void handle(HttpExchange exchange) throws IOException {
                            byte[] response = "".getBytes();
                            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK,
                                    response.length);
                            exchange.getResponseBody().write(response);
                            exchange.close();
                        }
                    }
            );
            httpServer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @AfterEach
    public void close() {
        httpServer.stop(0);
    }


    @Test
    public void getServerLeaderboardTest() {
        serverAddress = "http://localhost:8080";
        List<ServerLeaderboardEntry> result = LeaderboardCommunication.getServerLeaderboard();
        assertNull(result);
    }

    @Test
    public void updateServerLeaderboard() {
        serverAddress = "http://localhost:8080";
        LeaderboardCommunication.updateServerLeaderboard(username, games, score);
    }
}
