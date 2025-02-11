package com.miu.teo.book.cloudnativespring.catalogservice;

import com.miu.teo.book.cloudnativespring.catalogservice.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("testdata")
class CatalogserviceApplicationTestdataTests {

    @Autowired
    private BookRepository bookRepository;


    @Test
    void testBookRepositorySize() {
        assertEquals(3, bookRepository.count());
    }

}
