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

public class PatentActivity extends AppCompatActivity {

    private EditText patentTitle, purpose, filedOn, publishedDate, author1, author2;

    // Firebase Database reference
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patent);  // Ensure you have created this layout

        // Initialize Firebase Database
        databaseReference = FirebaseDatabase.getInstance().getReference("Patents");

        // Initialize UI elements
        patentTitle = findViewById(R.id.patent_title);
        purpose = findViewById(R.id.purpose);
        filedOn = findViewById(R.id.filed_on);
        publishedDate = findViewById(R.id.published_date);
        author1 = findViewById(R.id.author1);
        author2 = findViewById(R.id.author2);

        Button saveButton = findViewById(R.id.save_button);
        Button viewButton = findViewById(R.id.view_button);
        Button addButton = findViewById(R.id.add_button); // Add button for adding a new patent
        ImageButton backButton = findViewById(R.id.back_button); // Back button

        // Back button click listener
        backButton.setOnClickListener(v -> onBackPressed());

        // Save button click listener
        saveButton.setOnClickListener(v -> savePatent());

        // View details button click listener
        viewButton.setOnClickListener(v -> viewPatents());

        // Add button click listener
        addButton.setOnClickListener(v -> showAddPatentDialog());
    }

    private void savePatent() {
        String title = patentTitle.getText().toString().trim();
        String purposeText = purpose.getText().toString().trim();
        String filedOnText = filedOn.getText().toString().trim();
        String publishedDateText = publishedDate.getText().toString().trim();
        String author1Text = author1.getText().toString().trim();
        String author2Text = author2.getText().toString().trim();

        if (title.isEmpty() || purposeText.isEmpty() || filedOnText.isEmpty() || publishedDateText.isEmpty() || author1Text.isEmpty()) {
            Toast.makeText(this, "Please fill all required fields", Toast.LENGTH_SHORT).show();
            return;
        }

        String id = databaseReference.push().getKey(); // Generate unique ID
        Patent patent = new Patent(id, title, purposeText, filedOnText, publishedDateText, author1Text, author2Text);

        // Save to Firebase
        databaseReference.child(id).setValue(patent)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(PatentActivity.this, "Patent entry saved", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(PatentActivity.this, "Failed to save patent entry", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void viewPatents() {
        Intent intent = new Intent(PatentActivity.this, ViewPatentsActivity.class);  // Create ViewPatentsActivity to display patents
        startActivity(intent);
    }

    private void showAddPatentDialog() {
        // Implement AlertDialog to add a new patent entry
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add Patent");

        // Create a LinearLayout to hold the EditTexts
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        // Create EditTexts for user input
        final EditText titleInput = new EditText(this);
        titleInput.setHint("Patent Title");
        layout.addView(titleInput);

        final EditText purposeInput = new EditText(this);
        purposeInput.setHint("Purpose");
        layout.addView(purposeInput);

        final EditText filedOnInput = new EditText(this);
        filedOnInput.setHint("Filed On (YYYY-MM-DD)");
        layout.addView(filedOnInput);

        final EditText publishedDateInput = new EditText(this);
        publishedDateInput.setHint("Published Date (YYYY-MM-DD)");
        layout.addView(publishedDateInput);

        final EditText author1Input = new EditText(this);
        author1Input.setHint("Author 1");
        layout.addView(author1Input);

        final EditText author2Input = new EditText(this);
        author2Input.setHint("Author 2");
        layout.addView(author2Input);

        builder.setView(layout);

        builder.setPositiveButton("Add", (dialog, which) -> {
            String title = titleInput.getText().toString().trim();
            String purpose = purposeInput.getText().toString().trim();
            String filedOn = filedOnInput.getText().toString().trim();
            String publishedDate = publishedDateInput.getText().toString().trim();
            String author1 = author1Input.getText().toString().trim();
            String author2 = author2Input.getText().toString().trim();

            if (!title.isEmpty() && !purpose.isEmpty() && !filedOn.isEmpty() && !publishedDate.isEmpty() && !author1.isEmpty()) {
                String id = databaseReference.push().getKey(); // Generate unique ID
                Patent newPatent = new Patent(id, title, purpose, filedOn, publishedDate, author1, author2);

                // Save to Firebase
                databaseReference.child(id).setValue(newPatent)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Toast.makeText(PatentActivity.this, "Patent entry added", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(PatentActivity.this, "Failed to add patent entry", Toast.LENGTH_SHORT).show();
                            }
                        });
            } else {
                Toast.makeText(PatentActivity.this, "Please fill all required fields", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

        builder.show();
    }
}
