package server.websockets.handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;


public class GameWebsocketHandler extends TextWebSocketHandler {

    private static final String SERVER = "http://localhost:8080/";

    private static final Logger LOGGER = LoggerFactory.getLogger(GameWebsocketHandler.class);
    private int playersInLatestGame = 0;
    private final HashMap<String,ArrayList<WebSocketSession>> webSocketSessionList =
        new HashMap<String,ArrayList<WebSocketSession>>();



    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        Map<String, Object> properties = session.getAttributes();
        webSocketSessionList.put(session.getId(), new ArrayList<WebSocketSession>());



        LOGGER.info("Connection established");
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message)
        throws Exception {
        super.handleTextMessage(session, message);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status)
        throws Exception {
        webSocketSessionList.remove(session);
        LOGGER.info("Connection lost");
    }


}
