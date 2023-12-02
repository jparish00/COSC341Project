package com.example.cosc341project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class AddReview extends AppCompatActivity {

    int review_count;
    TextView vendorName;
    RadioGroup review_rating;
    RadioButton s1,s2,s3,s4,s5;
    EditText leave_review;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_review);

    }

    public void submitReview(View view){
        //TODO save review text and rating. increment count for number of reviews vendor has to compute average
    }

    public void back(View view){
        finish();
    }
}