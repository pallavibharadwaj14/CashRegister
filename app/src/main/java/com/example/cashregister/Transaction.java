package com.example.cashregister;

import java.io.Serializable;
import java.util.Date;

public class Transaction implements Serializable {
    // Name, quantity,cost,date of the product involved in the transaction
    private String productName;
    private int quantity;
    private int cost;
    private Date date;
    // Constructor to initialize a new Transaction object
    public Transaction(String productName, int quantity, int cost, Date date) {
        //Initialize
        this.productName = productName;
        this.quantity = quantity;
        this.cost = cost;
        this.date = date;
    }
 // Getter method for product name, quantity, cost, date
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
        // Return the date and time of the transaction
        return date;
    }
}