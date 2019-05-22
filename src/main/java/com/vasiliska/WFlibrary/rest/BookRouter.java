package com.vasiliska.WFlibrary.rest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class BookRouter {

    @Bean
    public RouterFunction<ServerResponse> composedRoutes(BookHandler bookHandler) {

        RouterFunction<ServerResponse> route = route()
                .GET("/book", accept(APPLICATION_JSON), bookHandler::findAll)
                .GET("/book/{id}", accept(APPLICATION_JSON), bookHandler::findById)
                .POST("/book", accept(APPLICATION_JSON), bookHandler::save)
                .PUT("/book/{id}", accept(APPLICATION_JSON), bookHandler::updateNameById)
                .DELETE("/book/{id}", bookHandler::deleteById)
                .build();

        return route;
    }
}
