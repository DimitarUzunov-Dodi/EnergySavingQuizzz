package client.communication;

import commons.QuestionTypeA;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.core.GenericType;
import java.lang.reflect.Type;
import java.util.function.Consumer;
import org.glassfish.jersey.client.ClientConfig;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;
import static client.utils.ServerUtils.serverAddress;

public class GameCommunication {

    private static StompSession session;

    private static StompSession connect(String url) throws IllegalStateException{
        session = connect(serverAddress);
        var client = new StandardWebSocketClient();
        var stomp = new WebSocketStompClient(client);
        stomp.setMessageConverter(new MappingJackson2MessageConverter());
        try {
            return stomp.connect(url, new StompSessionHandlerAdapter() {}).get();
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException();
        }
    }

    public static <T> void  registerForMessages(String dest, Class<T> type, Consumer<T> consumer){
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

    public static void send(String dest, Object o){
        session.send(dest, o);
    }
    public static String startSinglePlayerGame(){
        return ClientBuilder.newClient(new ClientConfig())
                .target(serverAddress).path("/api/game/new")
                .request(APPLICATION_JSON)
                .get(new GenericType<>() {});
    }

    public static QuestionTypeA getQuestion(String gameCode, int qIndex) {
        return ClientBuilder.newClient(new ClientConfig())
                .target(serverAddress).path(String.format("/api/game/getq/%s/%d", gameCode, qIndex))
                .request(APPLICATION_JSON)
                .get(new GenericType<>() {});
    }
}
