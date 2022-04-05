package client.communication;

import static client.communication.CommunicationUtils.serverAddress;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import commons.User;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CommunicationUtilsTest {
    public static HttpServer httpServer;
    public static String gameID = "111111";

    /**
     * Setups http server with similar mapping to game server
     * in order to simulate server responses.
     */
    @BeforeEach
    public void setup() {
        httpServer = null;
        try {
            httpServer = HttpServer.create(new InetSocketAddress(8080), 0);
            httpServer.createContext("/api/game/getUsers/ + gameID",
                    new HttpHandler() {
                        public void handle(HttpExchange exchange) throws IOException {
                            byte[] response = "Success".getBytes();
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
    public void getAllUsersTest() {
        serverAddress = "http://localhost:8080";
        Optional<List<User>> result = CommunicationUtils.getAllUsers(gameID);
        assertNotNull(result);
    }
}
