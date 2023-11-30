package com.example.cosc341project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

public class AddReview extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_review);
    }

    public void submitReview(View view){
        //TODO save review text and rating. increment count for number of reviews vendor has to compute average
    }

    public void back(View view){
        //TODO implement back button functionality
    }
}