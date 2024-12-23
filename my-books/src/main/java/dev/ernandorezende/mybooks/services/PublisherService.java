package dev.ernandorezende.mybooks.services;

import dev.ernandorezende.mybooks.entities.Publisher;
import dev.ernandorezende.mybooks.exceptions.PublisherNotFoundException;
import dev.ernandorezende.mybooks.repositories.PublisherRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class PublisherService {

    private final PublisherRepository publisherRepository;

    public PublisherService(PublisherRepository publisherRepository) {
        this.publisherRepository = publisherRepository;
    }

    public Publisher create(@RequestBody  Publisher publisher) {
        return publisherRepository.save(publisher);
    }

    public Publisher getById(Long id) {
        return publisherRepository.findById(id).orElseThrow(PublisherNotFoundException::new);
    }

    public Publisher update(Publisher publisherReq, Long id) {
        var publisher = getById(id);

        publisher.setName(publisherReq.getName());
        return publisherRepository.save(publisher);
    }

    public List<Publisher> getAll() {
        return publisherRepository.findAll();
    }

    public void delete(Long id) {
        publisherRepository.deleteById(id);
    }


}
