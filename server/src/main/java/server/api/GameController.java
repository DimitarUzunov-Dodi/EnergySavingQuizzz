package server.api;

import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import server.service.GameService;

@RestController
@RequestMapping("/api/game")
public class GameController {

    @Autowired
    private final Random random;
    @Autowired
    private final GameService gameService;

    /**
     * Constructor for the GameController class.
     *
     * @param random Random instance for game code generation
     * @param gameService Service
     */
    public GameController(Random random, GameService gameService) {
        this.random = random;
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

        if (gameService.doesGameExist(gameCode)) {
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
        return ResponseEntity.ok().body(gameService.getCurrentPublicGame());
    }

}
