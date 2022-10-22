package edu.popov.dao;

import edu.popov.models.Person;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PersonDAO {

    private static int personCount;
    private final List<Person> people;

    {
        people = new ArrayList<>();
        save(new Person("Tom", "Anderson", "tom@gmail.com", 34));
        save(new Person("John", "Smith", "john@gmail.com", 54));
        save(new Person("Elon", "Musk", "elon@gmail.com", 30));
        save(new Person("Sarah", "Conor", "sarah@gmail.com", 35));
        save(new Person("Chuck", "Norris", "chuck@gmail.com", 45));
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
        personToBeUpdated.setAge(updatedPerson.getAge());
    }

    public void delete(int id) {
//        people.remove(id);
        people.removeIf(person -> person.getId() == id);
    }
}
