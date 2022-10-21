package edu.popov.dao;

import edu.popov.view.Person;

import java.util.ArrayList;
import java.util.List;

public class PersonDAO {

    private static List<Person> people;

    public static List<Person> getPeople() {
        people = new ArrayList<>();
        addPerson(0, "Tom", "Anderson");
        addPerson(1, "John", "Smith");
        addPerson(2, "Elon", "Musk");
        addPerson(3, "Sarah", "Conor");
        addPerson(4, "Chuck", "Norris");

        return people;
    }

    public static void addPerson(int id, String name, String surname) {
        people.add(new Person(id, name, surname));
    }
}
