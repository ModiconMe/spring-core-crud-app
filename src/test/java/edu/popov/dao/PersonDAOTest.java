package edu.popov.dao;

import edu.popov.models.Person;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

class PersonDAOTest {


    private DriverManagerDataSource dataSource = new DriverManagerDataSource("jdbc:postgresql://localhost:5432/postgres",
            "postgres", "root");
    private JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

    private static final String INSERT = "INSERT INTO person (name, surname, email, age) VALUES(?, ?, ?, ?)";
    private static final String SELECT_ALL = "SELECT * FROM person";
    private static final String SELECT_PERSON = "SELECT * FROM person WHERE id = ?";
    private static final String UPDATE_PERSON = "UPDATE person SET name = ?," +
            " surname = ?, email = ?, age = ? WHERE id = ?";
    private static final String DELETE_PERSON = "DELETE FROM person WHERE id = ?";

    @Test
    public void testMultipleUpdate() {
        List<Person> people = create1000People();

        long before = System.currentTimeMillis();

        people.forEach(person -> {
            jdbcTemplate.update(INSERT,
                    person.getName(),
                    person.getSurname(),
                    person.getEmail(),
                    person.getAge());
        });

        long after = System.currentTimeMillis();
        System.out.println(after - before);
    }

    @Test
    public void testBatchUpdate() {
        List<Person> people = create1000People();

        long before = System.currentTimeMillis();

        jdbcTemplate.batchUpdate(INSERT, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setString(1, people.get(i).getName());
                ps.setString(2, people.get(i).getSurname());
                ps.setString(3, people.get(i).getEmail());
                ps.setInt(4, people.get(i).getAge());
            }

            @Override
            public int getBatchSize() {
                return people.size();
            }
        });


        long after = System.currentTimeMillis();
        System.out.println(after - before);
    }


    private List<Person> create1000People() {
        List<Person> people = new ArrayList<>();

        for (int i = 0; i < 1000; i++) {
            Person person = new Person();
            person.setName("Name" + i);
            person.setSurname("Surname" + i);
            person.setEmail("Email" + i);
            person.setAge(30 + i);
            people.add(person);
        }

        return people;
    }

}