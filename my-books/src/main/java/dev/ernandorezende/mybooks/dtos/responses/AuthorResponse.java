package dev.ernandorezende.mybooks.dtos.responses;

import java.util.List;
import java.util.Objects;

public class AuthorResponse {
    private Long id;
    private String name;
    private String books;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBooks() {
        return books;
    }

    public void setBooks(String books) {
        this.books = books;
    }

    @Override
    public String toString() {
        return "AuthorResponse{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

}
