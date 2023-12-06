package com.example.cosc341project;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "ReviewsDB";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "Reviews";
    public static final String COLUMN_VENDOR_USERNAME = "vendorUsername";
    public static final String COLUMN_CUST_USERNAME = "custUsername";
    public static final String COLUMN_REVIEW_RATING = "reviewRating";
    public static final String COLUMN_REVIEW_TEXT = "reviewText";
    public static final String COLUMN_REVIEW_ID = "reviewID";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_VENDOR_USERNAME + " TEXT, " +
                COLUMN_CUST_USERNAME + " TEXT, " +
                COLUMN_REVIEW_RATING + " INTEGER, " +
                COLUMN_REVIEW_TEXT + " TEXT, " +
                COLUMN_REVIEW_ID + " INTEGER PRIMARY KEY AUTOINCREMENT)";

        db.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
