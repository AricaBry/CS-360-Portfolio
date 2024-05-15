package com.example.inventoryapp_arica_bryant;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class ScreenLogin extends AppCompatActivity {

    // Member variables
    EditText editUsername, editPassword;
    Button loginButton, registerButton;
    TextView loginError;
    InventoryAppDB inventoryAppDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);

        // Assign IDs
        editUsername = findViewById(R.id.editUsername);
        editPassword = findViewById(R.id.editPassword);
        loginButton = findViewById(R.id.loginButton);
        registerButton = findViewById(R.id.registerButton);
        loginError = findViewById(R.id.loginError);

        // Listeners for buttons
        loginButton.setOnClickListener(v -> {
            // Handles the login button
            String user = editUsername.getText().toString();
            String pass = editPassword.getText().toString();

            if (user.isEmpty() || pass.isEmpty()) {
                // If content is null output error message to user
                loginError.setText(R.string.you_must_enter_a_username_and_password);
            }
            else {
                // If content is not null
                // Gets all users from database into a list
                inventoryAppDB = new InventoryAppDB(ScreenLogin.this);
                List<UserInfo> all = inventoryAppDB.getAllUsers();

                // Loops through list and determines if a matching username and password are inside the database
                boolean userFound = false;
                for (UserInfo userInfo : all) {
                    // If the username is found
                    if (userInfo.getUsername().equals(user)) {
                        userFound = true;
                        // If the password is found
                        if (userInfo.getPassword().equals(pass)) {
                            // User is authenticated
                            // Switches to Inventory screen
                            Intent intent = new Intent(ScreenLogin.this, Inventory.class);
                            startActivity(intent);
                        } else {
                            // password is incorrect
                            loginError.setText(R.string.incorrect_password);
                        }
                        // Exit once the user is found
                        break;
                    }
                }

                if (!userFound) {
                    // If username isn't found
                    loginError.setText(R.string.username_and_password_not_found);
                }
            }
        });

        // Handles the register button
        registerButton.setOnClickListener(v -> {
            Intent intent = new Intent(ScreenLogin.this, Register.class);
            startActivity(intent);
        });
    }
}
