package com.example.cv;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class EducationActivity extends AppCompatActivity {

    // Declare UI elements
    private EditText degreeNameEditText, specialisationEditText, collegeNameEditText, monthYearOfPassingEditText;
    private Spinner degreeTypeSpinner;

    // Firebase Database reference
    private DatabaseReference databaseReference;

    // Constants
    private static final String DATABASE_NODE = "Education"; // Change to the desired node name

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_education);

        // Initialize UI elements
        degreeNameEditText = findViewById(R.id.degree_name_edittext);
        specialisationEditText = findViewById(R.id.specialisation_edittext);
        collegeNameEditText = findViewById(R.id.college_name_edittext);
        monthYearOfPassingEditText = findViewById(R.id.month_year_of_passing_edittext);
        degreeTypeSpinner = findViewById(R.id.degree_type_spinner);
        Button saveButton = findViewById(R.id.save_button);
        Button viewDetailsButton = findViewById(R.id.view_details_button);
        ImageButton backButton = findViewById(R.id.back_button);

        // Initialize Firebase Database reference
        databaseReference = FirebaseDatabase.getInstance().getReference(DATABASE_NODE);

        // Back button listener
        backButton.setOnClickListener(v -> finish());

        // Save button click listener
        saveButton.setOnClickListener(v -> saveEducationDetails());

        // View details button listener
        viewDetailsButton.setOnClickListener(v -> {
            Intent intent = new Intent(EducationActivity.this, ViewEducationActivity.class);
            startActivity(intent);
        });
    }

    private void saveEducationDetails() {
        // Get input values
        String degreeName = degreeNameEditText.getText().toString().trim();
        String specialisation = specialisationEditText.getText().toString().trim();
        String collegeName = collegeNameEditText.getText().toString().trim();
        String monthYearOfPassing = monthYearOfPassingEditText.getText().toString().trim();
        String degreeType = degreeTypeSpinner.getSelectedItem().toString();

        // Validate input
        if (TextUtils.isEmpty(degreeName) || TextUtils.isEmpty(specialisation) ||
                TextUtils.isEmpty(collegeName) || TextUtils.isEmpty(monthYearOfPassing)) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create a map to store the education details
        Map<String, Object> educationDetails = new HashMap<>();
        educationDetails.put("degreeName", degreeName);
        educationDetails.put("specialisation", specialisation);
        educationDetails.put("collegeName", collegeName);
        educationDetails.put("monthYearOfPassing", monthYearOfPassing);
        educationDetails.put("degreeType", degreeType);

        // Save data to Firebase Realtime Database
        databaseReference.push() // This generates a unique key for each entry
                .setValue(educationDetails)
                .addOnSuccessListener(aVoid -> Toast.makeText(EducationActivity.this, "Education details saved!", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> Toast.makeText(EducationActivity.this, "Error saving details: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }
}
