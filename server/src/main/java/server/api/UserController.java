package server.api;

import commons.User;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import server.service.GameService;

@RestController
@RequestMapping("/api/user")
public class UserController {

    public Map<Object, Pair<Consumer<Pair<User, String>>, String>> listeners = new HashMap<>();

    @Autowired
    private final GameService gameService;

    /**
     * Constructor for the UserController class.
     * @param gameService Game Service
     */
    public UserController(GameService gameService) {
        this.gameService = gameService;
    }

    /**
     * Adds an user to the user list of a game.
     *
     * @param gameCode gamecode
     * @param username username
     * @return ResponseEntity
     */
    @PutMapping("/join/{gameCode}/{username}")
    public ResponseEntity<String> joinGame(@PathVariable String gameCode,
                                       @PathVariable String username) {
        if (!(gameService.doesGameExist(gameCode))) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("No game found with this game code!");
        } else if (gameService.isUsernamePresent(gameCode, username)) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Username already in use in this game!");
        } else if (gameService.isNotAllowedToJoin(gameCode)) {
            return ResponseEntity
                    .status(HttpStatus.I_AM_A_TEAPOT)
                    .body("Room is now closed!");
        } else {
            gameService.joinGame(gameCode, username);
            return ResponseEntity.ok()
                    .build();
        }
    }

    /**
     * Removes a user from the user list of a game.
     *
     * @param gameCode gamecode
     * @param username username
     * @return ResponseEntity
     */
    @DeleteMapping("/leave/{gameCode}/{username}")
    public ResponseEntity<String> leaveGame(@PathVariable String gameCode,
                                           @PathVariable String username) {
        if (!(gameService.doesGameExist(gameCode))) {
            return ResponseEntity
                    .badRequest()
                    .body("No game found with this game code!");
        } else if (!gameService.isUsernamePresent(gameCode, username)) {
            return ResponseEntity
                    .badRequest()
                    .body("No such user in selected game!");
        } else {
            gameService.leaveGame(gameCode, username);
            return ResponseEntity.ok()
                    .build();
        }
    }

    /**
     * POST mapping called when one of the users initiates start of the game.
     *
     * @param gameCode gamecode
     * @return ResponseEntity
     */
    @PostMapping("/start/{gameCode}")
    public ResponseEntity<String> startGame(@PathVariable String gameCode) {
        if (!(gameService.doesGameExist(gameCode))) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("No game found with this game code!");
        } else {
            gameService.closeRoom(gameCode);
            return ResponseEntity.ok()
                    .build();
        }
    }

    /**
     * Adds an user to the user list of a game.
     *
     * @param gameCode gamecode
     * @return ResponseEntity
     */
    @GetMapping("/start/{gameCode}/started")
    public ResponseEntity<String> testIfStarted(@PathVariable String gameCode) {
        if (!(gameService.doesGameExist(gameCode))) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("No game found with this game code!");
        } else if (gameService.isNotAllowedToJoin(gameCode)) {
            return ResponseEntity
                    .status(HttpStatus.I_AM_A_TEAPOT)
                    .body("Room is now closed!");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Room is still open!");
        }
    }
}
