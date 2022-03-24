package server.api;

import commons.ActivityImage;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import server.database.ActivityImagesRepository;

@RestController
@RequestMapping("/api/images")
public class ActivityImageController {
    private ActivityImagesRepository imageRepo;

    public ActivityImageController(ActivityImagesRepository imageRepo) {
        this.imageRepo = imageRepo;
    }


    @GetMapping(value = "/getImage/{imageId}")
    public ResponseEntity<ActivityImage> getImage(@PathVariable String imageId) {
        return ResponseEntity.ok(imageRepo.findById(Long.parseLong(imageId)).get());
    }
}
