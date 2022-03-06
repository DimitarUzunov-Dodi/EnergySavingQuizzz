package server.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.database.GameRepository;
import server.database.UserRepository;
import server.entities.Game;
import server.entities.User;

import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final GameRepository gameRepository;
    private final UserRepository userRepository;

    public UserController(GameRepository gameRepository, UserRepository userRepository) {
        this.gameRepository = gameRepository;
        this.userRepository = userRepository;
    }

    @PostMapping("/join/{gameCode}/{username}")
    public ResponseEntity<?> joinGame(@PathVariable String gameCode,
                                      @PathVariable String username) {
        Optional<Game> game = gameRepository.findById(gameCode);
        if (game.isEmpty())
            return ResponseEntity
                    .badRequest()
                    .body("No game found with this game code!");
        else {
            Optional<User> user = userRepository.findByGameCodeAndUsername(gameCode, username) ;
            if (user.isPresent())
                return ResponseEntity
                        .badRequest()
                        .body("Username already in use in this game! Please change it and try again!");
            else {
                User user1 = new User(gameCode, username);

                User saved = userRepository.save(user1);
                return ResponseEntity.ok()
                        .body(saved);
            }
        }
    }

    @PutMapping("/score/{gameCode}/{username}/{newScore}")
    public ResponseEntity<?> updateScore(@PathVariable String gameCode,
                                         @PathVariable String username, @PathVariable int newScore) {
        Optional<Game> game = gameRepository.findById(gameCode);
        if (game.isEmpty())
            return ResponseEntity
                    .badRequest()
                    .body("No game found with this game code!");
        else {
            Optional<User> user = userRepository.findByGameCodeAndUsername(gameCode, username) ;
            if (user.isEmpty())
                return ResponseEntity
                        .badRequest()
                        .body("No such user found!");
            else {
                User user1 = userRepository.findByGameCodeAndUsername(gameCode, username)
                        .get();
                user1.setScore(newScore);

                User saved = userRepository.save(user1);
                return ResponseEntity.ok()
                        .body(saved);
            }
        }
    }

}
