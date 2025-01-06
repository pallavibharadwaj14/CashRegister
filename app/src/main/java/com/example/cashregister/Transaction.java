package com.example.cashregister;

import java.io.Serializable;
import java.util.Date;

public class Transaction implements Serializable {
    private String productName;
    private int quantity;
    private int cost;
    private Date date;

    public Transaction(String productName, int quantity, int cost, Date date) {
        this.productName = productName;
        this.quantity = quantity;
        this.cost = cost;
        this.date = date;
    }

    public String getProductName() {
        return productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getCost() {
        return cost;
    }

    public Date getDate() {
        return date;
    }
}