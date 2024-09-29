package com.example.universalyogaadmin.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.universalyogaadmin.R;
import com.example.universalyogaadmin.database.DatabaseHelper;
import com.example.universalyogaadmin.model.YogaCourse;

public class CourseDetailActivity extends AppCompatActivity {

    private TextView tvName, tvCapacity, tvPrice, tvDuration, tvDescription, tvYogaType, tvYogaLevel;
    ImageView ivAddClass;

    private DatabaseHelper databaseHelper;

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
        databaseHelper = new DatabaseHelper(this);

        courseID = getIntent().getIntExtra("yoga_course_id", -1);
        loadClassDetails(courseID);

    }

    private void loadClassDetails(int id) {
        YogaCourse yogaCourse = databaseHelper.getYogaCourse(id);

        tvName.setText(yogaCourse.getDay()+" - " + yogaCourse.getTime());
        tvCapacity.setText(yogaCourse.getCapacity() + "");
        tvDuration.setText(yogaCourse.getDuration() + "");
        tvPrice.setText(yogaCourse.getPrice()+"");
        tvYogaType.setText(yogaCourse.getType());
        tvYogaLevel.setText(yogaCourse.getLevel());
        tvDescription.setText(yogaCourse.getDescription());
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
        Log.i("LOG", "search" + item.getItemId());

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
}