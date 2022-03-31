package server.websockets.controller;

import commons.Game;
import commons.EmojiMessage;
import java.util.Date;
import java.util.HashMap;
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

    private HashMap<String,HashMap<String, HashMap<String, Object>>> webSocketSessionList =
        new HashMap<String,HashMap<String, HashMap<String, Object>>>();
    private HashMap<String, HashMap<Integer, Long>> gameTimes
        = new HashMap<String, HashMap<Integer, Long>>();


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

    private int playersInCurrentGame = 0;

    public Game getGame() {
        String gameCode = gameService.createGame();
        return gameService.getGame(gameCode);
    }

    /**
     * creates the time for a game.
     *
     * @throws Exception Exception
     */
    @MessageMapping("/time/{currentGameID}/{questionNumber}")
    public void createDate(@DestinationVariable String currentGameID,
                           @DestinationVariable Integer questionNumber) throws Exception {
        LOGGER.info("creating time");


        Long time = new Date().getTime();
        if (gameTimes.get(currentGameID) == null) {
            gameTimes.put(currentGameID, new HashMap<Integer,Long>());
        }
        gameTimes.get(currentGameID).putIfAbsent(questionNumber, time);
        LOGGER.info(gameTimes.get(currentGameID).get(questionNumber).toString());
        LOGGER.info(questionNumber.toString());



    }


    /**
     * deals with retrieving correct time from server.
     * @param currentGameID the gameID
     * @param questionNumber the questionNumber
     * @return long representing the date
     * @throws Exception Exception
     */
    @MessageMapping("/time/get/{currentGameID}/{questionNumber}")
    @SendTo("/time/get/receive/{currentGameID}")
    public long getDate(@DestinationVariable String currentGameID,
                        @DestinationVariable Integer questionNumber) throws Exception {
        LOGGER.info("getting time");
        LOGGER.info(questionNumber.toString());

        return gameTimes.get(currentGameID).get(questionNumber);
    }

    /**
     * puts the user in a hashmap.
     * @param gameID the ID used to identify the game
     * @param username the name of the user
     * @param properties any properties that we want to associate with the user
     * @return the properties associated with the user
     * @throws Exception Exception
     */
    @MessageMapping("/game/{gameID}/{username}")
    @SendTo("/game/receive/{gameID}/{username}")
    public HashMap<String, Object> userConfig(
        @DestinationVariable String gameID, @DestinationVariable String username,
                                              HashMap<String, Object> properties)
        throws Exception {
        HashMap<String, Object> inner = properties;
        HashMap<String, HashMap<String, Object>> middle =
            new HashMap<String, HashMap<String, Object>>();
        middle.put(username, properties);
        HashMap<String, HashMap<String, HashMap<String, Object>>> outer =
            new HashMap<String, HashMap<String, HashMap<String, Object>>>();
        webSocketSessionList.put(gameID, middle);

        System.out.println(gameID);
        System.out.println(gameID);
        LOGGER.info(gameID);
        LOGGER.info("fcucucucucu");
        LOGGER.info(webSocketSessionList.toString());
        return properties;

    }


    /**
     * sends the appropriate emoji to the clients of all websockets connected.
     *
     * @param emojiInfo the recieved emoji
     * @return the sent emoji
     * @throws Exception Exception
     */
    @MessageMapping("/emoji/{gameID}/{username}")
    @SendTo("/emoji/receive/{gameID}")
    public EmojiMessage sendEmoji(
        @DestinationVariable String gameID, @DestinationVariable String username,
        EmojiMessage emojiInfo) throws Exception {

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
