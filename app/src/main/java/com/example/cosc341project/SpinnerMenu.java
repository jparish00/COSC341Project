package com.example.cosc341project;

import android.content.Context;
import android.widget.ArrayAdapter;

public class SpinnerMenu {

    public static ArrayAdapter<String> createAdapter(Context context) {
        String[] categories = new String[] { "Category 1", "Category 2", "Category 3", "Category 4", "Category 5" };

        return new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, categories);
    }
}