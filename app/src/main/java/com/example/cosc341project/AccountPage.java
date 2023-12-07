package com.example.cosc341project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class AccountPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_page);

        String userType = getIntent().getStringExtra("userType");
        String userName = getIntent().getStringExtra("username");
        NavSetup.setupDrawer(this, R.id.drawer_layout, R.id.nav_view, R.id.toolbar, userType, userName);

    }
}