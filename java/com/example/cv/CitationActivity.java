package com.example.cv;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CitationActivity extends AppCompatActivity {

    private final ArrayList<String> savedLinks = new ArrayList<>();
    private final ArrayList<String> savedLabels = new ArrayList<>();
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_citation);

        // Initialize Firebase Database reference
        databaseReference = FirebaseDatabase.getInstance().getReference("Citations");

        // Back button
        ImageButton backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(v -> onBackPressed());

        // Citation links
        setupCitationLink(R.id.citation_link_1, "https://orcid.org/0000-0001-6670-4527", "Orcid Id");
        setupCitationLink(R.id.citation_link_2, "https://www.scopus.com/authid/detail.uri?authorId=56168386200", "Scopus Id");
        setupCitationLink(R.id.citation_link_3, "https://www.webofscience.com/wos/author/record/2126956", "Researcher Id");
        setupCitationLink(R.id.citation_link_4, "https://scholar.google.co.in/citations?user=bCoX5RwAAAAJ&hl=", "Google Scholar Id");

        // Save button
        Button saveButton = findViewById(R.id.save_button);
        saveButton.setOnClickListener(v -> saveLinksToFirebase());

        // View Details button
        Button viewDetailsButton = findViewById(R.id.view_details_button);
        viewDetailsButton.setOnClickListener(v ->{
            Intent intent = new Intent(CitationActivity.this, ViewCitationActivity.class);
            startActivity(intent);
        });
    }

    private void setupCitationLink(int linkId, String url, String label) {
        LinearLayout linkLayout = findViewById(linkId);
        linkLayout.setOnClickListener(v -> openLink(url));

        // Store the label to be saved later
        savedLabels.add(label);
    }

    private void openLink(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }

    private void saveLinksToFirebase() {
        Map<String, String> citationsMap = new HashMap<>();
        citationsMap.put("Orcid Id", "https://orcid.org/0000-0001-6670-4527");
        citationsMap.put("Scopus Id", "https://www.scopus.com/authid/detail.uri?authorId=56168386200");
        citationsMap.put("Researcher Id", "https://www.webofscience.com/wos/author/record/2126956");
        citationsMap.put("Google Scholar Id", "https://scholar.google.co.in/citations?user=bCoX5RwAAAAJ&hl=");

        // Save the map to Firebase
        databaseReference.setValue(citationsMap).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(CitationActivity.this, "Links saved to Firebase!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(CitationActivity.this, "Failed to save links.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
