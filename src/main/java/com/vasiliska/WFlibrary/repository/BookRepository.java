package com.vasiliska.WFlibrary.repository;

import com.vasiliska.WFlibrary.domain.Book;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface BookRepository extends ReactiveMongoRepository<Book, String>, BookCustomRepository {

    Flux<Book> findByAuthor_LastNameAndAuthor_FirstName(String authorLastName, String authorFirstName);

    Flux<Book> findByGenre_Name(String genreName);

    void removeByAuthor_LastNameAndAuthor_FirstName(String authorLastName, String authorFirstName);

    void removeByGenre_Name(String genreName);

}
