package com.example.drinkdemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class DrinkController {
    @Autowired
    private DrinkRepository drinkRepo;

    public String findRecipesForInStockItems(Model model) {
        Set<Drink> drinks = new HashSet<>((List<Drink>) drinkRepo.findAll());
        Set<Drink> selectedDrinks = findAvailableDrinks(drinks);
        model.addAttribute("drinks",selectedDrinks);
        return "availableDrinks";
    }

    private Set<Drink> findAvailableDrinks(Set<Drink> drinks) {
        HashSet<Drink> availableDrinks = new HashSet<>();

        for(Drink drink :drinks){
            boolean allLiquorsAvailable = true;
            for(Liquor liquor: drink.getLiquors()){
                if(!liquor.isInStock()){
                    allLiquorsAvailable = false;
                }
            }
            boolean allMixersAvaliable = true;
            for (Mixer mixer: drink.getMixers()){
                if(!mixer.isInStock()){
                    allMixersAvaliable = false;
                }
            }
            if(allLiquorsAvailable&&allMixersAvaliable){
                availableDrinks.add(drink);
            }
        }
        return availableDrinks;
    }
}
