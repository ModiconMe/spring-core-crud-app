package edu.popov.controllers;

import edu.popov.dao.PersonDAO;
import edu.popov.view.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/people")
public class PeopleController {

    @Autowired
    List<Person> people;

    @GetMapping("")
    public String index(Model model) {
//        List<Person> people = PersonDAO.getPeople();
        model.addAttribute("people", people);
        return "people";
    }
}
