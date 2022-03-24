package client.communication;

import static client.utils.ServerUtils.serverAddress;
import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

import commons.QuestionTypeA;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.core.GenericType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.function.Consumer;
import org.glassfish.jersey.client.ClientConfig;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

public class GameCommunication {

    private static StompSession session;


    /**
     * Method that connects the client to the websocket session.
     * @param url Url to connect to
     * @param properties Properties that we want to send to server
     * @throws IllegalStateException IllegalStateException
     */
    public static void connect(String url, HashMap<String, Object> properties) throws IllegalStateException {
        var client = new StandardWebSocketClient();
        var stomp = new WebSocketStompClient(client);
        stomp.setMessageConverter(new MappingJackson2MessageConverter());

        client.setUserProperties(properties);

        try {
            session = stomp.connect(serverAddress.replace("http", "ws") + "/game",
                new StompSessionHandlerAdapter() {}).get();
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException();
        }
    }

    /**
     * subscribes the client to channels to receive objects from the server.
     * @param dest destination to subscribe to
     * @param type class to receive
     * @param consumer consumer that handles receiving the class
     * @param <T> the class
     */
    public static <T> void  registerForMessages(String dest, Class<T> type, Consumer<T> consumer) {
        session.subscribe(dest, new StompFrameHandler() {
            @Override
            public Type getPayloadType(StompHeaders headers) {
                return type;
            }

            @SuppressWarnings("unchecked")
            @Override
            public void handleFrame(StompHeaders headers, Object payload) {
                consumer.accept((T) payload);
            }
        });
    }

    public static void send(String dest, Object o) {
        session.send(dest, o);
    }

    /**
     *  Communication method for starting a new game.
     *
     * @return The game code of the newly created game.
     */
    public static String startSinglePlayerGame() {
        return ClientBuilder.newClient(new ClientConfig())
                .target(serverAddress).path("/api/game/new")
                .request(APPLICATION_JSON)
                .get(new GenericType<>() {});
    }

    /**
     *  Communication method for getting a question.
     *
     * @param gameCode Specify the game code for which to get questions
     * @param questionIndex The question io get out of 20
     * @return The question entity
     */
    public static QuestionTypeA getQuestion(String gameCode, int questionIndex) {
        return ClientBuilder.newClient(new ClientConfig())
                .target(serverAddress)
                .path(String.format("/api/game/getq/%s/%d", gameCode, questionIndex))
                .request(APPLICATION_JSON)
                .get(new GenericType<>() {});
    }
}
