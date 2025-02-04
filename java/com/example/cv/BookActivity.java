package com.example.cv;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/** @noinspection ALL*/
public class BookActivity extends AppCompatActivity {

    private DatabaseReference booksRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        // Initialize Firebase Database and Reference
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        booksRef = database.getReference("Books"); // Store books under "Books" node

        ImageButton backButton = findViewById(R.id.back_button);
        Button addButton = findViewById(R.id.form_book_btn_add);
        Button viewDetailsButton = findViewById(R.id.view_details_button);
        Button saveButton = findViewById(R.id.save_button);  // New Save Button

        // Back button functionality
        backButton.setOnClickListener(v -> onBackPressed());

        // Add button functionality
        addButton.setOnClickListener(v -> showAddBookDialog());

        // View Details button functionality (to see stored books)
        viewDetailsButton.setOnClickListener(v -> {
            Intent intent = new Intent(BookActivity.this, ViewBookActivity.class);
            startActivity(intent); // Navigate to ViewBookActivity
        });

        // Save button functionality
        saveButton.setOnClickListener(v -> {
            saveBookDetails();  // Call the method to save book details
        });
    }

    // Method to show the dialog box to add book details
    private void showAddBookDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_add_book, null);
        dialogBuilder.setView(dialogView);
        dialogBuilder.setTitle("Add New Book"); // Set title for the AlertDialog

        // Find the input fields in the dialog
        TextInputEditText bookTitle = dialogView.findViewById(R.id.book_title);
        TextInputEditText pubName = dialogView.findViewById(R.id.pub_name);
        TextInputEditText monthYear = dialogView.findViewById(R.id.month_year);
        TextInputEditText edition = dialogView.findViewById(R.id.edition);
        TextInputEditText isbnNo = dialogView.findViewById(R.id.isbn_no);
        TextInputEditText noOfPages = dialogView.findViewById(R.id.no_of_pages);
        TextInputEditText author1 = dialogView.findViewById(R.id.author1);
        TextInputEditText author2 = dialogView.findViewById(R.id.author2);
        TextInputEditText author3 = dialogView.findViewById(R.id.author3);

        Button adButton = dialogView.findViewById(R.id.add_button);
        Button cancelButton = dialogView.findViewById(R.id.cancel_button);

        // Create the dialog
        AlertDialog alertDialog = dialogBuilder.create();

        // Save button action in the dialog
        adButton.setOnClickListener(v -> {
            String title = bookTitle.getText().toString();
            String publisher = pubName.getText().toString();
            String date = monthYear.getText().toString();
            String bookEdition = edition.getText().toString();
            String isbn = isbnNo.getText().toString();
            String pages = noOfPages.getText().toString();
            String author1Name = author1.getText().toString();
            String author2Name = author2.getText().toString();
            String author3Name = author3.getText().toString();

            // Check if the important fields are filled
            if (title.isEmpty() || publisher.isEmpty() || date.isEmpty()) {
                Toast.makeText(BookActivity.this, "Please fill in the required fields.", Toast.LENGTH_SHORT).show();
                return;
            }

            // Ensure booksRef is not null before pushing
            if (booksRef != null) {
                // Generate a unique ID for each book
                String bookId = booksRef.push().getKey();

                // Create a new Book object
                Book book = new Book(bookId, title, publisher, date, bookEdition, isbn, pages, author1Name, author2Name, author3Name);

                // Save the book to Firebase under its unique ID
                if (bookId != null) {
                    booksRef.child(bookId).setValue(book);
                    Toast.makeText(BookActivity.this, "Book added successfully!", Toast.LENGTH_SHORT).show();
                }

                // Close the dialog after saving
                alertDialog.dismiss();
            }
        });

        // Cancel button action
        cancelButton.setOnClickListener(v -> alertDialog.dismiss());

        // Show the dialog
        alertDialog.show();
    }

    // Save button method to save book details (called when "Save" is clicked)
    private void saveBookDetails() {
        // You can call the same logic here that you use in the dialog's "Add" button to save book details
        // For now, we're assuming book details are saved via the dialog
        Toast.makeText(this, "Save button clicked", Toast.LENGTH_SHORT).show();

        // After saving the book details, navigate to the view page
        Intent intent = new Intent(BookActivity.this, ViewBookActivity.class);
        startActivity(intent);
    }
}
