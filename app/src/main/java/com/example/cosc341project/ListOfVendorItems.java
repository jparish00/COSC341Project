package com.example.cosc341project;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.cardview.widget.CardView;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class ListOfVendorItems {

    static ArrayList<String> items;
    static ArrayList<String> prices;
    static CardView modifyCardView;

    public static void populateItems(Context context, LinearLayout itemsContainer) {
        LayoutInflater inflater = LayoutInflater.from(context);

        items = new ArrayList<>();
        prices = new ArrayList<>();

        String username = CustHome.username;
        try {
            FileInputStream fis = context.openFileInput("vendor_info.txt");
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);

            String line;

            while ((line = br.readLine()) != null) {
                if (line.startsWith("@")) {
                    if (line.substring(1).split(" ")[0].equals(username)) {
                        br.readLine();
                        br.readLine();
                        br.readLine();
                        br.readLine();
                        line = br.readLine();
                        break;
                    }
                }
            }

            System.out.println(line);
            // Load items
            String[] products = line.split("/");
            for (int i = 0; i < products.length; i++) {
                items.add(products[i].split(":")[0]);
                prices.add(products[i].split(":")[1]);
            }

            // Add to layout
            for (int i = 0; i < items.size(); i++) {
                CardView cardView = (CardView) inflater.inflate(R.layout.items_vendor_card, itemsContainer, false);

                // Customize card view
                TextView itemName = cardView.findViewById(R.id.item_name);
                TextView itemPrice = cardView.findViewById(R.id.item_price);

                itemName.setText(items.get(i));
                itemPrice.setText("$" + prices.get(i));

                cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int index = itemsContainer.indexOfChild(v); // Gets cardview id
                        CardView cardView = (CardView) itemsContainer.getChildAt(index);

                        modifyCardView = cardView;

                        String name = items.get(index);
                        String price = prices.get(index);

                        Intent intent = new Intent(context.getApplicationContext(), ModifyItem.class);
                        Bundle b = new Bundle();
                        b.putInt("index", index);
                        b.putString("name", name);
                        b.putString("price", price);

                        intent.putExtras(b);
                        context.startActivity(intent);
                    }
                });

                itemsContainer.addView(cardView);
            }


            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void addItem(Context context, String name, String price) throws FileNotFoundException {
        int count = 0;

        // Read the existing file content
        try (FileInputStream fis = context.openFileInput("vendor_info.txt");
             InputStreamReader isr = new InputStreamReader(fis);
             BufferedReader br = new BufferedReader(isr)) {

            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith("@")) {
                    if (line.substring(1).split(" ")[0].equals(CustHome.username)) {
                        br.readLine();
                        br.readLine();
                        br.readLine();
                        br.readLine();
                        line = br.readLine();
                        break;
                    }
                }
            }

            System.out.println(line);

            // Write the updated content back to the file
            try (FileOutputStream fos = context.openFileOutput("vendor_info.txt", Context.MODE_PRIVATE);
                 OutputStreamWriter osw = new OutputStreamWriter(fos)) {

                for (String fileLine : ) {
                    osw.write(fileLine + "/" + name + ":" + price);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
