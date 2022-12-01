package edu.bible.services;

import edu.bible.models.Person;
import edu.bible.models.Book;
import edu.bible.repositories.BookRepository;
import edu.bible.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;


@Service
@Transactional(readOnly = true)
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;
    private final BookRepository bookRepository;

    @Autowired
    public PersonServiceImpl(PersonRepository personRepository, BookRepository bookRepository) {
        this.personRepository = personRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    public List<Person> findAll() {
        return personRepository.findAll();
    }

    @Override
    public Optional<Person> findById(int id) {
        return personRepository.findById(id);
    }

    @Override
    @Transactional
    public void save(Person person) {
        personRepository.save(person);
    }

    @Override
    @Transactional
    public void update(int id, Person updatedPerson) {
        updatedPerson.setId(id);
        personRepository.save(updatedPerson);
    }

    @Override
    @Transactional
    public void deleteById(int id) {
        personRepository.deleteById(id);
    }

    @Override
    @Transactional
    public List<Book> findBooks(int id) {
        Optional<Person> person = personRepository.findById(id);
        List<Book> books = bookRepository.findByPersonId(id);
        books.forEach(book -> {
            if (book.getAssignDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().plusDays(1).compareTo(LocalDate.now()) < 0) {
                book.setOverdue(true);
            }
        });
        return books;
    }
}
