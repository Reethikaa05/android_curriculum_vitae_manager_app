package com.example.cv;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ResearchInterestActivity extends AppCompatActivity {

    private TextInputEditText etResearchTopic;
    private DatabaseReference researchRef;
    private String facultyId = "faculty_123";  // In a real-world scenario, retrieve this dynamically
    private ArrayList<String> researchTopicsList = new ArrayList<>();  // To store multiple research topics
    private final int MAX_TOPICS = 5;  // Maximum allowed topics

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_research);

        // Initialize Views
        etResearchTopic = findViewById(R.id.form_research_et_topic);
        Button btnAddTopic = findViewById(R.id.add_button);
        Button btnSave = findViewById(R.id.form_research_btn_save);
        Button btnViewDetails = findViewById(R.id.form_research_btn_view_details);
        ImageButton backBtn = findViewById(R.id.form_research_back_btn);

        // Firebase Reference
        researchRef = FirebaseDatabase.getInstance().getReference("research_interests").child(facultyId);

        // Add Research Topic Dialog
        btnAddTopic.setOnClickListener(v -> showAddTopicDialog());

        // Save Research Interests to Firebase
        btnSave.setOnClickListener(v -> saveResearchInterests());

        // View Details Button
        btnViewDetails.setOnClickListener(v -> {
            Intent intent = new Intent(ResearchInterestActivity.this, ResearchDetailsActivity.class);
            intent.putExtra("faculty_id", facultyId);
            startActivity(intent);
        });

        // Back Button
        backBtn.setOnClickListener(v -> finish());
    }

    private void showAddTopicDialog() {
        // Check if the maximum number of topics is reached
        if (researchTopicsList.size() >= MAX_TOPICS) {
            Toast.makeText(this, "You have already added " + MAX_TOPICS + " topics", Toast.LENGTH_SHORT).show();
            return;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add Research Topic");

        // Create a TextInputEditText for the dialog
        final TextInputEditText input = new TextInputEditText(this);
        builder.setView(input);

        // Set dialog buttons
        builder.setPositiveButton("Add", (dialog, which) -> {
            String topic = input.getText().toString().trim();

            // Validate the input
            if (TextUtils.isEmpty(topic)) {
                Toast.makeText(ResearchInterestActivity.this, "Topic cannot be empty", Toast.LENGTH_SHORT).show();
                return;
            }

            // Add the topic to the list
            researchTopicsList.add(topic);
            Toast.makeText(ResearchInterestActivity.this, "Topic added: " + topic, Toast.LENGTH_SHORT).show();
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        builder.show();
    }

    private void saveResearchInterests() {
        // Check if at least 5 topics have been added
        if (researchTopicsList.size() < 5) {
            Toast.makeText(this, "Please add at least 5 research topics", Toast.LENGTH_SHORT).show();
            return;
        }

        // Save the topics to Firebase
        Map<String, Object> researchMap = new HashMap<>();
        researchMap.put("topics", researchTopicsList);  // Save the list of topics

        researchRef.setValue(researchMap)
                .addOnSuccessListener(aVoid -> Toast.makeText(ResearchInterestActivity.this, "Research topics saved successfully!", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> Toast.makeText(ResearchInterestActivity.this, "Failed to save research topics. Please try again.", Toast.LENGTH_SHORT).show());
    }
}
