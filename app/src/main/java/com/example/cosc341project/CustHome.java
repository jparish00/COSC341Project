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
import android.view.View;
import android.widget.AdapterView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class CustHome extends AppCompatActivity {

    // Views
    TextView marketName;
    // Vars
    Resources res;
    static String username, vendorName, userType;
    static String defaultMarket, currentMarket;
    static String type;
    static LinearLayout storeContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cust_home);

        Spinner vendorCategorySpinner = findViewById(R.id.categories);
        // Set up spinner with categories
        String[] categories = {"All", "Meats", "Clothes", "Gifts", "Drinks", "Toys"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        vendorCategorySpinner.setAdapter(adapter);

        marketName = findViewById(R.id.marketName);

        res = getResources();

        Bundle b = getIntent().getExtras();
        username = b.getString("username");
        userType = "customer";


        // Check if this is a vendor account. If it is, launch the vendor activity
        boolean isVendor = false;

        try {
            FileInputStream fis = openFileInput(res.getString(R.string.sign_in_type));
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);

            String line, userCheck;
            while ((line = br.readLine()) != null) {
                if (line.charAt(0) == '@') {
                    userCheck = line.split(" ")[0].substring(1);
                    type = line.split(" ")[1];
                    if (username.equals(userCheck) && type.equals("vendor")) {
                        isVendor = true;
                        userType ="vendor";
                        break;
                    }
                }
            }
        } catch(IOException e) {
            Toast.makeText(this, res.getString(R.string.toast_missing_login_file), Toast.LENGTH_SHORT).show();
        }


        if (isVendor) {

            // getVendorName
            try {
                FileInputStream fis = openFileInput(res.getString(R.string.vendor_data));
                InputStreamReader isr = new InputStreamReader(fis);
                BufferedReader br = new BufferedReader(isr);

                String line, userCheck;
                while ((line = br.readLine()) != null) {
                    if (line.charAt(0) == '@') {
                        userCheck = line.split(" ")[0].substring(1);
                        type = line.split(" ")[1];
                        if (username.equals(userCheck)) {
                            vendorName = br.readLine();
                            break;
                        }
                    }
                }
            } catch(IOException e) {
                Toast.makeText(this, res.getString(R.string.toast_missing_login_file), Toast.LENGTH_SHORT).show();
            }

            Intent intent = new Intent(getApplicationContext(), VendorLocations.class);
            b.putString("userType", userType);
            b.putString("username",username);
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
                        currentMarket = defaultMarket;
                        break;
                    }
                }
            }
        } catch(IOException e) {
            Toast.makeText(this, res.getString(R.string.toast_missing_login_file), Toast.LENGTH_SHORT).show();
        }


        /******** Nav bar items I swear not to touch out of fear **********/
        NavSetup.setupDrawer(this, R.id.drawer_layout, R.id.nav_view, R.id.toolbar, userType);

        // Container for stores
        storeContainer = findViewById(R.id.storesLayout);

        vendorCategorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // calling populateStores whenever a new category is selected
                ListOfStores.populateStores(CustHome.this, storeContainer, vendorCategorySpinner);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // wont run but just in case
            }
        });


        marketName.setText(currentMarket);

    }

}