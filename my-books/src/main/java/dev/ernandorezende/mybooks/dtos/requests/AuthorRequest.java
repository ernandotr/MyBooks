package dev.ernandorezende.mybooks.dtos.requests;

import jakarta.validation.constraints.NotBlank;

public record AuthorRequest(
        @NotBlank(message = "Author's name is mandatory.")
        String name) {
}
