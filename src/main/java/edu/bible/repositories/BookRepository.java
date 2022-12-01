package edu.bible.repositories;

import edu.bible.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

    List<Book> findByPersonId(int personId);
    List<Book> findByLabelStartingWithIgnoreCase(String start);

}
