package com.example.cashregister;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder> {
    // The context in which the adapter is used
    private Context context;
    // List of transactions to be displayed in the RecyclerView
    private List<Transaction> transactions;

    // Constructor that takes context and the list of transactions
    public TransactionAdapter(Context context, List<Transaction> transactions) {
        this.context = context;
        this.transactions = transactions;
    }

    @NonNull
    @Override
    public TransactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the layout for each transaction item
        View view = LayoutInflater.from(context).inflate(R.layout.transaction_item, parent, false);
        // Return a new ViewHolder with the inflated view
        return new TransactionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionViewHolder holder, int position) {
        // Get the transaction for the current position in the list
        Transaction transaction = transactions.get(position);
        // Set the data for each TextView in the ViewHolder
        holder.productNameTextView.setText(transaction.getProductName());
        holder.priceTextView.setText("Price: $" + transaction.getQuantity());
        holder.costTextView.setText("Cost: $" + transaction.getCost());
        // Set an OnClickListener to open the HistoryDetailsActivity when an item is clicked
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, HistoryDetailsActivity.class);
            intent.putExtra("transaction", transaction);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        // Return the number of transactions in the list
        return transactions.size();
    }
    // ViewHolder class to hold references to the views for each transaction item
    public static class TransactionViewHolder extends RecyclerView.ViewHolder {
        TextView productNameTextView;
        TextView priceTextView;
        TextView costTextView;

        public TransactionViewHolder(@NonNull View itemView) {
            super(itemView);
            // Initialize the TextViews by finding them in the itemView
            productNameTextView = itemView.findViewById(R.id.product_name);
            priceTextView = itemView.findViewById(R.id.price);
            costTextView = itemView.findViewById(R.id.cost);
        }
    }
}