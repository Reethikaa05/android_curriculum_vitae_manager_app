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

public class ViewProgramActivity extends AppCompatActivity {

    private LinearLayout programLayout;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_program); // Ensure you have this layout file

        // Initialize layout components
        programLayout = findViewById(R.id.program_layout);
        Button backButton = findViewById(R.id.back_button);
        databaseReference = FirebaseDatabase.getInstance().getReference("ProgramsAttended");

        fetchPrograms(); // Load the programs from Firebase

        // Back button click listener
        backButton.setOnClickListener(v -> onBackPressed()); // Go back to the previous activity
    }

    private void fetchPrograms() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                programLayout.removeAllViews(); // Clear previous views
                for (DataSnapshot programSnapshot : dataSnapshot.getChildren()) {
                    Program program = programSnapshot.getValue(Program.class);
                    if (program != null) {
                        addProgramView(program); // Add each program to the layout
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ViewProgramActivity.this, "Failed to load programs.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void addProgramView(Program program) {
        // Inflate the new layout for each program entry
        LinearLayout programEntry = (LinearLayout) getLayoutInflater().inflate(R.layout.program_item_view, programLayout, false);

        // Populate the views with program details
        ((TextView) programEntry.findViewById(R.id.program_title)).setText(program.getTitle());
        ((TextView) programEntry.findViewById(R.id.program_organized_by)).setText(program.getOrganizedBy());
        ((TextView) programEntry.findViewById(R.id.program_purpose)).setText(program.getPurpose());
        ((TextView) programEntry.findViewById(R.id.program_start_date)).setText(program.getStartDate());
        ((TextView) programEntry.findViewById(R.id.program_end_date)).setText(program.getEndDate());
        ((TextView) programEntry.findViewById(R.id.program_place)).setText(program.getPlace());
        ((TextView) programEntry.findViewById(R.id.program_type)).setText(program.getProgramType());

        // Add the program entry to the layout
        programLayout.addView(programEntry);
    }
}
