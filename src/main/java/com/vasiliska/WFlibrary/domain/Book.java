package com.vasiliska.WFlibrary.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "books")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class Book {

    @Id
    private String id;

    private String name;

    private Author author;

    private Genre genre;

    private List<Comment> comments;

    public Book(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public Book(String name, Author author, Genre genre, List<Comment> comments) {
        this.name = name;
        this.author = author;
        this.genre = genre;
        this.comments = comments;
    }

    @Override
    public String toString() {
        return "Книга: " + id + " " + name + " " + author + " " + genre + " " + "Коментарии: " + comments;
    }
}
