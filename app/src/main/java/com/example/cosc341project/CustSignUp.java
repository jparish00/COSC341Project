package com.example.cosc341project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileOutputStream;

public class CustSignUp extends AppCompatActivity {

    // Views
    TextView firstNameInput, lastNameInput, emailInput, passwordInput, repeatPassInput;
    Button returnButton, createAccountButton, createVendorButton;
    Resources res;
    // Var
    int[] textIds = { R.id.first_name_input, R.id.last_name_input, R.id.email_input, R.id.password_input, R.id.repeat_password_input };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cust_sign_up);

        // Views
        firstNameInput = findViewById(R.id.first_name_input);
        lastNameInput = findViewById(R.id.last_name_input);
        emailInput = findViewById(R.id.email_input);
        passwordInput = findViewById(R.id.password_input);
        repeatPassInput = findViewById(R.id.repeat_password_input);
        returnButton = findViewById(R.id.return_button);
        createAccountButton = findViewById(R.id.createAccountBtn); // different id from main activity
        createVendorButton = findViewById(R.id.createVendorBtn);

        returnButton.setOnClickListener(this::onReturnClick);
        createAccountButton.setOnClickListener(this::createAccount);
        createVendorButton.setOnClickListener(this::createAccountVendor);

        res = getResources();
    }

    public void onReturnClick(View v) {
        finish();
    }

    public void createAccount(View v) {

        // Check for input empty error
        String input;
        for (int id : textIds) {
            TextView t = findViewById(id);
            input = t.getText().toString();
            if (input.isEmpty()) {
                Toast.makeText(this, res.getString(R.string.toast_empty_spaces), Toast.LENGTH_SHORT).show();
                return;
            }
        }

        // One-by-one checking
        // Name
        String firstName = firstNameInput.getText().toString();
        String lastName = lastNameInput.getText().toString();

        String username = firstName.charAt(0) + lastName;

        // Email
        String email = emailInput.getText().toString();
        // Regex
        if (!email.matches("([a-zA-Z0-9_.-]+)@([a-zA-Z0-9]+)\\.([a-zA-Z0-9]+)")) {
            Toast.makeText(this, res.getString(R.string.toast_email_invalid), Toast.LENGTH_SHORT).show();
            return;
        }

        // Password check
        String password = passwordInput.getText().toString().trim();
        if (password.length() < 8) {
            Toast.makeText(this, res.getString(R.string.toast_password_short), Toast.LENGTH_SHORT).show();
            return;
        }
        // Verify password contains uppercase, lowercase, special character
        else if(!password.matches(".*\\d.*") || !password.matches(".*[a-z].*")
                || !password.matches(".*[A-Z].*")  || !password.matches(".*[@#$%^&+=!].*")) {
            Toast.makeText(this, res.getString(R.string.toast_password_invalid), Toast.LENGTH_SHORT).show();
            return;
        }
        String passwordCheck = repeatPassInput.getText().toString();
        if (!password.equals(passwordCheck)) {
            Toast.makeText(this, res.getString(R.string.toast_password_mismatch), Toast.LENGTH_SHORT).show();
            return;
        }

        // Success :)
        // Now add to file
        FileOutputStream fout;
        try {
            fout = openFileOutput(res.getString(R.string.user_data), Context.MODE_APPEND);
            fout.write(("@" + username + " " + email).getBytes());
            fout.write(password.getBytes());
            fout.write("false".getBytes()); // To be changed as preferences are added
            fout.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


        Toast.makeText(this, String.format(res.getString(R.string.toast_account_creation_success), username), Toast.LENGTH_LONG).show();
        finish();

    }

    public void createAccountVendor(View v) {

        Intent intent = new Intent(getApplicationContext(), VendorSignUp.class);
        startActivity(intent);

        // finish(); // Consider killing this, back button on next activity leads to login page

    }

}