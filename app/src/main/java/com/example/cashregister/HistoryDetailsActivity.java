package com.example.cashregister;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class HistoryDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_details);

        Transaction transaction = (Transaction) getIntent().getSerializableExtra("transaction");

        TextView productNameTextView = findViewById(R.id.product_name);
        TextView quantityTextView = findViewById(R.id.quantity);
        TextView costTextView = findViewById(R.id.cost);
        TextView dateTextView = findViewById(R.id.date);

        productNameTextView.setText("Product: " + transaction.getProductName());
        quantityTextView.setText("Price: $" + String.valueOf(transaction.getQuantity()));
        costTextView.setText("Cost: $" + String.valueOf(transaction.getCost()));
        dateTextView.setText("Date: " + transaction.getDate().toString());
    }
}