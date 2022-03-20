

package server.Websockets.Controller;


import commons.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import server.database.GameRepository;
import commons.Game;
import server.service.GameService;

import java.util.Random;


@Controller
public class EmojiController {

    @Autowired
    private final Random random;
    @Autowired
    private final GameRepository gameRepository;
    @Autowired
    private final GameService gameService;

    private Game currentGame;

    public EmojiController(Random random, GameRepository gameRepository, GameService gameService){
        this.random = random;
        this.gameRepository = gameRepository;
        this.gameService = gameService;
        this.currentGame = GetGame();

    }

    private static final Logger LOGGER = LoggerFactory.getLogger(EmojiController.class);
    private static final String SERVER = "http://localhost:8080/";



    private int PlayersInCurrentGame = 0;

    public Game GetGame(){
       Game game = gameService.createGame();
       return game;

    }

    /**
     * sends the appropriate emoji to the clients of all websockets connected
     *
     * @return
     * @throws Exception
     */
    @MessageMapping("/game")
    @SendTo("/game/receive")
    public Game sendGame() throws Exception {

        PlayersInCurrentGame++;
        return currentGame;

    }


    /**
     * sends the appropriate emoji to the clients of all websockets connected
     *
     * @param emojiInfo
     * @return
     * @throws Exception
     */
    @MessageMapping("/emoji")
    @SendTo("/emoji/receive")
    public Person sendEmoji(Person emojiInfo) throws Exception {

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
