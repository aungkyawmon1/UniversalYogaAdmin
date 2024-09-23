package com.example.universalyogaadmin.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.universalyogaadmin.R;
import com.example.universalyogaadmin.database.DatabaseHelper;

public class CreateCourse extends AppCompatActivity {

    private Spinner spinnerDayOfWeek, spinnerClassType, spinnerDifficultyLevel;
    private EditText editTextTime, editTextCapacity, editTextDuration, editTextPrice, editTextDescription;
    private Button buttonSubmit;

    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_create_course);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });

        // Initialize UI components
        spinnerDayOfWeek = findViewById(R.id.spinnerDayOfWeek);
        spinnerClassType = findViewById(R.id.spinnerClassType);
        spinnerDifficultyLevel = findViewById(R.id.spinnerDifficultyLevel);
        editTextTime = findViewById(R.id.editTextTime);
        editTextCapacity = findViewById(R.id.editTextCapacity);
        editTextDuration = findViewById(R.id.editTextDuration);
        editTextPrice = findViewById(R.id.editTextPrice);
        editTextDescription = findViewById(R.id.editTextDescription);
        buttonSubmit = findViewById(R.id.buttonSubmit);
        databaseHelper = new DatabaseHelper(this);

        // Set click listener for the submit button
        buttonSubmit.setOnClickListener(view -> validateAndSubmit());
    }

    private void validateAndSubmit() {
        // Validate required fields
        String day = spinnerDayOfWeek.getSelectedItem().toString();
        String time = editTextTime.getText().toString().trim();
        String capacity = editTextCapacity.getText().toString().trim();
        String duration = editTextDuration.getText().toString().trim();
        String price = editTextPrice.getText().toString().trim();
        String classType = spinnerClassType.getSelectedItem().toString();

        // Check for empty fields and show errors
        if (day.isEmpty() || time.isEmpty() || capacity.isEmpty() || duration.isEmpty() || price.isEmpty() || classType.isEmpty()) {
            Toast.makeText(this, "Please fill in all required fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Display entered details for confirmation
        showConfirmationDialog(day, time, capacity, duration, price, classType, editTextDescription.getText().toString());
    }

    private void showConfirmationDialog(String day, String time, String capacity, String duration, String price, String classType, String description) {
        // Show confirmation dialog or new activity
        new AlertDialog.Builder(this)
                .setTitle("Confirm Details")
                .setMessage("Day: " + day + "\nTime: " + time + "\nCapacity: " + capacity +
                        "\nDuration: " + duration + " minutes\nPrice: £" + price +
                        "\nClass Type: " + classType + "\nDescription: " + description)
                .setPositiveButton("Confirm", (dialog, which) -> {
                    // Save data to database
                    saveToDatabase(day, time, Integer.parseInt(capacity), Integer.parseInt(duration), Double.parseDouble(price), classType, description);
                })
                .setNegativeButton("Edit", null)
                .show();
    }

    private void saveToDatabase(String day, String time, int capacity, int duration, double price, String classType, String description) {
        // Save class details to the SQLite database
        // Implementation of database insertion goes here
        // Add the course to the database
        boolean isInserted = databaseHelper.addClass(day, time, capacity, duration, price, classType, description);
        if (isInserted) {
            Toast.makeText(this, "Course added successfully!", Toast.LENGTH_SHORT).show();
            finish();  // Close activity and go back to the list
        } else {
            Toast.makeText(this, "Failed to add course.", Toast.LENGTH_SHORT).show();
        }
    }
}