package com.example.inventoryapp_arica_bryant;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class ScreenSMS extends AppCompatActivity {

    // Member variables
    Button backButton2, smsButton;
    private static final int SMS_PERMISSION_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sms_screen);

        smsButton = findViewById(R.id.smsButton);
        backButton2 = findViewById(R.id.backButton2);

        // Button function that checks and requests permission
        smsButton.setOnClickListener(v -> {
            // Checks if permission is not granted
            if (ContextCompat.checkSelfPermission(ScreenSMS.this, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(ScreenSMS.this, "Permission is already granted", Toast.LENGTH_SHORT).show();
            } else {
                // Popup asks user for permissions
                ActivityCompat.requestPermissions(ScreenSMS.this, new String[]{Manifest.permission.SEND_SMS}, SMS_PERMISSION_CODE);
            }
        });

        // Handles the back button
        backButton2.setOnClickListener(v -> {
            // Returns to login page
            Intent intent = new Intent(ScreenSMS.this, Inventory.class);
            startActivity(intent);
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == SMS_PERMISSION_CODE) {
            // Checking if permission was granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(ScreenSMS.this, "SMS Notifications Enabled", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(ScreenSMS.this, "Permission was not granted", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
