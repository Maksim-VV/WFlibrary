package com.vasiliska.WFlibrary.rest;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import com.vasiliska.WFlibrary.domain.Book;
import com.vasiliska.WFlibrary.repository.BookRepository;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.BodyExtractors.toMono;
import static org.springframework.web.reactive.function.BodyInserters.fromObject;
import static org.springframework.web.reactive.function.server.ServerResponse.notFound;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
public class BookHandler {

    private BookRepository bookRepository;

    BookHandler(BookRepository repository) {
        this.bookRepository = repository;
    }

    Mono<ServerResponse> findAll(ServerRequest request) {
        Flux<Book> bookFlux = request.queryParam("genreName")
                .map(genreName -> bookRepository.findByGenre_Name(genreName))
                .orElse(bookRepository.findAll());
        return ok().contentType(APPLICATION_JSON).body(bookFlux, Book.class);
    }

    Mono<ServerResponse> findById(ServerRequest request) {
        return bookRepository.findById(request.pathVariable("id"))
                .flatMap(person -> ok().contentType(APPLICATION_JSON).body(fromObject(person)))
                .switchIfEmpty(notFound().build());
    }

    Mono<ServerResponse> save(ServerRequest request) {
        return request.body(toMono(Book.class))
                .doOnNext(book -> bookRepository.save(book).subscribe())
                .then(ok().build());
    }

    Mono<ServerResponse> updateNameById(ServerRequest request) {
        return request.body(toMono(String.class))
                .doOnNext(newName -> bookRepository.updateBookNameById(request.pathVariable("id"), newName).subscribe())
                .then(ok().build());
    }

    Mono<ServerResponse> deleteById(ServerRequest request) {
        return ok().body(bookRepository.deleteById(request.pathVariable("id")), Void.class);
    }
}
