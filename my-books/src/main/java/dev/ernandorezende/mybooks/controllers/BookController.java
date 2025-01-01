package dev.ernandorezende.mybooks.controllers;

import dev.ernandorezende.mybooks.dtos.requests.BooksRequest;
import dev.ernandorezende.mybooks.dtos.responses.BookResponse;
import dev.ernandorezende.mybooks.services.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/books")
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping
    public ResponseEntity<BookResponse> createBook(@RequestBody BooksRequest booksRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(bookService.create(booksRequest));
    }

    @GetMapping
    public ResponseEntity<List<BookResponse>> getAllBooks() {
        return ResponseEntity.status(HttpStatus.OK).body(bookService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookResponse> getBookById(@PathVariable("id") Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(bookService.getById(id));
    }

}
