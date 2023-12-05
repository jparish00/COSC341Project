package com.example.cosc341project;

import androidx.appcompat.app.AppCompatActivity;

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
    public void displayReviewsForVendor(Context context, String vendorUsername, LinearLayout linearLayout) {
        // Create an instance of the DatabaseHelper class
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        // Get a readable database
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Define the columns to retrieve
        String[] columns = {
                DatabaseHelper.COLUMN_VENDOR_USERNAME,
                DatabaseHelper.COLUMN_REVIEW_RATING,
                DatabaseHelper.COLUMN_REVIEW_TEXT
        };

        // Define the selection criteria
        String selection = DatabaseHelper.COLUMN_VENDOR_USERNAME + " = ?";
        String[] selectionArgs = {vendorUsername};

        // Execute the query to retrieve the reviews for the specific vendor
        Cursor cursor = db.query(DatabaseHelper.TABLE_NAME, columns, selection, selectionArgs, null, null, null);

        // Iterate through the cursor and add the reviews to the LinearLayout
        while (cursor.moveToNext()) {
            String reviewRating = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_REVIEW_RATING));
            String reviewText = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_REVIEW_TEXT));
            String customerName = cursor.getString((cursor.getColumnIndex(DatabaseHelper.COLUMN_CUST_USERNAME)));

            // Create a TextView to display the review
            TextView reviewTextView = new TextView(context);
            reviewTextView.setText(customerName+ "Rating: " + reviewRating + "\nReview: " + reviewText);

            // Add the TextView to the LinearLayout
            linearLayout.addView(reviewTextView);
        }

        // Close the cursor and database connection
        cursor.close();
        db.close();
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