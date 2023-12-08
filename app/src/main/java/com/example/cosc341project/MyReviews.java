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
        String userType = getIntent().getStringExtra("userType");
        NavSetup.setupDrawer(this, R.id.drawer_layout, R.id.nav_view, R.id.toolbar, userType);

        LinearLayout reviewsContainer = findViewById(R.id.reviewsLayout);
        MyReviewCards(this, reviewsContainer);
    }



    public static void MyReviewCards(Context context, LinearLayout reviewsContainer) {
        LayoutInflater inflater = LayoutInflater.from(context);

        for (int i = 0; i < 10; i++) {
            CardView cardView = (CardView) inflater.inflate(R.layout.review_preview_card, reviewsContainer, false);

            TextView storeTitle = cardView.findViewById(R.id.review_title);
            RatingBar storeRating = cardView.findViewById(R.id.review_rating);

            storeTitle.setText("Review " + (i + 1));
            storeRating.setRating(i % 5 + 1); // Just for example, setting a rating

            reviewsContainer.addView(cardView);
        }
    }


}