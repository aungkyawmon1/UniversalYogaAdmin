package com.example.universalyogaadmin.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.universalyogaadmin.*;
import com.example.universalyogaadmin.adapter.CourseAdapter;
import com.example.universalyogaadmin.database.DatabaseHelper;
import com.example.universalyogaadmin.model.YogaCourse;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerViewClasses;
    private CourseAdapter adapter;
    private DatabaseHelper databaseHelper;
    private FloatingActionButton fabAddCourse;


    // Retrieve the data as an ArrayList
    ArrayList<YogaCourse> yogaCourses;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
        // Initialize components
        recyclerViewClasses = findViewById(R.id.recyclerViewClasses);
        fabAddCourse = findViewById(R.id.fabAddCourse);
        databaseHelper = new DatabaseHelper(this);

       setUpRecyclerView();

        // Handle Floating Action Button click to add new course
        fabAddCourse.setOnClickListener(view -> {
            Intent intent = new Intent(this, CreateCourse.class);
            startActivity(intent);
        });
    }


    private void setUpRecyclerView() {
        yogaCourses = new ArrayList<>();
        adapter = new CourseAdapter(this, yogaCourses);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager( this, 1);
        recyclerViewClasses.setLayoutManager(layoutManager);
        recyclerViewClasses.setItemAnimator(new DefaultItemAnimator());
        recyclerViewClasses.setAdapter(adapter);
    }


    @Override
    protected void onResume() {
        super.onResume();
        // Refresh data when coming back from AddCourseActivity
        yogaCourses = databaseHelper.getAllYogaClasses();
        adapter.updateView(yogaCourses);
    }
}