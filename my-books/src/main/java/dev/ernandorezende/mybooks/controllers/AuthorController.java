package dev.ernandorezende.mybooks.controllers;

import dev.ernandorezende.mybooks.dtos.requests.AuthorRequest;
import dev.ernandorezende.mybooks.dtos.responses.AuthorResponse;
import dev.ernandorezende.mybooks.entities.Author;
import dev.ernandorezende.mybooks.services.AuthorService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping( "api/authors")
public class AuthorController {
    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping
    public ResponseEntity<Page<Author>> getAllAuthors(@PageableDefault(page = 0, size = 10) Pageable pageable ) {
        return ResponseEntity.ok(authorService.getAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthorResponse> getAuthorById(@PathVariable Long id) {
        return ResponseEntity.ok(authorService.getById(id));
    }

    @PostMapping
    public ResponseEntity<AuthorResponse> saveAuthor(@RequestBody AuthorRequest author) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authorService.save(author));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateAuthor(@PathVariable Long id, @RequestBody AuthorRequest author) {
        authorService.update(author, id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable Long id) {
        authorService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
