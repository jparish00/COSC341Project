package com.example.cosc341project;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

// UNTESTED
public class VendorSignUp extends AppCompatActivity {

    // Views
    TextView firstNameInput, lastNameInput, emailInput, storeURLInput, passwordInput, rePasswordInput;
    Button returnButton, nextButton, custAccountButton;
    Resources res;

    // Var
    int[] textIds = { R.id.first_name_input, R.id.last_name_input, R.id.email_input, R.id.password_input, R.id.reEnterPassword };

    public static Activity first; // To kill this activity from the second sign up vendor activity if sign up successful

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_sign_up);

        first = this;

        firstNameInput = findViewById(R.id.first_name_input);
        lastNameInput = findViewById(R.id.last_name_input);
        emailInput = findViewById(R.id.email_input);
        storeURLInput = findViewById(R.id.storewebsite);
        passwordInput = findViewById(R.id.password_input);
        rePasswordInput = findViewById(R.id.reEnterPassword);
        returnButton = findViewById(R.id.return_button);
        nextButton = findViewById(R.id.nextPage);
        custAccountButton = findViewById(R.id.ImCustomer);

        returnButton.setOnClickListener(v -> { finish(); });
        nextButton.setOnClickListener(this::onNextClick);
        custAccountButton.setOnClickListener(this::onImCustClick);

        res = getResources();
    }

    public void onNextClick(View v) {
        Intent intent = new Intent(getApplicationContext(), VendorSignUp2.class);
        Bundle b = new Bundle();

        String input;
        for (int id : textIds) {
            TextView t = findViewById(id);
            input = t.getText().toString();
            if (input.isEmpty()) {
                Toast.makeText(this, res.getString(R.string.toast_empty_spaces), Toast.LENGTH_SHORT).show();
                return;
            }
        }

        // Same checks as customer signup
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
        }
        // Verify password contains uppercase, lowercase, special character
        else if(!password.matches(".*\\d.*") || !password.matches(".*[a-z].*")
                || !password.matches(".*[A-Z].*")  || !password.matches(".*[@#$%^&+=!].*")) {
            Toast.makeText(this, res.getString(R.string.toast_password_invalid), Toast.LENGTH_SHORT).show();
            return;
        }
        String passwordCheck = rePasswordInput.getText().toString();
        if (!password.equals(passwordCheck)) {
            Toast.makeText(this, res.getString(R.string.toast_password_mismatch), Toast.LENGTH_SHORT).show();
        }

        // Store URL can be blank
        String storeURL = storeURLInput.getText().toString();

        b.putString("username", username);
        b.putString("email", email);
        b.putString("password", password);
        b.putString("store_url", storeURL);

        intent.putExtras(b);
        startActivity(intent);

    }

    public void onImCustClick(View v) {

        Intent intent = new Intent(getApplicationContext(), CustSignUp.class);
        startActivity(intent);
        finish();

    }
}