package server.api;

import commons.LeaderboardEntry;
import org.springframework.data.domain.Example;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.database.LeaderboardRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/leaderboard")
public class LeaderboardController {

    private final LeaderboardRepository repo;

    public LeaderboardController(LeaderboardRepository repo) {
        this.repo = repo;
    }

    @GetMapping(path = { "", "/" })
    public List<LeaderboardEntry> getServerLeaderboard() {
        return repo.findAll();
    }

    @GetMapping("/{username}")
    public ResponseEntity<LeaderboardEntry> getByUsername(@PathVariable("username") String username) {
        if (username == null || username.equals(""))
            return ResponseEntity.badRequest().build();

        Example<LeaderboardEntry> example = Example.of(new LeaderboardEntry(username, null, null));
        Optional<LeaderboardEntry> entry = repo.findOne(example);

        if(entry.isPresent()) {
            return ResponseEntity.ok(entry.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
/*
    @PostMapping(path = { "", "/" })
    public ResponseEntity<LeaderboardEntry> add(@RequestBody LeaderboardEntry quote) {

        if (quote.person == null || isNullOrEmpty(quote.person.firstName) || isNullOrEmpty(quote.person.lastName)
                || isNullOrEmpty(quote.quote)) {
            return ResponseEntity.badRequest().build();
        }

        Quote saved = repo.save(quote);
        return ResponseEntity.ok(saved);
    }
*/
}
