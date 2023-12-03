package com.example.cosc341project;

import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
    String venName, custName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_review);

        Intent intent = getIntent();

        leave_review.findViewById(R.id.add_review_input);
        leave_review.setHint(R.string.review_box_hint);
        review_rating.findViewById(R.id.reviewStar);
        s1.findViewById(R.id.oneStar);
        s2.findViewById(R.id.twoStar);
        s3.findViewById(R.id.threeStar);
        s4.findViewById(R.id.fourStar);
        s5.findViewById(R.id.fiveStar);

        s1.setText(R.string.oneStar);
        s2.setText(R.string.twoStar);
        s3.setText(R.string.threeStar);
        s4.setText(R.string.fourStar);
        s5.setText(R.string.fiveStar);

    }

    public void submitReview(View view){
        //TODO save review text and rating. increment count for number of reviews vendor has to compute average

    }

    public void back(View view){
        finish();
    }
}