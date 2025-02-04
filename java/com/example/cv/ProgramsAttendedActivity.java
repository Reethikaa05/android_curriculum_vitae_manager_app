package com.example.cv;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProgramsAttendedActivity extends AppCompatActivity {

    private EditText titleOfProgramEditText, organizedByEditText, purposeEditText, startDateEditText, endDateEditText, placeEditText;
    private Spinner programTypeSpinner;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_programs);

        // Initialize UI components
        programTypeSpinner = findViewById(R.id.program_type_spinner);
        titleOfProgramEditText = findViewById(R.id.program_title);
        organizedByEditText = findViewById(R.id.organized_by);
        purposeEditText = findViewById(R.id.purpose);
        startDateEditText = findViewById(R.id.start_date);
        endDateEditText = findViewById(R.id.end_date);
        placeEditText = findViewById(R.id.place);

        Button addButton = findViewById(R.id.add_button);
        Button saveButton = findViewById(R.id.save_button);
        Button viewButton = findViewById(R.id.view_button);
        ImageButton backButton = findViewById(R.id.back_button);

        // Initialize Firebase Database
        databaseReference = FirebaseDatabase.getInstance().getReference("ProgramsAttended");

        // Back button click listener
        backButton.setOnClickListener(v -> {
            finish(); // Navigate back to the previous activity
        });

        // Add button click listener
        addButton.setOnClickListener(v -> showAddDialog());

        // Save button click listener
        saveButton.setOnClickListener(v -> saveProgramData());

        // View button click listener
        viewButton.setOnClickListener(v -> {
            Intent intent = new Intent(ProgramsAttendedActivity.this, ViewProgramActivity.class);
            startActivity(intent);
        });
    }

    private void showAddDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add Program");

        View view = getLayoutInflater().inflate(R.layout.dialog_add_program, null);
        builder.setView(view);

        EditText titleEditText = view.findViewById(R.id.dialog_program_title);
        EditText organizedByEditText = view.findViewById(R.id.dialog_organized_by);
        EditText purposeEditText = view.findViewById(R.id.dialog_program_purpose);
        EditText startDateEditText = view.findViewById(R.id.dialog_start_date);
        EditText endDateEditText = view.findViewById(R.id.dialog_end_date);
        EditText placeEditText = view.findViewById(R.id.dialog_place);
        Spinner programTypeSpinner = view.findViewById(R.id.dialog_program_type_spinner);

        builder.setPositiveButton("Add", (dialog, which) -> {
            if (validateInput(titleEditText, organizedByEditText, purposeEditText, startDateEditText, endDateEditText, placeEditText)) {
                saveToFirebase(titleEditText.getText().toString(), organizedByEditText.getText().toString(),
                        purposeEditText.getText().toString(), startDateEditText.getText().toString(),
                        endDateEditText.getText().toString(), placeEditText.getText().toString(),
                        programTypeSpinner.getSelectedItem().toString());
                dialog.dismiss();
            } else {
                Toast.makeText(ProgramsAttendedActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private boolean validateInput(EditText... fields) {
        for (EditText field : fields) {
            if (field.getText().toString().trim().isEmpty()) {
                return false;
            }
        }
        return true;
    }

    private void saveToFirebase(String title, String organizedBy, String purpose, String startDate, String endDate, String place, String programType) {
        String programId = databaseReference.push().getKey();
        if (programId != null) {
            Program program = new Program(programId, title, organizedBy, purpose, startDate, endDate, place, programType);
            databaseReference.child(programId).setValue(program).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(ProgramsAttendedActivity.this, "Program added successfully!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ProgramsAttendedActivity.this, "Error adding program: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(ProgramsAttendedActivity.this, "Error generating program ID", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveProgramData() {
        if (validateInput(titleOfProgramEditText, organizedByEditText, purposeEditText, startDateEditText, endDateEditText, placeEditText)) {
            String title = titleOfProgramEditText.getText().toString();
            String organizedBy = organizedByEditText.getText().toString();
            String purpose = purposeEditText.getText().toString();
            String startDate = startDateEditText.getText().toString();
            String endDate = endDateEditText.getText().toString();
            String place = placeEditText.getText().toString();
            String programType = programTypeSpinner.getSelectedItem().toString();

            saveToFirebase(title, organizedBy, purpose, startDate, endDate, place, programType);
        } else {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
        }
    }
}
