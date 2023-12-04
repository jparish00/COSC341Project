package com.example.cosc341project;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.cardview.widget.CardView;

public class ListOfStores {

    public static void populateStores(Context context, LinearLayout storeContainer) {
        LayoutInflater inflater = LayoutInflater.from(context);

        for (int i = 0; i < 10; i++) {
            // Inflate or create your custom card view layout
            CardView cardView = (CardView) inflater.inflate(R.layout.store_card, storeContainer, false);

            // Customize card view
            TextView storeTitle = cardView.findViewById(R.id.store_title);
            RatingBar storeRating = cardView.findViewById(R.id.store_rating);
            TextView storeDesc = cardView.findViewById(R.id.store_desc);

            storeTitle.setText("Store " + (i + 1));
            storeRating.setRating(i % 5 + 1); // Just for example, setting a rating
            storeDesc.setText("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Ut hendrerit, diam in fermentum consequat, leo libero cursus purus, in ullamcorper.");

            // Set an OnClickListener for each CardView
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, StoreItems.class);
                    context.startActivity(intent);
                }
            });


            // Add the card view to your container
            storeContainer.addView(cardView);
        }
    }
}
