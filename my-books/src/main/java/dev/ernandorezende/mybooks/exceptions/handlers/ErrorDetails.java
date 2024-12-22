package dev.ernandorezende.mybooks.exceptions.handlers;

import java.time.LocalDateTime;

public record ErrorDetails(LocalDateTime timestamp, String message, String details) {
}
