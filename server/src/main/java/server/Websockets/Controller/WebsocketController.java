package server.Websockets.Controller;


import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WebsocketController {
    @MessageMapping("/chat") // app/chat
    @SendTo("/topic/chat")
    public String sendString(@Payload final String string) {
        return string;
    }


}
