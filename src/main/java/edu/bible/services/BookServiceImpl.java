package edu.bible.services;

import edu.bible.models.Book;
import edu.bible.models.Person;
import edu.bible.repositories.BookRepository;
import edu.bible.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final PersonRepository personRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository, PersonRepository personRepository) {
        this.bookRepository = bookRepository;
        this.personRepository = personRepository;
    }

    @Override
    public List<Book> findAll(boolean sort) {
        String field = "id";
        if (sort) field = "releaseDate";
        return bookRepository.findAll(Sort.by(field));
    }

    @Override
    public List<Book> findAll(int page, int booksPerPage, boolean sort) {
        String field = "id";
        if (sort) field = "releaseDate";
        return bookRepository.findAll(PageRequest.of(page, booksPerPage, Sort.by(field))).getContent();
    }

    @Override
    public Optional<Book> findById(int id) {
        return bookRepository.findById(id);
    }

    @Override
    @Transactional
    public void save(Book book) {
        bookRepository.save(book);
    }

    @Override
    @Transactional
    public void update(int id, Book updatedBook) {
        updatedBook.setId(id);
        bookRepository.save(updatedBook);
    }

    @Override
    @Transactional
    public void deleteById(int id) {
        bookRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void assignBook(int bookId, int personId) {
        Optional<Book> foundedBook = bookRepository.findById(bookId);
        Optional<Person> foundedPerson = personRepository.findById(personId);
        if (foundedBook.isPresent() && foundedPerson.isPresent()) {
            Book book = foundedBook.get();
            book.setAssignDate(new Date());
            Person person = foundedPerson.get();
            person.addBook(book);
        }
    }

    @Override
    @Transactional
    public void releaseBook(int id) {
        Optional<Book> foundedBook = bookRepository.findById(id);
        if (foundedBook.isPresent()) {
            Book book = foundedBook.get();
            book.setOverdue(false);
            book.setAssignDate(null);
            book.getPerson().removeBook(book);
        }
    }

    @Override
    @Transactional
    public List<Book> findByLabelLike(String start) {
        return bookRepository.findByLabelStartingWithIgnoreCase(start);
    }
}
