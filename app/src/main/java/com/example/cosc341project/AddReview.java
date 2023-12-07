package com.example.cosc341project;

import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class AddReview extends AppCompatActivity {

    int  review;
    TextView vendorName;
    RadioGroup review_rating;
    RadioButton s1,s2,s3,s4,s5, rb;
    EditText leave_review;
    String venName, custName, rv, reviewtext;
    Button submitButton, backButton;
    Resources res;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_review);

        submitButton = findViewById(R.id.submitReview);
        backButton = findViewById(R.id.review_button_back);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitReview(v);
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back(v);
            }
        });

        res = getResources();
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        venName = bundle.getString("vendorName");
        custName = bundle.getString("customerName");

        vendorName = findViewById(R.id.vendorName);
        vendorName.setText(venName);

        leave_review = findViewById(R.id.add_review_input);
        review_rating =findViewById(R.id.reviewStar);
        s1 = findViewById(R.id.oneStar);
        s2 = findViewById(R.id.twoStar);
        s3 = findViewById(R.id.threeStar);
        s4 = findViewById(R.id.fourStar);
        s5 = findViewById(R.id.fiveStar);

        s1.setText(R.string.oneStar);
        s2.setText(R.string.twoStar);
        s3.setText(R.string.threeStar);
        s4.setText(R.string.fourStar);
        s5.setText(R.string.fiveStar);

    }

    // See Reviews comment
    public void addReview(Context context, String vendorUsername, String custUsername, int reviewRating, String reviewText) {
        SQLiteDatabase db = null;
        try {
            // Create an instance of the DatabaseHelper class
            DatabaseHelper dbHelper = new DatabaseHelper(context);
            // Get a writable database
            db = dbHelper.getWritableDatabase();

            // Create a ContentValues object to store the review data
            ContentValues values = new ContentValues();
            values.put(DatabaseHelper.COLUMN_VENDOR_USERNAME, vendorUsername);
            values.put(DatabaseHelper.COLUMN_CUST_USERNAME, custUsername);
            values.put(DatabaseHelper.COLUMN_REVIEW_RATING, reviewRating);
            values.put(DatabaseHelper.COLUMN_REVIEW_TEXT, reviewText);

            // Insert the review data into the database
            db.insert(DatabaseHelper.TABLE_NAME, null, values);
        } catch (Exception e) {
            // Log the exception
            Log.e("AddReview", "Error adding review", e);
            // Optionally, show a user-friendly error message
            Toast.makeText(context, "Error adding review", Toast.LENGTH_SHORT).show();
        } finally {
            // Ensure the database connection is closed
            if (db != null) {
                db.close();
            }
        }
    }
    public void submitReview(View view) {
        int selectedID = review_rating.getCheckedRadioButtonId();
        if (selectedID == -1) {
            Toast.makeText(this, res.getString(R.string.invalid_review), Toast.LENGTH_SHORT).show();
            return; // Return to prevent further execution in case of no selection
        }

        rb = findViewById(selectedID); // Get the selected RadioButton
        rv = rb.getText().toString();
        review = Integer.parseInt(rv); // Convert rating to integer

        reviewtext = leave_review.getText().toString();

        try {
            addReview(this, venName, custName, review, reviewtext);
            Toast.makeText(this, "Review submitted successfully", Toast.LENGTH_SHORT).show(); // Toast for successful submission
        } catch (Exception e) {
            // Handle exception if review submission fails
            Toast.makeText(this, "Error submitting review", Toast.LENGTH_SHORT).show();
        }

        // Close activity after submitting review
        finish();
    }

    public void back(View view){
        finish();
    }
}