package dev.ernandorezende.mybooks.dtos.requests;

public class BooksRequest {
    private String title;
    private String isbn;
    private String pages;
    private String genre;
    private String language;
    private String url;
    private long publisher;
    private long author;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
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

    public long getPublisher() {
        return publisher;
    }

    public void setPublisher(long publisher) {
        this.publisher = publisher;
    }

    public long getAuthor() {
        return author;
    }

    public void setAuthor(long author) {
        this.author = author;
    }
}
