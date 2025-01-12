package dev.ernandorezende.mybooks.controllers;

import dev.ernandorezende.mybooks.dtos.requests.BookSubjectRequest;
import dev.ernandorezende.mybooks.dtos.responses.BookSubjectResponse;
import dev.ernandorezende.mybooks.services.BookSubjectService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/book-subjects")
public class BookSubjectController {

    private final BookSubjectService bookSubjectService;
    public BookSubjectController(BookSubjectService bookSubjectService) {
        this.bookSubjectService = bookSubjectService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<BookSubjectResponse> getBookSubjects(Pageable pageable) {
        return bookSubjectService.findAll(pageable);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public BookSubjectResponse getBookSubject(@PathVariable("id") Long id) {
        return bookSubjectService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookSubjectResponse createBookSubject(@Valid @RequestBody BookSubjectRequest bookSubjectRequest) {
        return bookSubjectService.save(bookSubjectRequest);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateBookSubject(@PathVariable("id") Long id, @Valid @RequestBody BookSubjectRequest bookSubjectRequest) {
        bookSubjectService.update(bookSubjectRequest, id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBookSubject(@PathVariable("id") Long id) {
        bookSubjectService.delete(id);
    }
}
