package com.example.cosc341project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileOutputStream;

// UNTESTED
public class VendorSignUp2 extends AppCompatActivity {

    // Views
    TextView vendorTitleInput, vendorAddressInput ;
    Spinner vendorCategorySpinner;
    Button returnButton, createAccountButton;

    // Var
    String username, password, email, storeURL;
    int[] textIds = { R.id.vendorT, R.id.vendorAddress };
    Resources res;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_sign_up2);

        Bundle b = getIntent().getExtras();
        username = b.getString("username");
        password = b.getString("password");
        email = b.getString("email");
        storeURL = b.getString("storeURL");

        // Views
        vendorTitleInput = findViewById(R.id.vendorT);
        vendorAddressInput = findViewById(R.id.vendorAddress);
        vendorCategorySpinner = findViewById(R.id.categories);
        returnButton = findViewById(R.id.return_button);
        createAccountButton = findViewById(R.id.create_account_confirm_button);

        returnButton.setOnClickListener(v -> { finish(); } );
        createAccountButton.setOnClickListener(this::createVendorAccount);

        res = getResources();
    }

    public void createVendorAccount(View v) {

        // Check that the info is filled out correctly

        String input;
        for (int id : textIds) {
            TextView t = findViewById(id);
            input = t.getText().toString();
            if (input.isEmpty()) {
                Toast.makeText(this, res.getString(R.string.toast_empty_spaces), Toast.LENGTH_SHORT).show();
                return;
            }
        }

        // getInfo
        String vendorTitle = vendorTitleInput.getText().toString();
        String vendorAddress = vendorAddressInput.getText().toString();

        String category = vendorCategorySpinner.getSelectedItem().toString();

        // Write user info, appending to end
        FileOutputStream fout;
        try {
            fout = openFileOutput(res.getString(R.string.user_data), Context.MODE_APPEND);
            fout.write(("@" + username + " " + email).getBytes());
            fout.write(password.getBytes());
            fout.write("false".getBytes());
            fout.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Write to vendor info, appending to end
        try {
            fout = openFileOutput(res.getString(R.string.vendor_data), Context.MODE_APPEND);
            fout.write(("@" + username + " " + vendorTitle + " " + email).getBytes());
            fout.write(storeURL.getBytes());
            fout.write(vendorAddress.getBytes());
            fout.write(category.getBytes());
            fout.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        VendorSignUp.first.finish();
        finish();

    }
}