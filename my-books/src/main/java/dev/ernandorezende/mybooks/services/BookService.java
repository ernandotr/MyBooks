package dev.ernandorezende.mybooks.services;

import dev.ernandorezende.mybooks.dtos.requests.BooksRequest;
import dev.ernandorezende.mybooks.dtos.responses.BookResponse;
import dev.ernandorezende.mybooks.entities.Book;
import dev.ernandorezende.mybooks.repositories.BookRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final ModelMapper modelMapper;

    public BookService(BookRepository bookRepository, ModelMapper modelMapper) {
        this.bookRepository = bookRepository;
        this.modelMapper = modelMapper;
    }

    public BookResponse save(BooksRequest booksRequest) {
        Book book = toEntity(booksRequest);
        book = bookRepository.save(book);

        return toResponse(book);
    }

    private Book toEntity(BooksRequest booksRequest) {
        return modelMapper.map(booksRequest, Book.class);
    }

    private BookResponse toResponse(Book book) {
        return modelMapper.map(book, BookResponse.class);

    }
}
