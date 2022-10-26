package edu.bible.models;

import javax.validation.constraints.*;
import java.util.List;

public class Person {

    private int id;

    @NotEmpty(message = "name should not be empty")
    @Size(min = 2, max = 50, message = "Name length should be between 2 and 50 characters")
    private String name;
    @Min(value = 1900, message = "Date of birth should be upper than 1900")
    private int dateOfBirth;

    @NotEmpty(message = "email should not be empty")
    @Size(min = 2, max = 50, message = "Name length should be between 2 and 50 characters")
    private String email;

    @Pattern(regexp = "[A-Z]\\w+, [A-Z]\\w+, \\d{6}|", message = "Your address should be in this format: County, City, Postal Code(6  digits)")
    private String address;

    public Person() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(int dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}

