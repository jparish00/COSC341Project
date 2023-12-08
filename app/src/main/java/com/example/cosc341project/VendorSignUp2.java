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
import android.widget.ArrayAdapter;

import java.io.FileOutputStream;

// UNTESTED
public class VendorSignUp2 extends AppCompatActivity {

    // Views
    TextView vendorTitleInput, vendorAddressInput;
    Spinner vendorCategorySpinner;
    Button returnButton, createAccountButton;

    // Var
    String username, password, email, storeURL;
    int[] textIds = {R.id.vendorT, R.id.vendorAddress};
    Resources res;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_sign_up2);

        Bundle b = getIntent().getExtras();
        username = b.getString("username");
        password = b.getString("password");
        email = b.getString("email");
        storeURL = b.getString("store_url");

        // Views
        vendorTitleInput = findViewById(R.id.vendorT);
        vendorAddressInput = findViewById(R.id.vendorAddress);
        vendorCategorySpinner = findViewById(R.id.categories);

        // String[] categories = {"Meats", "Vegetables", "Bakery", "Dairy", "Home-Care"};
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.categories, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        vendorCategorySpinner.setAdapter(adapter);


        returnButton = findViewById(R.id.return_button);
        createAccountButton = findViewById(R.id.create_account_confirm_button);

        returnButton.setOnClickListener(v -> {
            finish();
        });
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
            fout.write(("@" + username + " " + email + "\n").getBytes());
            fout.write((password + "\n").getBytes());
            fout.write("false\n".getBytes());
            fout.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Write to vendor info, appending to end
        try {
            fout = openFileOutput(res.getString(R.string.vendor_data), Context.MODE_APPEND);
            fout.write(("@" + username + " " + email + "\n").getBytes());
            fout.write((vendorTitle + "\n").getBytes());
            fout.write((storeURL + "\n").getBytes());
            fout.write((vendorAddress + "\n").getBytes());
            fout.write((category + "\n").getBytes());
            fout.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Write account information
        try {
            fout = openFileOutput(res.getString(R.string.sign_in_type), Context.MODE_APPEND);
            fout.write(("@" + username + " vendor\n").getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Write default market
        try {
            fout = openFileOutput(res.getString(R.string.default_market), Context.MODE_APPEND);
            fout.write(("@" + username + "\n").getBytes());
            fout.write((res.getString(R.string.default_market_preference) + "\n").getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }

        Toast.makeText(this, String.format(res.getString(R.string.toast_account_creation_success), username), Toast.LENGTH_LONG).show();

        CustSignUp.first.finish();
        VendorSignUp.first.finish();
        finish();

    }
}