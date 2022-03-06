package server.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.database.GameRepository;
import server.entities.Game;

import java.util.Optional;
import java.util.Random;

@RestController
@RequestMapping("/api/game")
public class GameController {

    private final Random random;
    private final GameRepository gameRepository;

    public GameController(Random random, GameRepository gameRepository) {
        this.random = random;
        this.gameRepository = gameRepository;
    }

    @PostMapping("/new")
    public ResponseEntity<?> createGame () {
        Game game = new Game();

        // this will convert any number sequence into 6 character.
        String code = String.format("%06d", random.nextInt(999999));

        while (gameRepository.findByGameCode(code).isPresent())
            code = String.format("%06d", random.nextInt(999999));

        if(gameRepository.findByGameCode(code).isEmpty())
            game.setGameCode(code);

        Game saved = gameRepository.save(game);
        return ResponseEntity.ok()
                .body(game);
    }

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
