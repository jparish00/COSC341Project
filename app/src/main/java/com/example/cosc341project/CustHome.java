package com.example.cosc341project;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;


public class CustHome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cust_home);
        NavSetup.setupDrawer(this, R.id.drawer_layout, R.id.nav_view, R.id.toolbar);

        Spinner spinner = findViewById(R.id.categories);

        // code for editing categories selector
        ArrayAdapter<String> adapter = SpinnerMenu.createAdapter(this);
        spinner.setAdapter(adapter);

        // code for inserting all stores in market
        LinearLayout storeContainer = findViewById(R.id.storesLayout);
        // Populating store cards
        ListOfStores.populateStores(this, storeContainer);

    }

}