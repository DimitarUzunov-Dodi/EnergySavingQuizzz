package server.websockets.controller;
import commons.EmojiMessage;
import commons.Game;
import commons.TaskScheduler;
import commons.WsGame;
import java.time.Instant;
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

    // used to schedule and execute all sorts of stuff (e.g. end the current round)
    public static final TaskScheduler scheduler = new TaskScheduler(1);

    private HashMap<String,HashMap<String, HashMap<String, Object>>> webSocketSessionList =
        new HashMap<String,HashMap<String, HashMap<String, Object>>>();
    private HashMap<String, HashMap<Integer, WsGame>> gameTimes
        = new HashMap<String, HashMap<Integer, WsGame>>();

    private HashMap<String, HashMap<Integer, Integer>> userInputs
        = new HashMap<String, HashMap<Integer, Integer>>();


    /**
     * Constructor for the EmojiController.
     * @param random the required random
     * @param gameService the Gameservice to retrieve game creation methods from
     */
    public EmojiController(Random random, GameService gameService) {
        this.random = random;
        this.gameService = gameService;
        checkActiveGames();
        //this.currentGame = getGame();

    }

    private static final Logger LOGGER = LoggerFactory.getLogger(EmojiController.class);

    private int playersInCurrentGame = 0;

    public Game getGame() {
        String gameCode = gameService.createGame();
        return gameService.getGame(gameCode);
    }

    public void checkActiveGames(){
        scheduler.scheduleAtFixedRate(() -> {
            for (String gameID: webSocketSessionList.keySet()){
                if (gameService.getGame(gameID).getUserList().size() == 0){
                    gameService.removeGame(gameID);
                    webSocketSessionList.remove(gameID);

                }
            }
        }, 1000);

    }


    /**
     * creates the time for a game.
     *
     * @throws Exception Exception
     */
    @MessageMapping("/time/{currentGameID}/{questionNumber}")
    @SendTo("/foo")
    public void createDate(@DestinationVariable String currentGameID,
                           @DestinationVariable Integer questionNumber) throws Exception {

        LOGGER.info("creating time");
        LOGGER.info(currentGameID + "  " + questionNumber);

        WsGame time = new WsGame(Instant.now(), Instant.now().plusSeconds(gameService
            .getQuestion(currentGameID, questionNumber)
            .getDuration().toSeconds()));
        if (!gameTimes.containsKey(currentGameID)) {
            LOGGER.info("poo");
            gameTimes.put(currentGameID, new HashMap<Integer,WsGame>());
        }
        gameTimes.get(currentGameID).putIfAbsent(questionNumber, time);
        LOGGER.info(gameTimes.get(currentGameID).get(questionNumber).toString());
        LOGGER.info(questionNumber.toString() + "is questionNumber");
        LOGGER.info(currentGameID + " is gameID");



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
    public Long[] getDate(@DestinationVariable String currentGameID,
                        @DestinationVariable Integer questionNumber, String foo) throws Exception {

        LOGGER.info("getting time");
        if (!gameTimes.containsKey(currentGameID)) {
            LOGGER.info("poo");
            gameTimes.put(currentGameID, new HashMap<Integer,WsGame>());
        }
        if (!userInputs.containsKey(currentGameID)) {
            userInputs.put(currentGameID, new HashMap<Integer,Integer>());
        }
        if (!userInputs.get(currentGameID).containsKey(questionNumber)) {
            userInputs.get(currentGameID).put(questionNumber, 0);
        }

        Integer last = userInputs.get(currentGameID).get(questionNumber);
        userInputs.get(currentGameID).put(questionNumber, last + 1);
        if (userInputs.get(currentGameID).get(questionNumber)
            == gameService.getGame(currentGameID).getUserList().size()) {
            Instant endTime = Instant.now().plusSeconds(gameService
                .getQuestion(currentGameID, questionNumber)
                .getDuration().toSeconds());
            Instant startTime = endTime.plusSeconds(8);
            WsGame time = new WsGame(startTime, endTime);
            if (questionNumber != 0) {
                LOGGER.info("plus 12");
                time.endTime = time.endTime.plusSeconds(6);
                time.startTime = time.startTime.plusSeconds(6);
            }
            gameTimes.get(currentGameID).putIfAbsent(questionNumber, time);
            LOGGER.info("SENDING CHECK");
            Long[] array = new Long[]{gameTimes.get(currentGameID)
                .get(questionNumber).startTime.toEpochMilli(),gameTimes.get(currentGameID)
                .get(questionNumber).endTime.toEpochMilli()};
            return array;
        }






        return null;

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
        System.out.println("ACtivated");
        LOGGER.info(gameID);
        System.out.println("fuck");
        if (emojiInfo.emojiID.equals("emoji1")) {
            LOGGER.info("Emoji1 send by" + emojiInfo.username);
        }
        if (emojiInfo.emojiID.equals("emoji2")) {
            LOGGER.info("Emoji2 send by" + emojiInfo.username);
        }
        if (emojiInfo.emojiID.equals("emoji3")) {
            LOGGER.info("Emoji3 send by" + emojiInfo.username);
        }
        return emojiInfo;

    }

}
