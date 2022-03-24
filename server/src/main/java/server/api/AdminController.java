package server.api;

import commons.Activity;
import commons.ActivityBankEntry;
import commons.ActivityImage;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import server.Main;
import server.database.ActivityImagesRepository;
import server.database.ActivityRepository;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private ActivityRepository repo;

    private ActivityImagesRepository imageRepo;

    public AdminController(ActivityRepository repo, ActivityImagesRepository imageRepo) {
        this.repo = repo;
        this.imageRepo = imageRepo;
    }

    /**
     * A POST mapping that activates server restart.
     * @return ResponseEntity with ok message
     */
    @PostMapping(value = "/restart")
    public ResponseEntity<String> restartServer() {
        System.out.println("Restart has been started");
        Main.restart();
        return ResponseEntity.ok("Restarted successfully");
    }

    /**
     * GET mapping that gives a full list of activities available on the server.
     * @return ResponseEntity with a list of activities
     */
    @GetMapping(value = "/activity/all")
    public ResponseEntity<List<Activity>> getAllActivities() {
        return  ResponseEntity.ok(repo.findAll());
    }

    /*
    @PostMapping(value = "/activity/add")
    public ResponseEntity<String> addDefaultActivity() {
        System.out.println("Adding new activity");
        repo.save(new Activity("TestText", 13, "SourceText", 1));
        return ResponseEntity.ok("Added entity successfully");
    }
     */

    /**
     * POST mapping that adds a new activity.
     * @RequestParam imageId id of the activity image
     * @RequestBody object of ActivityBankEntry class that will be transformed into proper activity
     * @return ResponseEntity with ok message
     */
    @PostMapping(value = "/activity/add")
    public ResponseEntity<String> addActivityBankEntry(
            @RequestParam(name = "imageId") String imageId,
            @RequestBody ActivityBankEntry newActivity) {
        System.out.println("Adding new activity bank entry");
        Activity translatedActivity = new Activity(
                newActivity.getTitle(),
                newActivity.getConsumption_in_wh(),
                newActivity.getSource(),
                Long.parseLong(imageId));
        repo.save(translatedActivity);
        return ResponseEntity.ok("Added entity successfully");
    }

    /**
     * DELETE mapping that deletes all available activities.
     * @return ResponseEntity with ok message
     */
    @DeleteMapping(value = "/activity/delete/all")
    public ResponseEntity<String> deleteAllActivities() {
        System.out.println("Deleting all activities");
        repo.deleteAll();
        imageRepo.deleteAll();
        return ResponseEntity.ok("Deleted all entities successfully");
    }

    /**
     * PUT mapping that edits an existing activity.
     * @PathVariable id of the edited activity
     * @RequestBody object of Activity class that will be saved instead previous version
     * @return ResponseEntity with ok message
     */
    @PutMapping(value = "/activity/edit/{activityId}")
    public ResponseEntity<String> editActivity(
            @PathVariable String activityId,
            @RequestBody Activity newActivity) {
        System.out.println("Editing activity " + activityId);
        repo.save(newActivity);
        return ResponseEntity.ok("Added entity successfully");
    }

    /**
     * POST mapping that adds a new Image.
     * @RequestBody object of ActivityImage class that will be saved
     * @return ResponseEntity with id of the added image
     */
    @PostMapping(value = "/activity/add/image")
    public ResponseEntity<Long> addActivityImage(
            @RequestBody ActivityImage newActivityImage) {
        System.out.println("Adding a new activity image");
        long id = imageRepo.save(newActivityImage).getImageId();
        return ResponseEntity.ok(id);
    }
}
