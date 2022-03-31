package server.api;
 
import commons.ActivityImage;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import server.database.ActivityImagesRepository;
 
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
 
public class TestActivityImagesRepository implements ActivityImagesRepository {
    public List<ActivityImage> images = new ArrayList<>();
 
    @Override
    public List<ActivityImage> findAll() {
        return null;
    }
 
    @Override
    public List<ActivityImage> findAll(Sort sort) {
        return null;
    }
 
    @Override
    public Page<ActivityImage> findAll(Pageable pageable) {
        return null;
    }
 
    @Override
    public List<ActivityImage> findAllById(Iterable<Long> longs) {
        return null;
    }
 
    @Override
    public long count() {
        return 0;
    }
 
    @Override
    public void deleteById(Long aLong) {
 
    }
 
    @Override
    public void delete(ActivityImage entity) {
 
    }
 
    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {
 
    }
 
    @Override
    public void deleteAll(Iterable<? extends ActivityImage> entities) {
 
    }
 
    @Override
    public void deleteAll() {
 
    }
 
    @Override
    public <S extends ActivityImage> S save(S entity) {
        return null;
    }
 
    @Override
    public <S extends ActivityImage> List<S> saveAll(Iterable<S> entities) {
        return null;
    }
 
    @Override
    public Optional<ActivityImage> findById(Long aLong) {
        return Optional.empty();
    }
 
    @Override
    public boolean existsById(Long aLong) {
        return false;
    }
 
    @Override
    public void flush() {
 
    }
 
    @Override
    public <S extends ActivityImage> S saveAndFlush(S entity) {
        return null;
    }
 
    @Override
    public <S extends ActivityImage> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }
 
    @Override
    public void deleteAllInBatch(Iterable<ActivityImage> entities) {
 
    }
 
    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {
 
    }
 
    @Override
    public void deleteAllInBatch() {
 
    }
 
    @Override
    public ActivityImage getOne(Long aLong) {
        return null;
    }
 
    @Override
    public ActivityImage getById(Long aLong) {
        return null;
    }
 
    @Override
    public <S extends ActivityImage> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }
 
    @Override
    public <S extends ActivityImage> List<S> findAll(Example<S> example) {
        return null;
    }
 
    @Override
    public <S extends ActivityImage> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }
 
    @Override
    public <S extends ActivityImage> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }
 
    @Override
    public <S extends ActivityImage> long count(Example<S> example) {
        return 0;
    }
 
    @Override
    public <S extends ActivityImage> boolean exists(Example<S> example) {
        return false;
    }
 
    @Override
    public <S extends ActivityImage, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }
}
