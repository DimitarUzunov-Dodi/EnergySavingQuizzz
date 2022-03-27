/*
 * Copyright 2021 Delft University of Technology
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package server.api;

import commons.Quote;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery.FetchableFluentQuery;
import server.database.QuoteRepository;

// TODO: don't use deprecated APIs
public class TestQuoteRepository implements QuoteRepository {

    public final List<Quote> quotes = new ArrayList<>();
    public final List<String> calledMethods = new ArrayList<>();

    private void call(String name) {
        calledMethods.add(name);
    }

    @Override
    public List<Quote> findAllById(Iterable<Long> ids) {
        return null;
    }

    @Override
    public <S extends Quote> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends Quote> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends Quote> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> ids) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public void deleteAllInBatch(Iterable<Quote> entities) {

    }

    @Override
    public Quote getOne(Long id) {
        return null;
    }

    @Override
    public Quote getById(Long id) {
        call("getById");
        return find(id).get();
    }

    private Optional<Quote> find(Long id) {
        return quotes.stream().filter(q -> q.id == id).findFirst();
    }

    @Override
    public <S extends Quote> S save(S entity) {
        call("save");
        entity.id = (long) quotes.size();
        quotes.add(entity);
        return entity;
    }

    @Override
    public Optional<Quote> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Long id) {
        call("existsById");
        return find(id).isPresent();
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public void delete(Quote entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> ids) {

    }

    @Override
    public void deleteAll(Iterable<? extends Quote> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public <S extends Quote> Optional<S> findOne(Example<S> example) {
        return null;
    }

    @Override
    public <S extends Quote> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Quote> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public List<Quote> findAll() {
        calledMethods.add("findAll");
        return quotes;
    }

    @Override
    public List<Quote> findAll(Sort sort) {
        return null;
    }

    @Override
    public <S extends Quote> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public Page<Quote> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Quote> long count(Example<S> example) {
        return 0;
    }

    @Override
    public long count() {
        return quotes.size();
    }

    @Override
    public <S extends Quote> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends Quote, R> R findBy(Example<S> example,
                                         Function<FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }
}