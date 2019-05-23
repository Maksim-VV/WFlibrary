package com.vasiliska.WFlibrary.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Comment {

    private String text;

    @Override
    public String toString() {
        return text;
    }
}
