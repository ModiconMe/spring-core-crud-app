package edu.popov.dao;

import edu.popov.view.Person;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PersonDAO {

    private static int personCount;
    private final List<Person> people;

    {
        people = new ArrayList<>();
        save(new Person("Tom", "Anderson", "tom@gmail.com"));
        save(new Person("John", "Smith", "john@gmail.com"));
        save(new Person("Elon", "Musk", "elon@gmail.com"));
        save(new Person("Sarah", "Conor", "sarah@gmail.com"));
        save(new Person("Chuck", "Norris", "chuck@gmail.com"));
    }

    public List<Person> index() {
        return people;
    }

    public Person show(int id) {
        return people.stream().filter(person -> person.getId() == id).findAny().orElse(null);
    }

    public void save(Person person) {
        person.setId(personCount++);
        people.add(person);
    }

    public void update(int id, Person updatedPerson) {
        Person personToBeUpdated = show(id);
        personToBeUpdated.setName(updatedPerson.getName());
        personToBeUpdated.setSurname(updatedPerson.getSurname());
        personToBeUpdated.setEmail(updatedPerson.getEmail());
    }

    public void delete(int id) {
//        people.remove(id);
        people.removeIf(person -> person.getId() == id);
    }
}
