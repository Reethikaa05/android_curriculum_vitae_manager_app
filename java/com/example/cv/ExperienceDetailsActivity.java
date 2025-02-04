package com.example.cv;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ExperienceDetailsActivity extends AppCompatActivity {

    private static final String TAG = "ExperienceDetailsActivity"; // For logging
    private LinearLayout experienceLayout;
    private DatabaseReference databaseReference;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_experience_details); // Ensure you have this layout file

        // Initialize layout components
        experienceLayout = findViewById(R.id.experience_layout);
        Button backButton = findViewById(R.id.back_button);
        databaseReference = FirebaseDatabase.getInstance().getReference("Experience");

        fetchExperienceDetails(); // Load experience details from Firebase

        // Back button click listener
        backButton.setOnClickListener(v -> finish()); // Go back to the previous activity
    }

    private void fetchExperienceDetails() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Check if experience details exist
                if (!dataSnapshot.exists()) {
                    Toast.makeText(ExperienceDetailsActivity.this, "No experience details found.", Toast.LENGTH_SHORT).show();
                    return;
                }

                for (DataSnapshot experienceSnapshot : dataSnapshot.getChildren()) {
                    Experience experience = experienceSnapshot.getValue(Experience.class);
                    if (experience != null) {
                        addExperienceView(experience); // Add each experience to the layout
                    } else {
                        Log.d(TAG, "Experience is null");
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ExperienceDetailsActivity.this, "Failed to load experience details: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void addExperienceView(Experience experience) {
        // Create a TextView for each experience entry
        TextView experienceView = new TextView(this);
        experienceView.setText("Job Title: " + experience.getJobTitle() +
                "\nYears of Experience: " + experience.getYearsOfExperience() +
                "\nDescription: " + experience.getDescription() +
                "\nExperience Type: " + experience.getExperienceType());

        // Optionally, style the TextView
        experienceView.setPadding(16, 16, 16, 16);
        experienceView.setTextSize(16);
        experienceView.setBackgroundResource(android.R.color.background_light);
        experienceView.setTextColor(getResources().getColor(android.R.color.black));

        // Add the TextView to the layout
        experienceLayout.addView(experienceView);
    }
}
