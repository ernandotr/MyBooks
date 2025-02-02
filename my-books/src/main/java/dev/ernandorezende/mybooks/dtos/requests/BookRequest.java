package dev.ernandorezende.mybooks.dtos.requests;

import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.URL;

import java.util.ArrayList;
import java.util.List;

public class BookRequest {
    @NotBlank(message = "The 'title' field is mandatory.")
    private String title;

    @NotBlank(message = "The 'page' field is mandatory.")
    private String pages;

    @NotNull(message = "The 'subject' field is mandatory.")
    private Long subject;

    private String language;

    @NotBlank(message = "The 'url' field is mandatory.")
    @URL
    private String url;

    @NotNull(message = "The 'publisher' field is mandatory.")
    private Long publisher;

    @NotEmpty(message = "At least one Author is required.")
    private List<Long> authors = new ArrayList<>();

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPages() {
        return pages;
    }

    public void setPages(String pages) {
        this.pages = pages;
    }

    public Long getSubject() {
        return subject;
    }

    public void setSubject(Long subject) {
        this.subject = subject;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getPublisher() {
        return publisher;
    }

    public void setPublisher(long publisher) {
        this.publisher = publisher;
    }

    public List<Long> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Long> authors) {
        this.authors = authors;
    }
}
