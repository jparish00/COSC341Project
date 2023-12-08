package com.example.cosc341project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.app.Dialog;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.FileNotFoundException;

public class VendorItems extends AppCompatActivity {
    LinearLayout itemsLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_items);
        TextView vendorName = findViewById(R.id.vendorName);
        vendorName.setText(CustHome.vendorName);

                String userType = getIntent().getStringExtra("userType");
        NavSetup.setupDrawer(this, R.id.drawer_layout, R.id.nav_view, R.id.toolbar, userType);

        itemsLayout = findViewById(R.id.itemsLayout);
        ListOfVendorItems.populateItems(this, itemsLayout); // Repopulate with updated data

        Button addItemBtn = findViewById(R.id.addItemBtn);
        addItemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });
    }


    private void showDialog() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.add_item_vendor);
        int width = (int)(getResources().getDisplayMetrics().widthPixels * 0.90);
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        dialog.show();

        dialog.getWindow().setLayout(width, height);
        EditText itemNameEditText = dialog.findViewById(R.id.addItemName);
        EditText itemPriceEditText = dialog.findViewById(R.id.addItemPrice);

        Button addButton = dialog.findViewById(R.id.addButton);
        Button cancelButton = dialog.findViewById(R.id.cancelButton);


        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String itemName = itemNameEditText.getText().toString();
                String itemPrice = itemPriceEditText.getText().toString();

                try {
                    ListOfVendorItems.addItem( VendorItems.this, itemName, itemPrice, itemsLayout);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
                dialog.dismiss();
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }

}