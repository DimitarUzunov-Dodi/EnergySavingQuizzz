package server.api;

import static org.junit.jupiter.api.Assertions.assertEquals;

import commons.Activity;
import commons.ActivityBankEntry;
import commons.ActivityImage;
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

    @Test
    public void addActivityBankEntryTest() {
        ActivityBankEntry a = new ActivityBankEntry();
        a.id = "testId";
        a.image_path = "imagepath";
        a.title = "TEST";
        a.source = "verygoodsource.com";
        a.consumption_in_wh = 3;
        controller.addActivityBankEntry("23", a);
        assertEquals(1, activityRepo.activities.size());
        Activity newActivity = activityRepo.getOneRandom().get();
        assertEquals(a.source, newActivity.getSource());
        assertEquals(a.title, newActivity.getActivityText());
        assertEquals(a.consumption_in_wh, newActivity.getValue());
    }

    @Test
    public void addActivityImage() {
        ActivityImage a = new ActivityImage();
        a.setImageId(1);
        controller.addActivityImage(a);
    }

}
