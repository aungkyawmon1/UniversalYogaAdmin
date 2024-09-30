package com.example.universalyogaadmin.activity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.universalyogaadmin.R;
import com.example.universalyogaadmin.database.DatabaseHelper;
import com.example.universalyogaadmin.model.YogaCourse;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;

public class CreateClass extends AppCompatActivity {

    private TextInputEditText editTextDate, editTextTeacher, editTextComment;

    private int courseID = -1;
    private String day = "";

    private DatabaseHelper databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_create_class);

        getSupportActionBar().setTitle("Add Class");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        editTextDate = findViewById(R.id.etDateOfClass);
        editTextTeacher = findViewById(R.id.etTeacher);
        editTextComment = findViewById(R.id.etComment);
        databaseHelper = new DatabaseHelper(this);

        courseID = getIntent().getIntExtra("yoga_course_id", -1);
        loadClassDetails(courseID);

        setUpDatePicker();
    }

    private void loadClassDetails(int id) {
        YogaCourse yogaCourse = databaseHelper.getYogaCourse(id);
        day = yogaCourse.getDay();
    }

    private void setUpDatePicker() {
        // Disable direct input for time EditText
        editTextDate.setInputType(InputType.TYPE_NULL);
        editTextDate.setFocusable(false);

        // Show TimePickerDialog when editTextTime is clicked
        editTextDate.setOnClickListener(v -> showDatePickerDialog() );
    }



        private void showDatePickerDialog() {
        // Get the current date to display in the picker as default
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and show it
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    // Format the selected date and set it in the EditText
                    String selectedDate = selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear;
                    editTextDate.setText(selectedDate);
                },
                year, month, day);

        // Show the DatePickerDialog
        datePickerDialog.show();
    }

    private void validateAndSubmit() {
        // Validate required fields
        String date = editTextDate.getText().toString();
        String teacher = editTextTeacher.getText().toString().trim();
        String comment = editTextComment.getText().toString().trim();

        // Check for empty fields and show errors
        if (date.isEmpty() || teacher.isEmpty()) {
            Toast.makeText(this, "Please fill in all required fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Display entered details for confirmation
        saveToDatabase(date, teacher, comment);
    }

    private void saveToDatabase(String date, String teacher, String comment) {
        // Save class details to the SQLite database
        // Implementation of database insertion goes here
        // Add the course to the database
        boolean isInserted = databaseHelper.addClass(courseID, date, teacher, comment, day);
        if (isInserted) {
            Toast.makeText(this, "Class added successfully!", Toast.LENGTH_SHORT).show();
            finish();  // Close activity and go back to the list
        } else {
            Toast.makeText(this, "Failed to add course.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.save_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection.
        Log.i("LOG", "search" + item.getItemId());

        if(item.getItemId() == R.id.save) {
            validateAndSubmit();
            return true;
        } else if (item.getItemId() == android.R.id.home) {
            finish(); // or perform any custom action
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }

    }
}