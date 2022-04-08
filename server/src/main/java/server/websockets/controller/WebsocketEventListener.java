package server.websockets.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
public class WebsocketEventListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebsocketEventListener.class);

    @Autowired
    private SimpMessageSendingOperations sendingOperations;

    /**
     * method that handles client connection.
     * @param event Event that happens when client connects
     */
    @EventListener
    public void handleWebSocketEventListener(final SessionConnectedEvent event) {
        sendingOperations.convertAndSend("/game", "we did it");
    }

    /**
     * method that handles client disconnecting.
     * @param event Event that happens when client disconnects
     */
    @EventListener
    public void handWebSocketDisconnect(final SessionDisconnectEvent event) {
        final StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

        final String username = (String) headerAccessor.getSessionAttributes().get("username");

        final String string = "we did it";

        sendingOperations.convertAndSend("/game", string);
    }
}
