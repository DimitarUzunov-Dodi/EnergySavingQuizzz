

package server.websockets.Controller;


import commons.Game;
import commons.Person;
import java.util.Random;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import server.service.GameService;



@Controller
public class EmojiController {

    @Autowired
    private final Random random;
    @Autowired
    private final GameService gameService;

    private Game currentGame;

    /**
     * Constructor for the EmojiController.
     * @param random the required random
     * @param gameService the Gameservice to retrieve game creation methods from
     */
    public EmojiController(Random random, GameService gameService) {
        this.random = random;
        this.gameService = gameService;
        //this.currentGame = getGame();

    }



    private static final Logger LOGGER = LoggerFactory.getLogger(EmojiController.class);
    private static final String SERVER = "http://localhost:8080/";



    private int playersInCurrentGame = 0;

    public Game getGame() {
        String gameCode = gameService.createGame();
        return gameService.getGame(gameCode);
    }

    /**
     * sends the appropriate emoji to the clients of all websockets connected.
     *
     * @return the retrieved game
     * @throws Exception Exception
     */
    @MessageMapping("/game")
    @SendTo("/game/receive")
    public Game sendGame() throws Exception {

        playersInCurrentGame++;
        return currentGame;

    }




    /**
     * sends the appropriate emoji to the clients of all websockets connected.
     *
     * @param emojiInfo the recieved emoji
     * @return the sent emoji
     * @throws Exception Exception
     */
    @MessageMapping("/emoji/{gameID}/{username}")
    @SendTo("/emoji/receive/{gameID}/{username}" )
    public Person sendEmoji(@DestinationVariable String gameID, @DestinationVariable String username, Person emojiInfo) throws Exception {
        System.out.println(gameID);
        System.out.println(gameID);
        LOGGER.info(gameID);
        System.out.println("fuck");
        if (emojiInfo.lastName.equals("emoji1")) {
            LOGGER.info("Emoji1 send by" + emojiInfo.firstName);
        }
        if (emojiInfo.lastName.equals("emoji2")) {
            LOGGER.info("Emoji2 send by" + emojiInfo.firstName);
        }
        if (emojiInfo.lastName.equals("emoji3")) {
            LOGGER.info("Emoji3 send by" + emojiInfo.firstName);
        }
        return emojiInfo;

    }

}
