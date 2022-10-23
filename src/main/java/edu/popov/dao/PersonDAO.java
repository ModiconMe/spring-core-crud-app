package edu.popov.dao;

import edu.popov.models.Person;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class PersonDAO {

    private final JdbcTemplate jdbcTemplate;

    private static final String INSERT = "INSERT INTO person (name, surname, email, age) VALUES(?, ?, ?, ?)";
    private static final String SELECT_ALL = "SELECT * FROM person";
    private static final String SELECT_PERSON = "SELECT * FROM person WHERE id = ?";
    private static final String UPDATE_PERSON = "UPDATE person SET name = ?," +
            " surname = ?, email = ?, age = ? WHERE id = ?";
    private static final String DELETE_PERSON = "DELETE FROM person WHERE id = ?";

    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> index() {
        return jdbcTemplate.query(SELECT_ALL, new BeanPropertyRowMapper<>(Person.class));
    }

    public Person show(int id) {
        return jdbcTemplate.query(SELECT_PERSON, new Object[]{id}, new BeanPropertyRowMapper<>(Person.class))
                .stream().findAny().orElseThrow();
    }

    public void save(Person person) {
        jdbcTemplate.update(INSERT,
                person.getName(),
                person.getSurname(),
                person.getEmail(),
                person.getAge());
    }

    public void update(int id, Person updatedPerson) {
        jdbcTemplate.update(UPDATE_PERSON,
                updatedPerson.getName(),
                updatedPerson.getSurname(),
                updatedPerson.getEmail(),
                updatedPerson.getAge(),
                id);
    }

    public void delete(int id) {
        jdbcTemplate.update(DELETE_PERSON, id);
    }
}
