package com.example.cv;

import android.annotation.SuppressLint;
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

public class PhdActivity extends AppCompatActivity {

    private EditText scholarNameEditText, regNoEditText, regDateEditText, titleEditText, submissionDateEditText, vivaDateEditText;
    private Spinner degreeTypeSpinner, modeOfDegreeSpinner, statusSpinner;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phd);

        // Initialize UI components
        scholarNameEditText = findViewById(R.id.name_of_scholar);
        regNoEditText = findViewById(R.id.reg_no);
        regDateEditText = findViewById(R.id.date_of_registration);
        titleEditText = findViewById(R.id.title);
        submissionDateEditText = findViewById(R.id.date_of_submission);
        vivaDateEditText = findViewById(R.id.viva_date);
        degreeTypeSpinner = findViewById(R.id.type_of_degree);
        modeOfDegreeSpinner = findViewById(R.id.mode_of_degree);
        statusSpinner = findViewById(R.id.status);

        Button addButton = findViewById(R.id.add_button);
        Button saveButton = findViewById(R.id.save_button);
        Button viewButton = findViewById(R.id.view_button);
        ImageButton backButton = findViewById(R.id.back_button);

        // Initialize Firebase Database
        databaseReference = FirebaseDatabase.getInstance().getReference("PhDDetails");

        // Back button click listener
        backButton.setOnClickListener(v -> {
            finish(); // Navigate back to the previous activity
        });

        // Add button click listener
        addButton.setOnClickListener(v -> showAddDialog());

        // Save button click listener
        saveButton.setOnClickListener(v -> savePhDData());

        // View button click listener
        viewButton.setOnClickListener(v -> {
            Intent intent = new Intent(PhdActivity.this, ViewPhDActivity.class);
            startActivity(intent);
        });
    }

    private void showAddDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add PhD Details");

        View view = getLayoutInflater().inflate(R.layout.dialog_add_phd, null);
        builder.setView(view);

        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) EditText scholarName = view.findViewById(R.id.dialog_scholar_name);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"})EditText regNo = view.findViewById(R.id.dialog_registration_no);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"})EditText regDate = view.findViewById(R.id.dialog_registration_date);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"})EditText submissionDate = view.findViewById(R.id.dialog_submission_date);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"})EditText vivaDate = view.findViewById(R.id.dialog_viva_date);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"})Spinner degreeTypeSpinner = view.findViewById(R.id.dialog_degree_type_spinner);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"})Spinner modeOfDegreeSpinner = view.findViewById(R.id.dialog_mode_of_degree_spinner);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"})Spinner statusSpinner = view.findViewById(R.id.dialog_status_spinner);

        builder.setPositiveButton("Add", (dialog, which) -> {
            // Validate input and save to Firebase
            if (validateInput(scholarName, regNo, regDate, submissionDate, vivaDate)) {
                saveToFirebase(scholarName.getText().toString(), regNo.getText().toString(), regDate.getText().toString(),
                        submissionDate.getText().toString(), vivaDate.getText().toString(), degreeTypeSpinner.getSelectedItem().toString(),
                        modeOfDegreeSpinner.getSelectedItem().toString(), statusSpinner.getSelectedItem().toString());
            }
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
        builder.show();
    }

    private void savePhDData() {
        if (validateInput(scholarNameEditText, regNoEditText, regDateEditText, submissionDateEditText, vivaDateEditText)) {
            saveToFirebase(scholarNameEditText.getText().toString(), regNoEditText.getText().toString(), regDateEditText.getText().toString(),
                    submissionDateEditText.getText().toString(), vivaDateEditText.getText().toString(), degreeTypeSpinner.getSelectedItem().toString(),
                    modeOfDegreeSpinner.getSelectedItem().toString(), statusSpinner.getSelectedItem().toString());
        }
    }

    private void saveToFirebase(String scholarName, String regNo, String regDate, String submissionDate, String vivaDate,
                                String degreeType, String modeOfDegree, String status) {
        String id = databaseReference.push().getKey();
        PhDDetails phdDetails = new PhDDetails(id, scholarName, regNo, regDate, submissionDate, vivaDate, degreeType, modeOfDegree, status);
        databaseReference.child(id).setValue(phdDetails).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(PhdActivity.this, "PhD details added", Toast.LENGTH_SHORT).show();
                clearInputs();
            } else {
                Toast.makeText(PhdActivity.this, "Failed to add PhD details", Toast.LENGTH_SHORT).show();
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
        scholarNameEditText.setText("");
        regNoEditText.setText("");
        regDateEditText.setText("");
        submissionDateEditText.setText("");
        vivaDateEditText.setText("");
        degreeTypeSpinner.setSelection(0); // Reset spinner to first item
        modeOfDegreeSpinner.setSelection(0); // Reset spinner to first item
        statusSpinner.setSelection(0); // Reset spinner to first item
    }
}
