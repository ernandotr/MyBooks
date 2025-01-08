package dev.ernandorezende.mybooks.controllers;

import dev.ernandorezende.mybooks.dtos.requests.BooksRequest;
import dev.ernandorezende.mybooks.dtos.responses.BookResponse;
import dev.ernandorezende.mybooks.dtos.responses.BookSummaryResponse;
import dev.ernandorezende.mybooks.exceptions.handlers.ErrorDetails;
import dev.ernandorezende.mybooks.services.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Book", description = "The book operations.")
@RestController
@RequestMapping("api/books")
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @Operation(summary = "Save a new book", description = "Allows the use to record a new book information.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successful operation."),
            @ApiResponse(responseCode = "500", description = "Internal errors.",
                    content = { @Content(schema = @Schema(implementation = ErrorDetails.class))})
    })
    @PostMapping(consumes = {"application/json", "application/xml"}, produces = {"application/json", "application/xml"})
    public ResponseEntity<BookResponse> createBook(@RequestBody BooksRequest booksRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(bookService.create(booksRequest));
    }


    @Operation(summary = "Get all books", description = "Retrieve a collection of books allowing pagination and sort.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation."),
            @ApiResponse(responseCode = "500", description = "Internal errors.",
                    content = { @Content(schema = @Schema(implementation = ErrorDetails.class))})
    })
    @GetMapping
    public ResponseEntity<Page<BookSummaryResponse>> getAllBooks(Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(bookService.getAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookResponse> getBookById(@PathVariable("id") Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(bookService.getBookById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateBook(@PathVariable("id") Long id, @RequestBody BooksRequest booksRequest) {
        bookService.update(booksRequest, id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBook(@PathVariable("id") Long id) {
        bookService.delete(id);
    }

}
