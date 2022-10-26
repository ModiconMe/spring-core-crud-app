package edu.bible.dao;

import edu.bible.models.Book;
import edu.bible.models.Person;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class PersonDAOImpl implements PersonDAO {

    private final JdbcTemplate jdbcTemplate;

    private static final String INSERT = "INSERT INTO person (name, dateOfBirth, email, address) VALUES(?, ?, ?, ?)";
    private static final String SELECT_ALL = "SELECT * FROM person";
    private static final String SELECT_PERSON_BY_ID = "SELECT * FROM person WHERE id = ?";
    private static final String SELECT_PERSON_BY_EMAIL = "SELECT * FROM person WHERE email = ?";
    private static final String UPDATE_PERSON = "UPDATE person SET name = ?, dateOfBirth = ?, email = ?, address = ? WHERE id = ?";
    private static final String DELETE_PERSON = "DELETE FROM person WHERE id = ?";
    private static final String GET_BOOKS = "SELECT * FROM book";

    public PersonDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Person> index() {
        return jdbcTemplate.query(SELECT_ALL, new BeanPropertyRowMapper<>(Person.class));
    }

    @Override
    public Optional<Person> selectPersonById(int id) {
        return jdbcTemplate.query(SELECT_PERSON_BY_ID, new Object[]{id}, new BeanPropertyRowMapper<>(Person.class))
                .stream().findAny();
    }

    @Override
    public Optional<Person> selectPersonByEmail(String email) {
        return jdbcTemplate.query(SELECT_PERSON_BY_EMAIL, new Object[]{email}, new BeanPropertyRowMapper<>(Person.class))
                .stream().findAny();
    }

    @Override
    public void save(Person person) {
        jdbcTemplate.update(INSERT,
                person.getName(),
                person.getDateOfBirth(),
                person.getEmail(),
                person.getAddress()
        );
    }

    @Override
    public void update(int id, Person person) {
        jdbcTemplate.update(UPDATE_PERSON,
                person.getName(),
                person.getDateOfBirth(),
                person.getEmail(),
                person.getAddress(),
                id);
    }

    @Override
    public void delete(int id) {
        jdbcTemplate.update(DELETE_PERSON, id);
    }

    @Override
    public List<Book> showBooks(int id) {
        return jdbcTemplate.query(GET_BOOKS, new BookMapper())
                .stream()
                .filter(book -> book.getPerson_id() == id)
                .toList();
    }
}

