package dev.ernandorezende.mybooks.exceptions.handlers;

import dev.ernandorezende.mybooks.exceptions.AuthorNotFoundException;
import dev.ernandorezende.mybooks.exceptions.BookNotFoundException;
import dev.ernandorezende.mybooks.exceptions.PublisherNotFoundException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handleGlobalException(Exception ex, WebRequest request) {
        var errorDetails = new ErrorDetails(
                LocalDateTime.now(),
                ex.getMessage(),
                request.getDescription(false));
        return ResponseEntity.internalServerError().body(errorDetails);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDetails> handleGlobalException(MethodArgumentNotValidException ex, WebRequest request) {
        var errorDetails = new ErrorDetails(
                LocalDateTime.now(),
                ex.getBindingResult().getAllErrors().stream()
                        .map(DefaultMessageSourceResolvable::getDefaultMessage)
                        .collect(Collectors.joining("\n")),
                request.getDescription(false)
        );
        return ResponseEntity.badRequest().body(errorDetails);
    }

    @ExceptionHandler(AuthorNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleAuthorNotFoundException(AuthorNotFoundException ex, WebRequest request) {
        var errorDetails = new ErrorDetails(
                LocalDateTime.now(),
                ex.getMessage(),
                request.getDescription(false));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDetails);
    }

    @ExceptionHandler(PublisherNotFoundException.class)
    ResponseEntity<ErrorDetails> handlePublisherNotFoundException(PublisherNotFoundException ex, WebRequest request) {
        var errorDetails = new ErrorDetails(
                LocalDateTime.now(),
                ex.getMessage(),
                request.getDescription(false));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDetails);
    }

    @ExceptionHandler(BookNotFoundException.class)
    ResponseEntity<ErrorDetails> handleBookNotFoundException(BookNotFoundException ex, WebRequest request) {
        var errorDetails = new ErrorDetails(
                LocalDateTime.now(),
                ex.getMessage(),
                request.getDescription(false)
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDetails);
    }
}
