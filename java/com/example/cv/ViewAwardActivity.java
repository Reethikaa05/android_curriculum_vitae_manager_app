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

public class ViewAwardActivity extends AppCompatActivity {

    private LinearLayout awardLayout;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_award); // Ensure you have this layout file

        // Initialize layout components
        awardLayout = findViewById(R.id.award_layout);
        Button backButton = findViewById(R.id.back_button);
        databaseReference = FirebaseDatabase.getInstance().getReference("Awards");

        fetchAwards(); // Load the awards from Firebase

        // Back button click listener
        backButton.setOnClickListener(v -> onBackPressed()); // Go back to the previous activity
    }

    private void fetchAwards() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                awardLayout.removeAllViews(); // Clear previous views before adding new ones
                for (DataSnapshot awardSnapshot : dataSnapshot.getChildren()) {
                    Award award = awardSnapshot.getValue(Award.class);
                    if (award != null) {
                        addAwardView(award); // Add each award to the layout
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ViewAwardActivity.this, "Failed to load awards.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void addAwardView(Award award) {
        // Inflate a custom layout for each award entry
        View awardView = LayoutInflater.from(this).inflate(R.layout.award_item_view, awardLayout, false);

        // Set the award details in the layout
        ((TextView) awardView.findViewById(R.id.award_name)).setText(award.getAwardName());
        ((TextView) awardView.findViewById(R.id.award_agency)).setText("Agency: " + award.getSponsoringAgency());
        ((TextView) awardView.findViewById(R.id.award_purpose)).setText("Purpose: " + award.getPurpose());
        ((TextView) awardView.findViewById(R.id.award_month)).setText("Month: " + award.getMonth());
        ((TextView) awardView.findViewById(R.id.award_year)).setText("Year: " + award.getYear());
        ((TextView) awardView.findViewById(R.id.award_status)).setText("Status: " + award.getType());

        // Add the custom award view to the layout
        awardLayout.addView(awardView);
    }
}
