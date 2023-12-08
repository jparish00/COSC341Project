package com.example.cosc341project;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.cardview.widget.CardView;

public class ListOfMarkets {

    public static void populateMarkets(Context context, LinearLayout marketContainer) {
        LayoutInflater inflater = LayoutInflater.from(context);

        for (int i = 0; i < 10; i++) {
            CardView cardView = (CardView) inflater.inflate(R.layout.market_card, marketContainer, false);

            TextView marketName = cardView.findViewById(R.id.market_name);
            TextView marketAddress = cardView.findViewById(R.id.market_address);
            RatingBar marketRating = cardView.findViewById(R.id.market_rating);

            marketName.setText("Market " + (i + 1));
            marketRating.setRating(i % 5 + 1); // Just for example, setting a rating
            marketAddress.setText("Market Address");


            marketContainer.addView(cardView);
        }
    }
}
