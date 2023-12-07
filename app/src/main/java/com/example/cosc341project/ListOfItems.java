package com.example.cosc341project;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class ListOfItems {

    static ArrayList<Integer> productIds;
    static ArrayList<String> productNames;
    static ArrayList<String> prices;
    public static void populateItems(Context context, LinearLayout itemsContainer) {
        LayoutInflater inflater = LayoutInflater.from(context);

        Resources res = context.getResources();

        // Pull the vendor store information from vendor_info.txt
        String products = "";
        try {
            FileInputStream fis = context.openFileInput(res.getString(R.string.vendor_data));
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);

            String line;
            while ((line = br.readLine()) != null) {
                if (line.charAt(0) == '@') {
                    line = br.readLine();
                    if (line.equals(StoreItems.vendorName)) { // vendor found
                        br.readLine();
                        br.readLine();
                        br.readLine();
                        products = br.readLine();
                        break;
                    }
                }
            }

        } catch(IOException e) {
            e.printStackTrace();
        }

        // Organize the information
        productNames = new ArrayList<>();
        prices = new ArrayList<>();
        productIds = new ArrayList<>();

        String[] productSplit = products.split("/");

        String[] splitInfo;
        for (int i = 0; i < productSplit.length; i++) {
            splitInfo = productSplit[i].split(":");
            productNames.add(splitInfo[0]);
            prices.add(splitInfo[1]);
        }

        for (int i = 0; i < productNames.size(); i++) {
            // Inflate or create your custom card view layout
            CardView cardView = (CardView) inflater.inflate(R.layout.items_card, itemsContainer, false);

            // Customize card view
            TextView itemtitle = cardView.findViewById(R.id.item_name);
            TextView itemPrice = cardView.findViewById(R.id.message);

            itemtitle.setText(productNames.get(i));
            itemPrice.setText("$"+ prices.get(i)); // Just for example, setting a rating
            productIds.add(cardView.getId());

            // Set an OnClickListener for each CardView
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int index = itemsContainer.indexOfChild(v); // Hopefully gets cardview id

                    String productName = productNames.get(index);
                    String productPrice = prices.get(index);

                    addToCart(context, StoreItems.username, StoreItems.vendorName, productName, productPrice);
                }
            });

            // Add the card view to your container
            itemsContainer.addView(cardView);
        }
    }

    public static void addToCart(Context context, String username, String vendorName, String productName, String productPrice) {

        // Write the information to cart
        // Format @username vendor:item:price
        Resources res = context.getResources();
        FileOutputStream fout;
        try {
            fout = context.openFileOutput(res.getString(R.string.cart_info), Context.MODE_APPEND);
            fout.write(("@" + username + "/" + vendorName + "/"
                + productName + ":" + productPrice + "\n").getBytes());
            fout.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Toast.makeText(context, res.getString(R.string.toast_added_to_cart), Toast.LENGTH_SHORT).show();

    }
}
