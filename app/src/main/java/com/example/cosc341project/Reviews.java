package com.example.cosc341project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class Reviews extends AppCompatActivity {

    LinearLayout reviews;

    String venName, custName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviews);


        Intent intent = getIntent();


        //TODO add text views programmatically to add new one for each review. get count of total reviews and calculate average to display
        reviews = new LinearLayout(this);
        setContentView(reviews);
        reviews.setOrientation(LinearLayout.VERTICAL);

    }

    public void addReview(View view){

        Intent intent = new Intent(getApplicationContext(),AddReview.class);
        Bundle bundle = new Bundle();
        bundle.putString("vendor", venName);
        bundle.putString("customer", custName);
        startActivity(intent);

    }

    public void back(View view){
        finish();
    }
}