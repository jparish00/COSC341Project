package com.example.cosc341project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.LinearLayout;

public class VendorLocations extends AppCompatActivity {


    // spinner drop down options should be location list and Items list
    // took off search bar because vendors only edit their own store in the vendor side view
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_locations);
        String userType = getIntent().getStringExtra("userType");
        NavSetup.setupDrawer(this, R.id.drawer_layout, R.id.nav_view, R.id.toolbar, userType);

        // code for inserting all stores in market
        LinearLayout locationLayout = findViewById(R.id.locationLayout);
        // Populating store cards
        ListOfLocations.populateLocations(this, locationLayout);
    }
}