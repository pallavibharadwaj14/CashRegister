package com.example.cashregister;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class HistoryDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Layout for activity
        setContentView(R.layout.activity_history_details);
        // Retrieve the "transaction" object passed from the previous activity
        Transaction transaction = (Transaction) getIntent().getSerializableExtra("transaction");
// Find the TextViews by their respective IDs from the layout file
        TextView productNameTextView = findViewById(R.id.product_name);
        TextView quantityTextView = findViewById(R.id.quantity);
        TextView costTextView = findViewById(R.id.cost);
        TextView dateTextView = findViewById(R.id.date);
// Set product name in TV
        productNameTextView.setText("Product: " + transaction.getProductName());
       //Set Quantity
        quantityTextView.setText("Price: $" + String.valueOf(transaction.getQuantity()));
        //Set cost
        costTextView.setText("Cost: $" + String.valueOf(transaction.getCost()));
        //Set Date
        dateTextView.setText("Date: " + transaction.getDate().toString());
    }
}