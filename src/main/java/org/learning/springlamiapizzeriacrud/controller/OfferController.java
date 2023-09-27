package org.learning.springlamiapizzeriacrud.controller;

import jakarta.validation.Valid;
import org.learning.springlamiapizzeriacrud.model.Offer;
import org.learning.springlamiapizzeriacrud.model.Pizza;
import org.learning.springlamiapizzeriacrud.repository.OfferRepository;
import org.learning.springlamiapizzeriacrud.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.Optional;

@Controller
@RequestMapping("/specialoffers")
public class OfferController {

    @Autowired
    private PizzaRepository pizzaRepository;
    @Autowired
    private OfferRepository offerRepository;

    @GetMapping("/create")
    public String create(@RequestParam("pizzaId") Integer pizzaId, Model model) {
        Optional<Pizza> pizzaResult = pizzaRepository.findById(pizzaId);

        if (pizzaResult.isPresent()) {
            Pizza pizza = pizzaResult.get();
            Offer offer = new Offer();
            offer.setPizza(pizza);
            offer.setStartDate(LocalDate.now());
            offer.setEndDate(LocalDate.now().plusDays(30));
            model.addAttribute("offer", offer);

            return "offers/form";

        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

    }


    @PostMapping("/create")
    public String doCreate(@Valid @ModelAttribute("offer") Offer offerForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "offers/form";
        }

        offerRepository.save(offerForm);
        return "redirect:/show/" + offerForm.getPizza().getId();

    }


    @GetMapping("/edit/{offerId}")
    public String edit(@PathVariable("offerId") Integer id, Model model )
    {
        Optional<Offer> offerResult = offerRepository.findById(id);

        if(offerResult.isPresent())
        {
            model.addAttribute("offer",offerResult.get());

            return "offers/edit";

        }
        else
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

    }


    @PostMapping("/edit/{offerId}")
    public String doEdit(@PathVariable("offerId") Integer offerId,@Valid @ModelAttribute("offer") Offer offerForm, BindingResult bindingResult )
    {
        offerForm.setId(offerId);
        offerRepository.save(offerForm);

        if(bindingResult.hasErrors())
        {
            return "offers/edit";
        }


        return "redirect:/show/" + offerForm.getPizza().getId();

    }

    @PostMapping("/delete/{offerId}")
    public String delete(@PathVariable("offerId") Integer id)
    {
        Optional<Offer> offerResult = offerRepository.findById(id);

        if(offerResult.isPresent())
        {
            Integer pizzaId = offerResult.get().getPizza().getId();
            offerRepository.deleteById(id);
          return  "redirect:/show/" + pizzaId;
        }
        else
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        }


    }


}
