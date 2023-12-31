package com.example.cosc341project;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import androidx.cardview.widget.CardView;

public class ListOfLocations {

    public static void populateLocations(Context context, LinearLayout locationContainer) {
        LayoutInflater inflater = LayoutInflater.from(context);

        for (int i = 0; i < 10; i++) {
            CardView cardView = (CardView) inflater.inflate(R.layout.location_card, locationContainer, false);

            TextView marketName = cardView.findViewById(R.id.marketName);
            TextView marketAddress = cardView.findViewById(R.id.marketAddress);
            Switch marketStatus = cardView.findViewById(R.id.toggleSwitch);

            marketName.setText(marketName.getText().toString());
            marketAddress.setText(marketAddress.getText().toString());

            locationContainer.addView(cardView);
        }
    }
}
