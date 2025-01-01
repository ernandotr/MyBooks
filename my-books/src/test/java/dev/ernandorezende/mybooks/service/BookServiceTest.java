package dev.ernandorezende.mybooks.service;

import dev.ernandorezende.mybooks.dtos.requests.BooksRequest;
import dev.ernandorezende.mybooks.dtos.responses.BookResponse;
import dev.ernandorezende.mybooks.entities.Author;
import dev.ernandorezende.mybooks.entities.Book;
import dev.ernandorezende.mybooks.entities.Publisher;
import dev.ernandorezende.mybooks.repositories.AuthorRepository;
import dev.ernandorezende.mybooks.repositories.BookRepository;
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

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

//@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    @Spy
    ModelMapper modelMapper;

    @Mock
    BookRepository bookRepository;
    @Mock
    AuthorRepository authorRepository;
    @Mock
    PublisherRepository publisherRepository;

    @InjectMocks
    BookService bookService;

    @Test
    void saveBookSuccess() {
        BooksRequest booksRequest = new BooksRequest();
        booksRequest.setTitle("Title");

        Book book = buildExpectedBook(1L,"Title1");
        when(bookRepository.save(any(Book.class))).thenReturn(book);
        BookResponse response = bookService.save(booksRequest);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(book.getTitle(), response.getTitle());
    }

    @Test
    void getAllBooks() {
        Book book = buildExpectedBook(1L, "Title1");
        when(bookRepository.findAll()).thenReturn(List.of(book));
        List<BookResponse> response = bookService.getAll();

        Assertions.assertNotNull(response);
        Assertions.assertEquals(1, response.size());
        Assertions.assertEquals(book.getTitle(), response.get(0).getTitle());
    }

    @Test
    void updateBookSuccess() {
        BooksRequest booksRequest = new BooksRequest();
        booksRequest.setTitle("Title");

        Book book = buildExpectedBook(1L, "Title1");
        Author author = new Author("Author1");
        Publisher publisher = new Publisher("Publisher1");

        when(authorRepository.findById(anyLong())).thenReturn(Optional.of(author));
        when(publisherRepository.findById(anyLong())).thenReturn(Optional.of(publisher));
        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(book));
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        bookService.update(booksRequest, 1L);

        verify(bookRepository, times(1)).save(any(Book.class));
    }

    private static Book buildExpectedBook(Long id, String title) {
        Book book = new Book();
        book.setId(id);
        book.setTitle(title);
        return book;
    }

}
