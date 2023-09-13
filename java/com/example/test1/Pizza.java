package com.example.test1;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
public class Pizza {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;

    @Column(name = "CUSTOMER'S NAME")

    private String name;
    private double price = 10.00;
    @Transient

    private List<Ingredient> ingredients;

    @Column(name = "INGREDIENTS")
    private String ingredientsAsString;

    public Pizza() {
        this.ingredients = new ArrayList<>();
    }

    public Pizza(String name) {
        this.name = name;
        this.ingredients = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void addIngredient(Ingredient ingredient) {
        ingredients.add(ingredient);
    }

    public void removeIngredient(Ingredient ingredient) {
        ingredients.remove(ingredient);
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setIngredientsAsString() {
        this.ingredientsAsString = ingredients.stream().map(Object::toString).collect(Collectors.joining(","));
    }

}