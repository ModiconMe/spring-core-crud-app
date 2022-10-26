package edu.bible.dao;

import edu.bible.models.Book;
import edu.bible.models.Person;

import java.util.List;
import java.util.Optional;

public interface BookDAO {
    List<Book> index();
    Optional<Book> show(int id);
    void save(Book book);
    void update(int id, Book book);
    void delete(int id);
    void setBookToAPerson(int bookId, int personId);
    void releaseBook(int bookId);
    Optional<Person> getBookOwner(int bookId);
}
