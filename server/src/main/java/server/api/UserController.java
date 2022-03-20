package server.api;

import commons.ScoreRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import server.database.GameRepository;
import server.database.UserRepository;
import commons.Game;
import server.entities.User;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private final GameRepository gameRepository;
    @Autowired
    private final UserRepository userRepository;

    /**
     * Constructor for the UserController class.
     *
     * @param gameRepository Game Repository
     * @param userRepository User Repository
     */
    public UserController(GameRepository gameRepository, UserRepository userRepository) {
        this.gameRepository = gameRepository;
        this.userRepository = userRepository;
    }

    /**
     * Saves an user to the database when they join a new game.
     *
     * @param gameCode Unique identifier for each game
     * @param username Unique username
     * @return The saved entity or a bad request in case of an error.
     */
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

    /**
     * Updates an user's score when they answer a question correctly.
     *
     * @param gameCode Unique identifier for each game
     * @return Response entity possibly containing a list of ScoreRecords
     */
    @GetMapping("/score/{gameCode}")
    public ResponseEntity<List<ScoreRecord>> getLeaderboard(@PathVariable String gameCode) {
        if(gameCode == null || gameCode.isEmpty())
            return ResponseEntity.badRequest().build();

        Optional<Game> game = gameRepository.findById(gameCode);
        if (game.isEmpty())
            return ResponseEntity.notFound().build();

        Optional<List<User>> entries = userRepository.findAllByGameCode(gameCode);
        if (entries.isEmpty())
            return ResponseEntity.noContent().build();

        return ResponseEntity.ok().body(
                entries.get()
                .stream().map(e -> new ScoreRecord(e.getUsername(), e.getScore()))
                .collect(Collectors.toList())
        );
    }

    @GetMapping("/score/{gameCode}/{username}/{newScore}")
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
