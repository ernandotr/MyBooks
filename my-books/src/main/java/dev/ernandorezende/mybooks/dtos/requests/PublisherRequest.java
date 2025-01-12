package dev.ernandorezende.mybooks.dtos.requests;

import jakarta.validation.constraints.NotBlank;

public record PublisherRequest(
        @NotBlank(message = "Publisher's name is required.")
        String name) {
}
