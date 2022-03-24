package server.database;

import commons.Activity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ActivityRepository extends JpaRepository<Activity, Long> {

    @Query(
            value = "SELECT * from activity order by RANDOM() limit 1",
            nativeQuery = true
    )
    Optional<Activity> getOneRandom();

    @Query(
            value = "SELECT * from activity order by RANDOM() limit 3",
            nativeQuery = true
    )
    List<Activity> getThreeRandom();

}
