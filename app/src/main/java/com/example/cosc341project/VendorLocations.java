package com.example.cosc341project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

public class VendorLocations extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_locations);
        String userType = getIntent().getStringExtra("userType");
        TextView vendorName = findViewById(R.id.vendorName);
        vendorName.setText(CustHome.vendorName);
        NavSetup.setupDrawer(this, R.id.drawer_layout, R.id.nav_view, R.id.toolbar, userType);

        LinearLayout locationLayout = findViewById(R.id.locationLayout);
        ListOfLocations.populateLocations(this, locationLayout);
    }
}