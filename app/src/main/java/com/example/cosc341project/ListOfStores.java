package com.example.cosc341project;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.Spinner;
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
    static ArrayList<String> vendorCategory;

    public static void populateStores(Context context, LinearLayout storeContainer, Spinner categorySpinner) {
        LayoutInflater inflater = LayoutInflater.from(context);
        Resources res = context.getResources();

        // Temporary data structures
        ArrayList<String> names = new ArrayList<>();
        ArrayList<Float> ratings = new ArrayList<>();
        ArrayList<String> desc = new ArrayList<>();
        ArrayList<String> address = new ArrayList<>();
        ArrayList<String> website = new ArrayList<>();
        ArrayList<String> categoryItem = new ArrayList<>();

        // Needed for indexing when a store is clicked
        vendorIds = new ArrayList<>();

        // Get the selected category from the spinner
        String selectedCategory = categorySpinner.getSelectedItem().toString();
        System.out.println(selectedCategory);

        // Open and get information from market_info
        try {
            FileInputStream fis = context.openFileInput(res.getString(R.string.vendor_data));
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);

            String line, marketCheck;
            int numStores = 0;

            while ((line = br.readLine()) != null) {
                System.out.println(line);

                if (line.charAt(0) == '@') {
                    marketCheck = line.split(" ")[0].substring(1);
                    System.out.print(marketCheck);
                    if(marketCheck.equals(CustHome.defaultMarket)) {
                        CustHome.currentMarket = marketCheck;
                        numStores = Integer.valueOf(line.split(" ")[1]);
                        break;
                    }
                }
            }

            for (int i = 0; i < numStores; i++) {
                System.out.println(br.readLine());
                String name = br.readLine();
                Float rating = Float.valueOf(br.readLine());
                String description = br.readLine();
                String addressLine = br.readLine();
                String web = br.readLine();
                String category = br.readLine();
                String itemsInStore = br.readLine(); // just to skip

               if(selectedCategory.equals("All")) {
                    names.add(name);
                    ratings.add(rating);
                    desc.add(description);
                    address.add(addressLine);
                    website.add(web);
                    categoryItem.add(category);
                } else if (category.equals(selectedCategory)) {
                       names.add(name);
                       ratings.add(rating);
                       desc.add(description);
                       address.add(addressLine);
                       website.add(web);
                       categoryItem.add(category);
                   }
            }
        } catch(IOException e) {
            e.printStackTrace();
        }

        System.out.println(names.toString());
        System.out.println(desc.toString());
        System.out.println(address.toString());
        System.out.println(website.toString());
        System.out.println(categoryItem.toString());


        // Store names away for indexing when a store is clicked
        vendorNames = names;
        vendorRatings = ratings;
        vendorCategory = categoryItem;

        // Clear the existing views in the container
        storeContainer.removeAllViews();

        // Load into cardview
        for (int i = 0; i < names.size(); i++) {
            // Inflate or create your custom card view layout
            CardView cardView = (CardView) inflater.inflate(R.layout.store_card, storeContainer, false);

            // Customize card view
            TextView storeTitle = cardView.findViewById(R.id.store_title);
            RatingBar storeRating = cardView.findViewById(R.id.store_rating);
            TextView storeDesc = cardView.findViewById(R.id.message_text);
            TextView storeAddress = cardView.findViewById(R.id.addressText);

            storeTitle.setText(names.get(i));
            storeRating.setRating(ratings.get(i));
            storeDesc.setText(desc.get(i));
            storeAddress.setText(address.get(i));
            vendorIds.add(cardView.getId());

            // Set an OnClickListener for each CardView
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle b = new Bundle();

                    int index = storeContainer.indexOfChild(v);

                    String vendorName = vendorNames.get(index);
                    Float vendorRating = vendorRatings.get(index);

                    b.putString("username", CustHome.username);
                    b.putString("vendor_name", vendorName);
                    b.putFloat("vendor_rating", vendorRating);
                    b.putString("market_name", CustHome.currentMarket);
                    b.putString("account_type", CustHome.userType);
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
