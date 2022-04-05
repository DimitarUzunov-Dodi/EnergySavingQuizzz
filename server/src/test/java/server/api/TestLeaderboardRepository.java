package server.api;

import commons.ServerLeaderboardEntry;
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
import server.database.LeaderboardRepository;

public class TestLeaderboardRepository implements LeaderboardRepository {
    public List<ServerLeaderboardEntry> leaderboardEntries = new ArrayList<>();

    @Override
    public Optional<ServerLeaderboardEntry> getAll() {
        return Optional.empty();
    }

    @Override
    public List<ServerLeaderboardEntry> findAll() {
        return leaderboardEntries;
    }

    @Override
    public List<ServerLeaderboardEntry> findAll(Sort sort) {
        return leaderboardEntries;
    }

    @Override
    public Page<ServerLeaderboardEntry> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public <S extends ServerLeaderboardEntry> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends ServerLeaderboardEntry> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends ServerLeaderboardEntry> Page<S> findAll(
            Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public List<ServerLeaderboardEntry> findAllById(Iterable<Long> longs) {
        return null;
    }

    @Override
    public void deleteById(Long along) {

    }

    @Override
    public void delete(ServerLeaderboardEntry entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends ServerLeaderboardEntry> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public <S extends ServerLeaderboardEntry> S save(S entity) {
        List<ServerLeaderboardEntry> sameID = leaderboardEntries.stream()
                .filter(c -> c.getUsername() == entity.getUsername())
                .collect(Collectors.toList());
        if (sameID.size() != 0) {
            leaderboardEntries = leaderboardEntries.stream()
                    .filter(c -> c.getUsername() != entity.getUsername())
                    .collect(Collectors.toList());
        }
        leaderboardEntries.add(entity);
        return null;
    }

    @Override
    public <S extends ServerLeaderboardEntry> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<ServerLeaderboardEntry> findById(Long along) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Long along) {
        return false;
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends ServerLeaderboardEntry> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends ServerLeaderboardEntry> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<ServerLeaderboardEntry> entities) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {

    }

    @Override
    public ServerLeaderboardEntry getOne(Long along) {
        return null;
    }

    @Override
    public ServerLeaderboardEntry getById(Long along) {
        return null;
    }

    @Override
    public <S extends ServerLeaderboardEntry> Optional<S> findOne(Example<S> example) {
        List<ServerLeaderboardEntry> sameID = leaderboardEntries.stream()
                .filter(c -> c.getUsername() == example.getProbe().getUsername())
                .collect(Collectors.toList());
        if (sameID.size() == 0) {
            return Optional.empty();
        } else {
            return (Optional<S>) Optional.of(sameID.get(0));
        }
    }

    @Override
    public <S extends ServerLeaderboardEntry> long count(Example<S> example) {
        return 0;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public <S extends ServerLeaderboardEntry> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends ServerLeaderboardEntry, R> R findBy(Example<S> example,
        Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }
}
