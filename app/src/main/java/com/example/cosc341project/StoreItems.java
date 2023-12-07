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
    Button reviewsButton, inboxButton;

    // Vars
    static String vendorName, marketName, username;
    static Float vendorRating;
    static String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_items);

        vendorTitle = findViewById(R.id.vendorTitleText);
        marketTitle = findViewById(R.id.marketName);
        ratingBar = findViewById(R.id.ratingBar);
        reviewsButton = findViewById(R.id.writeReviewBtn);
        inboxButton = findViewById(R.id.inboxBtn);

        Resources res = getResources();

        // Pull vendor name from bundle, store statically so we can access it from ListOfItems
        Bundle b = getIntent().getExtras();
        username = b.getString("username");
        marketName = b.getString("market_name");
        vendorName = b.getString("vendor_name");
        vendorRating = b.getFloat("vendor_rating");
        type = b.getString("account_type");

        marketTitle.setText(marketName);
        vendorTitle.setText(vendorName);
        ratingBar.setRating(vendorRating);

        NavSetup.setupDrawer(this, R.id.drawer_layout, R.id.nav_view, R.id.toolbar);

        // Code for inserting all items in market
        LinearLayout itemsContainer = findViewById(R.id.itemsLayout);
        // Populating store cards
        ListOfItems.populateItems(this, itemsContainer);

        // Go Back button
        ImageButton goBackBtn = findViewById(R.id.gobackbtn);

        reviewsButton.setOnClickListener(this::viewReview);
        inboxButton.setOnClickListener(this::onClickInbox);

        goBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to start the CustHome activity
//                Intent intent = new Intent(StoreItems.this, CustHome.class);
//                startActivity(intent);
                finish();
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

    public void onClickInbox(View v) {
        Intent intent = new Intent(getApplicationContext(), Inbox.class);
        Bundle bundle = new Bundle();

        bundle.putString("vendor_name", vendorName);
        bundle.putString("username", username);
        bundle.putString("account_type", type);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}