package com.example.inventoryapp_arica_bryant;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class Inventory extends AppCompatActivity {

    // Member variables
    TextView appTitle;
    Button addButton;
    Button smsSettingsButton;
    RecyclerView recyclerView;
    InventoryAppDB inventoryAppDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inventory_screen);

        appTitle = findViewById(R.id.appTitle);
        addButton = findViewById(R.id.addButton);
        smsSettingsButton = findViewById(R.id.smsSettingsButton);
        recyclerView = findViewById(R.id.iRecyclerView);

        inventoryAppDB = new InventoryAppDB(Inventory.this);

        // Creates the RecyclerView
        I_RecyclerViewAdapter adapter = new I_RecyclerViewAdapter(Inventory.this, inventoryAppDB.getAllItems());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(Inventory.this));

        // Setting onClick listeners for buttons
        // Navigates to Add item screen
        addButton.setOnClickListener(v -> {
            Intent intent = new Intent(Inventory.this, ScreenItem.class);
            startActivity(intent);
        });

        // Navigates to SMS settings screen
        smsSettingsButton.setOnClickListener(v -> {
            Intent intent = new Intent(Inventory.this, ScreenSMS.class);
            startActivity(intent);
        });
    }
}
