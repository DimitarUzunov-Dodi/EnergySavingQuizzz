package server.api;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ActivityImageControllerTest {
    private ActivityImageController controller;
    private TestActivityImagesRepository imageRepo;

    @BeforeEach
    public void setup() {
        imageRepo = new TestActivityImagesRepository();
        controller = new ActivityImageController(imageRepo);
    }


    @Test
    public void getImageTest() {
        String imageId = "1234";
        ResponseEntity r = controller.getImage(imageId);
        assertEquals(HttpStatus.OK, r.getStatusCode());
    }
}
