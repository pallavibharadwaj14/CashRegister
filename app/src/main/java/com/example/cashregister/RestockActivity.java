package com.example.cashregister;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;
import java.util.List;

public class RestockActivity extends AppCompatActivity {

    private ListView itemList; // ListView to display the products
    private EditText quantityInput; // EditText for entering the quantity to restock
    private Button okButton; // Button to confirm the restock action
    private Button cancelButton; // Button to cancel the restock action

    private List<Product> products; // List of products to be restocked

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restock);
// Initialize the views (ListView, EditText, Buttons)
        itemList = findViewById(R.id.item_list);
        quantityInput = findViewById(R.id.quantity_input);
        okButton = findViewById(R.id.ok_button);
        cancelButton = findViewById(R.id.cancel_button);
// Retrieve the products list from the Intent
        Intent intent = getIntent();
        products = (List<Product>) intent.getSerializableExtra("products");
//Check if product list empty.. If yes, close activity
        if (products == null) {
            // Handle the case where products is null
            finish();
            return;
        }

        ProductAdapter adapter = new ProductAdapter(this, products);
        itemList.setAdapter(adapter);
        itemList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        itemList.setOnItemClickListener((parent, view, position, id) -> {
            Product selectedProduct = products.get(position);
            // Handle item click for restocking
            Toast.makeText(RestockActivity.this, "Selected: " + selectedProduct.getName(), Toast.LENGTH_SHORT).show();
        });
// Set the "OK" button listener to confirm restock
        okButton.setOnClickListener(v -> {
            // Get the quantity entered in the input field
            String quantityStr = quantityInput.getText().toString();
            if (quantityStr.isEmpty()) {
                // If no quantity is entered, show a Toast and return
                Toast.makeText(RestockActivity.this, "Please enter a quantity", Toast.LENGTH_SHORT).show();
                return;
            }

            int selectedPosition = itemList.getCheckedItemPosition();
            if (selectedPosition == ListView.INVALID_POSITION) {
                Toast.makeText(RestockActivity.this, "Please select an item", Toast.LENGTH_SHORT).show();
                return;
            }
// Convert the entered quantity to an integer
            int quantity = Integer.parseInt(quantityStr);
            // Retrieve the selected product from the list
            Product selectedProduct = products.get(selectedPosition);
            // Update the inventory of the selected product
            selectedProduct.setInventory(selectedProduct.getInventory() + quantity);
            // Notify the adapter that the data has changed (update the ListView)
            adapter.notifyDataSetChanged();

            Toast.makeText(RestockActivity.this, "Item restocked", Toast.LENGTH_SHORT).show();
        });
// Set the "Cancel" button listener to close the activity
        cancelButton.setOnClickListener(v -> finish());
    }

    @Override
    //finish method to pass the updated products list back to the previous activity
    public void finish() {
        // Create an Intent to return the updated products list to ManagerActivity
        Intent data = new Intent(RestockActivity.this, ManagerActivity.class);
        data.putExtra("products", (Serializable) products);
        setResult(RESULT_OK, data);
        super.finish(); // Call the parent finish method to close the activity
    }
}