package com.example.drinkdemo;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
@Entity
public class Drink{
    private String name;
    @ManyToMany
    private Set<Liquor> liquors;
    @ManyToMany
    private Set<Mixer> mixers;
    @Id
    @GeneratedValue
    private Long id;

    protected Drink(){}

    public Drink(String name) {
        liquors = new HashSet<>();
        mixers = new HashSet<>();
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void addLiquor(Liquor liquor) {
        liquors.add(liquor);
    }

    public void addMixer(Mixer mixer) {
        mixers.add(mixer);
    }

    public Set<Liquor> getLiquors() {
        return liquors;
    }

    public Set<Mixer> getMixers() {
        return mixers;
    }

    public Long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Drink drink = (Drink) o;
        return Objects.equals(name, drink.name) &&
                Objects.equals(id, drink.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, id);
    }
}
