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

public class ViewInvitedLecturesActivity extends AppCompatActivity {

    private LinearLayout lecturesLayout;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_lectures);

        lecturesLayout = findViewById(R.id.lecture_layout);
        Button backButton = findViewById(R.id.back_button);
        databaseReference = FirebaseDatabase.getInstance().getReference("InvitedLectures");

        fetchInvitedLectures();

        // Back button click listener
        backButton.setOnClickListener(v -> onBackPressed());
    }

    private void fetchInvitedLectures() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                lecturesLayout.removeAllViews(); // Clear previous views
                for (DataSnapshot lectureSnapshot : dataSnapshot.getChildren()) {
                    InvitedLecture lecture = lectureSnapshot.getValue(InvitedLecture.class);
                    if (lecture != null) {
                        addLectureView(lecture);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ViewInvitedLecturesActivity.this, "Failed to load invited lectures.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void addLectureView(InvitedLecture lecture) {
        // Inflate the custom lecture item layout
        LayoutInflater inflater = LayoutInflater.from(this);
        LinearLayout lectureView = (LinearLayout) inflater.inflate(R.layout.lecture_item_view, lecturesLayout, false);

        // Set data into the TextViews
        ((TextView) lectureView.findViewById(R.id.lecture_title)).setText(lecture.getTitle());
        ((TextView) lectureView.findViewById(R.id.lecture_purpose)).setText(lecture.getPurpose());
        ((TextView) lectureView.findViewById(R.id.lecture_organized_by)).setText(lecture.getOrganizedBy());
        ((TextView) lectureView.findViewById(R.id.lecture_start_date)).setText(lecture.getStartDate());
        ((TextView) lectureView.findViewById(R.id.lecture_end_date)).setText(lecture.getEndDate());
        ((TextView) lectureView.findViewById(R.id.lecture_place)).setText(lecture.getPlace());

        // Add the inflated view to the layout
        lecturesLayout.addView(lectureView);
    }
}
