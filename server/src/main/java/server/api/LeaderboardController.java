package server.api;

import commons.LeaderboardEntry;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.database.LeaderboardRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/leaderboard")
public class LeaderboardController {

    private final LeaderboardRepository repo;

    /**
     * Constructor for leaderboard controller
     * @param repo Server Leaderboard Repository
     */
    public LeaderboardController(LeaderboardRepository repo) {
        this.repo = repo;
    }

    /**
     * Get all leaderboard entries from the server
     * @return list of all entries
     */
    @GetMapping("/")
    public List<LeaderboardEntry> getServerLeaderboard() {
        return repo.findAll();
    }

    /**
     * Get a specific entry from the server leaderboard
     * @param username entry username
     * @return HTTP 200 with a leaderboard entry if ok, error code otherwise
     */
    @GetMapping("/{username}")
    public ResponseEntity<LeaderboardEntry> getEntryByUsername(@PathVariable("username") String username) {
        if (username == null || username.equals(""))
            return ResponseEntity.badRequest().build();

        Example<LeaderboardEntry> example = Example.of(new LeaderboardEntry(username, null, null));
        Optional<LeaderboardEntry> entry = repo.findOne(example);

        if(entry.isPresent()) {
            return ResponseEntity.ok(entry.get());
        }
        else
            return ResponseEntity.notFound().build();
    }

    /**
     * Add/Update an entry
     * @param username entry username
     * @param score entry score
     * @param gamesPlayed entry gamesPlayed
     * @return HTTP 201 if all updated existing, 202 if created new entry, error code otherwise
     */
    @PutMapping("/{username}")
    public ResponseEntity<LeaderboardEntry> updateEntry(@PathVariable String username, @RequestBody Integer score, @RequestBody Integer gamesPlayed) {
        if (username == null || username.isEmpty() || score == null || gamesPlayed == null) {
            return ResponseEntity.badRequest().build();
        }

        Example<LeaderboardEntry> example = Example.of(new LeaderboardEntry(username, null, null));
        var found = repo.findOne(example);
        if(found.isPresent()) {
            found.get().setScore(score);
            found.get().setGamesPlayed(gamesPlayed);
            return ResponseEntity.accepted().body(repo.save(found.get()));
        }
        else {
            LeaderboardEntry e = new LeaderboardEntry(username, gamesPlayed, score);
            return ResponseEntity.status(HttpStatus.CREATED).body(repo.save(e));
        }
    }
}
