package server.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import commons.ScoreRecord;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import server.database.ScoreRecordRepository;

public class ScoreServiceTest {
    @Autowired
    private ScoreRecordRepository repository;

    private ScoreService scoreService;

    @BeforeEach
    public void doBeforeEach(){
         scoreService = new ScoreService(new ScoreRecordRepository() {
            @Override
            public List<ScoreRecord> findAll() {
                return null;
            }

            @Override
            public List<ScoreRecord> findAll(Sort sort) {
                return null;
            }

            @Override
            public List<ScoreRecord> findAllById(Iterable<String> strings) {
                return null;
            }

            @Override
            public <S extends ScoreRecord> List<S> saveAll(Iterable<S> entities) {
                return null;
            }

            @Override
            public void flush() {

            }

            @Override
            public <S extends ScoreRecord> S saveAndFlush(S entity) {
                return null;
            }

            @Override
            public <S extends ScoreRecord> List<S> saveAllAndFlush(Iterable<S> entities) {
                return null;
            }

            @Override
            public void deleteAllInBatch(Iterable<ScoreRecord> entities) {

            }

            @Override
            public void deleteAllByIdInBatch(Iterable<String> strings) {

            }

            @Override
            public void deleteAllInBatch() {

            }

            @Override
            public ScoreRecord getOne(String s) {
                return null;
            }

            @Override
            public ScoreRecord getById(String s) {
                return null;
            }

            @Override
            public <S extends ScoreRecord> List<S> findAll(Example<S> example) {
                return null;
            }

            @Override
            public <S extends ScoreRecord> List<S> findAll(Example<S> example, Sort sort) {
                return null;
            }

            @Override
            public Page<ScoreRecord> findAll(Pageable pageable) {
                return null;
            }

            @Override
            public <S extends ScoreRecord> S save(S entity) {
                return null;
            }

            @Override
            public Optional<ScoreRecord> findById(String s) {
                return Optional.empty();
            }

            @Override
            public boolean existsById(String s) {
                return false;
            }

            @Override
            public long count() {
                return 0;
            }

            @Override
            public void deleteById(String s) {

            }

            @Override
            public void delete(ScoreRecord entity) {

            }

            @Override
            public void deleteAllById(Iterable<? extends String> strings) {

            }

            @Override
            public void deleteAll(Iterable<? extends ScoreRecord> entities) {

            }

            @Override
            public void deleteAll() {

            }

            @Override
            public <S extends ScoreRecord> Optional<S> findOne(Example<S> example) {
                return Optional.empty();
            }

            @Override
            public <S extends ScoreRecord> Page<S> findAll(Example<S> example, Pageable pageable) {
                return null;
            }

            @Override
            public <S extends ScoreRecord> long count(Example<S> example) {
                return 0;
            }

            @Override
            public <S extends ScoreRecord> boolean exists(Example<S> example) {
                return false;
            }

            @Override
            public <S extends ScoreRecord, R> R findBy(Example<S> example,
                                                       Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
                return null;
            }
        });
    }

    @Test
    public void serviceNotNull() {


        assertNotNull(scoreService);
    }
    @Test
    public void addScoreTest(){

    }
/*
    @Test
    @Transactional
    void addScoreTest1() {
        var scoreService = new ScoreService(repository);
        scoreService.addScore("Bob", 20);
        var scoreRecord = repository.findById("Bob");
        assertTrue(scoreRecord.isPresent());
    }

    @Test
    @Transactional
    void addScoreTest2() {
        var scoreService = new ScoreService(repository);
        scoreService.addScore("Bob", 20);
        var scoreRecord = repository.findById("Bob");
        assertTrue(scoreRecord.get().getScore() == 20);
    }
*/
}
