package dev.ernandorezende.mybooks.controllers;

import dev.ernandorezende.mybooks.dtos.requests.PublisherRequest;
import dev.ernandorezende.mybooks.entities.Publisher;
import dev.ernandorezende.mybooks.services.PublisherService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping( "api/publishers")
public class PublisherController {

    private final PublisherService publisherService;

    public PublisherController(PublisherService publisherService) {
        this.publisherService = publisherService;
    }

    @GetMapping
    public ResponseEntity<List<Publisher>> getAllPublishers() {
        return ResponseEntity.ok(this.publisherService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Publisher> getPublisherById(@PathVariable Long id) {
        return ResponseEntity.ok(this.publisherService.getById(id));
    }

    @PostMapping
    public ResponseEntity<Publisher> savePublisher(@RequestBody PublisherRequest publisher) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.publisherService.create(publisher));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updatePublisher(@PathVariable Long id, @RequestBody PublisherRequest publisher) {
        this.publisherService.update(publisher, id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePublisher(@PathVariable Long id) {
        this.publisherService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
