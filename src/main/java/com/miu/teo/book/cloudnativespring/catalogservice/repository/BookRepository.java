package com.miu.teo.book.cloudnativespring.catalogservice.repository;

import com.miu.teo.book.cloudnativespring.catalogservice.domain.Book;

import java.util.Optional;

public interface BookRepository {
    Iterable<Book> findAll();
    Optional<Book> findByIsbn(String isbn);
    boolean existsByIsbn(String isbn);
    Book save(Book book);
    void deleteByIsbn(String isbn);
}
