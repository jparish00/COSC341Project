package com.example.cosc341project;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
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

public class ListOfCustomers {

    static ArrayList<String> customers;
    static ArrayList<String> prices;
    public static void populateCust(Context context, LinearLayout itemsContainer) {
        LayoutInflater inflater = LayoutInflater.from(context);

        Resources res = context.getResources();

        customers = new ArrayList<>();

        // Pull the vendor store information from vendor_info.txt
        String products = "";
        try {
            FileInputStream fis = context.openFileInput(res.getString(R.string.inbox_data));
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);

            String line, vendorCheck, custCheck;
            while ((line = br.readLine()) != null) {
                if (line.charAt(0) == '@') {
                    vendorCheck = line.substring(1).split("/")[0];
                    custCheck = line.substring(1).split("/")[1];
                    if (vendorCheck.equals(CustHome.vendorName)) { // vendor found
                        customers.add(custCheck);
                    }
                }
            }

        } catch(IOException e) {
            e.printStackTrace();
        }

        // Organize the information

        for (int i = 0; i < customers.size(); i++) {
            // Inflate or create your custom card view layout
            CardView cardView = (CardView) inflater.inflate(R.layout.customer_card, itemsContainer, false);

            // Customize card view
            TextView customerTitle = cardView.findViewById(R.id.customer_name);

            customerTitle.setText(customers.get(i));

            // Set an OnClickListener for each CardView
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int index = itemsContainer.indexOfChild(v);

                    String customer = customers.get(index);
                    System.out.println("Cust: " + customer);

                    Intent intent = new Intent(context.getApplicationContext(), Inbox.class);
                    Bundle b = new Bundle();
                    b.putString("username", customer);
                    b.putString("vendor_name", CustHome.vendorName);
                    b.putString("account_type", "vendor");
                    intent.putExtras(b);
                    context.startActivity(intent);
                }
            });

            // Add the card view to your container
            itemsContainer.addView(cardView);
        }
    }

}
