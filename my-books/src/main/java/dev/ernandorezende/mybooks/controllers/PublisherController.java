package dev.ernandorezende.mybooks.controllers;

import dev.ernandorezende.mybooks.dtos.requests.PublisherRequest;
import dev.ernandorezende.mybooks.dtos.responses.PublisherResponse;
import dev.ernandorezende.mybooks.exceptions.handlers.ErrorDetails;
import dev.ernandorezende.mybooks.services.PublisherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Publisher", description = "The publisher operations")
@RestController
@RequestMapping( "api/publishers")
public class PublisherController {

    private final PublisherService publisherService;

    public PublisherController(PublisherService publisherService) {
        this.publisherService = publisherService;
    }

    @Operation(
            summary = "Fetch all publishers",
            description = "Fetch all publisher entities containing id and and name fields"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "500", description = "Internal server errors",
                    content = @Content(schema = @Schema(implementation = ErrorDetails.class)))
    })
    @GetMapping(produces = {"application/json", "application/xml"})
    public ResponseEntity<Page<PublisherResponse>> getAllPublishers(Pageable pageable) {
        return ResponseEntity.ok(this.publisherService.getAll(pageable));
    }

    @Operation(
            summary = "Fetch an specific publisher",
            parameters = {@Parameter(in = ParameterIn.PATH, name = "id", description = "Publisher Id")},
            description = "Fetch a publisher by ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "404", description = "Resource not found",
                    content = @Content(schema = @Schema(implementation = ErrorDetails.class))),
            @ApiResponse(responseCode = "500", description = "Internal server errors",
                    content = @Content(schema = @Schema(implementation = ErrorDetails.class)))
    })
    @GetMapping(value = "/{id}", produces = {"application/json", "application/xml"})
    public ResponseEntity<PublisherResponse> getPublisherById(@PathVariable Long id) {
        return ResponseEntity.ok(this.publisherService.getById(id));
    }

    @Operation(
            summary = "Create a new publisher",
            description = "Allows the user create a new publisher, send the name in a xml or json payload format"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201" , description = "Successful operation"),
            @ApiResponse(responseCode = "500", description = "Internal server errors",
                    content = @Content(schema = @Schema(implementation = ErrorDetails.class)))
    })
    @PostMapping(consumes = {"application/json", "application/xml"}, produces = {"application/json", "application/xml"})
    public ResponseEntity<PublisherResponse> createPublisher(@Valid @RequestBody PublisherRequest publisher) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.publisherService.create(publisher));
    }

    @Operation(
            summary = "Update a publisher",
            parameters = {@Parameter(in = ParameterIn.PATH, name = "id", required = true, description = "Publisher Id")},
            description = "Allows the update the publisher information"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successful operation"),
            @ApiResponse(responseCode = "404", description = "Resource not found",
                    content = @Content(schema = @Schema(implementation = ErrorDetails.class))),
            @ApiResponse(responseCode = "500", description = "Internal server errors",
                    content = @Content(schema = @Schema(implementation = ErrorDetails.class)))
    })
    @PutMapping(value = "/{id}",
            consumes = {"application/json", "application/xml"}, produces = {"application/json", "application/xml"})
    public ResponseEntity<Void> updatePublisher(@PathVariable Long id, @Valid @RequestBody PublisherRequest publisher) {
        this.publisherService.update(publisher, id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Delete a publisher",
            parameters = {@Parameter(in = ParameterIn.PATH, name = "id", required = true, description = "Publisher Id")},
            description = "Allows the user delete an publisher by Id"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successful operation"),
            @ApiResponse(responseCode = "500", description = "Internal server errors",
                    content = @Content(schema = @Schema(implementation = ErrorDetails.class)))
    })
    @DeleteMapping(value = "/{id}", produces = {"application/json", "application/xml"})
    public ResponseEntity<Void> deletePublisher(@PathVariable Long id) {
        this.publisherService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
