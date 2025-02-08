package com.example.cashregister;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class HistoryActivity extends AppCompatActivity {
    // List declared to store transactions
    private List<Transaction> transactions;

    @Override
    //Oncreate method is called when activity is first created
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set layout for this activity
        setContentView(R.layout.activity_history);

        Intent intent = getIntent();
        transactions = (List<Transaction>) intent.getSerializableExtra("transactions");

        RecyclerView historyList = findViewById(R.id.history_list);
        historyList.setLayoutManager(new LinearLayoutManager(this));
        TransactionAdapter adapter = new TransactionAdapter(this, transactions);
        historyList.setAdapter(adapter);
    }
}