package com.example.cosc341project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;


public class CustHome extends AppCompatActivity {

    // Views
    TextView marketName;

    // Vars
    Resources res;
    static String username;
    static String defaultMarket, currentMarket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cust_home);

        marketName = findViewById(R.id.marketName);

        res = getResources();

        Bundle b = getIntent().getExtras();
        username = b.getString("username");

        // Check if this is a vendor account. If it is, launch the vendor activity
        boolean isVendor = false;
        try {
            FileInputStream fis = openFileInput(res.getString(R.string.sign_in_type));
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);

            String line, userCheck, type;
            while ((line = br.readLine()) != null) {
                if (line.charAt(0) == '@') {
                    userCheck = line.split(" ")[0].substring(1);
                    type = line.split(" ")[1];
                    if (username.equals(userCheck) && type == "vendor") {
                        isVendor = true;
                        break;
                    }
                }
            }
        } catch(IOException e) {
            Toast.makeText(this, res.getString(R.string.toast_missing_login_file), Toast.LENGTH_SHORT).show();
        }

        if (isVendor) {
            // TODO: Is this the right class to go to? A bit confused about that
            Intent intent = new Intent(getApplicationContext(), VendorLocations.class);
            intent.putExtras(b); // from above
            startActivity(intent);
            finish();
        }

        // Customer only from here
        // Get the customers default market
        try {
            FileInputStream fis = openFileInput(res.getString(R.string.default_market));
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);

            String line, userCheck;
            while ((line = br.readLine()) != null) {
                if (line.charAt(0) == '@') {
                    userCheck = line.split(" ")[0].substring(1);
                    if (username.equals(userCheck)) {
                        defaultMarket = br.readLine();
                        break;
                    }
                }
            }
        } catch(IOException e) {
            Toast.makeText(this, res.getString(R.string.toast_missing_login_file), Toast.LENGTH_SHORT).show();
        }



        /******** Nav bar items I swear not to touch out of fear **********/
        NavSetup.setupDrawer(this, R.id.drawer_layout, R.id.nav_view, R.id.toolbar);

        Spinner vendorCategorySpinner = findViewById(R.id.categories);

        String[] categories = {"Meats", "Vegetables", "Bakery", "Dairy", "Home-Care"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        vendorCategorySpinner.setAdapter(adapter);

        // Layout stuff I will likely touch
        // code for inserting all stores in market
        LinearLayout storeContainer = findViewById(R.id.storesLayout);
        // Populating store cards
        ListOfStores.populateStores(this, storeContainer);

        marketName.setText(currentMarket);

    }

}