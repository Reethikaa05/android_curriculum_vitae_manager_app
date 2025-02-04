package com.example.cv;

import android.annotation.SuppressLint;
import android.os.Bundle;
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
import android.view.LayoutInflater;
import android.view.View;

/** @noinspection ALL */
public class ViewJournalsActivity extends AppCompatActivity {

    private LinearLayout journalsLayout;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_journals);

        journalsLayout = findViewById(R.id.journal_layout);
        Button backButton = findViewById(R.id.back_button);
        databaseReference = FirebaseDatabase.getInstance().getReference("Journals");

        fetchJournals();

        // Back button click listener
        backButton.setOnClickListener(v -> onBackPressed()); // Go back to the previous activity
    }

    private void fetchJournals() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot journalSnapshot : dataSnapshot.getChildren()) {
                    Journal journal = journalSnapshot.getValue(Journal.class);
                    if (journal != null) {
                        addJournalView(journal);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ViewJournalsActivity.this, "Failed to load journals.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void addJournalView(Journal journal) {
        // Inflate the custom layout for each journal entry
        View journalView = LayoutInflater.from(this).inflate(R.layout.journal_item_view, journalsLayout, false);

        // Set the journal details in the layout
        ((TextView) journalView.findViewById(R.id.journal_title)).setText(journal.getArticleTitle());
        ((TextView) journalView.findViewById(R.id.journal_publisher)).setText(journal.getPubName());
        ((TextView) journalView.findViewById(R.id.journal_month_year)).setText(journal.getMonthYear());
        ((TextView) journalView.findViewById(R.id.journal_volume)).setText(journal.getVolumeNumber());
        ((TextView) journalView.findViewById(R.id.journal_edition)).setText(journal.getEdition());
        ((TextView) journalView.findViewById(R.id.journal_isbn)).setText(journal.getIsbnNo());
        ((TextView) journalView.findViewById(R.id.journal_pages)).setText(journal.getNoOfPages());
        ((TextView) journalView.findViewById(R.id.journal_authors)).setText(journal.getAuthor1() + ", " + journal.getAuthor2() + ", " + journal.getAuthor3());

        // Add the custom journal view to the layout
        journalsLayout.addView(journalView);
    }
}
