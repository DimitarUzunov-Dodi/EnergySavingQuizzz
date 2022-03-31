package server.api;

import commons.Activity;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import server.database.ActivityRepository;

public class TestActivityRepository implements ActivityRepository {
    public List<Activity> activities = new ArrayList<>();


    public Optional<Activity> getOneRandom() {
        return Optional.ofNullable(activities.get(0));
    }

    /**
     * Method that simulates that three activities are derived randomly, but
     * it gets first 3 activities in the list.
     * @return list with activities
     */
    public List<Activity> getThreeRandom() {
        ArrayList<Activity> result = new ArrayList<>();
        for (int i = 0; i < activities.size(); i++) {
            result.add(activities.get(i));
        }
        return result;
    }

    public Optional<Activity> getOneRelated(long t) {
        return Optional.ofNullable(activities.get(0));
    }

    @Override
    public List<Activity> findAllById(Iterable<Long> longs) {
        return null;
    }

    @Override
    public void deleteById(Long along) {

    }

    @Override
    public void delete(Activity entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends Activity> entities) {

    }

    @Override
    public void deleteAll() {
        activities = new ArrayList<>();
    }

    @Override
    public <S extends Activity> S save(S entity) {
        List<Activity> sameID = activities.stream()
                .filter(c -> c.getActivityId() == entity.getActivityId())
                .collect(Collectors.toList());
        if (sameID.size() != 0) {
            activities = activities.stream()
                    .filter(c -> c.getActivityId() != entity.getActivityId())
                    .collect(Collectors.toList());
        }
        activities.add(entity);
        return null;
    }

    @Override
    public <S extends Activity> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<Activity> findById(Long along) {
        List<Activity> sameID = activities.stream()
                .filter(c -> c.getActivityId() == along)
                .collect(Collectors.toList());
        if (sameID.size() == 0) {
            return Optional.empty();
        } else {
            return Optional.ofNullable(sameID.get(0));
        }
    }

    @Override
    public boolean existsById(Long along) {
        return false;
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends Activity> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends Activity> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {

    }

    @Override
    public void deleteAllInBatch(Iterable<Activity> entities) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public Activity getOne(Long along) {
        return null;
    }

    @Override
    public Activity getById(Long along) {
        return null;
    }

    @Override
    public <S extends Activity> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public List<Activity> findAll() {
        return activities;
    }

    @Override
    public List<Activity> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<Activity> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Activity> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends Activity> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends Activity> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public <S extends Activity> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Activity> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends Activity, R> R findBy(Example<S> example,
                                            Function<FluentQuery.FetchableFluentQuery<S>, R>
                                                    queryFunction) {
        return null;
    }
}
