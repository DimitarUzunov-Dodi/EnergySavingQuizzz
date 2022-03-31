package server.api;

import org.junit.jupiter.api.BeforeEach;

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
}
