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

public class ViewPhDActivity extends AppCompatActivity {

    private LinearLayout phdLayout;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_phd);

        phdLayout = findViewById(R.id.phd_layout);
        Button backButton = findViewById(R.id.back_button);
        databaseReference = FirebaseDatabase.getInstance().getReference("PhDDetails");

        fetchPhDs();

        // Back button click listener
        backButton.setOnClickListener(v -> onBackPressed());
    }

    private void fetchPhDs() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                phdLayout.removeAllViews(); // Clear previous views
                for (DataSnapshot phdSnapshot : dataSnapshot.getChildren()) {
                    PhDDetails phd = phdSnapshot.getValue(PhDDetails.class);
                    if (phd != null) {
                        addPhDView(phd);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ViewPhDActivity.this, "Failed to load PhD details.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void addPhDView(PhDDetails phd) {
        // Inflate the custom PhD item layout
        LayoutInflater inflater = LayoutInflater.from(this);
        LinearLayout phdView = (LinearLayout) inflater.inflate(R.layout.phd_item_view, phdLayout, false);

        // Set data into the TextViews
        ((TextView) phdView.findViewById(R.id.phd_scholar_name)).setText(phd.getScholarName());
        ((TextView) phdView.findViewById(R.id.phd_reg_no)).setText(phd.getRegNo());
        ((TextView) phdView.findViewById(R.id.phd_reg_date)).setText(phd.getRegDate());
        ((TextView) phdView.findViewById(R.id.phd_submission_date)).setText(phd.getSubmissionDate());
        ((TextView) phdView.findViewById(R.id.phd_viva_date)).setText(phd.getVivaDate());
        ((TextView) phdView.findViewById(R.id.phd_degree_type)).setText(phd.getDegreeType());
        ((TextView) phdView.findViewById(R.id.phd_mode_of_degree)).setText(phd.getModeOfDegree());
        ((TextView) phdView.findViewById(R.id.phd_status)).setText(phd.getStatus());

        // Add the inflated view to the layout
        phdLayout.addView(phdView);
    }
}
