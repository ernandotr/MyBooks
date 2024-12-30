package dev.ernandorezende.mybooks.services;

import dev.ernandorezende.mybooks.dtos.requests.AuthorRequest;
import dev.ernandorezende.mybooks.dtos.responses.AuthorResponse;
import dev.ernandorezende.mybooks.entities.Author;
import dev.ernandorezende.mybooks.exceptions.AuthorNotFoundException;
import dev.ernandorezende.mybooks.repositories.AuthorRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthorService {
    private final AuthorRepository authorRepository;
    private final ModelMapper modelMapper = new ModelMapper();


    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public List<AuthorResponse> getAll() {
        return authorRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public AuthorResponse getById(Long id) {
        return toResponse(findById(id));
    }

    public AuthorResponse save(AuthorRequest authorRequest) {
        var author = modelMapper.map(authorRequest, Author.class);
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

    private AuthorResponse toResponse(Author author) {
        return modelMapper.map(author, AuthorResponse.class);
    }


}
