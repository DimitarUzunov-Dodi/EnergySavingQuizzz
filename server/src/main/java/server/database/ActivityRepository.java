package server.database;

import commons.Activity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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

    @Query (
            value = "select * from activity order by ABS(value - :energy) "
                    + "offset 2 fetch next 1 row only",
            nativeQuery = true
    )
    Optional<Activity> getOneRelated(@Param("energy") long energy);

}
