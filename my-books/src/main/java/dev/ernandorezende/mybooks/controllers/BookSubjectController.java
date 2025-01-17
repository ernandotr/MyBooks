package dev.ernandorezende.mybooks.controllers;

import dev.ernandorezende.mybooks.dtos.requests.BookSubjectRequest;
import dev.ernandorezende.mybooks.dtos.responses.BookSubjectResponse;
import dev.ernandorezende.mybooks.exceptions.handlers.ErrorDetails;
import dev.ernandorezende.mybooks.services.BookSubjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Book subjects", description = "It allows managing to Book Subjects operations.")
@RestController
@RequestMapping("api/book-subjects")
public class BookSubjectController {

    private final BookSubjectService bookSubjectService;
    public BookSubjectController(BookSubjectService bookSubjectService) {
        this.bookSubjectService = bookSubjectService;
    }

    @Operation(
            summary = "List Book Subjects",
            description = "It allows listing all Book subjects with pagination and sort."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successful operation."),
            @ApiResponse(responseCode = "500", description = "Internal server errors.",
                    content = { @Content(schema = @Schema(implementation = ErrorDetails.class))})
    })
    @GetMapping(produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public Page<BookSubjectResponse> getBookSubjects(Pageable pageable) {
        return bookSubjectService.findAll(pageable);
    }

    @Operation(
            summary = "Retrieves a book subject.",
            description = "It retrieves a book subject by a passed Id."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation."),
            @ApiResponse(responseCode = "404", description = "Error if the resource not found."),
            @ApiResponse(responseCode = "500", description = "Internal server errors")
    })
    @GetMapping(value = "/{id}", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public BookSubjectResponse getBookSubject(@PathVariable("id") Long id) {
        return bookSubjectService.findById(id);
    }

    @Operation(
            summary = "Create a new Book Subject.",
            description = "It creates and return a created Book subject."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successful operation."),
            @ApiResponse(responseCode = "400", description = "Errors validating input fields."),
            @ApiResponse(responseCode = "500", description = "Internal server errors.")
    })
    @PostMapping(produces = "application/json", consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public BookSubjectResponse createBookSubject(@Valid @RequestBody BookSubjectRequest bookSubjectRequest) {
        return bookSubjectService.save(bookSubjectRequest);
    }

    @Operation(
            summary = "Update Book Subject.",
            description = "It allows to update Book Subject."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successful operation."),
            @ApiResponse(responseCode = "400", description = "Errors validating input fields."),
            @ApiResponse(responseCode = "404", description = "Error if the resource not found."),
            @ApiResponse(responseCode = "500", description = "Internal server errors.")
    })
    @PutMapping(value = "/{id}", consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateBookSubject(@PathVariable("id") Long id, @Valid @RequestBody BookSubjectRequest bookSubjectRequest) {
        bookSubjectService.update(bookSubjectRequest, id);
    }

    @Operation(
            summary = "Delete Book Subject.",
            description = "It allows to delete a Book Subject by a passed ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successful operation."),
            @ApiResponse(responseCode = "500", description = "Internal server errors.")
    })
    @DeleteMapping(value = "/{id}", produces = "application/json")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBookSubject(@PathVariable("id") Long id) {
        bookSubjectService.delete(id);
    }
}
