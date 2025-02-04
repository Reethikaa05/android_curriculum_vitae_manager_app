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

public class JournalActivity extends AppCompatActivity {

    private EditText articleTitle, pubName, MonthYear, volumeNumber, edition, isbnNo, noOfPages, author1, author2, author3;

    // Firebase Database reference
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journal);  // Ensure you create this layout for journals

        // Initialize Firebase Database
        databaseReference = FirebaseDatabase.getInstance().getReference("Journals");

        // Initialize UI elements
        articleTitle = findViewById(R.id.article_title);
        pubName = findViewById(R.id.pub_name);
        MonthYear = findViewById(R.id.month_year);
        volumeNumber = findViewById(R.id.volume_number);
        edition = findViewById(R.id.edition);
        isbnNo = findViewById(R.id.isbn_no);
        noOfPages = findViewById(R.id.no_of_pages);
        author1 = findViewById(R.id.author1);
        author2 = findViewById(R.id.author2);
        author3 = findViewById(R.id.author3);

        Button saveButton = findViewById(R.id.save_button);
        Button viewButton = findViewById(R.id.view_button);
        Button addButton = findViewById(R.id.add_button); // Add button to show AlertDialog
        ImageButton backButton = findViewById(R.id.back_button); // Back button

        // Back button click listener
        backButton.setOnClickListener(v -> onBackPressed());

        // Save button click listener
        saveButton.setOnClickListener(v -> saveJournal());

        // View details button click listener
        viewButton.setOnClickListener(v -> viewJournals());

        // Add button click listener
        addButton.setOnClickListener(v -> showAddJournalDialog());
    }

    private void saveJournal() {
        String title = articleTitle.getText().toString().trim();
        String journal = pubName.getText().toString().trim();
        String monthYearText = MonthYear.getText().toString().trim();
        String volumeText = volumeNumber.getText().toString().trim();
        String issue = edition.getText().toString().trim();
        String issn = isbnNo.getText().toString().trim();
        String pagesText = noOfPages.getText().toString().trim();
        String author1Text = author1.getText().toString().trim();
        String author2Text = author2.getText().toString().trim();
        String author3Text = author3.getText().toString().trim();

        if (title.isEmpty() || journal.isEmpty() || monthYearText.isEmpty() || volumeText.isEmpty() ||
                issue.isEmpty() || issn.isEmpty() || pagesText.isEmpty() || author1Text.isEmpty()) {
            Toast.makeText(this, "Please fill all required fields", Toast.LENGTH_SHORT).show();
            return;
        }

        String id = databaseReference.push().getKey(); // Generate unique ID
        Journal journalEntry = new Journal(id, title, journal, monthYearText, volumeText, issue, issn, pagesText, author1Text, author2Text, author3Text);

        // Save to Firebase
        databaseReference.child(id).setValue(journalEntry)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(JournalActivity.this, "Journal entry saved", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(JournalActivity.this, "Failed to save journal entry", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void viewJournals() {
        Intent intent = new Intent(JournalActivity.this, ViewJournalsActivity.class);  // Create ViewJournalsActivity to display journals
        startActivity(intent);
    }

    private void showAddJournalDialog() {
        // Implement AlertDialog to add a new journal entry
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add Journal");

        // Create a LinearLayout to hold the EditTexts
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        // Create EditTexts for user input
        final EditText titleInput = new EditText(this);
        titleInput.setHint("Article Title");
        layout.addView(titleInput);

        final EditText journalInput = new EditText(this);
        journalInput.setHint("Publisher Name");
        layout.addView(journalInput);

        final EditText monthYearInput = new EditText(this);
        monthYearInput.setHint("Month & Year");
        layout.addView(monthYearInput);

        final EditText volumeInput = new EditText(this);
        volumeInput.setHint("Volume");
        layout.addView(volumeInput);

        final EditText editionInput = new EditText(this);
        editionInput.setHint("Edition");
        layout.addView(editionInput);

        final EditText isbnInput = new EditText(this);
        isbnInput.setHint("ISBN");
        layout.addView(isbnInput);

        final EditText pagesInput = new EditText(this);
        pagesInput.setHint("Page Number");
        layout.addView(pagesInput);

        final EditText author1Input = new EditText(this);
        author1Input.setHint("Author 1");
        layout.addView(author1Input);

        final EditText author2Input = new EditText(this);
        author2Input.setHint("Author 2");
        layout.addView(author2Input);

        final EditText author3Input = new EditText(this);
        author3Input.setHint("Author 3");
        layout.addView(author3Input);

        builder.setView(layout);

        builder.setPositiveButton("Add", (dialog, which) -> {
            String title = titleInput.getText().toString().trim();
            String journal = journalInput.getText().toString().trim();
            String monthYearText = monthYearInput.getText().toString().trim();
            String volumeText = volumeInput.getText().toString().trim();
            String edition = editionInput.getText().toString().trim();
            String isbn = isbnInput.getText().toString().trim();
            String pages = pagesInput.getText().toString().trim();
            String author1 = author1Input.getText().toString().trim();
            String author2 = author2Input.getText().toString().trim();
            String author3 = author3Input.getText().toString().trim();

            if (!title.isEmpty() && !journal.isEmpty()) {
                String id = databaseReference.push().getKey(); // Generate unique ID
                Journal newJournal = new Journal(id, title, journal, monthYearText, volumeText, edition, isbn, pages, author1, author2, author3);

                databaseReference.child(id).setValue(newJournal)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Toast.makeText(JournalActivity.this, "Journal added", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(JournalActivity.this, "Failed to add journal", Toast.LENGTH_SHORT).show();
                            }
                        });
            } else {
                Toast.makeText(JournalActivity.this, "Please fill required fields", Toast.LENGTH_SHORT).show();
            }

            dialog.dismiss();
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        builder.show();
    }
}
