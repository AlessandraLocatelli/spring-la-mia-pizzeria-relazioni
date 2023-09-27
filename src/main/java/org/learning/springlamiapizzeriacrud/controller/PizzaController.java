package org.learning.springlamiapizzeriacrud.controller;

import jakarta.validation.Valid;
import org.learning.springlamiapizzeriacrud.model.Pizza;
import org.learning.springlamiapizzeriacrud.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/")
public class PizzaController {

    @Autowired
    private PizzaRepository pizzaRepository;


    @GetMapping()
    public String index(Model model, @RequestParam("keyword") Optional<String> searchKeyword) {

        List<Pizza> pizzaList;
        String keyword = "";

        if(searchKeyword.isPresent())
        {   keyword = searchKeyword.get();
            pizzaList = pizzaRepository.findByNameContaining(keyword);
        }
        else{
            pizzaList = pizzaRepository.findAll();
        }

        model.addAttribute("pizza", pizzaList);
        model.addAttribute("keyword", keyword);


        return "pizza/index";
    }

    @GetMapping("show/{id}")
    public String show(@PathVariable("id") Integer id, Model model) {
        Optional<Pizza> optionalPizza = pizzaRepository.findById(id);


        if (optionalPizza.isPresent()) {
            Pizza pizzaFromDB = optionalPizza.get();
            model.addAttribute("pizza", pizzaFromDB);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pizza with id: " + id + " not found.");

        }

        return "pizza/detail";
    }

    @GetMapping("/create")
    public String create(Model model)
    {

        model.addAttribute("pizza",new Pizza());

        return "pizza/form";
    }

    @PostMapping("/create")
    public String doCreate(@Valid @ModelAttribute("pizza") Pizza formPizza, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors())
        {
            return "pizza/form";

        }

        formPizza.setName(formPizza.getName().toUpperCase());

        pizzaRepository.save(formPizza);


        return "redirect:/";
    }


    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model)
    {

      Optional<Pizza> result =  pizzaRepository.findById(id);

      if(result.isPresent())
      {
          model.addAttribute("pizza",result.get());
          return "pizza/form";
      }


       throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/edit/{id}")
    public String doEdit(@Valid @ModelAttribute("pizza") Pizza formPizza,BindingResult bindingResult)
    {

        if(bindingResult.hasErrors())
        {

            return "pizza/form";
        }

        pizzaRepository.save(formPizza);

        return "redirect:/";
    }

    @PostMapping("/delete/{id}")
    public String doDelete(@PathVariable Integer id)
    {
        pizzaRepository.deleteById(id);

        return "redirect:/";
    }


}
