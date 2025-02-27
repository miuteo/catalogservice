package com.miu.teo.book.cloudnativespring.catalogservice.repository;

import com.miu.teo.book.cloudnativespring.catalogservice.domain.Book;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface BookRepository extends CrudRepository<Book, Long> {
    Optional<Book> findByIsbn(String isbn);
    boolean existsByIsbn(String isbn);

    @Modifying
    @Query("delete from book where isbn = :isbn")
    void deleteByIsbn(String isbn);
}
