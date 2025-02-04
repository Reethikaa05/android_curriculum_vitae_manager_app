package com.example.cv;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class MembershipActivity extends AppCompatActivity {

    private DatabaseReference databaseReference; // Declare DatabaseReference
    private TextInputEditText etMembershipType, etYear, etDescription;
    private final String facultyId = "faculty_123"; // Replace with actual faculty document ID
    private static final String DATABASE_NODE = "memberships"; // Define your database node

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_membership);

        // Initialize Firebase Database reference
        databaseReference = FirebaseDatabase.getInstance().getReference(DATABASE_NODE);

        // Find views
        etMembershipType = findViewById(R.id.form_membership_et_type);
        etYear = findViewById(R.id.form_membership_et_year);
        etDescription = findViewById(R.id.form_membership_et_description);
        Button btnSave = findViewById(R.id.form_membership_btn_save);
        Button btnViewDetails = findViewById(R.id.form_membership_btn_view_details);
        ImageButton backButton = findViewById(R.id.form_membership_back_btn);

        // Back Button Action
        backButton.setOnClickListener(v -> finish());

        // Save Button Action
        btnSave.setOnClickListener(v -> saveMembershipDetails());

        // View Details Button Action
        btnViewDetails.setOnClickListener(v -> {
            Intent intent = new Intent(MembershipActivity.this, MembershipDetailsActivity.class);
            intent.putExtra("faculty_id", facultyId);
            startActivity(intent);
        });
    }

    private void saveMembershipDetails() {
        String type = etMembershipType.getText().toString().trim();
        String year = etYear.getText().toString().trim();
        String description = etDescription.getText().toString().trim();

        // Validate input
        if (type.isEmpty() || year.isEmpty() || description.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create a Map for the membership details
        Map<String, Object> membership = new HashMap<>();
        membership.put("memberType", type);
        membership.put("year", year);
        membership.put("description", description);

        // Use the facultyId as the key for the membership entry
        databaseReference.child(facultyId).setValue(membership)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(this, "Membership saved successfully", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Error saving membership: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }
}
