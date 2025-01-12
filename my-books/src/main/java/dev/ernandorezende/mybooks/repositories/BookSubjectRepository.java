package dev.ernandorezende.mybooks.repositories;

import dev.ernandorezende.mybooks.entities.BookSubject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookSubjectRepository extends JpaRepository<BookSubject, Long> {
}
