package com.example.cosc341project;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.widget.Toolbar;
import com.google.android.material.navigation.NavigationView;

public class NavSetup {

    static String userType;
    static String username;
    public static void setupDrawer(final Activity activity, int drawerLayoutId, int navViewId, int toolbarId, String userTypeSent) {
        userType = userTypeSent;
        username = CustHome.username;
        Bundle b = new Bundle();

        DrawerLayout drawerLayout = activity.findViewById(drawerLayoutId);
        NavigationView navigationView = activity.findViewById(navViewId);
        Toolbar toolbar = activity.findViewById(toolbarId);

        ((AppCompatActivity) activity).setSupportActionBar(toolbar);
        ((AppCompatActivity) activity).getSupportActionBar().setTitle("");

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                activity, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        Menu menu = navigationView.getMenu();

        if ("vendor".equals(userType)) {
            // Remove the "Cart" menu item for vendors
            menu.removeItem(R.id.nav_mycart);

            // Change "All Markets" to "My Items"
            MenuItem allMarketItem = menu.findItem(R.id.nav_all_market);
            allMarketItem.setTitle("My Items");

        } else if ("customer".equals(userType)) {
            // Optionally remove "My Inbox" if it exists for customer
            menu.removeItem(R.id.nav_my_inbox);
        }


        navigationView.setNavigationItemSelectedListener(item -> {
            Intent intent = null;
            int id = item.getItemId();

            if (id == R.id.nav_home) {
                intent = new Intent(activity.getApplicationContext(), CustHome.class);
            } else if (id == R.id.nav_all_market) {
                if (userType.equals("customer")) {
                    intent = new Intent(activity.getApplicationContext(), ViewMarket.class);
                } else {
                    intent = new Intent(activity.getApplicationContext(), VendorItems.class);
                }
            } else if (id == R.id.nav_account) {
                intent = new Intent(activity.getApplicationContext(), AccountPage.class);
            } else if (id == R.id.nav_security) {
                intent = new Intent(activity.getApplicationContext(), SecurityPage.class);
            } else if (id == R.id.nav_reviews) {
                intent = new Intent(activity.getApplicationContext(), MyReviews.class);
            } else if (id == R.id.nav_logout) {
                intent = new Intent(activity.getApplicationContext(), MainActivity.class);
            } else if (id == R.id.nav_mycart) {
                intent = new Intent(activity.getApplicationContext(), Cart.class);
            } else if (id == R.id.nav_my_inbox)
                intent = new Intent(activity.getApplicationContext(), VendorInboxes.class);

            if (intent != null) {
                b.putString("userType", userType);
                b.putString("username", username);

                intent.putExtras(b);

                activity.startActivity(intent);
            }

            drawerLayout.closeDrawers();
            return true;
        });
    }
}
