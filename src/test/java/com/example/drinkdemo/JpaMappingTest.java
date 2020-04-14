package com.example.drinkdemo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class JpaMappingTest{

    @Autowired
    private LiquorRepository liquorRepo;
    @Autowired
    private MixerRepository mixerRepo;
    @Autowired
    private DrinkRepository drinkRepo;
    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void drinksShouldHaveLiquorsMixersAndGarnishes() {
        Liquor testLiquor1 = new Liquor("Test Liquor 1");
        liquorRepo.save(testLiquor1);
        Liquor testLiquor2 = new Liquor("Test Liquor 2");
        liquorRepo.save(testLiquor2);

        Mixer testMixer1 = new Mixer("Test Mixer 1");
        mixerRepo.save(testMixer1);
        Mixer testMixer2 = new Mixer("Test Mixer 2");
        mixerRepo.save(testMixer2);


        Drink testDrink1 = new Drink("Test Drink 1");
        testDrink1.addLiquor(testLiquor1);
        testDrink1.addLiquor(testLiquor2);
        testDrink1.addMixer(testMixer1);
        drinkRepo.save(testDrink1);

        Drink testDrink2 = new Drink("Test Drink 2");
        testDrink2.addLiquor(testLiquor1);
        testDrink2.addMixer(testMixer1);
        testDrink2.addMixer(testMixer2);
        drinkRepo.save(testDrink2);

        entityManager.flush();
        entityManager.clear();

        Liquor retrievedLiquor1 = liquorRepo.findById(testLiquor1.getId()).get();
        Liquor retrievedLiquor2 = liquorRepo.findById(testLiquor2.getId()).get();

        Mixer retrievedMixer1 = mixerRepo.findById(testMixer1.getId()).get();
        Mixer retrievedMixer2 = mixerRepo.findById(testMixer2.getId()).get();

        assertThat(retrievedLiquor1.getDrinks()).containsExactlyInAnyOrder(testDrink1, testDrink2);
        assertThat(retrievedLiquor2.getDrinks()).containsExactlyInAnyOrder(testDrink1);

        assertThat(retrievedMixer1.getDrinks()).containsExactlyInAnyOrder(testDrink1, testDrink2);
        assertThat(retrievedMixer2.getDrinks()).containsExactlyInAnyOrder(testDrink2);
    }

    @Test
    public void searchByLiquorNameAndMixerName(){
        Liquor testLiquor1 = new Liquor("Test Liquor 1");
        liquorRepo.save(testLiquor1);
        Liquor testLiquor2 = new Liquor("Test Liquor 2");
        liquorRepo.save(testLiquor2);

        Mixer testMixer1 = new Mixer("Test Mixer 1");
        mixerRepo.save(testMixer1);
        Mixer testMixer2 = new Mixer("Test Mixer 2");
        mixerRepo.save(testMixer2);


        Drink testDrink1 = new Drink("Test Drink 1");
        testDrink1.addLiquor(testLiquor1);
        testDrink1.addLiquor(testLiquor2);
        testDrink1.addMixer(testMixer1);
        drinkRepo.save(testDrink1);

        Drink testDrink2 = new Drink("Test Drink 2");
        testDrink2.addLiquor(testLiquor1);
        testDrink2.addMixer(testMixer1);
        testDrink2.addMixer(testMixer2);
        drinkRepo.save(testDrink2);

        entityManager.flush();
        entityManager.clear();

        Set<Drink> retrievedDrinks1 = drinkRepo.findAllByLiquorsContainsAndMixersContains(testLiquor1, testMixer1);
        assertThat(retrievedDrinks1).containsExactlyInAnyOrder(testDrink1, testDrink2);

    }

}
;