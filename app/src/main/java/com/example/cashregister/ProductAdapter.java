package com.example.cashregister;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class ProductAdapter extends BaseAdapter {
    // Holds the context for inflating views
    private Context context;
    private List<Product> products;  //List of product
    // Constructor to initialize the context and the products list
    public ProductAdapter(Context context, List<Product> products) {
        this.context = context;
        this.products = products;
    }

    @Override
    // Returns the number of items in the products list
    public int getCount() {
        // The size of the products list determines how many items are in the adapter
        return products.size();

    }

    @Override
    // Returns the item
    public Object getItem(int position) {
        // Fetches the product object at the given position in the list
        return products.get(position);
    }

    @Override
    // Returns the item ID
    public long getItemId(int position) {
        return position;
    }
    // Creates and returns the view for each product item in the list
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if the view has already been created (recycled view for better performance)
        if (convertView == null) {
            // Inflate a new view from the layout XML file for each item in the list
            convertView = LayoutInflater.from(context).inflate(R.layout.product_list_item, parent, false);
        }
// Get the product at the specified position
        Product product = products.get(position);
        // Find the TextViews in the inflated view for displaying product information
        TextView productName = convertView.findViewById(R.id.product_name);
        TextView productPrice = convertView.findViewById(R.id.product_price);
        TextView productStock = convertView.findViewById(R.id.product_stock);
        // Set the text for each TextView using the product details
        productName.setText(product.getName());
        productPrice.setText("Price: $" + product.getPrice());
        productStock.setText("Stock: " + product.getInventory());
        // Return the fully constructed view for this item
        return convertView;
    }
}