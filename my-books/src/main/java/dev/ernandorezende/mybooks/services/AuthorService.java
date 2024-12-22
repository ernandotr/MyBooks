package dev.ernandorezende.mybooks.services;

import dev.ernandorezende.mybooks.entities.Author;
import dev.ernandorezende.mybooks.repositories.AuthorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {
    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public List<Author> getAll() {
        return authorRepository.findAll();
    }

    public Author getById(Long id) {
        return authorRepository.findById(id).orElseThrow(() -> new RuntimeException("Author not found"));
    }

    public Author save(Author author) {
        return authorRepository.save(author);
    }

    public void update(Author authorReq, Long id) {
        var author = getById(id);

        author.setName(authorReq.getName());
        authorRepository.save(author);
    }

    public void delete(Long id) {
        authorRepository.deleteById(id);
    }


}
