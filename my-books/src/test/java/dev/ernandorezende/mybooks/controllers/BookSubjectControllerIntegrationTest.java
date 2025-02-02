package dev.ernandorezende.mybooks.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.ernandorezende.mybooks.dtos.requests.BookSubjectRequest;
import dev.ernandorezende.mybooks.dtos.responses.BookSubjectResponse;
import dev.ernandorezende.mybooks.services.BookSubjectService;
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
@WebMvcTest(BookSubjectController.class)
@AutoConfigureMockMvc(addFilters = false)
public class BookSubjectControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private BookSubjectService bookSubjectService;

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    void givenAuthor_whenGetSubjects_thenReturnSubject() throws Exception {
        BookSubjectResponse subjectResponse = new BookSubjectResponse();
        subjectResponse.setSubject("Test");
        Page<BookSubjectResponse> allSubjects = new PageImpl<>(List.of(subjectResponse));

        given(this.bookSubjectService.findAll(any(Pageable.class))).willReturn(allSubjects);

        mvc.perform(get("/api/book-subjects")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
               // .andExpect(jsonPath("$", hasSize(1)));

    }

    @Test
    void createNewAuthorSuccess() throws Exception {
        BookSubjectRequest request = new BookSubjectRequest("Programação");
        BookSubjectResponse response = new BookSubjectResponse();
        response.setSubject("Programação");
        response.setId(1L);

        given(bookSubjectService.save(any(BookSubjectRequest.class))).willReturn(response);
        mvc.perform(post("/api/book-subjects").content(mapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.books").doesNotExist());
    }

    @Test
    void editBookSubjectSuccess() throws Exception {
        BookSubjectRequest request = new BookSubjectRequest("Programaão");

        mvc.perform(put("/api/book-subjects/{id}", 1)
                        .content(mapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteBookSubjectSuccess() throws Exception {
        mvc.perform(delete("/api/book-subjects/{id}", 1))
                .andExpect(status().isNoContent());
    }

    @Test
    void getByIdSuccess() throws Exception {
        BookSubjectResponse response = new BookSubjectResponse();
        response.setSubject("Programaão");
        response.setId(1L);
        given(bookSubjectService.findById(anyLong())).willReturn(response);
        mvc.perform(get("/api/book-subjects/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists());

    }


}
