package com.example.drinkdemo;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class DrinkControllerTest {
    @InjectMocks
    DrinkController underTest;
    @Mock
    DrinkRepository drinkRepo;

    private Liquor testLiquor1;
    private Liquor testLiquor2;
    private Mixer testMixer1;
    private Mixer testMixer2;
    private Drink allIngredientsDrink;
    private Drink singleIngredientsDrink;

    private Model mockModel;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        createTestDrinks();
        mockModel = mock(Model.class);
        when(drinkRepo.findAll()).thenReturn(Arrays.asList(singleIngredientsDrink, allIngredientsDrink));
    }

    @Test
    public void findRecipesForInStockItemsShouldReturnProperTemplate(){
        String templateName = underTest.findRecipesForInStockItems(mockModel);
        assertThat(templateName).isEqualTo("availableDrinks");
    }
    @Test
    public void shouldReturnEmptyListOfDrinksIfNoLiquorsOrMixersAreAvailable(){
        underTest.findRecipesForInStockItems(mockModel);
        verify(drinkRepo).findAll();
        verify(mockModel).addAttribute("drinks", Collections.EMPTY_SET);
    }
    @Test
    public void findRecipesForInStockItemsShouldReturnSetOfSingleIngredientsDrink(){
        testLiquor1.restock();
        testMixer1.restock();
        underTest.findRecipesForInStockItems(mockModel);
        verify(mockModel).addAttribute("drinks", Collections.singleton(singleIngredientsDrink));
    }
    @Test
    public void findRecipesForMoreInStockItemsShouldReturnSetOfSingleIngredientsDrink(){
        testLiquor1.restock();
        testLiquor2.restock();
        testMixer1.restock();
        underTest.findRecipesForInStockItems(mockModel);
        verify(mockModel).addAttribute("drinks", Collections.singleton(singleIngredientsDrink));
    }
    @Test
    public void findRecipesForAllIngredientsInStockShouldReturnSetOfAllDrinks(){
        testLiquor1.restock();
        testLiquor2.restock();
        testMixer1.restock();
        testMixer2.restock();
        underTest.findRecipesForInStockItems(mockModel);
        verify(mockModel).addAttribute("drinks", new HashSet<Drink>(Arrays.asList(singleIngredientsDrink,allIngredientsDrink)));
    }
    private void createTestDrinks() {
        testLiquor1 = new Liquor("Test Liquor 1");
        testLiquor2 = new Liquor("Test Liquor 2");

        testMixer1 = new Mixer("Test Mixer 1");
        testMixer2 = new Mixer("Test Mixer 2");


        allIngredientsDrink = new Drink("Test Drink 1");
        allIngredientsDrink.addLiquor(testLiquor1);
        allIngredientsDrink.addLiquor(testLiquor2);
        allIngredientsDrink.addMixer(testMixer1);
        allIngredientsDrink.addMixer(testMixer2);

        singleIngredientsDrink = new Drink("Test Drink 2");
        singleIngredientsDrink.addLiquor(testLiquor1);
        singleIngredientsDrink.addMixer(testMixer1);

    }
}
