package server.api;

import commons.Activity;
import commons.ActivityBankEntry;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

    @PostMapping(value = "/activitybank/add")
    public ResponseEntity<String> addActivityBankEntry(@RequestBody ActivityBankEntry newActivity) {
        System.out.println("Adding new activity bank entry");
        Activity translatedActivity = new Activity(newActivity.getTitle(), newActivity.getConsumption_in_wh(), newActivity.getSource(), 1);
        repo.save(translatedActivity);
        return ResponseEntity.ok("Added entity successfully");
    }

    @DeleteMapping(value = "/activity/deleteAll")
    public ResponseEntity<String> deleteAllActivities() {
        System.out.println("Deleting all activities");
        repo.deleteAll();
        return ResponseEntity.ok("Deleted all entities successfully");
    }

    @PostMapping(value = "/activity/edit/{activityId}")
    public ResponseEntity<String> editActivity(@PathVariable String activityId, @RequestBody Activity newActivity) {
        System.out.println("Editing activity " + activityId);
        repo.save(newActivity);
        return ResponseEntity.ok("Added entity successfully");
    }
}
