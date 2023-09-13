package com.example.test1;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.CheckboxGroup;
import com.vaadin.flow.component.checkbox.CheckboxGroupVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

@Route("")
@PageTitle("Pizzeria - Oliwia Paliwoda")
public class MenuView extends VerticalLayout {
    private TextField customerName;
    private CheckboxGroup<String> ingredientCheckbox;
    private Button addButton;
    private Grid<Pizza> pizzaGrid;
    private Menu menu;
    private DatabaseRepository repository;

    private Map<String, Double> ingredientPopularity;

    private int ordersNumber;

    public MenuView(Menu menu, DatabaseRepository repository) {



        ingredientPopularity = new HashMap<>();

        menu.ingredientList.stream()
                .forEach(ingredient -> ingredientPopularity.put(ingredient.getName(), 0.0)); //ALGORYTM WYPELNIAM ZERAMI

        customerName = new TextField("Customer's name");
        ingredientCheckbox = new CheckboxGroup<>();
        //ingredientCheckbox.addThemeVariants(CheckboxGroupVariant.LUMO_VERTICAL);
        ingredientCheckbox.setLabel("Please select ingredients");
        ingredientCheckbox.setItems("Ketchup", "Cheese", "Mushrooms", "Oregano", "Ham", "Tomatoes", "Bacon", "Olives", "Sausages", "Pepperoni");
        addButton = new Button("Add pizza");
        pizzaGrid = new Grid<>(Pizza.class);
        this.menu = menu;

        pizzaGrid.setColumns("name", "price", "ingredients");
        pizzaGrid.getColumnByKey("name").setHeader("Customer's name");

        add(customerName, ingredientCheckbox, addButton, pizzaGrid);

        addButton.addClickListener(event -> {
            ordersNumber += 1;  //ALGORYTM - ZWIEKSZENIE ILOSCI ZAMOWIEN
            String name = customerName.getValue();
            Set<String> selectedIngredients = ingredientCheckbox.getValue();
            Pizza pizza = new Pizza(name);
            double totalPrice = pizza.getPrice();


            AtomicReference<Double> totalPriceRef = new AtomicReference<>(totalPrice);

            selectedIngredients.stream().forEach(ingredientName -> {
                Ingredient pizzaIngredient = new Ingredient(ingredientName, menu.getIngredientPrice(ingredientName));

                if (menu.ingredientUsage.containsKey(ingredientName)) {
                    Double currentValue = menu.ingredientUsage.get(ingredientName);
                    menu.ingredientUsage.replace(ingredientName, currentValue + 1);
                }
                pizza.addIngredient(pizzaIngredient);
                double currentPrice = totalPriceRef.get();
                currentPrice += menu.getIngredientPrice(ingredientName);
                totalPriceRef.set(currentPrice);
            });

            totalPrice = totalPriceRef.get();
            pizza.setPrice(totalPrice);

            System.out.println("\n\n\n");
            System.out.println("==========INGREDIENTS POPULARITY==========");

            ingredientPopularity.keySet().stream()
                    .forEach(key1 -> ingredientPopularity.replace(key1, (menu.ingredientUsage.get(key1) / ordersNumber)*100));

            ingredientPopularity.entrySet().stream()
                    .forEach(entry -> {
                        String key = entry.getKey();
                        Double value = entry.getValue();
                        DecimalFormat decimalFormat = new DecimalFormat("#.##");
                        System.out.println("Ingredient: " + key + ", Popularity: " + decimalFormat.format(value) + "%");
                    });

            menu.addPizza(pizza);
            updatePizzaGrid();
            pizzaGrid.getDataProvider().refreshAll();
            pizza.setIngredientsAsString();
            repository.save(pizza);;


        });

        updatePizzaGrid();
    }

    private void updatePizzaGrid() {
        pizzaGrid.setItems(menu.getMenu());
    }

    private Menu getMenu() {
        return menu;
    }
}
