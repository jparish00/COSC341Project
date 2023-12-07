package com.example.cosc341project;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Reviews extends AppCompatActivity {

    LinearLayout reviews;
    TextView vendor, averageReview;

    String venName, custName, avgRate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviews);


        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        venName = bundle.getString("vendorName");
        custName = bundle.getString("customerName");


        vendor = findViewById(R.id.vendor_name_text_R);
        averageReview = findViewById(R.id.avg_review);


        vendor.setText(venName);
        avgRate = String.valueOf(getAverageReviewRatingForVendor(this, venName));
        averageReview.setText(avgRate);

        reviews = new LinearLayout(this);
        setContentView(reviews);
        reviews.setOrientation(LinearLayout.VERTICAL);

        displayReviewsForVendor(this, venName, reviews);


    }

    // Commented out to run build, remove comments if I forget to before push
    public void displayReviewsForVendor(Context context, String vendorUsername, LinearLayout linearLayout) {
        // Create an instance of the DatabaseHelper class
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        // Get a readable database
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Define the columns to retrieve
        String[] columns = {
                DatabaseHelper.COLUMN_VENDOR_NAME,
                DatabaseHelper.COLUMN_REVIEW_RATING,
                DatabaseHelper.COLUMN_REVIEW_TEXT
        };

        // Define the selection criteria
        String selection = DatabaseHelper.COLUMN_VENDOR_NAME + " = ?";
        String[] selectionArgs = {vendorUsername};

        // Execute the query to retrieve the reviews for the specific vendor
        Cursor cursor = db.query(DatabaseHelper.TABLE_NAME, columns, selection, selectionArgs, null, null, null);


        // Iterate through the cursor and add the reviews to the LinearLayout
        while (cursor.moveToNext()) {
            @SuppressLint("Range") String reviewRating = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_REVIEW_RATING));
            @SuppressLint("Range") String reviewText = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_REVIEW_TEXT));
            @SuppressLint("Range") String customerName = cursor.getString((cursor.getColumnIndex(DatabaseHelper.COLUMN_CUST_USERNAME)));

            // Create a TextView to display the review
            TextView reviewTextView = new TextView(context);
            reviewTextView.setText(customerName + "Rating: " + reviewRating + "\nReview: " + reviewText);

            // Add the TextView to the LinearLayout
            linearLayout.addView(reviewTextView);
        }

        // Close the cursor and database connection
        cursor.close();
        db.close();
    }

    public float getAverageReviewRatingForVendor(Context context, String vendorUsername) {
        // Create an instance of the DatabaseHelper class
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        // Get a readable database
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT AVG(" + DatabaseHelper.COLUMN_REVIEW_RATING + ") FROM " + DatabaseHelper.TABLE_NAME +
                " WHERE " + DatabaseHelper.COLUMN_VENDOR_NAME + " = '" + vendorUsername + "'";
        Cursor cursor = db.rawQuery(query, null);
        float averageRating = 0;
        if (cursor.moveToFirst()) {
            averageRating = cursor.getFloat(0);
        }
        cursor.close();
        return averageRating;
    }


    public void addReview(View view) {

        Intent intent = new Intent(getApplicationContext(), AddReview.class);
        Bundle bundle = new Bundle();
        bundle.putString("vendorName", venName);
        bundle.putString("customerName", custName);
        intent.putExtras(bundle);
        startActivity(intent);

    }

    public void back(View view) {
        finish();
    }
}