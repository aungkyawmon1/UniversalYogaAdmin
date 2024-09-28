package com.example.universalyogaadmin.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.universalyogaadmin.R;
import com.example.universalyogaadmin.database.DatabaseHelper;
import com.example.universalyogaadmin.model.YogaClass;

public class CourseDetailActivity extends AppCompatActivity {

    private TextView tvName, tvCapacity, tvPrice, tvDuration, tvDescription, tvYogaType, tvYogaLevel;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_course_detail);

        getSupportActionBar().setTitle("Course Detail");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tvName = findViewById(R.id.tvNameTime);
        tvCapacity = findViewById(R.id.tvCapacity);
        tvPrice = findViewById(R.id.tvPrice);
        tvDuration = findViewById(R.id.tvDuration);
        tvYogaType = findViewById(R.id.tvYogaType);
        tvYogaLevel = findViewById(R.id.tvYogaLevel);
        tvDescription = findViewById(R.id.tvDescription);

        databaseHelper = new DatabaseHelper(this);

        int classId = getIntent().getIntExtra("yoga_class_id", -1);
        Log.i("ID", classId +"");
        loadClassDetails(classId);

    }

    private void loadClassDetails(int id) {
        YogaClass yogaClass = databaseHelper.getYogaCourse(id);

        tvName.setText(yogaClass.getDay()+" - " + yogaClass.getTime());
        tvCapacity.setText(yogaClass.getCapacity() + "");
        tvDuration.setText(yogaClass.getDuration() + "");
        tvPrice.setText(yogaClass.getPrice()+"");
        tvYogaType.setText(yogaClass.getType());
        tvYogaLevel.setText(yogaClass.getLevel());
        tvDescription.setText(yogaClass.getDescription());
    }
}