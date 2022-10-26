package edu.bible.dao;

import edu.bible.models.Book;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BookMapper implements RowMapper<Book> {

    @Override
    public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
        Book book = new Book();

        book.setId(rs.getInt("id"));
        book.setLabel(rs.getString("label"));
        book.setAuthor(rs.getString("author"));
        book.setReleaseDate(rs.getInt("releaseDate"));
        book.setPerson_id(rs.getInt("person_id"));

        return book;
    }
}
