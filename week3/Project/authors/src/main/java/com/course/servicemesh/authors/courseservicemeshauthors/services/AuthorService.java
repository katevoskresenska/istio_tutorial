package com.course.servicemesh.authors.courseservicemeshauthors.services;

import com.course.servicemesh.authors.courseservicemeshauthors.models.Author;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashMap;

@Component
public class AuthorService {
    private final HashMap<Integer, Author> authors;

    public AuthorService() {
        this.authors = new HashMap<>();
        this.authors.put(1, new Author(1).withFirstName("Loreth Anne").withLastName("White").withBirthDate("01.01.1975"));
        this.authors.put(2, new Author(2).withFirstName("Lisa").withLastName("Regan").withBirthDate("03.04.1956"));
        this.authors.put(3, new Author(3).withFirstName("Ty").withLastName("Patterson").withBirthDate("08.10.1988"));
        this.authors.put(4, new Author(4).withFirstName("Mikhail").withLastName("Sholokhov").withBirthDate("24.05.1905"));
        this.authors.put(5, new Author(5).withFirstName("Developer").withLastName("Developer").withBirthDate("21.11.2021"));
    }

    public Collection<Author> getAuthors() {
        return this.authors.values();
    }

    public Author findById(int id) {
        return this.authors.get(id);
    }
}
