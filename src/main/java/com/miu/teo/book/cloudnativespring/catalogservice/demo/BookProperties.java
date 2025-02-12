package com.miu.teo.book.cloudnativespring.catalogservice.demo;

import com.miu.teo.book.cloudnativespring.catalogservice.domain.Book;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Profile;

import java.util.List;

@ConfigurationProperties(prefix = "polar.demo")
@Profile("demo")
public record BookProperties(
        /**
         * demo books to be saved in memory database
         */
        List<Book> books) {
}
