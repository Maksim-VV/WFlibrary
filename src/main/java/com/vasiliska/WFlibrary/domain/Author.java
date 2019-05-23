package com.vasiliska.WFlibrary.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Author {

    private String lastName;
    private String firstName;

    @Override
    public String toString() {
        return "Автор: " + lastName + " " + firstName;
    }
}
