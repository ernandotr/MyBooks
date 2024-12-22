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
        return authorRepository.findById(id).orElse(null);
    }

    public Author save(Author author) {
        return authorRepository.save(author);
    }

    public Author update(Author authorReq, Long id) {
        var author = getById(id);
        if (author == null) {
            return null;
        }
        author.setName(authorReq.getName());
//        author.setEmail(authorReq.getEmail());
//        author.setPhone(authorReq.getPhone());
        return authorRepository.save(author);
    }

    public void delete(Long id) {
        authorRepository.deleteById(id);
    }


}
