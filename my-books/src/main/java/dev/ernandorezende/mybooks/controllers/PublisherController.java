package dev.ernandorezende.mybooks.controllers;

import dev.ernandorezende.mybooks.entities.Publisher;
import dev.ernandorezende.mybooks.services.PublisherService;
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
    public List<Publisher> getAllPublishers() {
        return this.publisherService.getAll();
    }

    @GetMapping("/{id}")
    public Publisher getPublisherById(@PathVariable Long id) {
        return this.publisherService.getById(id);
    }

    @PostMapping
    public Publisher savePublisher(Publisher publisher) {
        return this.publisherService.create(publisher);
    }

    @PutMapping("/{id}")
    public Publisher updatePublisher(@PathVariable Long id, @RequestBody Publisher publisher) {
        return this.publisherService.update(publisher, id);
    }

    @DeleteMapping("/{id}")
    public void deletePublisher(@PathVariable Long id) {
        this.publisherService.delete(id);
    }
}
