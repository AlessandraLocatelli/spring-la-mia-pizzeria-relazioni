package org.learning.springlamiapizzeriacrud.controller;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.validation.Valid;
import org.learning.springlamiapizzeriacrud.model.Ingredient;
import org.learning.springlamiapizzeriacrud.repository.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/ingredients")
public class IngredientController {

    @Autowired
    IngredientRepository ingredientRepository;

    @GetMapping
    public String index(Model model)
    {
        List<Ingredient> ingredientsList = ingredientRepository.findAll();
        model.addAttribute("ingredientsList",ingredientsList);
        model.addAttribute("ingredientsObj",new Ingredient());

        return "ingredients/index";


    }

    @PostMapping("/create")
    public String doCreate(@Valid @ModelAttribute("ingredientsObj") Ingredient ingredientForm, BindingResult bindingResult)
    {
        if(bindingResult.hasErrors())
        {
            return "ingredients/index";

        }

        ingredientRepository.save(ingredientForm);

        return"redirect:/ingredients";

    }

   @PostMapping("/delete/{iId}")
   public String delete(@PathVariable("iId") Integer id)
   {

       ingredientRepository.deleteById(id);
       return"redirect:/ingredients";

   }



}
