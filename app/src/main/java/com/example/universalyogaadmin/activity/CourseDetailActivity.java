package com.example.universalyogaadmin.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.universalyogaadmin.ClassUpdateListener;
import com.example.universalyogaadmin.R;
import com.example.universalyogaadmin.adapter.ClassAdapter;
import com.example.universalyogaadmin.database.DatabaseHelper;
import com.example.universalyogaadmin.model.YogaClass;
import com.example.universalyogaadmin.model.YogaCourse;

import java.util.ArrayList;

public class CourseDetailActivity extends AppCompatActivity implements ClassUpdateListener {

    private TextView tvName, tvCapacity, tvPrice, tvDuration, tvDescription, tvYogaType, tvYogaLevel;
    ImageView ivAddClass;
    RecyclerView rvClass;
    private DatabaseHelper databaseHelper;
    ClassAdapter classAdapter;

    ArrayList<YogaClass> yogaClasses;

    private  int courseID = -1;
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
        ivAddClass = findViewById(R.id.ivAddClass);
        rvClass = findViewById(R.id.rvClass);
        databaseHelper = new DatabaseHelper(this);

        setUpRecyclerView();
        setOnClickListener();

        courseID = getIntent().getIntExtra("yoga_course_id", -1);


    }

    @Override
    protected void onResume() {
        super.onResume();
        loadClassDetails(courseID);
        updateClassData();
    }

    private void loadClassDetails(int id) {
        YogaCourse yogaCourse = databaseHelper.getYogaCourse(id);

        tvName.setText(yogaCourse.getDay()+" - " + yogaCourse.getTime());
        tvCapacity.setText(yogaCourse.getCapacity() + "");
        tvDuration.setText(yogaCourse.getDuration() + "");
        tvPrice.setText(yogaCourse.getPrice()+"");
        tvYogaType.setText(yogaCourse.getType());
        tvYogaLevel.setText(yogaCourse.getLevel());
        tvDescription.setVisibility(yogaCourse.getDescription().isEmpty() ? View.GONE : View.VISIBLE);
        tvDescription.setText(yogaCourse.getDescription());
    }

    private void setUpRecyclerView() {
        yogaClasses = new ArrayList<>();
        classAdapter = new ClassAdapter(this, this, yogaClasses);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager( this, 1);
        rvClass.setLayoutManager(layoutManager);
        rvClass.setItemAnimator(new DefaultItemAnimator());
        rvClass.setAdapter(classAdapter);
    }

    private void updateClassData() {
        yogaClasses = databaseHelper.getAllYogaClasses(courseID);
        classAdapter.updateView(yogaClasses);
    }

    private void setOnClickListener() {
        ivAddClass.setOnClickListener(view -> {
            navigateToAddClass();
        });
    }

    private void navigateToAddClass() {
        Intent myIntent = new Intent(CourseDetailActivity.this, CreateClass.class);
        myIntent.putExtra("yoga_course_id", courseID );
        this.startActivity(myIntent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.course_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection.
        if(item.getItemId() == R.id.edit) {
            Intent myIntent = new Intent(CourseDetailActivity.this, EditCourseActivity.class);
            myIntent.putExtra("yoga_course_id", courseID);
            startActivity(myIntent);
            return true;
        } else if(item.getItemId() == R.id.delete) {
            createDeleteAlertDialog();
            return true;
        } else if (item.getItemId() == android.R.id.home) {
            finish(); // or perform any custom action
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    private void createDeleteAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // Add the buttons.
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User taps OK button.
                finish();
                deleteObservation();
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancels the dialog.
                dialog.dismiss();
            }
        });

        // Create the AlertDialog.
        AlertDialog dialog = builder.create();
        dialog.setTitle("Delete");
        dialog.setMessage("Do you want to delete this course?");
        dialog.show();
    }

    private void deleteObservation() {
        databaseHelper.deleteCourse(courseID);
    }

    @Override
    public void deleteClass(int classID) {

       createAlertDialog(classID);
    }

    private void createAlertDialog(int classID) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
// Add the buttons.
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User taps OK button.
                databaseHelper.deleteClass(classID);
                updateClassData();
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancels the dialog.
                dialog.dismiss();
            }
        });

        // Create the AlertDialog.
        AlertDialog dialog = builder.create();
        dialog.setTitle("Delete");
        dialog.setMessage("Do you want to delete this class?");
        dialog.show();
    }

    @Override
    public void updateClass(int classID) {
        Intent intent = new Intent(CourseDetailActivity.this, EditClassActivity.class);
        intent.putExtra("yoga_course_id", courseID);
        intent.putExtra("yoga_class_id", classID);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        this.startActivity(intent);
    }
}