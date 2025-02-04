package com.example.cv;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ViewEducationActivity extends AppCompatActivity {

    private static final String TAG = "ViewEducationActivity";
    private TextView detailsTextView;
    private DatabaseReference databaseReference;

    // Constants
    private static final String NODE_NAME = "Education";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_details);

        // Initialize Firebase Database reference
        databaseReference = FirebaseDatabase.getInstance().getReference(NODE_NAME);

        // Initialize UI elements
        detailsTextView = findViewById(R.id.details_text_view);
        Button editButton = findViewById(R.id.edit_button);

        // Set up back button
        findViewById(R.id.back_button).setOnClickListener(v -> finish());

        // Fetch and display saved details
        fetchEducationDetails();

        // Set up Edit button click listener
        editButton.setOnClickListener(v -> {
            // Navigate back to EducationActivity to allow editing
            Intent intent = new Intent(ViewEducationActivity.this, EducationActivity.class);
            startActivity(intent);
        });
    }

    private void fetchEducationDetails() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                StringBuilder details = new StringBuilder();
                boolean hasData = false;

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    hasData = true;  // Found at least one record
                    // Fetching the values from the database
                    String degreeName = snapshot.child("degreeName").getValue(String.class);
                    String specialisation = snapshot.child("specialisation").getValue(String.class);
                    String collegeName = snapshot.child("collegeName").getValue(String.class);
                    String monthYearOfPassing = snapshot.child("monthYearOfPassing").getValue(String.class);
                    String degreeType = snapshot.child("degreeType").getValue(String.class);

                    // Log the fetched values
                    Log.d(TAG, "Degree Name: " + degreeName);
                    Log.d(TAG, "Specialisation: " + specialisation);
                    Log.d(TAG, "College Name: " + collegeName);
                    Log.d(TAG, "Month & Year of Passing: " + monthYearOfPassing);
                    Log.d(TAG, "Degree Type: " + degreeType);

                    // Append the details to the StringBuilder
                    details.append("Degree Name: ").append(degreeName).append("\n")
                            .append("Specialisation: ").append(specialisation).append("\n")
                            .append("College Name: ").append(collegeName).append("\n")
                            .append("Month & Year of Passing: ").append(monthYearOfPassing).append("\n\n")
                            .append("Degree Type: ").append(degreeType).append("\n");

                }

                // Check if details are empty
                if (!hasData) {
                    detailsTextView.setText("No education details found.");
                } else {
                    detailsTextView.setText(details.toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ViewEducationActivity.this, "Error fetching details: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
