package com.miu.teo.book.cloudnativespring.catalogservice.controller;

import com.miu.teo.book.cloudnativespring.catalogservice.domain.Book;
import com.miu.teo.book.cloudnativespring.catalogservice.service.BookService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("books")
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public Iterable<Book> get(){
        return bookService.viewBookList();
    }

    @GetMapping("{isbn}")
    public Book get(@PathVariable String isbn){
       return bookService.viewBookDetails(isbn);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Book post(@Valid @RequestBody Book book){
        return bookService.addBookCatalog(book);
    }

    @DeleteMapping("{isbn}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String isbn){
        bookService.removeBookFromCatalog(isbn);
    }

    @PutMapping("{isbn}")
    public Book put(@PathVariable String isbn,@Valid @RequestBody Book book){
        return bookService.editBookDetails(isbn, book);
    }
}
