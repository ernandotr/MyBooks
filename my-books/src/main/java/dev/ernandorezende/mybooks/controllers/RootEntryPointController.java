package dev.ernandorezende.mybooks.controllers;

import dev.ernandorezende.mybooks.dtos.responses.RootEntryPointResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class RootEntryPointController {

    @GetMapping
    public ResponseEntity<RootEntryPointResponse> getRoot() {
        RootEntryPointResponse resp = new RootEntryPointResponse()
                .add( linkTo(methodOn(BookController.class).getAllBooks(Pageable.unpaged()))
                        .withRel("books") );
        return ResponseEntity.ok(resp);
    }

}