package com.example.cosc341project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Cart extends AppCompatActivity {

    // Views
    static TextView price;
    ImageButton backButton;
    Button requestItems;
    Resources res;

    // Var
    static String username;
    static String totalPrice;
    static float totalPriceValue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        Bundle b = getIntent().getExtras();
        username = b.getString("username"); // all we need

        res = getResources();

        backButton = findViewById(R.id.gobackbtn);
        requestItems = findViewById(R.id.request_button);
        price = findViewById(R.id.price);

        backButton.setOnClickListener(this::onBackClick);
        requestItems.setOnClickListener(this::onRequestClick);
        price.setText("Total: $0.00"); // Default, in case cart is empty
        totalPrice = "";
        totalPriceValue = 0.0f;

        // Setup
        // Code for inserting all items in cart
        LinearLayout itemsContainer = findViewById(R.id.cartLayout);
         //Populating store cards
        ListOfCartItems.populateCartItems(this, itemsContainer);
    }

    public void onRequestClick(View v) {

        // Do the same thing as onBackClick, but don't include stored lines

        // Copy all information, except for username(since we may have removed some
        ArrayList<String> lines = new ArrayList<>();
        try {
            FileInputStream fis = openFileInput(res.getString(R.string.cart_info));
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);

            String line, userCheck;
            while ((line = br.readLine()) != null) {
                if (line.charAt(0) == '@') {
                    userCheck = line.substring(1).split("/")[0];
                    if (!userCheck.equals(Cart.username)) {
                        lines.add(line);
                    }
                }
            }

        } catch(IOException e) {
            e.printStackTrace();
        }

        // Lines now has everything. Overwrite cart_info
        FileWriter fw;
        File f = new File(getApplicationContext().getFilesDir(), res.getString(R.string.cart_info));
        try {
            fw = new FileWriter(f, false);
            for (int i = 0; i < lines.size(); i++)
                fw.write(lines.get(i) + "\n");
            fw.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        // Now, add the messages to inbox
        // Extremely inefficient, but no time
        for (int i = 0; i < ListOfCartItems.vendors.size(); i++) {

            try {
                FileInputStream fis = openFileInput(res.getString(R.string.inbox_data));
                InputStreamReader isr = new InputStreamReader(fis);
                BufferedReader br = new BufferedReader(isr);

                String line, userCheck, vendorCheck;
                while ((line = br.readLine()) != null) {
                    System.out.println("LINE: " + line);
                    if (line.charAt(0) == '@') {
                        vendorCheck = line.substring(1).split("/")[0];
                        userCheck = line.substring(1).split("/")[1];
                        if (userCheck.equals(Cart.username) && vendorCheck.equals(ListOfCartItems.vendors.get(i))) {
                            lines.add(line);
                            line = br.readLine();
                            if(line.equals("none"))
                                lines.add(username + ":" + "Requesting " + ListOfCartItems.items.get(i)
                                        + " at $" + ListOfCartItems.prices.get(i));
                            else

                                lines.add(line + "/" + username + ":" + "Requesting " + ListOfCartItems.items.get(i)
                                    + " at $" + ListOfCartItems.prices.get(i));
                        } else {
                            lines.add(line);
                            lines.add(br.readLine());
                        }
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            f = new File(getApplicationContext().getFilesDir(), res.getString(R.string.inbox_data));
            try {
                fw = new FileWriter(f, false);
                for (int j = 0; j < lines.size(); j++)
                    fw.write(lines.get(j) + "\n");
                fw.close();

            } catch (Exception e) {
                e.printStackTrace();
            }


        }

        Toast.makeText(this, res.getString(R.string.toast_request_sent), Toast.LENGTH_SHORT).show();

        finish();


    }

    public void onBackClick(View v) {

        // Copy all information, except for username(since we may have removed some
        ArrayList<String> lines = new ArrayList<>();
        try {
            FileInputStream fis = openFileInput(res.getString(R.string.cart_info));
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);

            String line, userCheck;
            while ((line = br.readLine()) != null) {
                if (line.charAt(0) == '@') {
                    userCheck = line.substring(1).split("/")[0];
                    if (!userCheck.equals(Cart.username)) {
                        lines.add(line);
                    }
                }
            }

        } catch(IOException e) {
            e.printStackTrace();
        }

        // Add remaining lines
        for (int i = 0; i < ListOfCartItems.vendors.size(); i++) {
            lines.add("@" + username + "/" + ListOfCartItems.vendors.get(i) + "/"
                    + ListOfCartItems.items.get(i) + ":" + ListOfCartItems.prices.get(i));
        }

        // Lines now has everything. Overwrite cart_info
        FileWriter fw;
        File f = new File(getApplicationContext().getFilesDir(), res.getString(R.string.cart_info));
        try {
            fw = new FileWriter(f, false);
            for (int i = 0; i < lines.size(); i++)
                fw.write(lines.get(i) + "\n");
            fw.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        finish();
    }

}