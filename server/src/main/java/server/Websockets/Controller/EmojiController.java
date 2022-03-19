

package server.Websockets.Controller;


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
    public Pair<String, Integer> sendEmoji(Object o) {
        var emojiInfo = (Pair<String, Integer>) o;
        if (emojiInfo.getRight() == 1) {
            LOGGER.info("Emoji1 send");
        }
        if (emojiInfo.getRight() == 2) {
            LOGGER.info("Emoji2 send");
        }
        if (emojiInfo.getRight() == 3) {
            LOGGER.info("Emoji2 send");
        }
        return emojiInfo;

    }

}
