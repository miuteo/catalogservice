package com.miu.teo.book.cloudnativespring.catalogservice.exception;

public class BookAlreadyExistsException extends RuntimeException {
    public BookAlreadyExistsException(String isbn) {
        super("A book with ISBN " + isbn + " already exists");
    }
}
