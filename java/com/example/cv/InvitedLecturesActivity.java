package com.example.cv;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class InvitedLecturesActivity extends AppCompatActivity {

    private EditText lectureTitle, purpose, organizedBy, startDate, endDate, place;

    // Firebase Database reference
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lectures);  // Ensure you have created this layout

        // Initialize Firebase Database
        databaseReference = FirebaseDatabase.getInstance().getReference("InvitedLectures");

        // Initialize UI elements
        lectureTitle = findViewById(R.id.lecture_title);
        purpose = findViewById(R.id.purpose);
        organizedBy = findViewById(R.id.organized_by);
        startDate = findViewById(R.id.start_date);
        endDate = findViewById(R.id.end_date);
        place = findViewById(R.id.place);

        Button saveButton = findViewById(R.id.save_button);
        Button viewButton = findViewById(R.id.view_button);
        Button addButton = findViewById(R.id.add_button); // Add button for adding a new invited lecture
        ImageButton backButton = findViewById(R.id.back_button); // Back button

        // Back button click listener
        backButton.setOnClickListener(v -> onBackPressed());

        // Save button click listener
        saveButton.setOnClickListener(v -> saveLecture());

        // View details button click listener
        viewButton.setOnClickListener(v -> viewLectures());

        // Add button click listener
        addButton.setOnClickListener(v -> showAddLectureDialog());
    }

    private void saveLecture() {
        String title = lectureTitle.getText().toString().trim();
        String purposeText = purpose.getText().toString().trim();
        String organizedByText = organizedBy.getText().toString().trim();
        String startDateText = startDate.getText().toString().trim();
        String endDateText = endDate.getText().toString().trim();
        String placeText = place.getText().toString().trim();

        if (title.isEmpty() || purposeText.isEmpty() || organizedByText.isEmpty() || startDateText.isEmpty() || endDateText.isEmpty()) {
            Toast.makeText(this, "Please fill all required fields", Toast.LENGTH_SHORT).show();
            return;
        }

        String id = databaseReference.push().getKey(); // Generate unique ID
        InvitedLecture invitedLecture = new InvitedLecture(id, title, purposeText, organizedByText, startDateText, endDateText, placeText);

        // Save to Firebase
        databaseReference.child(id).setValue(invitedLecture)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(InvitedLecturesActivity.this, "Lecture entry saved", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(InvitedLecturesActivity.this, "Failed to save lecture entry", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void viewLectures() {
        Intent intent = new Intent(InvitedLecturesActivity.this, ViewInvitedLecturesActivity.class);  // Create ViewInvitedLecturesActivity to display lectures
        startActivity(intent);
    }

    private void showAddLectureDialog() {
        // Implement AlertDialog to add a new invited lecture entry
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add Invited Lecture");

        // Create a LinearLayout to hold the EditTexts
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        // Create EditTexts for user input
        final EditText titleInput = new EditText(this);
        titleInput.setHint("Lecture Title");
        layout.addView(titleInput);

        final EditText purposeInput = new EditText(this);
        purposeInput.setHint("Purpose");
        layout.addView(purposeInput);

        final EditText organizedByInput = new EditText(this);
        organizedByInput.setHint("Organized By");
        layout.addView(organizedByInput);

        final EditText startDateInput = new EditText(this);
        startDateInput.setHint("Start Date (YYYY-MM-DD)");
        layout.addView(startDateInput);

        final EditText endDateInput = new EditText(this);
        endDateInput.setHint("End Date (YYYY-MM-DD)");
        layout.addView(endDateInput);

        final EditText placeInput = new EditText(this);
        placeInput.setHint("Place");
        layout.addView(placeInput);

        builder.setView(layout);

        builder.setPositiveButton("Add", (dialog, which) -> {
            String title = titleInput.getText().toString().trim();
            String purpose = purposeInput.getText().toString().trim();
            String organizedBy = organizedByInput.getText().toString().trim();
            String startDate = startDateInput.getText().toString().trim();
            String endDate = endDateInput.getText().toString().trim();
            String place = placeInput.getText().toString().trim();

            if (!title.isEmpty() && !purpose.isEmpty() && !organizedBy.isEmpty() && !startDate.isEmpty() && !endDate.isEmpty()) {
                String id = databaseReference.push().getKey(); // Generate unique ID
                InvitedLecture newLecture = new InvitedLecture(id, title, purpose, organizedBy, startDate, endDate, place);

                // Save to Firebase
                databaseReference.child(id).setValue(newLecture)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Toast.makeText(InvitedLecturesActivity.this, "Lecture entry added", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(InvitedLecturesActivity.this, "Failed to add lecture entry", Toast.LENGTH_SHORT).show();
                            }
                        });
            } else {
                Toast.makeText(InvitedLecturesActivity.this, "Please fill all required fields", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

        builder.show();
    }
}
