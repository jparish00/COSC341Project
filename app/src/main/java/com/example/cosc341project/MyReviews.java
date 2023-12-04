package com.example.cosc341project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

public class MyReviews extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_reviews);
        NavSetup.setupDrawer(this, R.id.drawer_layout, R.id.nav_view, R.id.toolbar);

        // code for inserting all stores in market
        LinearLayout reviewsContainer = findViewById(R.id.reviewsLayout);
        // Populating store cards
        MyReviewCards(this, reviewsContainer);
    }



    public static void MyReviewCards(Context context, LinearLayout reviewsContainer) {
        LayoutInflater inflater = LayoutInflater.from(context);

        for (int i = 0; i < 10; i++) {
            // Inflate or create your custom card view layout
            CardView cardView = (CardView) inflater.inflate(R.layout.review_preview_card, reviewsContainer, false);

            // Customize card view
            TextView storeTitle = cardView.findViewById(R.id.review_title);
            RatingBar storeRating = cardView.findViewById(R.id.review_rating);

            storeTitle.setText("Review " + (i + 1));
            storeRating.setRating(i % 5 + 1); // Just for example, setting a rating

            // Add the card view to your container
            reviewsContainer.addView(cardView);
        }
    }


}