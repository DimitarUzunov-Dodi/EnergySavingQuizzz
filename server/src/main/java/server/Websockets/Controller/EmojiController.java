

package server.Websockets.Controller;


import commons.Person;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;


@Controller
public class EmojiController {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmojiController.class);

    @MessageMapping("/emoji")
    @SendTo("/emoji/receive")
    public Person sendEmoji(Person emojiInfo) throws Exception{

        if (emojiInfo.lastName.equals("emoji1")) {
            LOGGER.info("Emoji1 send by" + emojiInfo.firstName);
        }
        if (emojiInfo.lastName.equals("emoji2")) {
            LOGGER.info("Emoji2 send by" + emojiInfo.firstName);
        }
        if (emojiInfo.lastName.equals("emoji3")) {
            LOGGER.info("Emoji2 send by" + emojiInfo.firstName);
        }
        return emojiInfo;

    }

}
