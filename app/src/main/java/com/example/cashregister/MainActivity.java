package com.example.cashregister;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    // Declare class variables for product list, transaction list, and UI components
    private List<Product> products = new ArrayList<>();
    private List<Transaction> transactions = new ArrayList<>();
    private StringBuilder quantityBuilder = new StringBuilder();
    private TextView selectedProductTextView;
    private TextView quantityTextView;
    private TextView totalAmountTextView;
    private ListView productList;
    private ProductAdapter productAdapter;
    private Button buyButton;

    @Override
    //Activity created
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    // Initializing UI elements using IDs
        selectedProductTextView = findViewById(R.id.selected_product);
        quantityTextView = findViewById(R.id.quantity);
        totalAmountTextView = findViewById(R.id.total_amount);
        productList = findViewById(R.id.product_list);
        buyButton = findViewById(R.id.buy_button);
        Button managerButton = findViewById(R.id.manager_button);

        //Setting default values
        products.add(new Product("T-Shirt", 20, 100));
        products.add(new Product("Jeans", 40, 50));
        products.add(new Product("Jacket", 60, 30));
        products.add(new Product("Shoes", 50, 70));
        //Innitializing UI elements
        setupProductList();
        setupNumberPad();
        setupBuyButton();
        setNumberPadListeners();
        setupManagerButton();
    }
    //Handles the result from the ManagerActivity. Returns updated product and transacti
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            products = (List<Product>) data.getSerializableExtra("products");
            setupProductList();
        }
    }
    // Sets up the product list by using a custom adapter and handling item clicks
    private void setupProductList() {
        // Initialize product list and set item click listener
        productAdapter = new ProductAdapter(this, products);
        productList.setAdapter(productAdapter);

        productList.setOnItemClickListener((parent, view, position, id) -> {
            // Update selected product TextView
            Product selectedProduct = products.get(position);
            selectedProductTextView.setText(selectedProduct.getName());
        });
    }

    private void setupManagerButton() {
        Button managerButton = findViewById(R.id.manager_button);
        managerButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ManagerActivity.class);
            intent.putExtra("products", (Serializable) products);
            intent.putExtra("transactions", (Serializable) transactions);
            startActivityForResult(intent, 1);
        });
    }

    private void setNumberPadListeners() {
        int[] buttonIds = {
                R.id.button_0, R.id.button_1, R.id.button_2, R.id.button_3,
                R.id.button_4, R.id.button_5, R.id.button_6, R.id.button_7,
                R.id.button_8, R.id.button_9, R.id.button_clear
        };

        for (int id : buttonIds) {
            findViewById(id).setOnClickListener(numberPadClickListener);
        }
    }

    private final View.OnClickListener numberPadClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Button button = (Button) v;
            String buttonText = button.getText().toString();

            if (buttonText.equals("C")) {
                quantityBuilder.setLength(0);
            } else {
                quantityBuilder.append(buttonText);
            }

            quantityTextView.setText(quantityBuilder.toString());
        }
    };

    private void setupNumberPad() {
        // Initialize number pad buttons and set click listeners
        GridLayout numbersPad = findViewById(R.id.numbers_pad);
        for (int i = 0; i < numbersPad.getChildCount(); i++) {
            Button button = (Button) numbersPad.getChildAt(i);
            button.setOnClickListener(v -> {
                // Update quantity TextView
                String quantity = button.getText().toString();
                quantityTextView.setText(quantity);
            });
        }
    }
    // Sets up the "Buy" button click listener to handle purchase logic
    private void setupBuyButton() {
        buyButton.setOnClickListener(v -> {
            String selectedProductName = selectedProductTextView.getText().toString();
            String quantityStr = quantityTextView.getText().toString();
            //Both quantity and product is selected
            if (selectedProductName.isEmpty() || quantityStr.isEmpty()) {
                Toast.makeText(MainActivity.this, "Please select a product and quantity", Toast.LENGTH_SHORT).show();
                return;
            }

            int quantity = Integer.parseInt(quantityStr);
            Product selectedProduct = null;
            // Find the selected product from the product list
            for (Product product : products) {
                if (product.getName().equals(selectedProductName)) {
                    selectedProduct = product;
                    break;
                }
            }
// Check if the product is available and if enough inventory exists
            if (selectedProduct == null) {
                Toast.makeText(MainActivity.this, "Selected product not found", Toast.LENGTH_SHORT).show();
                return;
            }

            if (selectedProduct.getInventory() < quantity) {
                Toast.makeText(MainActivity.this, "Not enough inventory", Toast.LENGTH_SHORT).show();
                return;
            }

            int total = quantity * (selectedProduct.getPrice());
            totalAmountTextView.setText("Total: $" + total);
// For debugging
            Log.d("DEBUG", "Selected Product: " + selectedProductName);
            Log.d("DEBUG", "Quantity: " + quantity);
            Log.d("DEBUG", "Total Calculation: " + total);


            // Update product inventory
            selectedProduct.setInventory(selectedProduct.getInventory() - quantity);
// Add a new transaction entry
            transactions.add(new Transaction(selectedProductName, quantity, total, new Date()));
            // Notify the adapter to refresh the product list view
            productAdapter.notifyDataSetChanged();
// Reset the UI for the next transaction
            resetUI();
        });
    }
//Resets the UI elements to their initial state after a purchase
    private void resetUI() {
        selectedProductTextView.setText("");
        quantityTextView.setText("");
        quantityBuilder.setLength(0);
        totalAmountTextView.setText("Total Amount");
    }
}