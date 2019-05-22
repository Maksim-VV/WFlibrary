package com.vasiliska.WFlibrary.repository;

import com.mongodb.client.result.UpdateResult;
import com.vasiliska.WFlibrary.domain.Author;
import com.vasiliska.WFlibrary.domain.Comment;
import com.vasiliska.WFlibrary.domain.Genre;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BookCustomRepository {

    Flux<Author> findAllDistinctAuthors();

    Flux<Genre> findAllDistinctGenres();

    Flux<Comment> findCommentsByBookId(String id);

    Mono<UpdateResult> updateBookNameById(String id, String newName);

    Mono<UpdateResult> addCommentByBookId(String id, Comment comment);

    Mono<UpdateResult> deleteCommentsByBookId(String id);

}
