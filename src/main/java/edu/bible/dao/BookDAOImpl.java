package edu.bible.dao;

import edu.bible.models.Book;
import edu.bible.models.Person;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class BookDAOImpl implements BookDAO {

    private final JdbcTemplate jdbcTemplate;
    private final PersonDAO personDAO;

    private static final String INSERT = "INSERT INTO book (label, author, releaseDate) VALUES(?, ?, ?)";
    private static final String SELECT_ALL = "SELECT * FROM book";
    private static final String SELECT_BOOK = "SELECT * FROM book WHERE id = ?";
    private static final String UPDATE_BOOK = "UPDATE book SET label = ?, author = ?, releaseDate = ? WHERE id = ?";
    private static final String DELETE_BOOK = "DELETE FROM book WHERE id = ?";
    private static final String SET_BOOK = "UPDATE book SET person_id = ? WHERE id = ?";
    private static final String SELECT_BOOK_OWNER = "SELECT p.* FROM book AS b JOIN person AS p ON p.id = b.person_id WHERE b.id = ?";

    public BookDAOImpl(JdbcTemplate jdbcTemplate, PersonDAO personDAO) {
        this.jdbcTemplate = jdbcTemplate;
        this.personDAO = personDAO;
    }

    @Override
    public List<Book> index() {
        return jdbcTemplate.query(SELECT_ALL, new BookMapper());
    }

    @Override
    public Optional<Book> show(int id) {
        return jdbcTemplate.query(SELECT_BOOK, new Object[]{id}, new BookMapper())
                .stream().findAny();
    }

    @Override
    public void save(Book book) {
        jdbcTemplate.update(INSERT,
                book.getLabel(),
                book.getAuthor(),
                book.getReleaseDate()
        );
    }

    @Override
    public void update(int id, Book book) {
        jdbcTemplate.update(UPDATE_BOOK,
                book.getLabel(),
                book.getAuthor(),
                book.getReleaseDate(),
                id);
    }

    @Override
    public void delete(int id) {
        jdbcTemplate.update(DELETE_BOOK, id);
    }

    @Override
    public void setBookToAPerson(int bookId, int personId) {
        Optional<Book> book = show(bookId);
        Optional<Person> person = personDAO.selectPersonById(personId);

        if (book.isPresent()) {
            if (person.isPresent()) {
                jdbcTemplate.update(SET_BOOK,
                        person.get().getId(),
                        book.get().getId()
                        );
            } else throw new NullPointerException("Invalid person");
        } else throw new NullPointerException("Invalid book");
    }

    @Override
    public void releaseBook(int bookId) {
        jdbcTemplate.update(SET_BOOK,
                null,
                bookId
        );
    }

    @Override
    public Optional<Person> getBookOwner(int bookId) {
        return jdbcTemplate.query(SELECT_BOOK_OWNER, new Object[]{bookId}, new BeanPropertyRowMapper<>(Person.class))
                .stream().findAny();
    }
}
