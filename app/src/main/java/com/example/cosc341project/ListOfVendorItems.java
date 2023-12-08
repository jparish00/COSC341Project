package com.example.cosc341project;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.cardview.widget.CardView;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class ListOfVendorItems {

    static ArrayList<String> items;
    static ArrayList<String> prices;
    static CardView modifyCardView;
    static Context modContext;


    public static void populateItems(Context context, LinearLayout itemsContainer) {
        LayoutInflater inflater = LayoutInflater.from(context);
        Resources res = context.getResources();

        items = new ArrayList<>();
        prices = new ArrayList<>();


        String username = CustHome.username;
        try {
            FileInputStream fis = context.openFileInput(res.getString(R.string.vendor_data));
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

                ImageView deleteButton = cardView.findViewById(R.id.delete_icon);

                TextView itemName = cardView.findViewById(R.id.item_name);
                TextView itemPrice = cardView.findViewById(R.id.item_price);

                itemName.setText(items.get(i));
                itemPrice.setText("$" + prices.get(i));

                // Modify
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

                deleteButton.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        int index = itemsContainer.indexOfChild(view); // Gets cardview id
                        CardView cardView = (CardView)itemsContainer.getChildAt(index);

                        items.remove(index);
                        prices.remove(index);

                        itemsContainer.removeView(view.getRootView());
                        return true;
                    }
                });

                itemsContainer.addView(cardView);
            }


            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void addItem(Context context, String name, String price, LinearLayout itemsContainer) throws FileNotFoundException {

        items.add(name);
        prices.add(price);

        LayoutInflater inflater = LayoutInflater.from(context);
        CardView cardView = (CardView) inflater.inflate(R.layout.items_vendor_card, itemsContainer, false);

        TextView itemName = cardView.findViewById(R.id.item_name);
        TextView itemPrice = cardView.findViewById(R.id.item_price);

        itemName.setText(items.get(items.size()-1));
        itemPrice.setText("$" + prices.get(prices.size()-1));

        // Modify
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
        saveVendorData(context);

    }

    public static void saveVendorData(Context context) {
        Resources res = context.getResources();
        int count = 0, itemLocation = -1;
        ArrayList<String> lines = new ArrayList<>();
        try (FileInputStream fis = context.openFileInput(res.getString(R.string.vendor_data));
             InputStreamReader isr = new InputStreamReader(fis);
             BufferedReader br = new BufferedReader(isr)) {

            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
                if (line.startsWith("@")) {
                    if (line.substring(1).split(" ")[0].equals(CustHome.username)) {
                        itemLocation = count += 7;
                    }
                }
                count++;
            }

        } catch(Exception e) {
            e.printStackTrace();
        }

        // modify lines
        String itemLine = "";
        for (int i = 0; i < items.size(); i++) {
            itemLine += items.get(i) + ":" + prices.get(i);
            if (i != items.size()-1)
                itemLine += "/";
        }
        lines.set(itemLocation, itemLine);


        FileWriter fw;
        File f = new File(context.getApplicationContext().getFilesDir(), res.getString(R.string.vendor_data));
        try {
            fw = new FileWriter(f, false);
            for (int i = 0; i < lines.size(); i++)
                fw.write(lines.get(i) + "\n");
            fw.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
