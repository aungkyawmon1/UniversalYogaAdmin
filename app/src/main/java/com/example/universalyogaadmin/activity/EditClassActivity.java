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
import com.example.universalyogaadmin.model.YogaClass;
import com.example.universalyogaadmin.model.YogaCourse;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class EditClassActivity extends AppCompatActivity {

    private TextInputEditText editTextDate, editTextTeacher, editTextComment;

    private int classID = -1;
    private int courseID = -1;
    private String dayOfWeekString = "Monday";
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_class);

        getSupportActionBar().setTitle("Edit Class");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        editTextDate = findViewById(R.id.etDateOfClass);
        editTextTeacher = findViewById(R.id.etTeacher);
        editTextComment = findViewById(R.id.etComment);
        databaseHelper = new DatabaseHelper(this);

        classID = getIntent().getIntExtra("yoga_class_id", -1);
        courseID = getIntent().getIntExtra("yoga_course_id", -1);
        loadClassDetails(classID);
        setUpDatePicker();
    }

    private void loadClassDetails(int id) {
        YogaClass yogaClass = databaseHelper.getYogaClasses(id);

        dayOfWeekString = yogaClass.getDay();
        editTextDate.setText(yogaClass.getDate());
        editTextTeacher.setText(yogaClass.getTeacher());
        editTextComment.setText(yogaClass.getComment());
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

        // Convert day string to corresponding Calendar constant
        int dayOfWeek = getDayOfWeekFromString(dayOfWeekString);
        if (dayOfWeek == -1) {
            Toast.makeText(this, "Invalid day of week", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create a new instance of DatePickerDialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    Calendar selectedCalendar = Calendar.getInstance();
                    selectedCalendar.set(selectedYear, selectedMonth, selectedDay);
                    int selectedDayOfWeek = selectedCalendar.get(Calendar.DAY_OF_WEEK);

                    // Check if the selected day matches the specified day (e.g., Monday)
                    if (selectedDayOfWeek == dayOfWeek) {
                        // Format the selected date and set it in the EditText
                        String selectedDate = selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear;
                        editTextDate.setText(selectedDate);
                    } else {
                        Toast.makeText(this, "Please select a " + dayOfWeekString, Toast.LENGTH_SHORT).show();
                    }
                },
                year, month, day);

        // Add a listener to filter the dates based on the day of the week
        datePickerDialog.getDatePicker().init(year, month, day, (view, year1, monthOfYear, dayOfMonth) -> {
            Calendar selectedCalendar = Calendar.getInstance();
            selectedCalendar.set(year1, monthOfYear, dayOfMonth);
            int selectedDayOfWeek = selectedCalendar.get(Calendar.DAY_OF_WEEK);

            // If the selected day is not the desired day, automatically move to the next matching day
            if (selectedDayOfWeek != dayOfWeek) {
                while (selectedDayOfWeek != dayOfWeek) {
                    selectedCalendar.add(Calendar.DAY_OF_MONTH, 1);
                    selectedDayOfWeek = selectedCalendar.get(Calendar.DAY_OF_WEEK);
                }

                view.updateDate(
                        selectedCalendar.get(Calendar.YEAR),
                        selectedCalendar.get(Calendar.MONTH),
                        selectedCalendar.get(Calendar.DAY_OF_MONTH)
                );
            }
        });
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        // Show the DatePickerDialog
        datePickerDialog.show();
    }

    // Helper function to map day string to Calendar constant
    private int getDayOfWeekFromString(String day) {
        Map<String, Integer> dayMap = new HashMap<>();
        dayMap.put("Sunday", Calendar.SUNDAY);
        dayMap.put("Monday", Calendar.MONDAY);
        dayMap.put("Tuesday", Calendar.TUESDAY);
        dayMap.put("Wednesday", Calendar.WEDNESDAY);
        dayMap.put("Thursday", Calendar.THURSDAY);
        dayMap.put("Friday", Calendar.FRIDAY);
        dayMap.put("Saturday", Calendar.SATURDAY);

        return dayMap.getOrDefault(day, -1); // Return -1 if the day is invalid
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
        YogaClass yogaClass = new YogaClass(classID, courseID, date, teacher, comment, "");
        boolean isInserted = databaseHelper.updateClass(classID, yogaClass);
        if (isInserted) {
            Toast.makeText(this, "Class updated successfully!", Toast.LENGTH_SHORT).show();
            finish();  // Close activity and go back to the list
        } else {
            Toast.makeText(this, "Failed to update course.", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.update_menu, menu);
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