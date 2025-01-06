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
        setContentView(R.layout.activity_manager);

        Intent intent = getIntent();
        products = (List<Product>) intent.getSerializableExtra("products");
        transactions = (List<Transaction>) intent.getSerializableExtra("transactions");

        historyButton = findViewById(R.id.history_button);
        restockButton = findViewById(R.id.restock_button);

        historyButton.setOnClickListener(v -> {
            Intent historyIntent = new Intent(ManagerActivity.this, HistoryActivity.class);
            historyIntent.putExtra("transactions", (Serializable) transactions);
            startActivity(historyIntent);
        });

        restockButton.setOnClickListener(v -> {
            Intent restockIntent = new Intent(ManagerActivity.this, RestockActivity.class);
            restockIntent.putExtra("products", (Serializable) products);
            startActivityForResult(restockIntent, 1);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            products = (List<Product>) data.getSerializableExtra("products");
            Intent resultIntent = new Intent();
            resultIntent.putExtra("products", (Serializable) products);
            setResult(RESULT_OK, resultIntent);
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        Intent resultIntent = new Intent();
        resultIntent.putExtra("products", (Serializable) products);
        setResult(RESULT_OK, resultIntent);
        super.onBackPressed();
    }
}