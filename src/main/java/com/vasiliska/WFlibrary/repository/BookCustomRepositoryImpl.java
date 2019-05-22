package com.vasiliska.WFlibrary.repository;

import com.mongodb.client.result.UpdateResult;
import com.vasiliska.WFlibrary.domain.Author;
import com.vasiliska.WFlibrary.domain.Book;
import com.vasiliska.WFlibrary.domain.Comment;
import com.vasiliska.WFlibrary.domain.Genre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

public class BookCustomRepositoryImpl implements BookCustomRepository {

    @Autowired
    private ReactiveMongoTemplate mongoTemplate;

    @Override
    public Flux<Author> findAllDistinctAuthors() {
        return mongoTemplate.aggregate(newAggregation(group("author.lastName", "author.firstName")),
                Book.class, Author.class);
    }

    @Override
    public Flux<Genre> findAllDistinctGenres() {
        return mongoTemplate.findDistinct("genre.name", Book.class, Genre.class);
    }

    @Override
    public Flux<Comment> findCommentsByBookId(String id) {
        return mongoTemplate.aggregate(newAggregation(
                match(Criteria.where("_id").is(id)),
                unwind("comments"),
                project().andExclude("_id").andInclude("comments.text")), Book.class, Comment.class);
    }

    @Override
    public Mono<UpdateResult> updateBookNameById(String id, String newName) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(id));
        Update update = new Update();
        update.set("name", newName);
        return mongoTemplate.updateFirst(query, update, Book.class);
    }

    @Override
    public Mono<UpdateResult> addCommentByBookId(String id, Comment comment) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(id));
        Update update = new Update();
        update.push("comments", comment);
        return mongoTemplate.updateFirst(query, update, Book.class);
    }

    @Override
    public Mono<UpdateResult> deleteCommentsByBookId(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(id));
        Update update = new Update();
        update.unset("comments");
        return mongoTemplate.updateFirst(query, update, Book.class);
    }
}
