package dev.ernandorezende.mybooks.services;

import dev.ernandorezende.mybooks.dtos.requests.AuthorRequest;
import dev.ernandorezende.mybooks.entities.Author;
import dev.ernandorezende.mybooks.exceptions.AuthorNotFoundException;
import dev.ernandorezende.mybooks.repositories.AuthorRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {
    private final AuthorRepository authorRepository;
    private final ModelMapper modelMapper = new ModelMapper();


    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public List<Author> getAll() {
        return authorRepository.findAll();
    }

    public Author getById(Long id) {
        return authorRepository.findById(id).orElseThrow(AuthorNotFoundException::new);
    }

    public Author save(AuthorRequest authorRequest) {
        var author = modelMapper.map(authorRequest, Author.class);
        return authorRepository.save(author);
    }

    public void update(AuthorRequest authorReq, Long id) {
        var author = getById(id);

        author.setName(authorReq.getName());
        authorRepository.save(author);
    }

    public void delete(Long id) {
        authorRepository.deleteById(id);
    }


}
