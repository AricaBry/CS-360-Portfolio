package com.example.inventoryapp_arica_bryant;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class ScreenItem extends AppCompatActivity {

    // Member variables
    EditText editItemName, editItemQuantity;
    Button increaseButton, decreaseButton, saveButton;

    // Member variables for intent
    String name, quantity, id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_screen);

        // Assign ids
        editItemName = findViewById(R.id.editItemName);
        editItemQuantity = findViewById(R.id.editItemQuantity);
        increaseButton = findViewById(R.id.increaseButton);
        decreaseButton = findViewById(R.id.decreaseButton);
        saveButton = findViewById(R.id.saveButton);

        // Checks for potential intent data
        setRecyclerData();

        // Listeners for buttons
        // Handles the increase button
        increaseButton.setOnClickListener(v -> {
            try {
                int quantity = Integer.parseInt(editItemQuantity.getText().toString());
                quantity += 1;
                editItemQuantity.setText(String.valueOf(quantity));
            } catch (Exception e) {
                Toast toast = Toast.makeText(ScreenItem.this, "Error occurred setting quantity", Toast.LENGTH_SHORT);
                toast.show();
            }
        });

        // Handles the decrease button
        decreaseButton.setOnClickListener(v -> {
            try {
                int quantity = Integer.parseInt(editItemQuantity.getText().toString());
                if (quantity >= 1) {
                    quantity -= 1;
                    editItemQuantity.setText(String.valueOf(quantity));
                } else {
                    // Current quantity will not go below 0
                    editItemQuantity.setText(String.valueOf(quantity));
                }
            } catch (Exception e) {
                Toast toast = Toast.makeText(ScreenItem.this, "Error occurred setting quantity", Toast.LENGTH_SHORT);
                toast.show();
            }
        });

        // Handles the save button
        saveButton.setOnClickListener(v -> {
            ItemInfo itemInfo;

            // If the item already exists
            if (id != null) {
                try (InventoryAppDB inventoryAppDB = new InventoryAppDB(ScreenItem.this)) {

                    // updates itemInfo data with new intents
                    String nItem = editItemName.getText().toString().trim();
                    String nQuantity = editItemQuantity.getText().toString().trim();
                    itemInfo = new ItemInfo(nItem, Integer.parseInt(nQuantity), Integer.parseInt(id));

                    // updates item in database
                    inventoryAppDB.updateOne(id, itemInfo);

                    // Checks for sms notifications
                    checkSms(itemInfo);
                }

            } else if (editItemName.getText().toString().isEmpty() || editItemQuantity.getText().toString().isEmpty()) {
                // If content is null
                Toast.makeText(ScreenItem.this, "Please enter an Item Name and Quantity", Toast.LENGTH_SHORT).show();
            } else {
                // If content is not null
                // Adds item info to the database
                String nItem = editItemName.getText().toString();
                String nQuantity = editItemQuantity.getText().toString();

                itemInfo = new ItemInfo(nItem, Integer.parseInt(nQuantity), -1);
                try (InventoryAppDB inventoryAppDB = new InventoryAppDB(ScreenItem.this)) {
                    inventoryAppDB.addItem(itemInfo);
                }

                // Checks for sms notifications
                checkSms(itemInfo);
            }

            // Returns to inventory screen
            Intent intent = new Intent(ScreenItem.this, Inventory.class);
            startActivity(intent);
        });
    }
    // Existing data is grabbed and set from the intent if there is data to retrieve
    // If there is no created data from the intent then the method simply does nothing
    void setRecyclerData () {
        // If there is data to retrieve from the intent
        if (getIntent().hasExtra("id") && getIntent().hasExtra("name") && getIntent().hasExtra("quantity")) {
            // Grabbing already created data from intent
            id = getIntent().getStringExtra("id");
            name = getIntent().getStringExtra("name");
            quantity = getIntent().getStringExtra("quantity");

            // Setting intent data
            editItemName.setText(name);
            editItemQuantity.setText(quantity);
        }
    }

    // Checks if you have SMS notifications enabled
    void checkSms(ItemInfo itemInfo) {
        String msg;
        String phoneNum = "5551234567";

        if (ContextCompat.checkSelfPermission(ScreenItem.this, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
            // If the item quantity count is getting low (below 5 items) an alert will be sent
            // ONLY to those with SMS notifications enabled
            if (itemInfo.getItemQuantity() <= 5) {
                // Sends an sms notification
                msg = String.format("Inventory Item: %s almost out of stock!!", itemInfo.getItemName());

                try {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(phoneNum, null, msg, null, null);
                    Toast.makeText(ScreenItem.this, "New Notification", Toast.LENGTH_SHORT).show();
                } catch (Exception e){
                    Toast.makeText(ScreenItem.this, "Notification Error Occurred", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
