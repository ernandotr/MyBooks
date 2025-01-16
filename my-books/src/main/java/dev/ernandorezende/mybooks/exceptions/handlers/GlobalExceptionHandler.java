package dev.ernandorezende.mybooks.exceptions.handlers;

import dev.ernandorezende.mybooks.exceptions.AuthorNotFoundException;
import dev.ernandorezende.mybooks.exceptions.BookNotFoundException;
import dev.ernandorezende.mybooks.exceptions.PublisherNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ErrorDetails handleGlobalException(Exception ex, WebRequest request) {
        return new ErrorDetails(
                LocalDateTime.now(),
                ex.getMessage(),
                request.getDescription(false));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();

            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
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
