package com.example.cosc341project;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class ListOfStores {

    static ArrayList<String> vendorNames;
    static ArrayList<Integer> vendorIds;
    static ArrayList<Float> vendorRatings;

    public static void populateStores(Context context, LinearLayout storeContainer) {
        LayoutInflater inflater = LayoutInflater.from(context);
        Resources res = context.getResources();

        // Temporary data structures
        ArrayList<String> names = new ArrayList<>();
        ArrayList<Float> ratings = new ArrayList<>();
        ArrayList<String> desc = new ArrayList<>();
        // Needed for indexing when a store is clicked
        vendorIds = new ArrayList<>();


        // Open and get information from market_info
        try {
            FileInputStream fis = context.openFileInput(res.getString(R.string.market_info));
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);

            String line, marketCheck;
            int numStores = 0;

            while ((line = br.readLine()) != null) {
                if (line.charAt(0) == '@') {
                    marketCheck = line.split(" ")[0].substring(1);
                    if(marketCheck.equals(CustHome.defaultMarket)) {
                        CustHome.currentMarket = marketCheck;
                        numStores = Integer.valueOf(line.split(" ")[1]);
                        break;
                    }
                }
            }

            for (int i = 0; i < numStores; i++) {
                    names.add(br.readLine());
                    ratings.add(Float.valueOf(br.readLine()));
                    desc.add(br.readLine());
            }

        } catch(IOException e) {
            e.printStackTrace();
        }

        // Store names away for indexing when a store is clicked
        vendorNames = names;
        vendorRatings = ratings;

        // Load into cardview
        for (int i = 0; i < names.size(); i++) {
            // Inflate or create your custom card view layout
            CardView cardView = (CardView) inflater.inflate(R.layout.store_card, storeContainer, false);

            // Customize card view
            TextView storeTitle = cardView.findViewById(R.id.store_title);
            RatingBar storeRating = cardView.findViewById(R.id.store_rating);
            TextView storeDesc = cardView.findViewById(R.id.message_text);

            storeTitle.setText(names.get(i));
            storeRating.setRating(Float.valueOf(ratings.get(i))); // Just for example, setting a rating
            storeDesc.setText(desc.get(i));
            vendorIds.add(cardView.getId());


            // Set an OnClickListener for each CardView
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle b = new Bundle();

                    int index = storeContainer.indexOfChild(v);

                    String vendorName = "";
                    Float vendorRating = 0.0f;
                    vendorName = vendorNames.get(index);
                    vendorRating = Float.valueOf(vendorRatings.get(index));

                    b.putString("username", CustHome.username);
                    b.putString("vendor_name", vendorName);
                    b.putFloat("vendor_rating", vendorRating);
                    b.putString("market_name", CustHome.currentMarket);
                    b.putString("account_type", CustHome.type);
                    Intent intent = new Intent(context, StoreItems.class);
                    intent.putExtras(b);
                    context.startActivity(intent);
                }
            });


            // Add the card view to your container
            storeContainer.addView(cardView);
        }
    }
}
