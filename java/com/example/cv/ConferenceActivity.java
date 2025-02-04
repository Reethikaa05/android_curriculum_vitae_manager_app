package com.example.cv;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ConferenceActivity extends AppCompatActivity {

    private EditText articleTitleEditText, conferenceNameEditText, publisherNameEditText, monthYearEditText, renewalEditText, noOfPagesEditText;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conference);

        // Initialize UI components
        articleTitleEditText = findViewById(R.id.article_title);
        conferenceNameEditText = findViewById(R.id.conference_name);
        publisherNameEditText = findViewById(R.id.publisher_name);
        monthYearEditText = findViewById(R.id.month_year);
        renewalEditText = findViewById(R.id.renewal);
        noOfPagesEditText = findViewById(R.id.no_of_pages);

        Button addButton = findViewById(R.id.add_button);
        Button saveButton = findViewById(R.id.save_button);
        Button viewButton = findViewById(R.id.view_button);
        ImageButton backButton = findViewById(R.id.back_button);

        // Initialize Firebase Database
        databaseReference = FirebaseDatabase.getInstance().getReference("ConferenceArticles");

        // Back button click listener
        backButton.setOnClickListener(v -> {
            finish(); // Navigate back to the previous activity
        });

        // Add button click listener
        addButton.setOnClickListener(v -> showAddDialog());

        // Save button click listener
        saveButton.setOnClickListener(v -> saveArticleData());

        // View button click listener
        viewButton.setOnClickListener(v -> {
            Intent intent = new Intent(ConferenceActivity.this, ViewConferenceActivity.class);
            startActivity(intent);
        });
    }

    private void showAddDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add Conference Paper");

        View view = getLayoutInflater().inflate(R.layout.dialog_add_conference, null);
        builder.setView(view);

        @SuppressLint({"MissingInflatedId", "LocalSuppress"})EditText titleEditText = view.findViewById(R.id.dialog_article_title);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) EditText conferenceNameEditText = view.findViewById(R.id.dialog_conference_name);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"})EditText publisherNameEditText = view.findViewById(R.id.dialog_publisher_name);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"})EditText monthYearEditText = view.findViewById(R.id.dialog_month_year);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"})EditText renewalEditText = view.findViewById(R.id.dialog_renewal);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"})EditText noOfPagesEditText = view.findViewById(R.id.dialog_no_of_pages);

        builder.setPositiveButton("Add", (dialog, which) -> {
            if (validateInput(titleEditText, conferenceNameEditText, publisherNameEditText, monthYearEditText, renewalEditText, noOfPagesEditText)) {
                saveToFirebase(
                        titleEditText.getText().toString(),
                        conferenceNameEditText.getText().toString(),
                        publisherNameEditText.getText().toString(),
                        monthYearEditText.getText().toString(),
                        renewalEditText.getText().toString(),
                        noOfPagesEditText.getText().toString()
                );
                dialog.dismiss();
            } else {
                Toast.makeText(ConferenceActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
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

    private void saveToFirebase(String title, String conferenceName, String publisherName, String monthYear, String renewal, String noOfPages) {
        String articleId = databaseReference.push().getKey();
        if (articleId != null) {
            Conference article = new Conference(articleId, title, conferenceName, publisherName, monthYear, renewal, noOfPages);
            databaseReference.child(articleId).setValue(article).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(ConferenceActivity.this, "Conference Paper added successfully!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ConferenceActivity.this, "Error adding article: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(ConferenceActivity.this, "Error generating article ID", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveArticleData() {
        if (validateInput(articleTitleEditText, conferenceNameEditText, publisherNameEditText, monthYearEditText, renewalEditText, noOfPagesEditText)) {
            String title = articleTitleEditText.getText().toString();
            String conferenceName = conferenceNameEditText.getText().toString();
            String publisherName = publisherNameEditText.getText().toString();
            String monthYear = monthYearEditText.getText().toString();
            String renewal = renewalEditText.getText().toString();
            String noOfPages = noOfPagesEditText.getText().toString();

            saveToFirebase(title, conferenceName, publisherName, monthYear, renewal, noOfPages);
        } else {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
        }
    }
}
