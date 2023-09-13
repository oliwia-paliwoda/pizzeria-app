package com.example.test1;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Ingredient {
    @Id
    private String name;
    private double price;



    public Ingredient() {
        this.name = "Default Ingredient";
        this.price = 0;
    }

    public Ingredient(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return this.name;
    }

    public double getPrice(){
        return this.price;
    }

    @Override
    public String toString() {
        return name;
    }

}
