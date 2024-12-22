package dev.ernandorezende.mybooks.exceptions;

public class PublisherNotFoundException extends RuntimeException {
    public PublisherNotFoundException() {
        super("Publisher not found.");
    }
}
