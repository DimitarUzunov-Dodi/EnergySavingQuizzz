package server.api;

import static org.junit.jupiter.api.Assertions.assertEquals;

import commons.Activity;
import commons.ActivityBankEntry;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AdminControllerTest {
    private AdminController controller;
    private TestActivityRepository activityRepo;
    private TestActivityImagesRepository imageRepo;

    /**
     * Test setup entities.
     */
    @BeforeEach
    public void setup() {
        activityRepo = new TestActivityRepository();
        imageRepo = new TestActivityImagesRepository();
        controller = new AdminController(activityRepo, imageRepo);
    }

    @Test
    public void getAllTest() {
        assertEquals(0, activityRepo.activities.size());
        Activity a = new Activity(1, "a", 23, "a", 1);
        Activity b = new Activity(2, "b", 13, "b", 2);
        activityRepo.save(a);
        assertEquals(1, activityRepo.activities.size());
        activityRepo.save(b);
        assertEquals(2, activityRepo.activities.size());
        List<Activity> list = controller.getAllActivities().getBody();
        assertEquals(2, list.size());
    }

    @Test
    public void addActivityBankEntryTest() {
        assertEquals(0, activityRepo.activities.size());
        ActivityBankEntry a = new ActivityBankEntry("test_id", "/test/test", "a", 24, "test");
        controller.addActivityBankEntry("3", a);
        assertEquals(1, activityRepo.activities.size());
        Activity createdActivity = activityRepo.activities.get(0);
        assertEquals("a", createdActivity.getActivityText());
        assertEquals(24, createdActivity.getValue());
        assertEquals("test", createdActivity.getSource());
    }

    @Test
    public void deleteAllTest() {
        assertEquals(0, activityRepo.activities.size());
        Activity a = new Activity(1, "a", 23, "a", 1);
        Activity b = new Activity(2, "b", 13, "b", 2);
        activityRepo.save(a);
        assertEquals(1, activityRepo.activities.size());
        activityRepo.save(b);
        assertEquals(2, activityRepo.activities.size());
        controller.deleteAllActivities();
        assertEquals(0, activityRepo.activities.size());
        Activity c = new Activity(3, "c", 17, "c", 6);
        activityRepo.save(c);
        assertEquals(1, activityRepo.activities.size());
    }

    @Test
    public void editActivityTest() {
        assertEquals(0, activityRepo.activities.size());
        Activity a = new Activity(1, "a", 23, "a", 1);
        Activity b = new Activity(2, "b", 13, "b", 2);
        activityRepo.save(a);
        assertEquals(1, activityRepo.activities.size());
        activityRepo.save(b);
        assertEquals(2, activityRepo.activities.size());
        Activity beforeEdit = activityRepo.activities.get(0);
        assertEquals(a.getActivityId(), beforeEdit.getActivityId());
        assertEquals(a.getImageId(), beforeEdit.getImageId());
        assertEquals(a.getValue(), beforeEdit.getValue());
        Activity editedA = new Activity(1, "a_edited", 17, "a_edited_source", 40);
        controller.editActivity(Long.toString(editedA.getActivityId()), editedA);
        Optional<Activity> afterEditOptional = activityRepo.findById(a.getActivityId());
        Activity afterEdit = null;
        if (!afterEditOptional.isEmpty()) {
            afterEdit = afterEditOptional.get();
        }
        assertEquals(editedA.getActivityId(), afterEdit.getActivityId());
        assertEquals(editedA.getValue(), afterEdit.getValue());
        assertEquals(editedA.getImageId(), afterEdit.getImageId());
    }
}
