package com.example.cosc341project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class StoreItems extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_items);
        NavSetup.setupDrawer(this, R.id.drawer_layout, R.id.nav_view, R.id.toolbar);

        // Code for inserting all items in market
        LinearLayout itemsContainer = findViewById(R.id.itemsLayout);
        // Populating store cards
        ListOfItems.populateItems(this, itemsContainer);


        // Go Back button
        ImageButton goBackBtn = findViewById(R.id.gobackbtn);
        goBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to start the CustHome activity
                Intent intent = new Intent(StoreItems.this, CustHome.class);
                startActivity(intent);
            }
        });
    }
}