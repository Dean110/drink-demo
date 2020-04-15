package com.example.drinkdemo;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Set;

public interface DrinkRepository extends CrudRepository<Drink, Long>{
   Set<Drink> findAllByLiquorsContainsAndMixersContains(Liquor liquor, Mixer mixer);
   Set<Drink> findAllByLiquorsContainsAndMixersContains(List<Liquor> liquor, List<Mixer> mixer);

}
