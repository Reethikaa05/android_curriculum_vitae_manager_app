package com.example.cv;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MembershipDetailsActivity extends AppCompatActivity {

    private DatabaseReference databaseReference; // Declare DatabaseReference
    private String facultyId;
    private TextView tvMembershipDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_details);

        tvMembershipDetails = findViewById(R.id.tv_membership_details);
        ImageButton backButton = findViewById(R.id.btn_back);
        databaseReference = FirebaseDatabase.getInstance().getReference("memberships"); // Initialize DatabaseReference

        // Back Button Action
        backButton.setOnClickListener(v -> finish());
        // Get faculty ID from intent
        facultyId = getIntent().getStringExtra("faculty_id");

        // Fetch Membership details
        fetchMembershipDetails();
    }

    private void fetchMembershipDetails() {
        databaseReference.child(facultyId).addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String memberType = dataSnapshot.child("memberType").getValue(String.class);
                    String year = dataSnapshot.child("year").getValue(String.class);
                    String description = dataSnapshot.child("description").getValue(String.class);

                    // Display the membership details
                    String details = "Membership Type: " + memberType + "\n" +
                            "Year: " + year + "\n" +
                            "Description: " + description;

                    tvMembershipDetails.setText(details);
                } else {
                    tvMembershipDetails.setText("No membership details found.");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MembershipDetailsActivity.this, "Error fetching data: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
