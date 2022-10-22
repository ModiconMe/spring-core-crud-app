package edu.popov.models;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class Person {

    private int id;

    @NotEmpty(message = "name should not be empty")
    @Size(min = 2, max = 30, message = "Name length should be between 2 and 30 characters")
    private String name;

    @NotEmpty(message = "surname should not be empty")
    @Size(min = 2, max = 30, message = "Surname length should be between 2 and 30 characters")
    private String surname;

    @NotEmpty(message = "name should not be empty")
    @Email(message = "email should be valid")
    private String email;

    @Min(value = 0, message = "Age should be greater then 0")
    private int age;

    public Person() {
    }

    public Person(String name, String surname, String email, int age) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.age = age;
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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}

