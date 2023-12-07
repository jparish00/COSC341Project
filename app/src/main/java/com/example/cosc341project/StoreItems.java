package com.example.cosc341project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

public class StoreItems extends AppCompatActivity {

    // Views
    TextView marketTitle, vendorTitle;
    RatingBar ratingBar;
    Button reviewBtn;
    ImageButton goBackBtn;

    // Vars
    static String vendorName, marketName, username;
    static Float vendorRating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_items);

        vendorTitle = findViewById(R.id.vendorTitleText);
        marketTitle = findViewById(R.id.marketName);
        ratingBar = findViewById(R.id.ratingBar);
        reviewBtn = findViewById(R.id.writeReviewBtn);
        goBackBtn = findViewById(R.id.gobackbtn);

        Resources res = getResources();

        // Pull vendor name from bundle, store statically so we can access it from ListOfItems
        Bundle b = getIntent().getExtras();
        username = b.getString("username");
        marketName = b.getString("market_name");
        vendorName = b.getString("vendor_name");
        vendorRating = b.getFloat("vendor_rating");

        marketTitle.setText(marketName);
        vendorTitle.setText(vendorName);
        ratingBar.setRating(vendorRating);

        NavSetup.setupDrawer(this, R.id.drawer_layout, R.id.nav_view, R.id.toolbar, getIntent().getStringExtra("userType"));

        // Code for inserting all items in market
        LinearLayout itemsContainer = findViewById(R.id.itemsLayout);
        // Populating store cards
        ListOfItems.populateItems(this, itemsContainer);


        goBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        reviewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewReview(v);
            }
        });
    }

    public void viewReview(View view){
        Intent intent = new Intent(getApplicationContext(), Reviews.class);
        Bundle bundle = new Bundle();

        bundle.putString("vendorName", vendorName);
        bundle.putString("customerName", username);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}