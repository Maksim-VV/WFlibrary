package com.vasiliska.WFlibrary.repository;

import com.github.mongobee.Mongobee;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import com.vasiliska.WFlibrary.changelog.DatabaseChangelog;
import com.vasiliska.WFlibrary.domain.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.test.annotation.DirtiesContext;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.ArrayList;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;


@DataMongoTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class BookRepositoryTest {

    private final String TEST_BOOK_NAME1 = "Преступление и наказание";
    private final String TEST_AUTHOR1 = "Достоевский";
    private final String TEST_AUTHOR_NAME1 = "Федор";
    private final String TEST_GENRE1 = "Драма";
    private final String TEST_BOOK_NAME2 = "Мастер и Маргарита";

    @Autowired
    private BookRepository bookRepository;

    @TestConfiguration
    static class BookRepositoryTestContextConfiguration {
        @Bean
        public MongoClient reactiveMongoClient(@Value("${mongo.db.url}") String mongoUrl,
                                               @Value("${local.mongo.port}") String mongoPort) {
            return MongoClients.create(mongoUrl + ":" + mongoPort);
        }

        @Bean
        public Mongobee mongobee(Environment environment,
                                 @Value("${mongo.db.url}") String mongoUrl,
                                 @Value("${local.mongo.port}") String mongoPort,
                                 @Value("${mongo.db.name}") String mongoDbName) {
            Mongobee runner = new Mongobee(mongoUrl + ":" + mongoPort);
            runner.setDbName(mongoDbName);
            runner.setChangeLogsScanPackage(DatabaseChangelog.class.getPackage().getName());
            runner.setSpringEnvironment(environment);
            return runner;
        }
    }

    @Test
    void findAll() {
        Flux<Book> all = bookRepository.findAll();
        StepVerifier.create(all)
                .recordWith(ArrayList::new)
                .expectNextCount(2)
                .consumeRecordedWith(results ->
                        assertThat(results)
                                .extracting("name")
                                .contains(TEST_BOOK_NAME1, TEST_BOOK_NAME2)
                )
                .verifyComplete();
    }

    @Test
    void findById() {
        StepVerifier.create(bookRepository.findById("1"))
                .assertNext(book -> {
                    assertEquals(book.getName(), TEST_BOOK_NAME1);
                    assertEquals(book.getAuthor().getLastName(), TEST_AUTHOR1);
                })
                .expectComplete()
                .verify();
    }

    @Test
    void findByAuthor_FirstNameAndAuthor_LastName() {
        Flux<Book> flux = bookRepository
                .findByAuthor_LastNameAndAuthor_FirstName(TEST_AUTHOR1, TEST_AUTHOR_NAME1);
        StepVerifier.create(flux)
                .recordWith(ArrayList::new)
                .expectNextCount(1)
                .consumeRecordedWith(results ->
                        assertThat(results)
                                .extracting("name")
                                .contains(TEST_BOOK_NAME1)
                )
                .verifyComplete();
    }

    @Test
    void findByGenre_Name() {
        Flux<Book> flux = bookRepository.findByGenre_Name(TEST_GENRE1);
        StepVerifier.create(flux)
                .recordWith(ArrayList::new)
                .expectNextCount(1)
                .consumeRecordedWith(results ->
                        assertThat(results)
                                .extracting("name")
                                .contains(TEST_BOOK_NAME1)
                )
                .verifyComplete();
    }

    @Test
    void updateNameById() {
        bookRepository.updateBookNameById("1", TEST_BOOK_NAME1).subscribe();
        StepVerifier.create(bookRepository.findById("1"))
                .assertNext(book -> assertEquals(book.getName(), TEST_BOOK_NAME1))
                .expectComplete()
                .verify();
    }

    @Test
    void deleteById() {
        bookRepository.deleteById("1").subscribe();
        StepVerifier.create(bookRepository.findAll())
                .recordWith(ArrayList::new)
                .expectNextCount(1)
                .consumeRecordedWith(results ->
                        assertThat(results)
                                .extracting("name")
                                .contains(TEST_BOOK_NAME2)
                )
                .verifyComplete();
    }


}