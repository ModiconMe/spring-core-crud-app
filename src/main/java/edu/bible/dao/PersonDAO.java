package edu.bible.dao;

import edu.bible.models.Book;
import edu.bible.models.Person;

import java.util.List;
import java.util.Optional;

public interface PersonDAO {
    List<Person> index();
    Optional<Person> selectPersonById(int id);
    Optional<Person> selectPersonByEmail(String email);
    void save(Person person);
    void update(int id, Person person);
    void delete(int id);
    List<Book> showBooks(int id);
}
