package dev.ernandorezende.mybooks.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.ernandorezende.mybooks.dtos.requests.AuthorRequest;
import dev.ernandorezende.mybooks.dtos.responses.AuthorResponse;
import dev.ernandorezende.mybooks.dtos.responses.AuthorSummaryResponse;
import dev.ernandorezende.mybooks.services.AuthorService;
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
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(AuthorController.class)
@AutoConfigureMockMvc(addFilters = false)
public class AuthorControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AuthorService authorService;

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    void givenAuthor_whenGetAuthors_thenReturnAuthor() throws Exception {
        AuthorSummaryResponse author = new AuthorSummaryResponse();
        author.setName("Test");
        Page<AuthorSummaryResponse> allAuthors = new PageImpl<>(List.of(author));

        given(this.authorService.getAll(any(Pageable.class))).willReturn(allAuthors);

        mvc.perform(get("/api/authors")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
               // .andExpect(jsonPath("$", hasSize(1)));

    }

    @Test
    void createNewAuthorSuccess() throws Exception {
        AuthorRequest request = new AuthorRequest("Simon Sinek");
        AuthorResponse response = new AuthorResponse();
        response.setName("Simon Sinek");
        response.setId(1L);

        given(authorService.save(any(AuthorRequest.class))).willReturn(response);
        mvc.perform(post("/api/authors").content(mapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.books").doesNotExist());
    }

    @Test
    void editAuthorSuccess() throws Exception {
        AuthorRequest request = new AuthorRequest("Simon Sinek");

        mvc.perform(put("/api/authors/{id}", 1).content(mapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

}
