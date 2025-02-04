package com.example.cv;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ViewBookChaptersActivity extends AppCompatActivity {

    private LinearLayout chaptersLayout;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_book_chapters); // Ensure you have this layout file

        // Initialize layout components
        chaptersLayout = findViewById(R.id.chapters_layout);
        Button backButton = findViewById(R.id.back_button);
        databaseReference = FirebaseDatabase.getInstance().getReference("BookChapters");

        fetchBookChapters(); // Load book chapters from Firebase

        // Back button click listener
        backButton.setOnClickListener(v -> onBackPressed()); // Go back to the previous activity
    }

    private void fetchBookChapters() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                chaptersLayout.removeAllViews(); // Clear previous views before adding new ones
                for (DataSnapshot chapterSnapshot : dataSnapshot.getChildren()) {
                    BookChapter bookChapter = chapterSnapshot.getValue(BookChapter.class);
                    if (bookChapter != null) {
                        addBookChapterView(bookChapter); // Add each book chapter to the layout
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ViewBookChaptersActivity.this, "Failed to load chapters.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @SuppressLint({"SetTextI18n", "MissingInflatedId"})
    private void addBookChapterView(BookChapter bookChapter) {
        // Inflate a custom layout for each book chapter entry
        View chapterView = LayoutInflater.from(this).inflate(R.layout.chapter_item_view, chaptersLayout, false);

        // Set the chapter details in the layout
        ((TextView) chapterView.findViewById(R.id.chapter_title)).setText("Title: " + bookChapter.getTitle());
        ((TextView) chapterView.findViewById(R.id.chapter_publisher)).setText("Publisher: " + bookChapter.getPublisher());
        ((TextView) chapterView.findViewById(R.id.chapter_month_year)).setText("Month/Year: " + bookChapter.getMonthYear());
        ((TextView) chapterView.findViewById(R.id.chapter_number)).setText("Chapter Number: " + bookChapter.getChapterNumber());
        ((TextView) chapterView.findViewById(R.id.chapter_edition)).setText("Edition: " + bookChapter.getEdition());
        ((TextView) chapterView.findViewById(R.id.chapter_isbn)).setText("ISBN: " + bookChapter.getIsbn());
        ((TextView) chapterView.findViewById(R.id.chapter_pages)).setText("Pages: " + bookChapter.getPages());
        ((TextView) chapterView.findViewById(R.id.chapter_authors)).setText("Authors: " + bookChapter.getAuthor1() + ", " +
                bookChapter.getAuthor2() + ", " + bookChapter.getAuthor3());

        // Add the custom chapter view to the layout
        chaptersLayout.addView(chapterView);
    }
}
