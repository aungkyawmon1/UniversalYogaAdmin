package com.example.universalyogaadmin.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.universalyogaadmin.model.YogaClass;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Name and Version
    private static final String DATABASE_NAME = "Yoga.db";
    private static final int DATABASE_VERSION = 1;

    // Table Name
    private static final String TABLE_CLASSES = "course";

    // Table Columns
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_DAY = "day";
    private static final String COLUMN_TIME = "time";
    private static final String COLUMN_CAPACITY = "capacity";
    private static final String COLUMN_DURATION = "duration";
    private static final String COLUMN_PRICE = "price";
    private static final String COLUMN_TYPE = "type";
    private static final String COLUMN_DESCRIPTION = "description";

    // SQL Query to Create Table
    private static final String CREATE_TABLE_CLASSES =
            "CREATE TABLE " + TABLE_CLASSES + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_DAY + " TEXT NOT NULL, " +
                    COLUMN_TIME + " TEXT NOT NULL, " +
                    COLUMN_CAPACITY + " INTEGER NOT NULL, " +
                    COLUMN_DURATION + " INTEGER NOT NULL, " +
                    COLUMN_PRICE + " REAL NOT NULL, " +
                    COLUMN_TYPE + " TEXT NOT NULL, " +
                    COLUMN_DESCRIPTION + " TEXT)";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create the classes table
        db.execSQL(CREATE_TABLE_CLASSES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop the old table if it exists
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CLASSES);
        onCreate(db);
    }

    // Method to add a new class
    public boolean addClass(String day, String time, int capacity, int duration, double price, String type, String description) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_DAY, day);
        values.put(COLUMN_TIME, time);
        values.put(COLUMN_CAPACITY, capacity);
        values.put(COLUMN_DURATION, duration);
        values.put(COLUMN_PRICE, price);
        values.put(COLUMN_TYPE, type);
        values.put(COLUMN_DESCRIPTION, description);

        long result = db.insert(TABLE_CLASSES, null, values);
        db.close();
        return result != -1;
    }

    // Method to get all classes
    public ArrayList<YogaClass> getAllYogaClasses() {
        ArrayList<YogaClass> yogaClasses = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_CLASSES, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                String day = cursor.getString(cursor.getColumnIndex("day"));
                String time = cursor.getString(cursor.getColumnIndex("time"));
                int capacity = cursor.getInt(cursor.getColumnIndex("capacity"));
                int duration = cursor.getInt(cursor.getColumnIndex("duration"));
                double price = cursor.getDouble(cursor.getColumnIndex("price"));
                String type = cursor.getString(cursor.getColumnIndex("type"));
                String description = cursor.getString(cursor.getColumnIndex("description"));

                YogaClass yogaClass = new YogaClass(id, day, time, capacity, duration, price, type, description);
                yogaClasses.add(yogaClass);
            }
            cursor.close();
        }
        return yogaClasses;
    }

    // Method to delete a class by ID
    public boolean deleteClass(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_CLASSES, COLUMN_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
        return result > 0;
    }

    // Method to reset the database by deleting all rows
    public void resetDatabase() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CLASSES, null, null);
        db.close();
    }
}