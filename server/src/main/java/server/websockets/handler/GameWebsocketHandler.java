package server.websockets.handler;


import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.WebSocketHandler;


public class GameWebsocketHandler implements WebSocketHandler {

    private static final String SERVER = "http://localhost:8080/";

    private static final Logger LOGGER = LoggerFactory.getLogger(GameWebsocketHandler.class);
    private int playersInLatestGame = 0;
    private HashMap<String,HashMap<String, HashMap<String, Object>>> webSocketSessionList =
        new HashMap<String,HashMap<String, HashMap<String, Object>>>();



    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        Map<String, Object> properties = session.getAttributes();
        System.out.println(session.getAttributes());
        System.out.println("fooooo");
        webSocketSessionList.put(session.getId(), new HashMap<String, HashMap<String, Object>>());



      //  LOGGER.info("Connection established");
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message)
        throws Exception {

    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception)
        throws Exception {

    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status)
        throws Exception {
        webSocketSessionList.remove(session);
      //  LOGGER.info("Connection lost");
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }


}
