package edu.bible.controllers;

import edu.bible.models.Person;
import edu.bible.services.PersonServiceImpl;
import edu.bible.util.PersonValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/people")
public class PeopleController {

    private final PersonServiceImpl personService;
    private final PersonValidator personValidator;

    @Autowired
    public PeopleController(PersonServiceImpl personService, PersonValidator personValidator) {
        this.personService = personService;
        this.personValidator = personValidator;
    }

    /**
     * Show all persons
     * @param model
     */
    @GetMapping("")
    public String index(Model model) {
        model.addAttribute("people", personService.findAll());
        return "people/index";
    }

    /**
     * Show person and his books
     * @param id
     * @param model
     */
    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        Optional<Person> person = personService.findById(id);
        person.ifPresent(value -> model.addAttribute("person", value));
        model.addAttribute("books", personService.findBooks(id));
        return "people/show";
    }

    /**
     * @param person
     * @return create person page
     */
    @GetMapping("/new")
    public String newPerson(@ModelAttribute("person") Person person) {
        return "people/new";
    }

    /**
     * Create new person
     * @param person
     * @param bindingResult
     */
    @PostMapping
    public String create(@ModelAttribute("person") @Valid Person person,
                         BindingResult bindingResult) {
        personValidator.validate(person, bindingResult);
        if(bindingResult.hasErrors()) return "people/new";
        personService.save(person);
        return "redirect:/people";
    }

    /**
     * @param id
     * @param model
     * @return person edit page
     */
    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model) {
        Optional<Person> person = personService.findById(id);
        person.ifPresent(value -> model.addAttribute("person", value));
        return "people/edit";
    }

    /**
     * edit person
     * @param person
     * @param bindingResult
     * @param id
     */
    @PatchMapping("/{id}")
    public String update(@ModelAttribute("person") @Valid Person person,
                         BindingResult bindingResult, @PathVariable("id") int id) {
        personValidator.validate(person, bindingResult);
        if(bindingResult.hasErrors()) return "people/edit";
        personService.update(id, person);
        return "redirect:/people";
    }

    /**
     * delete person by id
     * @param id
     */
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        personService.deleteById(id);
        return "redirect:/people";
    }

}
