package dev.ernandorezende.mybooks.exceptions;

public class AuthorNotFoundException extends RuntimeException {
    public AuthorNotFoundException() {
        super("Author not found.");
    }
}
