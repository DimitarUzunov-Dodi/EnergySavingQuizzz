package server.api;

import static org.junit.jupiter.api.Assertions.assertEquals;

import commons.Activity;
import java.util.List;
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
}
