package edu.bible.controllers;

import edu.bible.dao.BookDAO;
import edu.bible.dao.PersonDAO;
import edu.bible.models.Book;
import edu.bible.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/books")
public class BookController {

    private final BookDAO bookDAO;
    private final PersonDAO personDAO;

    @Autowired
    public BookController(BookDAO bookDAO, PersonDAO personDAO) {
        this.bookDAO = bookDAO;
        this.personDAO = personDAO;
    }

    @GetMapping("")
    public String index(Model model) {
        model.addAttribute("books", bookDAO.index());
        return "books/index";
    }

    @GetMapping("/{id}")
    public String show(
            @PathVariable("id") int id,
            Model model,
            @ModelAttribute("person") Person person) {
        Optional<Book> book = bookDAO.show(id);
        book.ifPresent(value -> model.addAttribute("book", value));
//
        Optional<Person> bookOwner = bookDAO.getBookOwner(id);
        bookOwner.ifPresent(value -> model.addAttribute("owner", value));

        model.addAttribute("people", personDAO.index());
//        if (bookOwner.isPresent()) {
//            model.addAttribute("owner", bookOwner.get());
//        } else {
//            model.addAttribute("people", personDAO.index());
//        }
        return "books/show";
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("book") Book book) {
        return "books/new";
    }

    @PostMapping
    public String create(@ModelAttribute("book") @Valid Book book,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return "books/new";
        bookDAO.save(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model) {
        Optional<Book> book = bookDAO.show(id);
        book.ifPresent(value -> model.addAttribute("book", value));
        return "books/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") @Valid Book book,
                         BindingResult bindingResult, @PathVariable("id") int id) {
        if (bindingResult.hasErrors()) return "books/edit";
        bookDAO.update(id, book);
        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        bookDAO.delete(id);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/get-book")
    public String getBook(
            @PathVariable("id") int id,
            @ModelAttribute("person") Person person
    ) {
//        bookDAO.delete(id);
        bookDAO.setBookToAPerson(id, person.getId());
        return "redirect:/books/{id}";
    }

    @PatchMapping("/{id}/release-book")
    public String releaseBook(@PathVariable("id") int id) {
        bookDAO.releaseBook(id);
        return "redirect:/books/{id}";
    }

}
