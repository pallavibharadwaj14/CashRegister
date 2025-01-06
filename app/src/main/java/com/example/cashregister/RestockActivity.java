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

    private ListView itemList;
    private EditText quantityInput;
    private Button okButton;
    private Button cancelButton;

    private List<Product> products;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restock);

        itemList = findViewById(R.id.item_list);
        quantityInput = findViewById(R.id.quantity_input);
        okButton = findViewById(R.id.ok_button);
        cancelButton = findViewById(R.id.cancel_button);

        Intent intent = getIntent();
        products = (List<Product>) intent.getSerializableExtra("products");

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

        okButton.setOnClickListener(v -> {
            String quantityStr = quantityInput.getText().toString();
            if (quantityStr.isEmpty()) {
                Toast.makeText(RestockActivity.this, "Please enter a quantity", Toast.LENGTH_SHORT).show();
                return;
            }

            int selectedPosition = itemList.getCheckedItemPosition();
            if (selectedPosition == ListView.INVALID_POSITION) {
                Toast.makeText(RestockActivity.this, "Please select an item", Toast.LENGTH_SHORT).show();
                return;
            }

            int quantity = Integer.parseInt(quantityStr);
            Product selectedProduct = products.get(selectedPosition);
            selectedProduct.setInventory(selectedProduct.getInventory() + quantity);
            adapter.notifyDataSetChanged();

            Toast.makeText(RestockActivity.this, "Item restocked", Toast.LENGTH_SHORT).show();
        });

        cancelButton.setOnClickListener(v -> finish());
    }

    @Override
    public void finish() {
        Intent data = new Intent(RestockActivity.this, ManagerActivity.class);
        data.putExtra("products", (Serializable) products);
        setResult(RESULT_OK, data);
        super.finish();
    }
}