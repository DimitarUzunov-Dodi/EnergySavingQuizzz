package server.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import server.database.GameRepository;
import server.entities.Game;

import java.util.Optional;
import java.util.Random;

@RestController
@RequestMapping("/api/game")
public class GameController {

    @Autowired
    private final Random random;
    @Autowired
    private final GameRepository gameRepository;

    /**
     * Constructor for the GameController class.
     *
     * @param random Random instance for game code generation
     * @param GameRepository Game Repository
     */
    public GameController(Random random, GameRepository gameRepository) {
        this.random = random;
        this.gameRepository = gameRepository;
    }

    /**
     * Creates a new game and saves it in the database.
     *
     * @return The saved game
     */
    @PostMapping("/new")
    public ResponseEntity<?> createGame () {
        Game game = new Game();

        // Generating a random six digit number.
        String code = String.format("%06d", random.nextInt(999999));

        // In case the code is already present in the database, get another.
        //TODO: implement time-out
        while (gameRepository.findByGameCode(code).isPresent())
            code = String.format("%06d", random.nextInt(999999));

        if(gameRepository.findByGameCode(code).isEmpty())
            game.setGameCode(code);

        Game saved = gameRepository.save(game);
        return ResponseEntity.ok()
                .body(game);
    }

    /**
     * Deletes game from the database when it has ended.
     *
     * @param gameCode Unique game code
     * @return HTTP 200 Status if okay, error otherwise.
     */
    @DeleteMapping("/end/{gameCode}")
    public ResponseEntity<?>  endGame(@PathVariable String gameCode) {
        Optional<Game> game = gameRepository.findByGameCode(gameCode);

        if (game.isEmpty())
            return ResponseEntity
                    .badRequest()
                    .body("Game not found!");
        else {
            gameRepository.deleteById(gameCode);
            return ResponseEntity.ok(gameCode);
        }
    }

}
