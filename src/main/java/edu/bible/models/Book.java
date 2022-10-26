package edu.bible.models;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class Book {

    private int id;
    @NotEmpty(message = "label should not be empty")
    @Size(min = 2, max = 30, message = "Label length should be between 2 and 30 characters")
    private String label;
    @NotEmpty(message = "Author should not be empty")
    @Size(min = 2, max = 30, message = "Author length should be between 2 and 30 characters")
    private String author;
    @Min(value = 1500, message = "Release date should be upper than 1500")
    private int releaseDate;
    private int person_id;

    public Book() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(int releaseDate) {
        this.releaseDate = releaseDate;
    }

    public int getPerson_id() {
        return person_id;
    }

    public void setPerson_id(int person_id) {
        this.person_id = person_id;
    }
}
