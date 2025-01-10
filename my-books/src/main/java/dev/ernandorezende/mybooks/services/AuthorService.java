package dev.ernandorezende.mybooks.services;

import dev.ernandorezende.mybooks.dtos.requests.AuthorRequest;
import dev.ernandorezende.mybooks.dtos.responses.AuthorResponse;
import dev.ernandorezende.mybooks.dtos.responses.AuthorSummaryResponse;
import dev.ernandorezende.mybooks.entities.Author;
import dev.ernandorezende.mybooks.entities.Book;
import dev.ernandorezende.mybooks.exceptions.AuthorNotFoundException;
import dev.ernandorezende.mybooks.repositories.AuthorRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AuthorService {
    private final AuthorRepository authorRepository;
    private final ModelMapper modelMapper;


    public AuthorService(AuthorRepository authorRepository, ModelMapper modelMapper) {
        this.authorRepository = authorRepository;
        this.modelMapper = modelMapper;
    }

    public Page<AuthorSummaryResponse> getAll(Pageable pageable) {
        Page<Author> authors = authorRepository.findAll(pageable);
        List<AuthorSummaryResponse> authorResponseList = authors.stream()
                .map(this::toSummaryResponse)
                .collect(Collectors.toList());
        return new PageImpl<>(authorResponseList, pageable, authors.getTotalElements());
    }

    public AuthorResponse getById(Long id) {
        return toResponse(findById(id));
    }

    private AuthorResponse toResponse(Author author) {
        AuthorResponse response = modelMapper.map(author, AuthorResponse.class);
        Optional.of(author.getBooks()).ifPresent(books -> {
            response.setBooks(books.stream().map(Book::getTitle).collect(Collectors.joining()));
        });
        return response;
    }

    public AuthorResponse save(AuthorRequest authorRequest) {
        var author = new Author(authorRequest.name());
        return toResponse(authorRepository.save(author));
    }

    public void update(AuthorRequest authorReq, Long id) {
        Author author = findById(id);
        author.setName(authorReq.name());
        authorRepository.save(author);
    }

    public void delete(Long id) {
        authorRepository.deleteById(id);
    }

    private Author findById(Long id) {
        return authorRepository.findById(id).orElseThrow(AuthorNotFoundException::new);
    }

    private AuthorSummaryResponse toSummaryResponse(Author author) {
        return modelMapper.map(author, AuthorSummaryResponse.class);
    }


}
