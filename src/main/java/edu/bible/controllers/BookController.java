package edu.bible.controllers;

import edu.bible.models.Book;
import edu.bible.models.Person;
import edu.bible.services.BookServiceImpl;
import edu.bible.services.PersonServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Objects;
import java.util.Optional;

@Controller
@RequestMapping("/books")
public class BookController {

    private final BookServiceImpl bookService;
    private final PersonServiceImpl personService;

    @Autowired
    public BookController(BookServiceImpl bookService, PersonServiceImpl personService) {
        this.bookService = bookService;
        this.personService = personService;
    }

    /**
     * Shows all books
     * @param model
     * @param page
     * @param booksPerPage
     * @param sort
     * @return thymeleaf+html page
     */
    @GetMapping("")
    public String index(Model model,
                        @RequestParam(value = "page", required = false) Integer page,
                        @RequestParam(value = "books_per_page", required = false) Integer booksPerPage,
                        @RequestParam(value = "sort", defaultValue = "false") Boolean sort) {

        if (page != null && booksPerPage != null) {
            model.addAttribute("books", bookService.findAll(page, booksPerPage, sort));
        } else {
            model.addAttribute("books", bookService.findAll(sort));
        }
        return "books/index";
    }

    /**
     * Shows books by person
     * @param id
     * @param model
     * @param person
     * @return thymeleaf+html page
     */
    @GetMapping("/{id}")
    public String show(
            @PathVariable("id") int id,
            Model model,
            @ModelAttribute("person") Person person) {
        Optional<Book> foundedBook = bookService.findById(id);
        Book book = new Book();
        if (foundedBook.isPresent()) {
            book = foundedBook.get();
            model.addAttribute("book", book);
        }

        Person bookOwner = book.getPerson();
        if (bookOwner != null) {
            model.addAttribute("owner", bookOwner);
        } else {
            model.addAttribute("people", personService.findAll());
        }
        return "books/show";
    }

    /**
     * @param book
     * @return create page
     */
    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book) {
        return "books/new";
    }

    /**
     * Create new book
     * @param book
     * @param bindingResult
     */
    @PostMapping
    public String create(@ModelAttribute("book") @Valid Book book,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return "books/new";
        bookService.save(book);
        return "redirect:/books";
    }

    /**
     * @param id
     * @param model
     * @return edit page
     */
    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model) {
        Optional<Book> book = bookService.findById(id);
        book.ifPresent(value -> model.addAttribute("book", value));
        return "books/edit";
    }

    /**
     * Edit book
     * @param book
     * @param bindingResult
     * @param id
     */
    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") @Valid Book book,
                         BindingResult bindingResult, @PathVariable("id") int id) {
        if (bindingResult.hasErrors()) return "books/edit";
        bookService.update(id, book);
        return "redirect:/books";
    }

    /**
     * Delete book by id
     * @param id
     */
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        bookService.deleteById(id);
        return "redirect:/books";
    }

    /**
     * Take a book by person
     * @param id
     * @param person
     */
    @PatchMapping("/{id}/get-book")
    public String getBook(
            @PathVariable("id") int id,
            @ModelAttribute("person") Person person
    ) {
        bookService.assignBook(id, person.getId());
        return "redirect:/books/{id}";
    }

    /**
     * Release book from person
     * @param id
     */
    @PatchMapping("/{id}/release-book")
    public String releaseBook(@PathVariable("id") int id) {
        bookService.releaseBook(id);
        return "redirect:/books/{id}";
    }

    /**
     * Return search result
     * @param label
     * @param model
     */
    @RequestMapping("/search")
    public String edit(@RequestParam(value = "search", required = false) String label, Model model) {
        if (label != null && !Objects.equals(label, "")) {
            model.addAttribute("books", bookService.findByLabelLike(label));
        }
        return "books/search";
    }
}
