package com.example.universalyogaadmin.activity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.universalyogaadmin.*;
import com.example.universalyogaadmin.adapter.ClassesAdapter;
import com.example.universalyogaadmin.database.DatabaseHelper;
import com.example.universalyogaadmin.model.YogaClass;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerViewClasses;
    private ClassesAdapter adapter;
    private DatabaseHelper databaseHelper;
    private FloatingActionButton fabAddCourse;


    // Retrieve the data as an ArrayList
    ArrayList<YogaClass> yogaClasses;
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
        yogaClasses = new ArrayList<>();
        adapter = new ClassesAdapter(this, yogaClasses);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager( this, 1);
        recyclerViewClasses.setLayoutManager(layoutManager);
        recyclerViewClasses.setItemAnimator(new DefaultItemAnimator());
        recyclerViewClasses.setAdapter(adapter);
    }


    @Override
    protected void onResume() {
        super.onResume();
        // Refresh data when coming back from AddCourseActivity
        yogaClasses = databaseHelper.getAllYogaClasses();
        adapter.updateView(yogaClasses);
    }
}