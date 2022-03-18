package server.Websockets.Handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import server.Websockets.Controller.WebsocketEventListener;

import javax.websocket.Session;
import java.util.ArrayList;
import java.util.List;

public class GameWebsocketHandler extends TextWebSocketHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GameWebsocketHandler.class);

    private final List<WebSocketSession> webSocketSessionList = new ArrayList<>();
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
       webSocketSessionList.add(session);
       LOGGER.info("Connection established dudoi");
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        super.handleTextMessage(session, message);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        webSocketSessionList.remove(session);
        LOGGER.info("Connection lost");
    }


}
