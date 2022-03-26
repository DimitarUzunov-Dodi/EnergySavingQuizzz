package server.websockets.Controller;


import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WebsocketController {
    @SendTo("/topic/chat")
    @MessageMapping("/chat") // app/chat
    public String sendString(@Payload final String string) {
        return string;
    }


}
