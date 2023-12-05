package com.example.cosc341project;

import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class AddReview extends AppCompatActivity {

    int review_count, review;
    TextView vendorName;
    RadioGroup review_rating;
    RadioButton s1,s2,s3,s4,s5, rb;
    EditText leave_review;
    String venName, custName, rv, reviewtext;

    Resources res;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_review);

        res = getResources();
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

    // See Reviews comment
//    public void addReview(Context context, String vendorUsername, String custUsername, int reviewRating, String reviewText) {
//        // Create an instance of the DatabaseHelper class
//        DatabaseHelper dbHelper = new DatabaseHelper(context);
//        // Get a writable database
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//
//        // Create a ContentValues object to store the review data
//        ContentValues values = new ContentValues();
//        values.put(DatabaseHelper.COLUMN_VENDOR_USERNAME, vendorUsername);
//        values.put(DatabaseHelper.COLUMN_CUST_USERNAME, custUsername);
//        values.put(DatabaseHelper.COLUMN_REVIEW_RATING, reviewRating);
//        values.put(DatabaseHelper.COLUMN_REVIEW_TEXT, reviewText);
//
//        // Insert the review data into the database
//        db.insert(DatabaseHelper.TABLE_NAME, null, values);
//
//        // Close the database connection
//        db.close();
//    }

    public void submitReview(View view){
        //TODO save review text and rating. increment count for number of reviews vendor has to compute average

        int selectedID = review_rating.getCheckedRadioButtonId();
        if(selectedID==-1){
           Toast.makeText(this, res.getString(R.string.invalid_review), Toast.LENGTH_SHORT);
        }
        else
            rb.findViewById(selectedID);

        rv = rb.getText().toString();
        review = Integer.parseInt(rv);

        reviewtext = leave_review.getText().toString();
        review_count++;

        //todo store review rating, count, reviewtext, custname under vendor name in text.




    }

    public void back(View view){
        finish();
    }
}