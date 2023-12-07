package com.example.cosc341project;

import android.app.Activity;
import android.content.Context;
import android.content.SyncStats;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class ListOfCartItems {

    static ArrayList<Integer> viewIds;
    static ArrayList<Integer> buttonIds;
    static ArrayList<String> vendors;
    static ArrayList<String> items;
    static ArrayList<String> prices;

    public static void populateCartItems(Context context, LinearLayout itemsContainer) {
        LayoutInflater inflater = LayoutInflater.from(context);

        Resources res = context.getResources();

        viewIds = new ArrayList<>();
        buttonIds = new ArrayList<>();
        vendors = new ArrayList<>();
        items = new ArrayList<>();
        prices = new ArrayList<>();

        String line, userCheck;
        try {
            FileInputStream fis = context.openFileInput(res.getString(R.string.cart_info));
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);

            boolean cartFound = false;
            while ((line = br.readLine()) != null) {
                if (line.charAt(0) == '@') {
                    userCheck = line.substring(1).split("/")[0];
                    if (userCheck.equals(Cart.username)) {
                        cartFound = true;
                        System.out.println("HERE: " + line);
                        vendors.add(line.split("/")[1]);
                        items.add(line.split("/")[2].split(":")[0]);
                        prices.add(line.split("/")[2].split(":")[1]);
                    }
                }
            }

            if (!cartFound) {
                Cart.totalPriceValue = 0.0f;
                Cart.totalPrice = "Total: $0.00";
                return;
            }

            } catch(IOException e) {
            e.printStackTrace();
        }


        for (int i = 0; i < vendors.size(); i++) {
            // Inflate or create your custom card view layout
            CardView cardView = (CardView) inflater.inflate(R.layout.cart_card, itemsContainer, false);

            // Customize card view
            TextView itemVendor = cardView.findViewById(R.id.cart_vendor_name);
            TextView itemName = cardView.findViewById(R.id.cart_item_name);
            TextView itemPrice = cardView.findViewById(R.id.cart_price);

            Cart.totalPriceValue += Float.valueOf(prices.get(i));

            itemVendor.setText(vendors.get(i));
            itemName.setText(items.get(i));
            itemPrice.setText("$" + prices.get(i));

            Button deleteButton = cardView.findViewById(R.id.delete_button);

            buttonIds.add(deleteButton.getId());
            viewIds.add(cardView.getId());

            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int id = v.getRootView().getId(); // Hopefully gets cardview id

                    CardView cardView = itemsContainer.findViewById(id);

                    TextView priceView = cardView.findViewById(R.id.cart_price);
                    Float removePrice = Float.valueOf(priceView.getText().toString().substring(1));
                    Cart.totalPriceValue -= removePrice;
                    Cart.totalPrice = String.valueOf(Cart.totalPriceValue);
                    Cart.price.setText("Total: $" + Cart.totalPrice);

                    itemsContainer.removeView(cardView);

                }
            });

            itemsContainer.addView(cardView);
        }

        Cart.totalPrice = "Total: $" + String.valueOf(Cart.totalPriceValue);
        Cart.price.setText(Cart.totalPrice);

    }

}
