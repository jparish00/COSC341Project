package com.example.cosc341project;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.cardview.widget.CardView;

import org.w3c.dom.Text;

public class ListOfVendorItems {

    public static void populateItems(Context context, LinearLayout itemsContainer) {
        LayoutInflater inflater = LayoutInflater.from(context);

        for (int i = 0; i < 10; i++) {
            // Inflate or create your custom card view layout
            CardView cardView = (CardView) inflater.inflate(R.layout.items_card, itemsContainer, false);

            // Customize card view
            TextView itemName = cardView.findViewById(R.id.item_name);
            TextView itemPrice = cardView.findViewById(R.id.item_price);

            itemName.setText("item " + (i + 1));
            itemPrice.setText("$0.00");


            // Add the card view to your container
            itemsContainer.addView(cardView);
        }
    }
}
