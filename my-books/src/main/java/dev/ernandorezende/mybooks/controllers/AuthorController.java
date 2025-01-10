package dev.ernandorezende.mybooks.controllers;

import dev.ernandorezende.mybooks.dtos.requests.AuthorRequest;
import dev.ernandorezende.mybooks.dtos.responses.AuthorResponse;
import dev.ernandorezende.mybooks.dtos.responses.AuthorSummaryResponse;
import dev.ernandorezende.mybooks.entities.Author;
import dev.ernandorezende.mybooks.exceptions.handlers.ErrorDetails;
import dev.ernandorezende.mybooks.services.AuthorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Author", description = "The author operations")
@RestController
@RequestMapping( "api/authors")
public class AuthorController {
    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @Operation(
            summary = "Fetch all authors",
            description = "fetch all author entities containing id and and name fields"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "500", description = "Internal server errors",
                    content = @Content(schema = @Schema(implementation = ErrorDetails.class)))
    })
    @GetMapping(produces = {"application/json", "application/xml"})
    public ResponseEntity<Page<AuthorSummaryResponse>> getAllAuthors(Pageable pageable ) {
        return ResponseEntity.ok(authorService.getAll(pageable));
    }

    @Operation(
            summary = "Fetch an specific author",
            parameters = {@Parameter(in = ParameterIn.PATH, name = "id", description = "Author Id")},
            description = "fetch an author by ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "404", description = "Resource not found",
                    content = @Content(schema = @Schema(implementation = ErrorDetails.class))),
            @ApiResponse(responseCode = "500", description = "Internal server errors",
                    content = @Content(schema = @Schema(implementation = ErrorDetails.class)))
    })
    @GetMapping(value = "/{id}", produces = {"application/json", "application/xml"})
    public ResponseEntity<AuthorResponse> getAuthorById(@PathVariable Long id) {
        return ResponseEntity.ok(authorService.getById(id));
    }

    @Operation(
            summary = "Create a new author",
            description = "Allows the user create a new author, send the name in a xml or json payload format"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201" , description = "Successful operation"),
            @ApiResponse(responseCode = "500", description = "Internal server errors",
                    content = @Content(schema = @Schema(implementation = ErrorDetails.class)))
    })
    @PostMapping(consumes = {"application/json", "application/xml"}, produces = {"application/json", "application/xml"})
    public ResponseEntity<AuthorResponse> saveAuthor(@Valid @RequestBody AuthorRequest author) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authorService.save(author));
    }

    @Operation(
            summary = "Update an author",
            parameters = {@Parameter(in = ParameterIn.PATH, name = "id", required = true, description = "Author Id")},
            description = "Allows the update the author information"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successful operation"),
            @ApiResponse(responseCode = "404", description = "Resource not found",
                    content = @Content(schema = @Schema(implementation = ErrorDetails.class))),
            @ApiResponse(responseCode = "500", description = "Internal server errors",
                    content = @Content(schema = @Schema(implementation = ErrorDetails.class)))
    })
    @PutMapping(value = "/{id}", consumes = {"application/json", "application/xml"}, produces = {"application/json", "application/xml"})
    public ResponseEntity<Void> updateAuthor(@PathVariable Long id, @RequestBody AuthorRequest author) {
        authorService.update(author, id);
        return ResponseEntity.noContent().build();
    }


    @Operation(
            summary = "Delete an author",
            parameters = {@Parameter(in = ParameterIn.PATH, name = "id", required = true, description = "Author Id")},
            description = "Allows the user delete an author by Id"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successful operation"),
            @ApiResponse(responseCode = "500", description = "Internal server errors",
                    content = @Content(schema = @Schema(implementation = ErrorDetails.class)))
    })
    @DeleteMapping(value = "/{id}", produces = {"application/json", "application/xml"})
    public ResponseEntity<Void> deleteAuthor(@PathVariable Long id) {
        authorService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
