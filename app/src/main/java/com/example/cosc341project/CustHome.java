package com.example.cosc341project;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;


public class CustHome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cust_home);
        NavSetup.setupDrawer(this, R.id.drawer_layout, R.id.nav_view, R.id.toolbar);
        

    }

}