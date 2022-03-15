package server.api;

import commons.Activity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import server.Main;
import server.database.ActivityRepository;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private ActivityRepository repo;

    public AdminController(ActivityRepository repo){
        this.repo = repo;
    }

    @PostMapping(value = "/restart")
    public ResponseEntity<String> restartServer() {
        System.out.println("Restart has been started");
        Main.restart();
        return ResponseEntity.ok("Restarted successfully");
    }

    @GetMapping(value = "/activity")
    public ResponseEntity<List<Activity>> getAllActivities() {
        return  ResponseEntity.ok(repo.findAll());
    }

    @PostMapping(value = "/activity/add")
    public ResponseEntity<String> addDefaultActivity() {
        System.out.println("Adding new activity");
        repo.save(new Activity("TestText", 13, "SourceText", 1));
        return ResponseEntity.ok("Added entity successfully");
    }
}
