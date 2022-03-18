

package server.Websockets.Controller;

import commons.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;



@Controller
public class EmojiController {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmojiController.class);

    @MessageMapping("/emoji")
    @SendTo("/emoji/receive")
    public Person sendEmoji(String s) {
        LOGGER.info("BRRAAAAAAT");
        return new Person(s, s);
    }

}
