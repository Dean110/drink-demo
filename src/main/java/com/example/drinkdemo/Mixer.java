package com.example.drinkdemo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
@Entity
public class Mixer{
    private String name;
    @Id
    @GeneratedValue
    private Long id;
    @ManyToMany(mappedBy = "mixers")
    private Set<Drink> drinks;
    private boolean inStock;

    protected Mixer(){}

    public Mixer(String name) {
        drinks = new HashSet<>();
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Long getId() {
        return id;
    }

    public Set<Drink> getDrinks() {
        return drinks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Mixer mixer = (Mixer) o;
        return Objects.equals(name, mixer.name) &&
                Objects.equals(id, mixer.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, id);
    }

    public void restock() {
        inStock = true;
    }
    public void markOutOfStock(){
        inStock = false;
    }

    public boolean isInStock() {
        return inStock;
    }
}
