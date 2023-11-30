package com.example.cosc341project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

public class Reviews extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviews);

        //TODO add text views programmatically to add new one for each review. get count of total reviews and calculate average to display


    }

    public void addReview(View view){

        //TODO set intent and send vendor ID to the add review page to leave review

    }

    public void back(View view){
        //TODO implement back button functionality
    }
}