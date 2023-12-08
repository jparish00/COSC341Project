package com.example.cosc341project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class VendorInboxes extends AppCompatActivity {


    // Views
    ImageButton backButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_inboxes);

        backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(v -> { finish(); } );

        // Code for inserting all items in market
        LinearLayout itemsContainer = findViewById(R.id.custLayout);
        // Populating store cards
        ListOfCustomers.populateCust(this, itemsContainer);
    }
}