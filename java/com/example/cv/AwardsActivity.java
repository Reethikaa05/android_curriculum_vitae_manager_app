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

public class AwardsActivity extends AppCompatActivity {

    private EditText awardTitleEditText, awardingAgencyEditText, purposeEditText, yearEditText, monthEditText;
    private Spinner typeSpinner;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_award);

        // Initialize UI components
        awardTitleEditText = findViewById(R.id.award_name);
        awardingAgencyEditText = findViewById(R.id.sponsoring_agency);
        purposeEditText = findViewById(R.id.purpose);
        yearEditText = findViewById(R.id.year_of_award);
        monthEditText = findViewById(R.id.month);
        typeSpinner = findViewById(R.id.type_of_award);

        Button addButton = findViewById(R.id.add_button);
        Button saveButton = findViewById(R.id.save_button);
        Button viewButton = findViewById(R.id.view_button);
        ImageButton backButton = findViewById(R.id.back_button);

        // Initialize Firebase Database
        databaseReference = FirebaseDatabase.getInstance().getReference("Awards");

        // Back button click listener
        backButton.setOnClickListener(v -> {
            finish(); // Navigate back to the previous activity
        });

        // Add button click listener
        addButton.setOnClickListener(v -> showAddDialog());

        // Save button click listener
        saveButton.setOnClickListener(v -> saveAwardData());

        // View button click listener
        viewButton.setOnClickListener(v -> {
            Intent intent = new Intent(AwardsActivity.this, ViewAwardActivity.class);
            startActivity(intent);
        });
    }

    private void showAddDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add Award");

        View view = getLayoutInflater().inflate(R.layout.dialog_add_award, null);
        builder.setView(view);

        EditText titleEditText = view.findViewById(R.id.dialog_award_title);
        EditText agencyEditText = view.findViewById(R.id.dialog_awarding_agency);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) EditText purposeEditText = view.findViewById(R.id.dialog_award_purpose);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) EditText monthEditText = view.findViewById(R.id.dialog_month);
        EditText yearEditText = view.findViewById(R.id.dialog_award_year);
        Spinner typeSpinner = view.findViewById(R.id.dialog_status_spinner);

        builder.setPositiveButton("Add", (dialog, which) -> {
            // Validate input and save to Firebase
            if (validateInput(titleEditText, agencyEditText, purposeEditText, yearEditText, monthEditText)) {
                saveToFirebase(titleEditText.getText().toString(), agencyEditText.getText().toString(),
                        purposeEditText.getText().toString(), yearEditText.getText().toString(),
                        monthEditText.getText().toString(), typeSpinner.getSelectedItem().toString());
            }
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
        builder.show();
    }

    private void saveAwardData() {
        if (validateInput(awardTitleEditText, awardingAgencyEditText, purposeEditText, yearEditText, monthEditText)) {
            saveToFirebase(awardTitleEditText.getText().toString(), awardingAgencyEditText.getText().toString(),
                    purposeEditText.getText().toString(), yearEditText.getText().toString(),
                    monthEditText.getText().toString(), typeSpinner.getSelectedItem().toString());
        }
    }

    private void saveToFirebase(String title, String agency, String purpose, String year, String month, String type) {
        String id = databaseReference.push().getKey();
        Award award = new Award(id, title, agency, purpose, month, year, type);
        databaseReference.child(id).setValue(award).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(AwardsActivity.this, "Award added", Toast.LENGTH_SHORT).show();
                clearInputs();
            } else {
                Toast.makeText(AwardsActivity.this, "Failed to add award", Toast.LENGTH_SHORT).show();
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
        awardTitleEditText.setText("");
        awardingAgencyEditText.setText("");
        purposeEditText.setText("");
        yearEditText.setText("");
        monthEditText.setText("");
        typeSpinner.setSelection(0); // Reset spinner to first item
    }
}
