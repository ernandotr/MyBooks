package dev.ernandorezende.mybooks.services;

import dev.ernandorezende.mybooks.dtos.requests.BooksRequest;
import dev.ernandorezende.mybooks.dtos.responses.BookResponse;
import dev.ernandorezende.mybooks.dtos.responses.BookSummaryResponse;
import dev.ernandorezende.mybooks.entities.Author;
import dev.ernandorezende.mybooks.entities.Book;
import dev.ernandorezende.mybooks.entities.Publisher;
import dev.ernandorezende.mybooks.exceptions.BookNotFoundException;
import dev.ernandorezende.mybooks.exceptions.PublisherNotFoundException;
import dev.ernandorezende.mybooks.repositories.AuthorRepository;
import dev.ernandorezende.mybooks.repositories.BookRepository;
import dev.ernandorezende.mybooks.repositories.PublisherRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class BookService {

    private final AuthorRepository authorRepository;
    private  final PublisherRepository publisherRepository;
    private final BookRepository bookRepository;
    private final ModelMapper modelMapper;

    public BookService(BookRepository bookRepository, ModelMapper modelMapper,
                       AuthorRepository authorRepository, PublisherRepository publisherRepository) {
        this.bookRepository = bookRepository;
        this.modelMapper = modelMapper;
        this.authorRepository = authorRepository;
        this.publisherRepository = publisherRepository;
    }

    public BookResponse create(BooksRequest booksRequest) {
        Book book = toEntity(booksRequest);
        book.setAuthors(authorRepository.findAllById(booksRequest.getAuthors()));
        book.setPublisher(getPublisher(booksRequest));
        book = bookRepository.save(book);

        return toResponse(book);
    }

    public void update(BooksRequest booksRequest, Long id) {
        if(booksRequest.getAuthors() == null || booksRequest.getAuthors().isEmpty()) {
            throw new RuntimeException("Authors cannot be empty");
        }
        Book book = getById(id);
        book.setAuthors(authorRepository.findAllById(booksRequest.getAuthors()));
        book.setPublisher(getPublisher(booksRequest));
        book.setTitle(booksRequest.getTitle());
        book.setGenre(booksRequest.getGenre());
        book.setLanguage(booksRequest.getLanguage());
        book.setPages(booksRequest.getPages());
        book.setUrl(booksRequest.getUrl());
        bookRepository.save(book);
    }

    private Publisher getPublisher(BooksRequest booksRequest) {
        return publisherRepository.findById(booksRequest.getPublisher()).orElseThrow(PublisherNotFoundException::new);
    }

    public Page<BookSummaryResponse> getAll(Pageable pageable) {
        Page<Book> booksPage = bookRepository.findAll(pageable);
        var books = booksPage.getContent().stream().map(this::toSummaryResponse).toList();
        return new PageImpl<>(books, pageable, booksPage.getTotalElements());
    }

    public BookResponse getBookById(Long id) {
        return toResponse(getById(id));
    }

    private Book getById(Long id) {
        return bookRepository.findById(id).orElseThrow(BookNotFoundException::new);
    }

    public void delete(Long id) {
        bookRepository.deleteById(id);
    }

    private Book toEntity(BooksRequest booksRequest) {
        return modelMapper.map(booksRequest, Book.class);
    }

    private BookSummaryResponse toSummaryResponse(Book book) {
        var response = modelMapper.map(book, BookSummaryResponse.class);
        response.setAuthor(book.getAuthors().stream()
                .map(Author::getName)
                .collect(Collectors.joining(", ")));
        return response;
    }

    private BookResponse toResponse(Book book) {
        return modelMapper.map(book, BookResponse.class);

    }
}
