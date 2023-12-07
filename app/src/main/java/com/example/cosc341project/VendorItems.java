package com.example.cosc341project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.app.Dialog;
import android.view.View;
import android.widget.Button;

public class VendorItems extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_items);
        String userType = getIntent().getStringExtra("userType");
        NavSetup.setupDrawer(this, R.id.drawer_layout, R.id.nav_view, R.id.toolbar, userType);

        LinearLayout itemsLayout = findViewById(R.id.itemsLayout);
        ListOfVendorItems.populateItems(this, itemsLayout);

        Button addItemBtn = findViewById(R.id.addItemBtn);
        addItemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });
    }

    private void showDialog() {
        // Create a Dialog instance
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.add_item_vendor);
        
        //ListOfVendorItems.addItem();

        dialog.show();
    }
}