package com.miu.teo.book.cloudnativespring.catalogservice.persistence;

import com.miu.teo.book.cloudnativespring.catalogservice.domain.Book;
import com.miu.teo.book.cloudnativespring.catalogservice.repository.BookRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Profile("testdata")
public class InMemoryBookRepository implements BookRepository {

    private static final Map<String, Book> books = new ConcurrentHashMap<>();

    @Override
    public <S extends Book> Iterable<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<Book> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public Iterable<Book> findAll() {
        return books.values();
    }

    @Override
    public Iterable<Book> findAllById(Iterable<Long> longs) {
        return null;
    }

    @Override
    public Optional<Book> findByIsbn(String isbn) {
        return Optional.ofNullable(books.get(isbn));
    }

    @Override
    public boolean existsByIsbn(String isbn) {
        return books.containsKey(isbn);
    }

    @Override
    public Book save(Book book) {
        books.put(book.isbn(), book);
                return book;
    }

    @Override
    public void deleteByIsbn(String isbn) {
            books.remove(isbn);
    }

    @Override
    public long count() {
        return books.size();
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public void delete(Book entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends Book> entities) {

    }

    @Override
    public void deleteAll() {

    }
}
