package server.database;

import commons.ActivityImage;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ActivityImagesRepository extends JpaRepository<ActivityImage, Long> {

}
