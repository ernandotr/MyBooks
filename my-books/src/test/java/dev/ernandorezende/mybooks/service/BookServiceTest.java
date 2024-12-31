package dev.ernandorezende.mybooks.service;

import dev.ernandorezende.mybooks.dtos.requests.BooksRequest;
import dev.ernandorezende.mybooks.dtos.responses.BookResponse;
import dev.ernandorezende.mybooks.entities.Book;
import dev.ernandorezende.mybooks.repositories.BookRepository;
import dev.ernandorezende.mybooks.services.BookService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

//@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    @Spy
    ModelMapper modelMapper;

    @Mock
    BookRepository bookRepository;

    @InjectMocks
    BookService bookService;

    @Test
    void saveBook() {
        BooksRequest booksRequest = new BooksRequest();
        booksRequest.setTitle("Title");

        Book book = buildExpectedBook();
        when(bookRepository.save(any(Book.class))).thenReturn(book);
        BookResponse response = bookService.save(booksRequest);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(book.getTitle(), response.getTitle());
    }

    private static Book buildExpectedBook() {
        Book book = new Book();
        book.setId(1L);
        book.setTitle("Title");
        return book;
    }

}
