package com.example.universalyogaadmin.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.universalyogaadmin.model.YogaCourse;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Name and Version
    private static final String DATABASE_NAME = "Yoga.db";
    private static final int DATABASE_VERSION = 1;

    // Table Name
    private static final String TABLE_COURSE = "course";
    private static final String TABLE_CLASS = "class";

    // Table Columns
    private static final String COURSE_COLUMN_ID = "id";
    private static final String COURSE_COLUMN_DAY = "day";
    private static final String COURSE_COLUMN_TIME = "time";
    private static final String COURSE_COLUMN_CAPACITY = "capacity";
    private static final String COURSE_COLUMN_DURATION = "duration";
    private static final String COURSE_COLUMN_PRICE = "price";
    private static final String COURSE_COLUMN_TYPE = "type";
    private static final String COURSE_COLUMN_LEVEL = "level";
    private static final String COURSE_COLUMN_DESCRIPTION = "description";
    private static final String CLASS_COLUMN_ID = "id";
    private static final String CLASS_COLUMN_DATE = "date_class";
    private static final String CLASS_COLUMN_TEACHER = "teacher";
    private static final String CLASS_COLUMN_COMMENT = "comment";

    // SQL Query to Create Table
    private static final String CREATE_TABLE_COURSE =
            "CREATE TABLE " + TABLE_COURSE + " (" +
                    COURSE_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COURSE_COLUMN_DAY + " TEXT NOT NULL, " +
                    COURSE_COLUMN_TIME + " TEXT NOT NULL, " +
                    COURSE_COLUMN_CAPACITY + " INTEGER NOT NULL, " +
                    COURSE_COLUMN_DURATION + " INTEGER NOT NULL, " +
                    COURSE_COLUMN_PRICE + " REAL NOT NULL, " +
                    COURSE_COLUMN_TYPE + " TEXT NOT NULL, " +
                    COURSE_COLUMN_LEVEL + " TEXT NOT NULL, " +
                    COURSE_COLUMN_DESCRIPTION + " TEXT)";

    private static final String CREATE_TABLE_CLASS =
            "CREATE TABLE " + TABLE_CLASS + "("
                    + CLASS_COLUMN_ID  + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COURSE_COLUMN_ID + " INTEGER NOT NULL,"
                    + CLASS_COLUMN_DATE + " TEXT NOT NULL,"
                    + CLASS_COLUMN_TEACHER + " TEXT NOT NULL,"
                    + CLASS_COLUMN_COMMENT + " TEXT"
                    + ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create the classes table
        db.execSQL(CREATE_TABLE_COURSE);
        db.execSQL(CREATE_TABLE_CLASS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop the old table if it exists
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COURSE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CLASS);
        onCreate(db);
    }

    // Method to add a new class
    public boolean addCourse(String day, String time, int capacity, int duration, double price, String type, String level, String description) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COURSE_COLUMN_DAY, day);
        values.put(COURSE_COLUMN_TIME, time);
        values.put(COURSE_COLUMN_CAPACITY, capacity);
        values.put(COURSE_COLUMN_DURATION, duration);
        values.put(COURSE_COLUMN_PRICE, price);
        values.put(COURSE_COLUMN_TYPE, type);
        values.put(COURSE_COLUMN_LEVEL, level);
        values.put(COURSE_COLUMN_DESCRIPTION, description);

        long result = db.insert(TABLE_COURSE, null, values);
        db.close();
        return result != -1;
    }

    public boolean addClass(int courseID, String date, String teacher, String comment) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COURSE_COLUMN_ID, courseID);
        values.put(CLASS_COLUMN_DATE, date);
        values.put(CLASS_COLUMN_TEACHER, teacher);
        values.put(CLASS_COLUMN_COMMENT, comment);

        long result = db.insert(TABLE_CLASS, null, values);
        db.close();
        return result != -1;
    }

    // Method to get all classes
    public ArrayList<YogaCourse> getAllYogaClasses() {
        ArrayList<YogaCourse> yogaCourses = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_COURSE , null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String day = cursor.getString(cursor.getColumnIndexOrThrow("day"));
                String time = cursor.getString(cursor.getColumnIndexOrThrow("time"));
                int capacity = cursor.getInt(cursor.getColumnIndexOrThrow("capacity"));
                int duration = cursor.getInt(cursor.getColumnIndexOrThrow("duration"));
                double price = cursor.getDouble(cursor.getColumnIndexOrThrow("price"));
                String type = cursor.getString(cursor.getColumnIndexOrThrow("type"));
                String level = cursor.getString(cursor.getColumnIndexOrThrow("level"));
                String description = cursor.getString(cursor.getColumnIndexOrThrow("description"));

                YogaCourse yogaCourse = new YogaCourse(id, day, time, capacity, duration, price, type, level, description);
                yogaCourses.add(yogaCourse);
            }
            cursor.close();
        }
        db.close();
        return yogaCourses;
    }

    public YogaCourse getYogaCourse(int courseID) {
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM " + TABLE_COURSE + " WHERE " + COURSE_COLUMN_ID + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(courseID)});

        YogaCourse yogaCourse = new YogaCourse(0, "", "",0,0,0, "", "", "");


        Log.i("TAG", "outside");
        if (cursor != null) {
            while (cursor.moveToFirst()) {
                Log.i("TAG", "inside");
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String day = cursor.getString(cursor.getColumnIndexOrThrow("day"));
                String time = cursor.getString(cursor.getColumnIndexOrThrow("time"));
                int capacity = cursor.getInt(cursor.getColumnIndexOrThrow("capacity"));
                int duration = cursor.getInt(cursor.getColumnIndexOrThrow("duration"));
                double price = cursor.getDouble(cursor.getColumnIndexOrThrow("price"));
                String type = cursor.getString(cursor.getColumnIndexOrThrow("type"));
                String level = cursor.getString(cursor.getColumnIndexOrThrow("level"));
                String description = cursor.getString(cursor.getColumnIndexOrThrow("description"));

                yogaCourse = new YogaCourse(id, day, time, capacity, duration, price, type, level, description);

                break;
            }
            cursor.close();
        }
        db.close();
        return yogaCourse;
    }

    // Method to delete a class by ID
    public boolean deleteCourse(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_COURSE, COURSE_COLUMN_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
        return result > 0;
    }

    // Method to reset the database by deleting all rows
    public void resetDatabase() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_COURSE, null, null);
        db.close();
    }
}