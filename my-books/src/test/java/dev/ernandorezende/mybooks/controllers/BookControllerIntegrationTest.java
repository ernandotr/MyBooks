package dev.ernandorezende.mybooks.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.ernandorezende.mybooks.dtos.requests.BookRequest;
import dev.ernandorezende.mybooks.dtos.responses.BookResponse;
import dev.ernandorezende.mybooks.dtos.responses.BookSummaryResponse;
import dev.ernandorezende.mybooks.services.BookService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(BookController.class)
@AutoConfigureMockMvc(addFilters = false)
public class BookControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private BookService bookService;

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    void givenBook_whenGetBooks_thenReturnBook() throws Exception {
        BookSummaryResponse bookSummaryResponse = new BookSummaryResponse();
        bookSummaryResponse.setTitle("Test");
        Page<BookSummaryResponse> allBooks = new PageImpl<>(List.of(bookSummaryResponse));

        given(this.bookService.getAll(any(Pageable.class))).willReturn(allBooks);

        mvc.perform(get("/api/books")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    @Test
    void createNewBookSuccess() throws Exception {
        BookRequest request = buildBookRequest();
        BookSummaryResponse response = new BookSummaryResponse();
        response.setTitle("Desvendando o Java");
        response.setId(1L);

        given(bookService.create(any(BookRequest.class))).willReturn(response);
        mvc.perform(post("/api/books").content(mapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.books").doesNotExist());
    }

    @Test
    void editBookSuccess() throws Exception {
        BookRequest request = buildBookRequest();

        mvc.perform(put("/api/books/{id}", 1)
                        .content(mapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteBookSuccess() throws Exception {
        mvc.perform(delete("/api/books/{id}", 1))
                .andExpect(status().isNoContent());
    }

    @Test
    void getByIdSuccess() throws Exception {
        BookResponse response = new BookResponse();
        response.setTitle("Desvendando o Java");
        response.setId(1L);
        given(bookService.getBookById(anyLong())).willReturn(response);
        mvc.perform(get("/api/books/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists());

    }

    private static BookRequest buildBookRequest() {
        BookRequest request = new BookRequest();
        request.setTitle("Desvendando o Java");
        request.setPublisher(1L);
        request.setSubject(1L);
        request.setPages("100");
        request.setAuthors(List.of(1L));
        request.setUrl("http://localhost/books/1");
        return request;
    }

}
