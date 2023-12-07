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
    public static void setupDrawer(final Activity activity, int drawerLayoutId, int navViewId, int toolbarId) {
        DrawerLayout drawerLayout = activity.findViewById(drawerLayoutId);
        NavigationView navigationView = activity.findViewById(navViewId);
        Toolbar toolbar = activity.findViewById(toolbarId);

        Menu menu = navigationView.getMenu();

        // Set up the toolbar
        ((AppCompatActivity)activity).setSupportActionBar(toolbar);
        ((AppCompatActivity)activity).getSupportActionBar().setTitle("");

        // Set up the drawer toggle
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                activity, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // Set up the navigation item listener
        navigationView.setNavigationItemSelectedListener(item -> {
            Intent intent = null;
            int id = item.getItemId();

            if (id == R.id.nav_home) {
                intent= new Intent(activity, CustHome.class);
            } else if (id == R.id.nav_all_market) {
                intent= new Intent(activity, ViewMarket.class);
            } else if (id == R.id.nav_account) {
                intent = new Intent(activity, AccountPage.class);
            } else if (id == R.id.nav_security) {
                intent = new Intent(activity, SecurityPage.class);
            } else if (id == R.id.nav_reviews) {
                intent = new Intent(activity, MyReviews.class);
            } else if (id == R.id.nav_logout) {
                intent = new Intent(activity, MainActivity.class);
            } else if (id == R.id.nav_mycart)
                intent = new Intent(activity, Cart.class);

            if (intent != null) {

                if(id == R.id.nav_mycart) {
                    Bundle b = new Bundle();
                    b.putString("username", CustHome.username);
                    intent.putExtras(b);
                }
                activity.startActivity(intent);
            }

            drawerLayout.closeDrawers(); // Close drawer after item selection
            return true;
        });
    }
}
