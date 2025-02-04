package com.example.cv;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ConsultancyActivity extends AppCompatActivity {

    private EditText projectTitleEditText, typeOfProjectEditText, sponsoringAgencyEditText, amountEditText,
            startDateEditText, endDateEditText, yearOfCompletionEditText;
    private Spinner statusSpinner;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consult);

        // Initialize UI components
        projectTitleEditText = findViewById(R.id.project_title);
        typeOfProjectEditText = findViewById(R.id.type_of_project);
        sponsoringAgencyEditText = findViewById(R.id.sponsoring_agency);
        amountEditText = findViewById(R.id.amount);
        startDateEditText = findViewById(R.id.start_date);
        endDateEditText = findViewById(R.id.end_date);
        yearOfCompletionEditText = findViewById(R.id.year_of_completion);
        statusSpinner = findViewById(R.id.status_spinner);

        Button addButton = findViewById(R.id.add_button);
        Button saveButton = findViewById(R.id.save_button);
        Button viewButton = findViewById(R.id.view_button);

        // Initialize Firebase Database
        databaseReference = FirebaseDatabase.getInstance().getReference("Consultancies");

        // Add button click listener
        addButton.setOnClickListener(v -> showAddDialog());

        // Save button click listener
        saveButton.setOnClickListener(v -> saveConsultancyData());

        // View button click listener
        viewButton.setOnClickListener(v -> {
            Intent intent = new Intent(ConsultancyActivity.this, ViewConsultancyActivity.class);
            startActivity(intent);
        });
    }

    private void showAddDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add Consultancy");

        View view = getLayoutInflater().inflate(R.layout.dialog_add_consultancy, null);
        builder.setView(view);

        EditText titleEditText = view.findViewById(R.id.dialog_project_title);
        EditText typeEditText = view.findViewById(R.id.dialog_type_of_project);
        EditText agencyEditText = view.findViewById(R.id.dialog_sponsoring_agency);
        EditText amountEditText = view.findViewById(R.id.dialog_amount);
        EditText startDateEditText = view.findViewById(R.id.dialog_start_date);
        EditText endDateEditText = view.findViewById(R.id.dialog_end_date);
        EditText yearEditText = view.findViewById(R.id.dialog_year_of_completion);
        Spinner statusSpinner = view.findViewById(R.id.dialog_status_spinner);

        builder.setPositiveButton("Add", (dialog, which) -> {
            // Validate input and save to Firebase
            if (validateInput(titleEditText, typeEditText, agencyEditText, amountEditText, startDateEditText,
                    endDateEditText, yearEditText)) {
                saveToFirebase(titleEditText.getText().toString(), typeEditText.getText().toString(),
                        agencyEditText.getText().toString(), amountEditText.getText().toString(),
                        startDateEditText.getText().toString(), endDateEditText.getText().toString(),
                        yearEditText.getText().toString(), statusSpinner.getSelectedItem().toString());
            }
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
        builder.show();
    }

    private void saveConsultancyData() {
        if (validateInput(projectTitleEditText, typeOfProjectEditText, sponsoringAgencyEditText, amountEditText,
                startDateEditText, endDateEditText, yearOfCompletionEditText)) {
            saveToFirebase(projectTitleEditText.getText().toString(), typeOfProjectEditText.getText().toString(),
                    sponsoringAgencyEditText.getText().toString(), amountEditText.getText().toString(),
                    startDateEditText.getText().toString(), endDateEditText.getText().toString(),
                    yearOfCompletionEditText.getText().toString(), statusSpinner.getSelectedItem().toString());
        }
    }

    private void saveToFirebase(String title, String type, String agency, String amount, String startDate,
                                String endDate, String year, String status) {
        String id = databaseReference.push().getKey();
        Consultancy consultancy = new Consultancy(id, title, type, agency, amount, startDate, endDate, year, status);
        databaseReference.child(id).setValue(consultancy).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(ConsultancyActivity.this, "Consultancy added", Toast.LENGTH_SHORT).show();
                clearInputs();
            } else {
                Toast.makeText(ConsultancyActivity.this, "Failed to add consultancy", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean validateInput(EditText... inputs) {
        for (EditText input : inputs) {
            if (input.getText().toString().trim().isEmpty()) {
                input.setError("Required");
                return false;
            }
        }
        return true;
    }

    private void clearInputs() {
        projectTitleEditText.setText("");
        typeOfProjectEditText.setText("");
        sponsoringAgencyEditText.setText("");
        amountEditText.setText("");
        startDateEditText.setText("");
        endDateEditText.setText("");
        yearOfCompletionEditText.setText("");
        statusSpinner.setSelection(0); // Reset spinner to first item
    }
}

