package dev.ernandorezende.mybooks.repositories;

import dev.ernandorezende.mybooks.entities.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
