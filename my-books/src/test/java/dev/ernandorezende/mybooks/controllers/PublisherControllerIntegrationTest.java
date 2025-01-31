package dev.ernandorezende.mybooks.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.ernandorezende.mybooks.dtos.requests.AuthorRequest;
import dev.ernandorezende.mybooks.dtos.requests.PublisherRequest;
import dev.ernandorezende.mybooks.dtos.responses.PublisherResponse;
import dev.ernandorezende.mybooks.services.PublisherService;
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
@WebMvcTest(PublisherController.class)
@AutoConfigureMockMvc(addFilters = false)
public class PublisherControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private PublisherService publisherService;

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    void givenPublisher_whenGetAuthors_thenReturnPublisher() throws Exception {
        PublisherResponse publisherResponse = new PublisherResponse();
        publisherResponse.setName("Test");
        Page<PublisherResponse> allPublishers = new PageImpl<>(List.of(publisherResponse));

        given(this.publisherService.getAll(any(Pageable.class))).willReturn(allPublishers);

        mvc.perform(get("/api/publishers")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
               // .andExpect(jsonPath("$", hasSize(1)));

    }

    @Test
    void createNewAuthorSuccess() throws Exception {
        PublisherRequest request = new PublisherRequest("Simon Sinek");
        PublisherResponse response = new PublisherResponse();
        response.setName("Simon Sinek");
        response.setId(1L);

        given(publisherService.create(any(PublisherRequest.class))).willReturn(response);
        mvc.perform(post("/api/publishers").content(mapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.books").doesNotExist());
    }

    @Test
    void editAuthorSuccess() throws Exception {
        AuthorRequest request = new AuthorRequest("Simon Sinek");

        mvc.perform(put("/api/publishers/{id}", 1)
                        .content(mapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteAuthorSuccess() throws Exception {
        mvc.perform(delete("/api/publishers/{id}", 1))
                .andExpect(status().isNoContent());
    }

    @Test
    void getByIdSuccess() throws Exception {
        PublisherResponse response = new PublisherResponse();
        response.setName("Simon Sinek");
        response.setId(1L);
        given(publisherService.getById(anyLong())).willReturn(response);
        mvc.perform(get("/api/publishers/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists());

    }


}
