package com.vasiliska.WFlibrary.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Genre {

    private String name;

    @Override
    public String toString() {
        return "жанр: " + name;
    }
}
