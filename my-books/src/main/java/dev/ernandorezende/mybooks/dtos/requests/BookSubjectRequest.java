package dev.ernandorezende.mybooks.dtos.requests;

import jakarta.validation.constraints.NotBlank;

public record BookSubjectRequest(
        @NotBlank(message = "The 'subject' field is mandatory.")
        String subject
) {
}
