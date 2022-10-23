package edu.popov.dao;

import edu.popov.models.Person;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class PersonDAO {

    private static int personCount;

    private static Connection con;
    private static final String URL = "jdbc:postgresql://localhost:5432/crud_db";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "01rohivi";

    private static final String INSERT = "INSERT INTO person (name, surname, email, age) VALUES(?, ?, ?, ?)";
    private static final String SELECT_ALL = "SELECT * FROM person";
    private static final String SELECT_PERSON = "SELECT * FROM person WHERE id = ?";
    private static final String UPDATE_PERSON = "UPDATE person SET name = ?," +
            " surname = ?, email = ?, age = ? WHERE id = ?";
    private static final String DELETE_PERSON = "DELETE FROM person WHERE id = ?";

    {
        try {
            Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public List<Person> index() {
        List<Person> people = new ArrayList<>();
        try (
                PreparedStatement stmt = con.prepareStatement(SELECT_ALL);
        ) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Person person = new Person();
                person.setId(rs.getInt("id"));
                person.setName(rs.getString("name"));
                person.setSurname(rs.getString("surname"));
                person.setEmail(rs.getString("email"));
                person.setAge(rs.getInt("age"));
                people.add(person);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return people;
    }

    public Person show(int id) {
        try (
                PreparedStatement stmt = con.prepareStatement(SELECT_PERSON);
        ) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Person person = new Person();
                person.setId(rs.getInt("id"));
                person.setName(rs.getString("name"));
                person.setSurname(rs.getString("surname"));
                person.setEmail(rs.getString("email"));
                person.setAge(rs.getInt("age"));
                return person;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new NullPointerException();
    }

    public void save(Person person) {
        try (
                PreparedStatement stmt = con.prepareStatement(INSERT);
        ) {
            stmt.setString(1, person.getName());
            stmt.setString(2, person.getSurname());
            stmt.setString(3, person.getEmail());
            stmt.setInt(4, person.getAge());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(int id, Person updatedPerson) {
        try (
                PreparedStatement stmt = con.prepareStatement(UPDATE_PERSON);
        ) {
            stmt.setString(1, updatedPerson.getName());
            stmt.setString(2, updatedPerson.getSurname());
            stmt.setString(3, updatedPerson.getEmail());
            stmt.setInt(4, updatedPerson.getAge());
            stmt.setInt(5, id);

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int id) {
        try (
                PreparedStatement stmt = con.prepareStatement(DELETE_PERSON);
        ) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
