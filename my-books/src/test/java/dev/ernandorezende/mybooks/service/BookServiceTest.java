package dev.ernandorezende.mybooks.service;

import dev.ernandorezende.mybooks.dtos.requests.BooksRequest;
import dev.ernandorezende.mybooks.dtos.responses.BookResponse;
import dev.ernandorezende.mybooks.dtos.responses.BookSummaryResponse;
import dev.ernandorezende.mybooks.entities.Author;
import dev.ernandorezende.mybooks.entities.Book;
import dev.ernandorezende.mybooks.entities.BookSubject;
import dev.ernandorezende.mybooks.entities.Publisher;
import dev.ernandorezende.mybooks.exceptions.AuthorNotFoundException;
import dev.ernandorezende.mybooks.exceptions.BookNotFoundException;
import dev.ernandorezende.mybooks.exceptions.PublisherNotFoundException;
import dev.ernandorezende.mybooks.repositories.AuthorRepository;
import dev.ernandorezende.mybooks.repositories.BookRepository;
import dev.ernandorezende.mybooks.repositories.BookSubjectRepository;
import dev.ernandorezende.mybooks.repositories.PublisherRepository;
import dev.ernandorezende.mybooks.services.BookService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Spy
    ModelMapper modelMapper;

    @Mock
    BookRepository bookRepository;
    @Mock
    AuthorRepository authorRepository;
    @Mock
    PublisherRepository publisherRepository;
    @Mock
    BookSubjectRepository pubBookSubjectRepository;

    @InjectMocks
    BookService bookService;

    @Test
    void saveBookSuccess() {
        BooksRequest booksRequest = new BooksRequest();
        booksRequest.setTitle("Title");
        booksRequest.setPublisher(1L);

        Book book = buildExpectedBook();
        Author author = buildAuthor();
        Publisher publisher = buildPublisher();
        book.setAuthors(Set.of(author));
        book.setPublisher(publisher);

        when(publisherRepository.findById(anyLong())).thenReturn(Optional.of(publisher));
        when(bookRepository.save(any(Book.class))).thenReturn(book);
        BookResponse response = bookService.create(booksRequest);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(book.getTitle(), response.getTitle());
    }

    @Test
    void saveBookFailureAuthorNotFound() {
        BooksRequest booksRequest = new BooksRequest();
        booksRequest.setTitle("Title");

        when(authorRepository.findAllById(anyList())).thenThrow(AuthorNotFoundException.class);
        Assertions.assertThrows(AuthorNotFoundException.class, () -> bookService.create(booksRequest));
    }

    @Test
    void saveBookFailurePublisherNotFound() {
        BooksRequest booksRequest = new BooksRequest();
        booksRequest.setTitle("Title");
        booksRequest.setPublisher(1L);

        when(publisherRepository.findById(anyLong())).thenReturn(Optional.empty());
        Assertions.assertThrows(PublisherNotFoundException.class, () -> bookService.create(booksRequest));
    }

    @Test
    void getAllBooks() {
        Book book = buildExpectedBook();
        when(bookRepository.findAll(any(Pageable.class))).thenReturn(new PageImpl<>(List.of(book), Pageable.unpaged(), 1L));

        Page<BookSummaryResponse> response = bookService.getAll(Pageable.unpaged());

        Assertions.assertNotNull(response);
        Assertions.assertEquals(1, response.getTotalElements());
        Assertions.assertEquals(book.getTitle(), response.getContent().getFirst().getTitle());
    }

    @Test
    void getBookByIdSuccess() {
        Book book = buildExpectedBook();
        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(book));
        BookResponse response = bookService.getBookById(1L);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(book.getTitle(), response.getTitle());
    }

    @Test
    void getBookByIdFailureBookNotFound() {
        when(bookRepository.findById(anyLong())).thenReturn(Optional.empty());
        Assertions.assertThrows(BookNotFoundException.class, () -> bookService.getBookById(2L));

    }

    @Test
    void updateBookSuccess() {
        BooksRequest booksRequest = new BooksRequest();
        booksRequest.setTitle("Title");
        booksRequest.setPublisher(1L);
        booksRequest.setSubject(1L);
        booksRequest.setAuthors(List.of(1L, 2L));

        Book book = buildExpectedBook();
        Publisher publisher = buildPublisher();

        when(publisherRepository.findById(anyLong())).thenReturn(Optional.of(publisher));
        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(book));
        when(pubBookSubjectRepository.findById(anyLong())).thenReturn(Optional.of(new BookSubject()));
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        bookService.update(booksRequest, 1L);

        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
    void updateBookFailureWithoutAuthor() {
        BooksRequest booksRequest = new BooksRequest();
        booksRequest.setTitle("Title");

        Assertions.assertThrows(RuntimeException.class, () -> bookService.update(booksRequest, 1L));
    }

    @Test
    void updateBookFailurePublisherNotFound() {
        BooksRequest booksRequest = new BooksRequest();
        booksRequest.setTitle("Title2");
        booksRequest.setPublisher(1L);
        booksRequest.setAuthors(List.of(1L));

        Author author = buildAuthor();
        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(new Book()));
        when(authorRepository.findAllById(anyList())).thenReturn(List.of(author));
        when(publisherRepository.findById(anyLong())).thenReturn(Optional.empty());
        Assertions.assertThrows(PublisherNotFoundException.class, () -> bookService.update(booksRequest, 2L));
    }

    @Test
    void updateBookFailureBookNotFound() {
        BooksRequest booksRequest = new BooksRequest();
        booksRequest.setTitle("Title");
        booksRequest.setAuthors(List.of(1L));

        when(bookRepository.findById(anyLong())).thenReturn(Optional.empty());
        Assertions.assertThrows(BookNotFoundException.class, () -> bookService.update(booksRequest, 1L));
    }


    private static Publisher buildPublisher() {
        return new Publisher("Publisher1");
    }

    private static Author buildAuthor() {
        return new Author("Author1");
    }

    private static Book buildExpectedBook() {
        Book book = new Book();
        book.setId(1L);
        book.setTitle("Title1");
        book.setAuthors(Set.of(buildAuthor()));
        return book;
    }

}
