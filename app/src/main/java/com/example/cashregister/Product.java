package com.example.cashregister;

import java.io.Serializable;
public class Product implements Serializable {
    private String name;
    private int price;
    private int inventory;

    public Product(String name, int price, int inventory) {
        this.name = name;
        this.price = price;
        this.inventory = inventory;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getInventory() {
        return inventory;
    }

    public void setInventory(int inventory) {
        this.inventory = inventory;
    }
}