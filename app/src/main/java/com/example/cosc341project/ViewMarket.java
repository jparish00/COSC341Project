package com.example.cosc341project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.LinearLayout;

public class ViewMarket extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_market);
        String userType = getIntent().getStringExtra("userType");
        String userName = getIntent().getStringExtra("username");
        NavSetup.setupDrawer(this, R.id.drawer_layout, R.id.nav_view, R.id.toolbar, userType, userName);

        // code for inserting all stores in market
        LinearLayout marketContainer = findViewById(R.id.marketContainer);
        // Populating store cards
        ListOfMarkets.populateMarkets(this, marketContainer);
    }
}