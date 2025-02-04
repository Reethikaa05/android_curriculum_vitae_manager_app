package com.example.cv;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
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

public class ViewCitationActivity extends AppCompatActivity {

    private static final String TAG = "ViewCitationActivity";
    private LinearLayout layout;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_citation);

        // Initialize UI elements
        layout = findViewById(R.id.saved_links_layout);
        ImageButton backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(v -> onBackPressed());

        // Initialize Firebase Database reference
        databaseReference = FirebaseDatabase.getInstance().getReference("Citations");

        // Fetch and display citations from Firebase
        fetchCitationDetails();
    }

    private void fetchCitationDetails() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                layout.removeAllViews(); // Clear existing views to avoid duplication

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    // Fetch label (key) and URL (value)
                    String label = snapshot.getKey();
                    String url = snapshot.getValue(String.class);

                    if (label != null && url != null) {
                        addCitationToLayout(label, url);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle database error
                Toast.makeText(ViewCitationActivity.this, "Error fetching data: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e(TAG, "Database error: " + databaseError.getMessage());
            }
        });
    }

    private void addCitationToLayout(String label, String url) {
        // Create a TextView to display the label and link
        TextView textView = new TextView(this);
        textView.setText(label + ": " + url);

        // Optionally, set additional properties for better display (e.g., padding, text color)
        textView.setPadding(16, 16, 16, 16);
        textView.setTextSize(16);
        textView.setBackgroundResource(android.R.color.background_light);
        textView.setTextColor(getResources().getColor(android.R.color.black));

        // Add the TextView to the layout
        layout.addView(textView);
    }
}
