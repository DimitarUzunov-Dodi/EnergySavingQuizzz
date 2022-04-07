package client.communication;

import static client.communication.CommunicationUtils.serverAddress;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import commons.Activity;
import jakarta.ws.rs.core.Response;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AdminCommunicationTest {
    public static HttpServer httpServer;
    public static String activityId = "1";


    /**
     * Setups http server with similar mapping to game server
     * in order to simulate server responses.
     */
    @BeforeEach
    public void setup() {
        httpServer = null;
        try {
            httpServer = HttpServer.create(new InetSocketAddress(8080), 0);
            httpServer.createContext("/api/admin/restart",
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
            httpServer.createContext("/api/admin/activity/all",
                    new HttpHandler() {
                        public void handle(HttpExchange exchange) throws IOException {
                            byte[] response = new byte[0];
                            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK,
                                    response.length);
                            exchange.getResponseBody().write(response);
                            exchange.close();
                        }
                    }
            );
            httpServer.createContext("/api/admin/activity/delete/all",
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
            httpServer.createContext("/api/admin/activity/edit/" + activityId,
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
            httpServer.createContext("/api/admin/activity/add",
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
    public void restartServerTest() {
        serverAddress = "http://localhost:8080";
        Response result = AdminCommunication.invokeServerRestart();
        assertEquals("Success".length(), result.getLength());
    }

    @Test
    public void getAllActivitiesTest() {
        serverAddress = "http://localhost:8080";
        List<Activity> result = AdminCommunication.getAllActivities();
        assertNull(result);
    }

    @Test
    public void deleteActivitiesTest() {
        serverAddress = "http://localhost:8080";
        Response result = AdminCommunication.deleteActivities();
        assertEquals("Success".length(), result.getLength());
    }

    @Test
    public void editActivityTest() {
        serverAddress = "http://localhost:8080";
        Response result = AdminCommunication.editActivity(1,
                new Activity(Long.parseLong(activityId), "1",23, "1", 1));
        assertEquals("Success".length(), result.getLength());
    }

    @Test
    public void addActivityBankEntryTest() {
        serverAddress = "http://localhost:8080";
        Response result = AdminCommunication.addActivityBankEntry(null, 1);
        assertEquals("Success".length(), result.getLength());
    }
}
