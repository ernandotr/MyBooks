package dev.ernandorezende.mybooks.services;

import dev.ernandorezende.mybooks.dtos.requests.BookRequest;
import dev.ernandorezende.mybooks.dtos.responses.BookResponse;
import dev.ernandorezende.mybooks.dtos.responses.BookSummaryResponse;
import dev.ernandorezende.mybooks.entities.Author;
import dev.ernandorezende.mybooks.entities.Book;
import dev.ernandorezende.mybooks.entities.BookSubject;
import dev.ernandorezende.mybooks.entities.Publisher;
import dev.ernandorezende.mybooks.exceptions.BookNotFoundException;
import dev.ernandorezende.mybooks.exceptions.PublisherNotFoundException;
import dev.ernandorezende.mybooks.exceptions.handlers.BookSubjectNotFoundException;
import dev.ernandorezende.mybooks.repositories.AuthorRepository;
import dev.ernandorezende.mybooks.repositories.BookRepository;
import dev.ernandorezende.mybooks.repositories.BookSubjectRepository;
import dev.ernandorezende.mybooks.repositories.PublisherRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.stream.Collectors;

@Service
public class BookService {

    private final AuthorRepository authorRepository;
    private  final PublisherRepository publisherRepository;
    private final BookRepository bookRepository;
    private final BookSubjectRepository bookSubjectRepository;
    private final ModelMapper modelMapper;

    public BookService(BookRepository bookRepository, ModelMapper modelMapper,
                       AuthorRepository authorRepository, PublisherRepository publisherRepository, BookSubjectRepository bookSubjectRepository) {
        this.bookRepository = bookRepository;
        this.modelMapper = modelMapper;
        this.authorRepository = authorRepository;
        this.publisherRepository = publisherRepository;
        this.bookSubjectRepository = bookSubjectRepository;
    }

    @Transactional
    public BookSummaryResponse create(BookRequest bookRequest) {
        Book book = toEntity(bookRequest);
        book.setAuthors(new HashSet<>(authorRepository.findAllById(bookRequest.getAuthors())));
        book.setPublisher(getPublisher(bookRequest));
        book.setSubject(getSubject(bookRequest));
        book = bookRepository.save(book);

        return toSummaryResponse(book);
    }

    @Transactional
    public void update(BookRequest bookRequest, Long id) {
        if(bookRequest.getAuthors() == null || bookRequest.getAuthors().isEmpty()) {
            throw new RuntimeException("Authors cannot be empty");
        }
        Book book = getById(id);
        book.setAuthors(new HashSet<>(authorRepository.findAllById(bookRequest.getAuthors())));
        book.setPublisher(getPublisher(bookRequest));
        book.setTitle(bookRequest.getTitle());
        book.setSubject(getSubject(bookRequest));
        book.setLanguage(bookRequest.getLanguage());
        book.setPages(bookRequest.getPages());
        book.setUrl(bookRequest.getUrl());
        bookRepository.save(book);
    }

    private BookSubject getSubject(BookRequest bookRequest) {
        return bookSubjectRepository.findById(bookRequest.getSubject())
                .orElseThrow(BookSubjectNotFoundException::new);
    }

    private Publisher getPublisher(BookRequest bookRequest) {
        return publisherRepository.findById(bookRequest.getPublisher())
                .orElseThrow(PublisherNotFoundException::new);
    }

    @Transactional(readOnly = true)
    public Page<BookSummaryResponse> getAll(Pageable pageable) {
        Page<Book> booksPage = bookRepository.findAll(pageable);
        var books = booksPage.getContent().stream().map(this::toSummaryResponse).toList();
        return new PageImpl<>(books, pageable, booksPage.getTotalElements());
    }

    @Transactional(readOnly = true)
    public BookResponse getBookById(Long id) {
        return toResponse(getById(id));
    }

    private Book getById(Long id) {
        return bookRepository.findById(id).orElseThrow(BookNotFoundException::new);
    }

    @Transactional
    public void delete(Long id) {
        bookRepository.deleteById(id);
    }

    private Book toEntity(BookRequest bookRequest) {
        return modelMapper.map(bookRequest, Book.class);
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
