package com.vasiliska.WFlibrary.exception;

public class BookNotFoundException extends RuntimeException {
    public BookNotFoundException(String message) { super((message)); }
}
