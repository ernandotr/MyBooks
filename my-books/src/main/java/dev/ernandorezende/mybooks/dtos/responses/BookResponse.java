package dev.ernandorezende.mybooks.dtos.responses;

import java.util.List;

public class BookResponse {
    private Long id;
    private String title;
    private String pages;
    private String genre;
    private String language;
    private String url;
    private List<AuthorSummaryResponse> authors;
    private PublisherResponse publisher;
    private BookSubjectResponse subject;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
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

    public List<AuthorSummaryResponse> getAuthors() {
        return authors;
    }

    public void setAuthors(List<AuthorSummaryResponse> authors) {
        this.authors = authors;
    }

    public PublisherResponse getPublisher() {
        return publisher;
    }

    public void setPublisher(PublisherResponse publisher) {
        this.publisher = publisher;
    }

    public BookSubjectResponse getSubject() {
        return subject;
    }

    public void setSubject(BookSubjectResponse subject) {
        this.subject = subject;
    }
}
