package com.miu.teo.book.cloudnativespring.catalogservice.service;

import com.miu.teo.book.cloudnativespring.catalogservice.domain.Book;
import com.miu.teo.book.cloudnativespring.catalogservice.exception.BookAlreadyExistsException;
import com.miu.teo.book.cloudnativespring.catalogservice.exception.BookNotFoundException;
import com.miu.teo.book.cloudnativespring.catalogservice.repository.BookRepository;
import org.springframework.stereotype.Service;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository){
        this.bookRepository = bookRepository;
    }
    public Iterable<Book> viewBookList(){
        return bookRepository.findAll();
    }

    public Book viewBookDetails(String isbn){
        return bookRepository.findByIsbn(isbn).orElseThrow(() -> new BookNotFoundException(isbn));
    }

    public Book addBookCatalog(Book book){
        if(bookRepository.existsByIsbn(book.isbn()))
            throw new BookAlreadyExistsException(book.isbn());
        return bookRepository.save(book);
    }

    public void removeBookFromCatalog(String isbn){
        bookRepository.deleteByIsbn(isbn);
    }

    public Book editBookDetails(String isbn, Book book){
        return bookRepository.findByIsbn(isbn)
                .map(oldBook -> new Book(book.id(),isbn,book.title(),book.author(),book.price(),
                        oldBook.createdDate(),oldBook.lastModifiedDate(),oldBook.version()))
                .map(bookRepository::save)
                .orElseGet(() -> addBookCatalog(book));
    }
}
