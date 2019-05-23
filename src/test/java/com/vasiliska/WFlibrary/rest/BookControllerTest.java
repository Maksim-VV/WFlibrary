package com.vasiliska.WFlibrary.rest;

import com.vasiliska.WFlibrary.domain.Book;
import com.vasiliska.WFlibrary.repository.BookRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.server.RouterFunction;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.web.reactive.function.BodyInserters.fromObject;

@RunWith(SpringRunner.class)
@WebFluxTest
@ContextConfiguration(classes = {BookRouter.class, BookHandler.class})
public class BookControllerTest {

    @Autowired
    private RouterFunction route;

    @MockBean
    private BookRepository bookRepository;

    @Test
    public void findAll() {
        WebTestClient client = WebTestClient
                .bindToRouterFunction(route)
                .build();

        Book expected1 = new Book("1", "test1");
        Book expected2 = new Book("2", "test2");
        List<Book> books = Arrays.asList(expected1, expected2);
        given(bookRepository.findAll()).willReturn(Flux.fromIterable(books));

        client.get()
                .uri("/book")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Book.class)
                .isEqualTo(books);
    }

    @Test
    public void findAllByGenreName() {
        WebTestClient client = WebTestClient
                .bindToRouterFunction(route)
                .build();

        Book expected1 = new Book("1", "test1");
        Book expected2 = new Book("2", "test2");
        List<Book> books = Arrays.asList(expected1, expected2);
        given(bookRepository.findByGenre_Name("Fiction")).willReturn(Flux.fromIterable(books));

        client.get()
                .uri("/book?genreName=Fiction")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Book.class)
                .isEqualTo(books);
    }

    @Test
    public void findById() {
        WebTestClient client = WebTestClient
                .bindToRouterFunction(route)
                .build();

        Book expected = new Book("1", "test");
        given(bookRepository.findById("1")).willReturn(Mono.just(expected));

        client.get()
                .uri("/book/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody(Book.class)
                .isEqualTo(expected);
    }

    @Test
    public void findById_should_return_not_found_if_book_was_not_found() {
        WebTestClient client = WebTestClient
                .bindToRouterFunction(route)
                .build();

        given(bookRepository.findById("2")).willReturn(Mono.empty());

        client.get()
                .uri("/book/2")
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    public void save() {
        WebTestClient client = WebTestClient
                .bindToRouterFunction(route)
                .build();

        Book book = new Book("1", "test");
        given(bookRepository.save(book)).willReturn(Mono.just(book));

        client.post()
                .uri("/book")
                .body(fromObject(book))
                .exchange()
                .expectStatus()
                .isOk();
    }

    @Test
    public void updateNameById() {
        WebTestClient client = WebTestClient
                .bindToRouterFunction(route)
                .build();

        given(bookRepository.updateBookNameById("1", "new name")).willReturn(Mono.empty());

        client.put()
                .uri("/book/1")
                .body(fromObject("new name"))
                .exchange()
                .expectStatus()
                .isOk();
    }

    @Test
    public void deleteById() {
        WebTestClient client = WebTestClient
                .bindToRouterFunction(route)
                .build();

        given(bookRepository.deleteById("1")).willReturn(Mono.empty());

        client.delete()
                .uri("/book/1")
                .exchange()
                .expectStatus()
                .isOk();
    }
}
