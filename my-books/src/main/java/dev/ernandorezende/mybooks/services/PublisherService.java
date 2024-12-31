package dev.ernandorezende.mybooks.services;

import dev.ernandorezende.mybooks.dtos.requests.PublisherRequest;
import dev.ernandorezende.mybooks.dtos.responses.PublisherResponse;
import dev.ernandorezende.mybooks.entities.Publisher;
import dev.ernandorezende.mybooks.exceptions.PublisherNotFoundException;
import dev.ernandorezende.mybooks.repositories.PublisherRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PublisherService {

    private final ModelMapper modelMapper;
    private final PublisherRepository publisherRepository;

    public PublisherService(PublisherRepository publisherRepository, ModelMapper modelMapper) {
        this.publisherRepository = publisherRepository;
        this.modelMapper = modelMapper;
    }

    public PublisherResponse create(PublisherRequest publisherReq) {
        Publisher publisher  = new Publisher(publisherReq.name());
        publisher = publisherRepository.save(publisher);
        return toResponse(publisher);
    }

    public PublisherResponse getById(Long id) {
        return toResponse(findById(id));
    }

    public void update(PublisherRequest publisherReq, Long id) {
        Publisher publisher = findById(id);
        publisher.setName(publisherReq.name());
        publisherRepository.save(publisher);
    }

    public List<PublisherResponse> getAll() {
        return publisherRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public void delete(Long id) {
        publisherRepository.deleteById(id);
    }

    private Publisher findById(Long id) {
        return publisherRepository.findById(id).orElseThrow(PublisherNotFoundException::new);
    }

    private PublisherResponse toResponse(Publisher publisher) {
        return modelMapper.map(publisher, PublisherResponse.class);
    }

}
