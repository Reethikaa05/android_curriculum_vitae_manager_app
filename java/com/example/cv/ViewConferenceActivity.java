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

public class ViewConferenceActivity extends AppCompatActivity {

    private LinearLayout conferenceLayout;
    private DatabaseReference databaseReference;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_conference); // Ensure you have this layout file

        // Initialize layout components
        conferenceLayout = findViewById(R.id.conference_layout);
        Button backButton = findViewById(R.id.back_button);
        databaseReference = FirebaseDatabase.getInstance().getReference("ConferenceArticles");

        fetchConferences(); // Load the conferences from Firebase

        // Back button click listener
        backButton.setOnClickListener(v -> onBackPressed()); // Go back to the previous activity
    }

    private void fetchConferences() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Clear existing views to prevent duplication
                conferenceLayout.removeAllViews();

                for (DataSnapshot conferenceSnapshot : dataSnapshot.getChildren()) {
                    Conference conference = conferenceSnapshot.getValue(Conference.class);
                    if (conference != null) {
                        addConferenceView(conference); // Add each conference to the layout
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ViewConferenceActivity.this, "Failed to load conferences.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void addConferenceView(Conference conference) {
        // Inflate the custom layout for each conference entry
        LinearLayout conferenceView = (LinearLayout) getLayoutInflater().inflate(R.layout.conference_item_view, conferenceLayout, false);

        // Set data for each TextView
        ((TextView) conferenceView.findViewById(R.id.conference_title)).setText(conference.getTitle());
        ((TextView) conferenceView.findViewById(R.id.conference_name)).setText(conference.getConferenceName());
        ((TextView) conferenceView.findViewById(R.id.conference_publisher)).setText(conference.getPublisherName());
        ((TextView) conferenceView.findViewById(R.id.conference_month_year)).setText(conference.getMonthYear());
        ((TextView) conferenceView.findViewById(R.id.conference_renewal)).setText(conference.getRenewal());
        ((TextView) conferenceView.findViewById(R.id.conference_pages)).setText(conference.getNoOfPages());

        // Add the inflated view to the conference layout
        conferenceLayout.addView(conferenceView);
    }
}
