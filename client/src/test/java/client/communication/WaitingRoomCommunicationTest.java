package client.communication;

import static client.communication.CommunicationUtils.serverAddress;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import jakarta.ws.rs.core.Response;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class WaitingRoomCommunicationTest {
    public static HttpServer httpServer;
    public static String connectionGameCode = "333333";
    public static String connectionUsername = "username";

    /**
     * Setups http server with similar mapping to game server
     * in order to simulate server responses.
     */
    @BeforeEach
    public void setup() {
        httpServer = null;
        try {
            httpServer = HttpServer.create(new InetSocketAddress(8080), 0);
            httpServer.createContext("/api/game/new",
                new HttpHandler() {
                    public void handle(HttpExchange exchange) throws IOException {
                        byte[] response = "000000".getBytes();
                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, response.length);
                        exchange.getResponseBody().write(response);
                        exchange.close();
                    }
                }
            );
            httpServer.createContext("/api/user/join/" + connectionGameCode
                            + "/" + connectionUsername,
                new HttpHandler() {
                    public void handle(HttpExchange exchange) throws IOException {
                        byte[] response = "0".getBytes();
                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, response.length);
                        exchange.getResponseBody().write(response);
                        exchange.close();
                    }
                }
            );
            httpServer.createContext("/api/user/leave/" + connectionGameCode
                            + "/" + connectionUsername,
                new HttpHandler() {
                    public void handle(HttpExchange exchange) throws IOException {
                        byte[] response = "0".getBytes();
                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, response.length);
                        exchange.getResponseBody().write(response);
                        exchange.close();
                    }
                }
            );
            httpServer.createContext("/api/user/start/" + connectionGameCode,
                new HttpHandler() {
                    public void handle(HttpExchange exchange) throws IOException {
                        byte[] response = "0".getBytes();
                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, response.length);
                        exchange.getResponseBody().write(response);
                        exchange.close();
                    }
                }
            );
            httpServer.createContext("/api/user/start/" + connectionGameCode
                            + "/started",
                new HttpHandler() {
                    public void handle(HttpExchange exchange) throws IOException {
                        byte[] response = "0".getBytes();
                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST,
                                response.length);
                        exchange.getResponseBody().write(response);
                        exchange.close();
                    }
                }
            );
            httpServer.createContext("/api/game/get/public",
                new HttpHandler() {
                    public void handle(HttpExchange exchange) throws IOException {
                            byte[] response = "123456".getBytes();
                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, response.length);
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
    public void gameCreateRoomTest() {
        serverAddress = "http://localhost:8080";
        String result = WaitingRoomCommunication.createNewGame();
        assertEquals("000000", result);
    }

    @Test
    public void getPublicCodeTest() {
        serverAddress = "http://localhost:8080";
        String result = WaitingRoomCommunication.getPublicCode();
        assertEquals("123456", result);
    }

    @Test
    public void joinGameTest() {
        serverAddress = "http://localhost:8080";
        Response result = WaitingRoomCommunication.joinGame(connectionGameCode,
                connectionUsername);
        assertEquals(200, result.getStatus());
    }

    @Test
    public void leaveGameTest() {
        serverAddress = "http://localhost:8080";
        Response result = WaitingRoomCommunication.leaveGame(connectionGameCode,
                connectionUsername);
        assertEquals(200, result.getStatus());
    }

    @Test
    public void startGameTest() {
        serverAddress = "http://localhost:8080";
        Response result = WaitingRoomCommunication.startGame(connectionGameCode);
        assertEquals(200, result.getStatus());
    }

    @Test
    public void isStartedTest() {
        serverAddress = "http://localhost:8080";
        Response result = WaitingRoomCommunication.isStarted(connectionGameCode);
        assertEquals(400, result.getStatus());
    }
}
