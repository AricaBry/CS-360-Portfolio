package com.example.inventoryapp_arica_bryant;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class Register extends AppCompatActivity {

    // Member variables
    EditText createUsername, createPassword;
    Button createAccount, backButton1;
    TextView loginError2;
    InventoryAppDB inventoryAppDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_screen);

        createUsername = findViewById(R.id.createUsername);
        createPassword = findViewById(R.id.createPassword);
        createAccount = findViewById(R.id.createAccount);
        backButton1 = findViewById(R.id.backButton1);
        loginError2 = findViewById(R.id.loginError2);

        // Handles the registration
        createAccount.setOnClickListener(v -> {
            String user = createUsername.getText().toString();
            String pass = createPassword.getText().toString();

            // If content is null output error message to user
            if (user.isEmpty() || pass.isEmpty()) {
                loginError2.setText(R.string.you_must_enter_a_username_and_password);
            } else {
                // Gets all users from database into a list
                inventoryAppDB = new InventoryAppDB(Register.this);
                List<UserInfo> all = inventoryAppDB.getAllUsers();

                // Loops through list and determines if a matching username and password are inside the database
                boolean userFound = false;
                for (UserInfo userInfo : all) {
                    // If the username is found
                    if (userInfo.getUsername().equals(user)) {
                        userFound = true;
                        loginError2.setText(R.string.username_already_exists);
                        break;
                    }
                }
                // If username is not found, Add the new user details to the database
                // Adds new user details to the database
                if (!userFound) {
                    UserInfo newInfo = new UserInfo(user, pass, -1);
                    try (InventoryAppDB inventoryAppDB = new InventoryAppDB(Register.this)) {
                        inventoryAppDB.addUser(newInfo);
                    }
                    // Returns to login page
                    Intent intent = new Intent(Register.this, ScreenLogin.class);
                    startActivity(intent);
                }
            }
        });

        // Handles the back button
        backButton1.setOnClickListener(v -> {
            // Returns to login page
            Intent intent = new Intent(Register.this, ScreenLogin.class);
            startActivity(intent);
        });
    }
}
