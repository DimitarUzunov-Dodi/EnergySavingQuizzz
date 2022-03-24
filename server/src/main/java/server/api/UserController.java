package server.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import server.service.GameService;

@RestController
@RequestMapping("/api/user")
public class UserController {

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
                    .badRequest()
                    .body("No game found with this game code!");
        } else if (gameService.isUsernamePresent(gameCode, username)) {
            return ResponseEntity
                    .badRequest()
                    .body("Username already in use in this game!");
        } else {
            gameService.joinGame(gameCode, username);
            return ResponseEntity.ok()
                    .build();
        }
    }

}
