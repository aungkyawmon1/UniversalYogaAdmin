package com.example.universalyogaadmin.model;

public class YogaClass {
    private int id; // Add ID if needed for database operations
    private int courseID;
    private String date;
    private String teacher;
    private String comment;

    public YogaClass(int id, int courseID, String date, String teacher, String comment) {
        this.id = id;
        this.courseID = courseID;
        this.date = date;
        this.teacher = teacher;
        this.comment = comment;
    }

    // Getters and Setters
    public int getId() { return id; }
    public int getCourseID() { return courseID; }
    public String getDate() { return date; }
    public String getTeacher() { return teacher; }
    public String getComment() { return comment; }

}
