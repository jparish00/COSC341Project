package com.example.cosc341project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.LinearLayout;

public class VendorItems extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_items);
        String userType = getIntent().getStringExtra("userType");
        NavSetup.setupDrawer(this, R.id.drawer_layout, R.id.nav_view, R.id.toolbar, userType);

        // code for inserting all stores in market
        LinearLayout itemsLayout = findViewById(R.id.itemsLayout);
        // Populating store cards
        ListOfVendorItems.populateItems(this, itemsLayout);
    }
}