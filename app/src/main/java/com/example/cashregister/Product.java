package com.example.cashregister;

import java.io.Serializable;
//implements Serializable so that instances of this class can be passed between activities
public class Product implements Serializable {
    // Class variable
    private String name;
    private int price;
    private int inventory;
    // Constructor to initialize a new product object
    public Product(String name, int price, int inventory) {
        this.name = name;
        this.price = price;
        this.inventory = inventory;
    }
// Getter for the product name
    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }
    // Getter for the product inventory (items  available in stock)
    public int getInventory() {
        return inventory;
    }

    // Setter for the product inventory (used to update stock after a purchase or restock)
    public void setInventory(int inventory) {
        this.inventory = inventory;
    }
}