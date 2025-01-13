package dev.ernandorezende.mybooks.services;

import dev.ernandorezende.mybooks.dtos.requests.BookSubjectRequest;
import dev.ernandorezende.mybooks.dtos.responses.BookSubjectResponse;
import dev.ernandorezende.mybooks.entities.BookSubject;
import dev.ernandorezende.mybooks.exceptions.handlers.BookSubjectNotFoundException;
import dev.ernandorezende.mybooks.repositories.BookSubjectRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookSubjectService {
    private final BookSubjectRepository bookSubjectRepository;
    public BookSubjectService(BookSubjectRepository bookSubjectRepository) {
        this.bookSubjectRepository = bookSubjectRepository;
    }

    public Page<BookSubjectResponse> findAll(Pageable pageable) {
        List<BookSubjectResponse> bookSubjects = bookSubjectRepository.findAll(pageable).getContent()
                .stream().map(this::toResponse).toList();
        return new PageImpl<>(bookSubjects, pageable, bookSubjects.size());
    }

    public BookSubjectResponse findById(Long id) {
        BookSubject bookSubject = getById(id);
        return toResponse(bookSubject);
    }

    public BookSubjectResponse save(BookSubjectRequest bookSubjectRequest) {
        BookSubject bookSubject = new BookSubject(bookSubjectRequest.subject());
        bookSubject = bookSubjectRepository.save(bookSubject);
        return toResponse(bookSubject);
    }

    public void update(BookSubjectRequest bookSubjectRequest, Long id) {
        BookSubject bookSubject = getById(id);
        bookSubject.setSubject(bookSubjectRequest.subject());
        bookSubjectRepository.save(bookSubject);
    }

    public void delete(Long id) {
        bookSubjectRepository.deleteById(id);
    }

    private BookSubject getById(Long id) {
        return bookSubjectRepository.findById(id).orElseThrow(BookSubjectNotFoundException::new);
    }

    private BookSubjectResponse toResponse(BookSubject bookSubject) {
        return new BookSubjectResponse(bookSubject.getId(), bookSubject.getSubject());
    }

}
