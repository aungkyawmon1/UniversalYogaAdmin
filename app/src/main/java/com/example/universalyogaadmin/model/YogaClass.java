package com.example.universalyogaadmin.model;

public class YogaClass {
    private int id; // Add ID if needed for database operations
    private String day;
    private String time;
    private int capacity;
    private int duration;
    private double price;
    private String type;
    private String description;

    // Constructor
    public YogaClass(int id, String day, String time, int capacity, int duration, double price, String type, String description) {
        this.id = id;
        this.day = day;
        this.time = time;
        this.capacity = capacity;
        this.duration = duration;
        this.price = price;
        this.type = type;
        this.description = description;
    }

    // Getters and Setters
    public int getId() { return id; }
    public String getDay() { return day; }
    public String getTime() { return time; }
    public int getCapacity() { return capacity; }
    public int getDuration() { return duration; }
    public double getPrice() { return price; }
    public String getType() { return type; }
    public String getDescription() { return description; }
}
