package server.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import server.database.GameRepository;
import server.service.GameService;

import java.util.Random;

@RestController
@RequestMapping("/api/game")
public class GameController {

    @Autowired
    private final Random random;
    @Autowired
    private final GameRepository gameRepository;
    @Autowired
    private final GameService gameService;

    /**
     * Constructor for the GameController class.
     *
     * @param random Random instance for game code generation
     * @param gameRepository Game Repository
     */
    public GameController(Random random, GameRepository gameRepository, GameService gameService) {
        this.random = random;
        this.gameRepository = gameRepository;
        this.gameService = gameService;
    }


    /**
     * Creates a new game and saves it in the database.
     *
     * @return The saved game
     */
    @PostMapping("/new")
    public ResponseEntity<?> createGame () {
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

        if (gameService.doesGameExist(gameCode))
            return ResponseEntity
                    .badRequest()
                    .body("Game not found!");
        else {
            return ResponseEntity.ok(gameService.removeGame(gameCode));
        }
    }

    @GetMapping("/getq/{gameCode}/{qIndex}")
    public ResponseEntity<?> getQuestion(@PathVariable String gameCode, @PathVariable int qIndex) {
        if (!gameService.doesGameExist(gameCode))
            return ResponseEntity
                    .badRequest()
                    .body("Game not found!");
        else {
                return ResponseEntity.ok(gameService.getQuestion(gameCode, qIndex));
        }
    }

}
