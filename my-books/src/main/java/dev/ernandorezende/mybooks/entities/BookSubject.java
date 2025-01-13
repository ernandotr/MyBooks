package dev.ernandorezende.mybooks.entities;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "book_subjects")
public class BookSubject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String subject;

    public BookSubject() {}

    public BookSubject(String subject) {
        this.subject = subject;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof BookSubject that)) return false;
        return Objects.equals(id, that.id) && Objects.equals(subject, that.subject);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, subject);
    }

    @Override
    public String toString() {
        return "BookSubject{" +
                "id=" + id +
                ", subject='" + subject + '\'' +
                '}';
    }
}
