package com.example.cv;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
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

public class ViewPatentsActivity extends AppCompatActivity {

    private LinearLayout patentsLayout;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_patents);

        patentsLayout = findViewById(R.id.patent_layout);
        Button backButton = findViewById(R.id.back_button);
        databaseReference = FirebaseDatabase.getInstance().getReference("Patents");

        fetchPatents();

        // Back button click listener
        backButton.setOnClickListener(v -> onBackPressed());
    }

    private void fetchPatents() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                patentsLayout.removeAllViews(); // Clear previous views
                for (DataSnapshot patentSnapshot : dataSnapshot.getChildren()) {
                    Patent patent = patentSnapshot.getValue(Patent.class);
                    if (patent != null) {
                        addPatentView(patent);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ViewPatentsActivity.this, "Failed to load patents.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void addPatentView(Patent patent) {
        // Inflate the custom patent item layout
        LayoutInflater inflater = LayoutInflater.from(this);
        LinearLayout patentView = (LinearLayout) inflater.inflate(R.layout.patent_item_view, patentsLayout, false);

        // Set data into the TextViews
        ((TextView) patentView.findViewById(R.id.patent_title)).setText(patent.getTitle());
        ((TextView) patentView.findViewById(R.id.patent_purpose)).setText(patent.getPurpose());
        ((TextView) patentView.findViewById(R.id.patent_filed_on)).setText(patent.getFiledOn());
        ((TextView) patentView.findViewById(R.id.patent_published_date)).setText(patent.getPublishedDate());
        ((TextView) patentView.findViewById(R.id.patent_author1)).setText(patent.getAuthor1());
        ((TextView) patentView.findViewById(R.id.patent_author2)).setText(patent.getAuthor2());

        // Add the inflated view to the layout
        patentsLayout.addView(patentView);
    }
}
