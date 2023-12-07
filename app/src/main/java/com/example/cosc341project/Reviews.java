package com.example.cosc341project;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Reviews extends AppCompatActivity {

    LinearLayout reviews;
    TextView vendor, averageReview;
    String venName, custName, avgRate;
    Button addReview, goBackBtn;

    private static final int ADD_REVIEW_REQUEST = 1; // Request code for AddReview activity


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviews);
        goBackBtn = findViewById(R.id.ReviewBackButt);
        addReview = findViewById(R.id.add_review);
        goBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        addReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               addReview(v);
            }
        });

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        venName = bundle.getString("vendorName");
        custName = bundle.getString("customerName");

        vendor = findViewById(R.id.vendor_name_text_R);
        averageReview = findViewById(R.id.avg_review);

        try {
            vendor.setText(venName);
            avgRate = String.valueOf(getAverageReviewRatingForVendor(this, venName));
            averageReview.setText(avgRate);

            reviews = findViewById(R.id.review_List);

            displayReviewsForVendor(this, venName, reviews);
        } catch (Exception e) {
            Log.e("Reviews", "Error in onCreate: ", e);
        }
    }

    public void displayReviewsForVendor(Context context, String vendorUsername, LinearLayout linearLayout) {
        SQLiteDatabase db = null;
        Cursor cursor = null;

        try {
            DatabaseHelper dbHelper = new DatabaseHelper(context);
            db = dbHelper.getReadableDatabase();

            String[] columns = {
                    DatabaseHelper.COLUMN_VENDOR_NAME,
                    DatabaseHelper.COLUMN_REVIEW_RATING,
                    DatabaseHelper.COLUMN_REVIEW_TEXT
            };

            String selection = DatabaseHelper.COLUMN_VENDOR_NAME + " = ?";
            String[] selectionArgs = {vendorUsername};

            cursor = db.query(DatabaseHelper.TABLE_NAME, columns, selection, selectionArgs, null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    @SuppressLint("Range") String reviewRating = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_REVIEW_RATING));
                    @SuppressLint("Range") String reviewText = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_REVIEW_TEXT));
                    @SuppressLint("Range") String customerName = cursor.getString((cursor.getColumnIndex(DatabaseHelper.COLUMN_CUST_USERNAME)));

                    TextView reviewTextView = new TextView(context);
                    reviewTextView.setText(customerName + " Rating: " + reviewRating + "\nReview: " + reviewText);

                    linearLayout.addView(reviewTextView);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e("Reviews", "Error in displayReviewsForVendor: ", e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }
    }

    public float getAverageReviewRatingForVendor(Context context, String vendorUsername) {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        float averageRating = 0;

        try {
            DatabaseHelper dbHelper = new DatabaseHelper(context);
            db = dbHelper.getReadableDatabase();

            String query = "SELECT AVG(" + DatabaseHelper.COLUMN_REVIEW_RATING + ") FROM " + DatabaseHelper.TABLE_NAME +
                    " WHERE " + DatabaseHelper.COLUMN_VENDOR_NAME + " = '" + vendorUsername + "'";
            cursor = db.rawQuery(query, null);

            if (cursor != null && cursor.moveToFirst()) {
                averageRating = cursor.getFloat(0);
            }
        } catch (Exception e) {
            Log.e("Reviews", "Error in getAverageReviewRatingForVendor: ", e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }

        return averageRating;
    }

    public void addReview(View view) {
        Intent intent = new Intent(getApplicationContext(), AddReview.class);
        Bundle bundle = new Bundle();
        bundle.putString("vendorName", venName);
        bundle.putString("customerName", custName);
        intent.putExtras(bundle);
        startActivityForResult(intent, ADD_REVIEW_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_REVIEW_REQUEST) {
            // Refresh the reviews
            reviews.removeAllViews(); // Clear all views in the LinearLayout
            displayReviewsForVendor(this, venName, reviews);
            avgRate = String.valueOf(getAverageReviewRatingForVendor(this, venName));
            averageReview.setText(avgRate);
        }
    }
}