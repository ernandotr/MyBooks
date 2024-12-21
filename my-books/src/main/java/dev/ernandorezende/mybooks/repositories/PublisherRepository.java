package dev.ernandorezende.mybooks.repositories;

import dev.ernandorezende.mybooks.entities.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PublisherRepository extends JpaRepository<Publisher, Long> {
}
