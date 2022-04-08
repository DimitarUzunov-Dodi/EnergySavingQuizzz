package server.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import server.service.GameService;

@RestController
@RequestMapping("/api/game")
public class GameController {

    @Autowired
    private final GameService gameService;

    /**
     * Constructor for the GameController class.
     *
     * @param gameService Service
     */
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    /**
     * Creates a new game and saves it in the database.
     *
     * @return The saved game
     */
    @GetMapping("/new")
    public ResponseEntity<?> createGame() {
        System.out.println("ACTIVATED TO CREATE NEW GAME");
        return ResponseEntity.ok()
                .body(gameService.createGame());
    }

    /**
     * Deletes game from the database when it has ended.
     *
     * @param gameCode Unique game code
     * @return HTTP 200 Status if okay, error otherwise.
     */
    @DeleteMapping("/end/{gameCode}")
    public ResponseEntity<?>  endGame(@PathVariable String gameCode) {

        if (!gameService.doesGameExist(gameCode)) {
            return ResponseEntity
                    .badRequest()
                    .body("Game not found!");
        } else {
            return ResponseEntity.ok(gameService.removeGame(gameCode));
        }
    }

    /**
     *  Get question from list of generated ones.
     *
     * @param gameCode The game code for the specific game
     * @param questionIndex The index of the wanted question
     * @return The question entity
     */
    @GetMapping("/getq/{gameCode}/{questionIndex}")
    public ResponseEntity<?> getQuestion(@PathVariable String gameCode,
                                         @PathVariable int questionIndex) {
        if (!gameService.doesGameExist(gameCode)) {
            return ResponseEntity
                    .badRequest()
                    .body("Game not found!");
        } else {
            return ResponseEntity.ok(gameService.getQuestion(gameCode, questionIndex));
        }
    }

    /**
     * Get the correct answer from the question.
     *
     * @param gameCode The game code for the specific game
     * @param questionIndex The index of the wanted question
     * @return The correct answer(in energy consumption number)
     */
    @GetMapping("/getAnswer/{gameCode}/{questionIndex}")
    public ResponseEntity<?> getAnswer(@PathVariable String gameCode,
                                          @PathVariable int questionIndex) {
        if (!gameService.doesGameExist(gameCode)) {
            return ResponseEntity
                    .badRequest()
                    .body("Game not found!");
        } else {
            return ResponseEntity.ok(gameService.getCorrectAnswer(gameCode, questionIndex));
        }
    }

    /**
     * Process the answer and give bonus points.
     *
     * @param gameCode The game code for the specific game
     * @param username The username
     * @param questionIndex The index of the wanted question
     * @param answer correct answer(in energy consumption number) to the question asked
     * @param time the time spent on giving the answer
     * @return bonus points achieved
     */
    @GetMapping("/processAnswer")
    public ResponseEntity<?> processAnswer(@RequestParam(name = "gameCode") String gameCode,
                                           @RequestParam(name = "username") String username,
                                           @RequestParam(name = "questionIndex") int questionIndex,
                                           @RequestParam(name = "answer") long answer,
                                           @RequestParam(name = "time") long time) {
        if (!(gameService.doesGameExist(gameCode))) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("No game found with this game code!");
        } else if (!gameService.isUsernamePresent(gameCode, username)) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Username is not present in the game!");
        } else {
            return ResponseEntity.ok(
                    gameService.processAnswer(gameCode, username, questionIndex, answer, time)
            );
        }

    }

    /**
     * Gets all the users in a game.
     *
     * @param gameCode The unique gameCode
     * @return List of users
     */
    @GetMapping("/getUsers/{gameCode}")
    public ResponseEntity<?> getUsersInGame(@PathVariable String gameCode) {
        if (!gameService.doesGameExist(gameCode)) {
            return ResponseEntity
                    .badRequest()
                    .body("Game not found!");
        } else {
            return ResponseEntity.ok(gameService.getUsers(gameCode));
        }
    }

    /**
     * GET mapping that gets gamecode of a public game.
     *
     * @return ResponseEntity
     */
    @GetMapping("/get/public")
    public ResponseEntity<?> getPublicCode() {
        if (gameService.getCurrentPublicGame().equals("")) {
            gameService.setCurrentPublicGame(gameService.createGame());
        }
        return ResponseEntity.ok().body(gameService.getCurrentPublicGame());
    }

}
