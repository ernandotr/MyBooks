package dev.ernandorezende.mybooks.services;

import dev.ernandorezende.mybooks.dtos.requests.PublisherRequest;
import dev.ernandorezende.mybooks.entities.Publisher;
import dev.ernandorezende.mybooks.exceptions.PublisherNotFoundException;
import dev.ernandorezende.mybooks.repositories.PublisherRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PublisherService {

    private final ModelMapper modelMapper = new ModelMapper();
    private final PublisherRepository publisherRepository;

    public PublisherService(PublisherRepository publisherRepository) {
        this.publisherRepository = publisherRepository;
    }

    public Publisher create(PublisherRequest publisherReq) {
        Publisher publisher = modelMapper.map(publisherReq, Publisher.class);
        return publisherRepository.save(publisher);
    }

    public Publisher getById(Long id) {
        return publisherRepository.findById(id).orElseThrow(PublisherNotFoundException::new);
    }

    public void update(PublisherRequest publisherReq, Long id) {
        var publisher = getById(id);

        publisher.setName(publisherReq.getName());
        publisherRepository.save(publisher);
    }

    public List<Publisher> getAll() {
        return publisherRepository.findAll();
    }

    public void delete(Long id) {
        publisherRepository.deleteById(id);
    }


}
