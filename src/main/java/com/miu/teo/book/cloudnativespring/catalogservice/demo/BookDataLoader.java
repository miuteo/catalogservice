package com.miu.teo.book.cloudnativespring.catalogservice.demo;

import com.miu.teo.book.cloudnativespring.catalogservice.repository.BookRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Profile("demo")
public class BookDataLoader {
    final BookRepository bookRepository;
    final BookProperties bookProperties;

    public BookDataLoader(BookRepository bookRepository, BookProperties bookProperties) {
        this.bookRepository = bookRepository;
        this.bookProperties = bookProperties;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void loadData() {
        bookRepository.saveAll(bookProperties.books());
    }
}
