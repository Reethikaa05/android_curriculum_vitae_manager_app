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

public class BookChapterActivity extends AppCompatActivity {

    private EditText bookTitle, pubName, monthYear, bookChapterNumber, edition, isbnNo, noOfPages, author1, author2, author3;

    // Firebase Database reference
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_chapter);

        // Initialize Firebase Database
        databaseReference = FirebaseDatabase.getInstance().getReference("BookChapters");

        // Initialize UI elements
        bookTitle = findViewById(R.id.book_title);
        pubName = findViewById(R.id.pub_name);
        monthYear = findViewById(R.id.month_year);
        bookChapterNumber = findViewById(R.id.book_chapter_number);
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
        saveButton.setOnClickListener(v -> saveBookChapter());

        // View details button click listener
        viewButton.setOnClickListener(v -> viewBookChapters());

        // Add button click listener
        addButton.setOnClickListener(v -> showAddBookDialog());
    }

    private void saveBookChapter() {
        String title = bookTitle.getText().toString().trim();
        String publisher = pubName.getText().toString().trim();
        String monthYearText = monthYear.getText().toString().trim();
        String chapterNumber = bookChapterNumber.getText().toString().trim();
        String editionText = edition.getText().toString().trim();
        String isbn = isbnNo.getText().toString().trim();
        String pages = noOfPages.getText().toString().trim();
        String author1Text = author1.getText().toString().trim();
        String author2Text = author2.getText().toString().trim();
        String author3Text = author3.getText().toString().trim();

        if (title.isEmpty() || publisher.isEmpty() || monthYearText.isEmpty() || chapterNumber.isEmpty() ||
                editionText.isEmpty() || isbn.isEmpty() || pages.isEmpty() || author1Text.isEmpty()) {
            Toast.makeText(this, "Please fill all required fields", Toast.LENGTH_SHORT).show();
            return;
        }

        String id = databaseReference.push().getKey(); // Generate unique ID
        BookChapter bookChapter = new BookChapter(id, title, publisher, monthYearText, chapterNumber,
                editionText, isbn, pages, author1Text, author2Text, author3Text);

        // Save to Firebase
        databaseReference.child(id).setValue(bookChapter)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(BookChapterActivity.this, "Book chapter saved", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(BookChapterActivity.this, "Failed to save chapter", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void viewBookChapters() {
        Intent intent = new Intent(BookChapterActivity.this, ViewBookChaptersActivity.class);
        startActivity(intent);
    }


    private void showAddBookDialog() {
        // Implement AlertDialog to add a new book chapter
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add Book Chapter");

        // Create a LinearLayout to hold the EditTexts
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        // Create EditTexts for user input
        final EditText titleInput = new EditText(this);
        titleInput.setHint("Title");
        layout.addView(titleInput);

        final EditText publisherInput = new EditText(this);
        publisherInput.setHint("Publisher");
        layout.addView(publisherInput);

        final EditText monthYearInput = new EditText(this);
        monthYearInput.setHint("Month & Year");
        layout.addView(monthYearInput);

        final EditText chapterNumberInput = new EditText(this);
        chapterNumberInput.setHint("Chapter Number");
        layout.addView(chapterNumberInput);

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

        // Add more EditTexts as needed...

        builder.setView(layout);

        builder.setPositiveButton("Add", (dialog, which) -> {
            String title = titleInput.getText().toString().trim();
            String publisher = publisherInput.getText().toString().trim();
            String monthYearText = monthYearInput.getText().toString().trim();
            String chapterNumber = chapterNumberInput.getText().toString().trim();
            String edition = editionInput.getText().toString().trim();
            String isbn = isbnInput.getText().toString().trim();
            String pages = pagesInput.getText().toString().trim();
            String author1 = author1Input.getText().toString().trim();
            String author2 = author2Input.getText().toString().trim();
            String author3 = author3Input.getText().toString().trim();

            // Handle adding the new book chapter here
            // e.g., save to database or list

            if (!title.isEmpty() && !publisher.isEmpty()) {
                String id = databaseReference.push().getKey(); // Generate unique ID
                BookChapter newBookChapter = new BookChapter(id, title, publisher,monthYearText,chapterNumber,edition,isbn, pages, author1,author2, author3); // Add other fields as needed

                databaseReference.child(id).setValue(newBookChapter)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Toast.makeText(BookChapterActivity.this, "Book chapter added", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(BookChapterActivity.this, "Failed to add chapter", Toast.LENGTH_SHORT).show();
                            }
                        });
            } else {
                Toast.makeText(BookChapterActivity.this, "Please fill required fields", Toast.LENGTH_SHORT).show();
            }

            dialog.dismiss();
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        builder.show();
    }
}
