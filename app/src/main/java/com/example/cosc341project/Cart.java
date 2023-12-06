package com.example.cosc341project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Cart extends AppCompatActivity {

    // Views
    static TextView price;
    Button backButton, requestItems;

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

        // UNCOMMENT ONCE XML IS CREATED
        //backButton = findViewById(R.id.back_button);
        //requestItems = findViewById(R.id.request_button);
        //price = findViewById(R.id.price_text);

        backButton.setOnClickListener(v -> { finish(); } );
        requestItems.setOnClickListener(this::onRequestClick);
        price.setText("0.00"); // Default, in case cart is empty
        totalPrice = "";
        totalPriceValue = 0.0f;


        // Setup
//        // Code for inserting all items in cart
//        LinearLayout itemsContainer = findViewById(R.id.cartLayout);
        // Populating store cards
//        ListOfCartItems.populateCartItems(this, itemsContainer);
    }

    public void onRequestClick(View v) {

        


    }

}