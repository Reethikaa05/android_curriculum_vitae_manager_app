package com.example.cv;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class ExperienceActivity extends AppCompatActivity {

    // Declare UI elements
    private TextInputEditText jobTitleEditText, yearsOfExperienceEditText, descriptionEditText;
    private Spinner experienceTypeSpinner;

    // Firebase Database reference
    private DatabaseReference databaseReference;

    // Constants
    private static final String DATABASE_NODE = "Experience"; // Change to the desired node name

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_exp);

        // Initialize UI elements
        jobTitleEditText = findViewById(R.id.form_exp_et_job);
        yearsOfExperienceEditText = findViewById(R.id.form_exp_et_years_of_experience);
        descriptionEditText = findViewById(R.id.form_exp_et_desc);
        experienceTypeSpinner = findViewById(R.id.spinner_experience_type);
        Button saveButton = findViewById(R.id.form_exp_btn_save);
        Button viewDetailsButton = findViewById(R.id.form_exp_btn_view_details);
        ImageButton backButton = findViewById(R.id.back_button);

        // Initialize Firebase Database reference
        databaseReference = FirebaseDatabase.getInstance().getReference(DATABASE_NODE);

        // Back button listener
        backButton.setOnClickListener(v -> finish());

        // Save button click listener
        saveButton.setOnClickListener(v -> saveExperienceDetails());

        // View details button listener
        viewDetailsButton.setOnClickListener(v -> {
            Intent intent = new Intent(ExperienceActivity.this, ExperienceDetailsActivity.class);
            startActivity(intent);
        });
    }

    private void saveExperienceDetails() {
        // Get input values
        String jobTitle = jobTitleEditText.getText().toString().trim();
        String yearsOfExperience = yearsOfExperienceEditText.getText().toString().trim();
        String description = descriptionEditText.getText().toString().trim();
        String experienceType = experienceTypeSpinner.getSelectedItem().toString();

        // Validate input
        if (TextUtils.isEmpty(jobTitle) || TextUtils.isEmpty(yearsOfExperience) || TextUtils.isEmpty(description)) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create a map to store the experience details
        Map<String, Object> experienceDetails = new HashMap<>();
        experienceDetails.put("jobTitle", jobTitle);
        experienceDetails.put("yearsOfExperience", yearsOfExperience);
        experienceDetails.put("description", description);
        experienceDetails.put("experienceType", experienceType);

        // Save data to Firebase Realtime Database
        databaseReference.push() // This generates a unique key for each entry
                .setValue(experienceDetails)
                .addOnSuccessListener(aVoid -> Toast.makeText(ExperienceActivity.this, "Experience details saved!", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> Toast.makeText(ExperienceActivity.this, "Error saving details: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }
}
