package com.miu.teo.book.cloudnativespring.catalogservice.controller;

import com.miu.teo.book.cloudnativespring.catalogservice.exception.BookNotFoundException;
import com.miu.teo.book.cloudnativespring.catalogservice.service.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = BookController.class)
class BookControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BookService bookService;

    @Test
    void whenGetNonExistingBook_thenReturn404() throws Exception {
        String ibsn = "1234567890";
        given(bookService.viewBookDetails(ibsn)).willThrow(BookNotFoundException.class);
        mockMvc.perform(get("/books/"+ibsn))
                .andExpect(status().isNotFound());
    }

}