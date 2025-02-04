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

public class ViewConsultancyActivity extends AppCompatActivity {

    private LinearLayout consultancyLayout;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_consult);

        // Initialize layout components
        consultancyLayout = findViewById(R.id.consultancy_layout);
        Button backButton = findViewById(R.id.back_button);
        databaseReference = FirebaseDatabase.getInstance().getReference("Consultancies");

        // Fetch and display consultancies from Firebase
        fetchConsultancies();

        // Back button click listener
        backButton.setOnClickListener(v -> finish()); // Close the activity
    }

    private void fetchConsultancies() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Clear existing views to prevent duplication
                consultancyLayout.removeAllViews();

                for (DataSnapshot consultancySnapshot : dataSnapshot.getChildren()) {
                    Consultancy consultancy = consultancySnapshot.getValue(Consultancy.class);
                    if (consultancy != null) {
                        addConsultancyView(consultancy);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ViewConsultancyActivity.this, "Failed to load consultancies.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void addConsultancyView(Consultancy consultancy) {
        // Inflate a custom layout for each consultancy entry
        LinearLayout consultancyView = (LinearLayout) getLayoutInflater().inflate(R.layout.consultancy_item_view, consultancyLayout, false);

        // Set data for each TextView in the inflated layout
        ((TextView) consultancyView.findViewById(R.id.consultancy_project_title)).setText("Project Title: " + consultancy.getProjectTitle());
        ((TextView) consultancyView.findViewById(R.id.consultancy_type_of_project)).setText("Type of Project: " + consultancy.getTypeOfProject());
        ((TextView) consultancyView.findViewById(R.id.consultancy_sponsoring_agency)).setText("Sponsoring Agency: " + consultancy.getSponsoringAgency());
        ((TextView) consultancyView.findViewById(R.id.consultancy_amount)).setText("Amount: " + consultancy.getAmount());
        ((TextView) consultancyView.findViewById(R.id.consultancy_start_date)).setText("Start Date: " + consultancy.getStartDate());
        ((TextView) consultancyView.findViewById(R.id.consultancy_end_date)).setText("End Date: " + consultancy.getEndDate());
        ((TextView) consultancyView.findViewById(R.id.consultancy_year_of_completion)).setText("Year of Completion: " + consultancy.getYearOfCompletion());
        ((TextView) consultancyView.findViewById(R.id.consultancy_status)).setText("Status: " + consultancy.getStatus());

        // Add the inflated view to the consultancy layout
        consultancyLayout.addView(consultancyView);
    }
}
