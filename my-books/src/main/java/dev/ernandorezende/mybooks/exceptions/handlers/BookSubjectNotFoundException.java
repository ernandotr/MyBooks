package dev.ernandorezende.mybooks.exceptions.handlers;

public class BookSubjectNotFoundException extends RuntimeException {
    public BookSubjectNotFoundException() {
        super("Book subject not found.");
    }
}
