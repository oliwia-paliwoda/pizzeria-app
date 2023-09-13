package com.example.test1;

import org.springframework.stereotype.Component;

import java.util.*;

@Component

public class Menu {
    private List<Pizza> menu;

    public List<Ingredient> ingredientList;
    public Map<String, Double> ingredientUsage;
    DatabaseRepository repository;

    public Menu(DatabaseRepository repository) {
        menu = new ArrayList<>();
        ingredientList = new ArrayList<>();
        Ingredient Ketchup = new Ingredient("Ketchup", 2.00);
        Ingredient Cheese = new Ingredient("Cheese", 3.00);
        Ingredient Pepperoni = new Ingredient("Pepperoni", 4.00);
        Ingredient Mushrooms = new Ingredient("Mushrooms", 3.00);
        Ingredient Olives = new Ingredient("Olives", 2.00);
        Ingredient Sausages = new Ingredient("Sausages", 4.00);
        Ingredient Ham = new Ingredient("Ham", 3.00);
        Ingredient Tomatoes = new Ingredient("Tomatoes", 2.00);
        Ingredient Oregano = new Ingredient("Oregano", 2.00);
        Ingredient Bacon = new Ingredient("Bacon", 3.00);
        ingredientList.add(Ketchup);
        ingredientList.add(Cheese);
        ingredientList.add(Pepperoni);
        ingredientList.add(Mushrooms);
        ingredientList.add(Olives);
        ingredientList.add(Sausages);
        ingredientList.add(Ham);
        ingredientList.add(Tomatoes);
        ingredientList.add(Oregano);
        ingredientList.add(Bacon);

        ingredientList.stream().forEach(repository::save);

        ingredientUsage = new HashMap<>();

        ingredientList.stream().forEach(ingredient -> ingredientUsage.put(ingredient.getName(), 0.0));

    }

    public void addPizza(Pizza pizza) {
        menu.add(pizza);
    }

    public List<Pizza> getMenu() {
        return menu;
    }

    public double getIngredientPrice(String ingredientName) {
        double price = 0;
        Optional<Ingredient> first = ingredientList.stream().filter(x -> x.getName() == ingredientName).findFirst();
        if (first.isEmpty()) {
            return 0;
        } else {

            return first.get().getPrice();
        }

    }

}
