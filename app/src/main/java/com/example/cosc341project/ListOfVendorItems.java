package com.example.cosc341project;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.cardview.widget.CardView;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class ListOfVendorItems {

    public static void populateItems(Context context, LinearLayout itemsContainer, String userName) {
        LayoutInflater inflater = LayoutInflater.from(context);

        try {
            FileInputStream fis = context.openFileInput("vendor_info.txt");
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            String line;
            boolean isCorrectVendor = false;

            while ((line = br.readLine()) != null) {
                if (line.startsWith("@")) {
                    if (line.substring(1).split(" ")[0].equals(userName)) {
                        isCorrectVendor = true; // Found the correct vendor
                    } else {
                        isCorrectVendor = false; // Reset if it's a different vendor
                    }
                } else if (isCorrectVendor && line.startsWith("@items")) {
                    String[] items = line.substring(7).split("/"); // Process items

                    for (String item : items) {
                        String[] itemDetails = item.split(":");
                        if (itemDetails.length == 2) {
                            String itemName = itemDetails[0];
                            String itemPrice = itemDetails[1];

                            CardView cardView = (CardView) inflater.inflate(R.layout.items_card, itemsContainer, false);
                            TextView itemNameView = cardView.findViewById(R.id.item_name);
                            TextView itemPriceView = cardView.findViewById(R.id.item_price);

                            itemNameView.setText(itemName);
                            itemPriceView.setText("$" + itemPrice);

                            itemsContainer.addView(cardView);
                        }
                    }
                    break;
                }
            }
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
