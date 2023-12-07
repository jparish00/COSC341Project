package com.example.cosc341project;

import android.content.Context;
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

        // find Conversation. It there isn't one, make one
        String line, userCheck;
        try {
            FileInputStream fis = context.openFileInput(res.getString(R.string.cart_info));
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);

            boolean cartFound = false;
            while ((line = br.readLine()) != null) {
                if (line.charAt(0) == '@') {
                    userCheck = line.substring(1);
                    if (userCheck.equals(Cart.username)) { // conversation found
                        cartFound = true;
                        break;
                    }
                }
            }

            if (!cartFound) {
                Cart.totalPrice = "0.00";
                return;
            }

            while ((line = br.readLine()) != null) {
                if (line.charAt(0) == '@')
                    break;
                vendors.add(line.split("/")[0]);
                items.add(line.split("/")[1].split(":")[0]);
                prices.add(line.split("/")[1].split(":")[1]);
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
                public void onClick(View view) {
                    CardView cardView = null;
                    for (int i = 0; i < buttonIds.size(); i++) {
                        if(buttonIds.get(i) == view.getId()) {
                            cardView = view.findViewById(viewIds.get(i)); // NOT SURE IF THIS WILL WORK
                            break;
                        }
                    }

                    TextView priceView = cardView.findViewById(R.id.cart_price);
                    Float removePrice = Float.valueOf(priceView.getText().toString().substring(1));
                    Cart.totalPriceValue -= removePrice;
                    Cart.totalPrice = String.valueOf(Cart.totalPriceValue);
                    Cart.price.setText(String.valueOf(Cart.totalPrice));

                    itemsContainer.removeView(cardView);

                }
            });

            itemsContainer.addView(cardView);
        }

        Cart.totalPrice = String.valueOf(Cart.totalPriceValue);
        Cart.price.setText(String.valueOf(Cart.totalPrice));

    }

}
