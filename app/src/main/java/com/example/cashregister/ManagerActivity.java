package com.example.cashregister;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;
import java.util.List;

public class ManagerActivity extends AppCompatActivity {

    private List<Product> products;
    private List<Transaction> transactions;
    private Button historyButton;
    private Button restockButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Set layout for activity
        setContentView(R.layout.activity_manager);
        // Retrieve the products and transactions lists passed from main activity
        Intent intent = getIntent();
        products = (List<Product>) intent.getSerializableExtra("products");
        transactions = (List<Transaction>) intent.getSerializableExtra("transactions");
        // Initialize buttons from the layout file
        historyButton = findViewById(R.id.history_button);
        restockButton = findViewById(R.id.restock_button);
        // Set up onClickListener for the "History" button
        historyButton.setOnClickListener(v -> {
            //Create an intent to navigate to the HistoryActivity to view all transactions
            Intent historyIntent = new Intent(ManagerActivity.this, HistoryActivity.class);
            // Pass the transactions list to the HistoryActivity
            historyIntent.putExtra("transactions", (Serializable) transactions);
            startActivity(historyIntent);
        });
        // Set up onClickListener for the "Restock" button
        restockButton.setOnClickListener(v -> {
            // Create an intent to navigate to the RestockActivity for restocking products
            Intent restockIntent = new Intent(ManagerActivity.this, RestockActivity.class);
            // Pass the products list to the RestockActivity
            restockIntent.putExtra("products", (Serializable) products);
            startActivityForResult(restockIntent, 1);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Check if the result is from RestockActivity
        if (requestCode == 1 && resultCode == RESULT_OK) {
            // Retrieve the updated list of products from RestockActivity
            products = (List<Product>) data.getSerializableExtra("products");
            // Create an intent to pass the updated products list back to the calling activity (MainActivity)
            Intent resultIntent = new Intent();
            resultIntent.putExtra("products", (Serializable) products);
            setResult(RESULT_OK, resultIntent);
            //close activity
            finish();
        }
    }

    @Override
    // Before finishing this activity, return the updated products list to the calling activity (MainActivity)
    public void onBackPressed() {
        Intent resultIntent = new Intent();
        resultIntent.putExtra("products", (Serializable) products);
        setResult(RESULT_OK, resultIntent);
        super.onBackPressed();
    }
}