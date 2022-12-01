package edu.bible.services;

import edu.bible.models.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {

    List<Book> findAll(boolean sort);
    List<Book> findAll(int page, int booksPerPage, boolean sort);
    Optional<Book> findById(int id);
    void save(Book book);
    void update(int id, Book updatedBook);
    void deleteById(int id);
    void assignBook(int bookId, int personId);
    void releaseBook(int id);
    List<Book> findByLabelLike(String start);

}
