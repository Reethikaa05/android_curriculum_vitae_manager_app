package com.example.cv;

import android.annotation.SuppressLint;
import android.os.Bundle;
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

public class ResearchDetailsActivity extends AppCompatActivity {

    private LinearLayout researchInterestLayout;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_research_details);

        // Initialize layout components
        researchInterestLayout = findViewById(R.id.research_interest_layout);
        Button backButton = findViewById(R.id.back_button);

        // Firebase reference to "research_interests" under the specific faculty ID
        // Faculty ID, can be passed dynamically
        String facultyId = "faculty_123";
        databaseReference = FirebaseDatabase.getInstance().getReference("research_interests").child(facultyId);

        // Fetch research interests and display them
        fetchResearchInterests();

        // Back button click listener to go back to the previous activity
        backButton.setOnClickListener(v -> onBackPressed());
    }

    /**
     * Fetch research interests from Firebase and add them to the layout.
     */
    private void fetchResearchInterests() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                researchInterestLayout.removeAllViews(); // Clear previous entries

                // Check if data exists
                if (dataSnapshot.exists()) {
                    boolean hasData = false;

                    // Loop through the children (research interests)
                    for (DataSnapshot researchSnapshot : dataSnapshot.getChildren()) {
                        ResearchInterest researchInterest = researchSnapshot.getValue(ResearchInterest.class);

                        if (researchInterest != null) {
                            // Add the research interest to the layout
                            addResearchInterestView(researchInterest);
                            hasData = true;
                        }
                    }

                    // If no research interests were added, show a message
                    if (!hasData) {
                        addNoDataView();
                    }

                } else {
                    // No data found in this reference
                    addNoDataView();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle errors here
                Toast.makeText(ResearchDetailsActivity.this, "Failed to load research interests.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Add a TextView for each research interest entry.
     */
    @SuppressLint("SetTextI18n")
    private void addResearchInterestView(ResearchInterest researchInterest) {
        // Create a new TextView
        TextView researchInterestView = new TextView(this);

        // Set the text for the research interest, with a null check for title
        if (researchInterest.getTitle() != null) {
            researchInterestView.setText("Research Title: " + researchInterest.getTitle());
        } else {
            researchInterestView.setText("Research Title: N/A"); // Handle missing title case
        }

        // Style the TextView (optional)
        researchInterestView.setPadding(16, 16, 16, 16);
        researchInterestView.setTextSize(16);
        researchInterestView.setBackgroundResource(android.R.color.background_light);
        researchInterestView.setTextColor(getResources().getColor(android.R.color.black));

        // Add the TextView to the layout
        researchInterestLayout.addView(researchInterestView);
    }

    /**
     * Add a TextView to indicate no research interests are available.
     */
    @SuppressLint("SetTextI18n")
    private void addNoDataView() {
        TextView noDataView = new TextView(this);
        noDataView.setText("No research interests available.");
        noDataView.setPadding(16, 16, 16, 16);
        noDataView.setTextSize(16);
        noDataView.setTextColor(getResources().getColor(android.R.color.black));

        // Add the TextView to the layout
        researchInterestLayout.addView(noDataView);
    }
}
