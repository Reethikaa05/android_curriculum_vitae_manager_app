package com.example.cv;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Initialize the faculty member LinearLayouts for 12 members
        LinearLayout faculty1 = findViewById(R.id.faculty_member_1);
        LinearLayout faculty2 = findViewById(R.id.faculty_member_2);
        LinearLayout faculty3 = findViewById(R.id.faculty_member_3);
        LinearLayout faculty4 = findViewById(R.id.faculty_member_4);
        LinearLayout faculty5 = findViewById(R.id.faculty_member_5);
        LinearLayout faculty6 = findViewById(R.id.faculty_member_6);
        LinearLayout faculty7 = findViewById(R.id.faculty_member_7);
        LinearLayout faculty8 = findViewById(R.id.faculty_member_8);
        LinearLayout faculty9 = findViewById(R.id.faculty_member_9);
        LinearLayout faculty10 = findViewById(R.id.faculty_member_10);
        LinearLayout faculty11 = findViewById(R.id.faculty_member_11);
        LinearLayout faculty12 = findViewById(R.id.faculty_member_12);

        // Set click listeners for each faculty member
        faculty1.setOnClickListener(this::onFacultyClick);
        faculty2.setOnClickListener(this::onFacultyClick);
        faculty3.setOnClickListener(this::onFacultyClick);
        faculty4.setOnClickListener(this::onFacultyClick);
        faculty5.setOnClickListener(this::onFacultyClick);
        faculty6.setOnClickListener(this::onFacultyClick);
        faculty7.setOnClickListener(this::onFacultyClick);
        faculty8.setOnClickListener(this::onFacultyClick);
        faculty9.setOnClickListener(this::onFacultyClick);
        faculty10.setOnClickListener(this::onFacultyClick);
        faculty11.setOnClickListener(this::onFacultyClick);
        faculty12.setOnClickListener(this::onFacultyClick);
    }

    public void onFacultyClick(View view) {
        Intent intent = null;

        // Navigate based on the faculty member clicked
        if (view.getId() == R.id.faculty_member_1) {
            intent = new Intent(HomeActivity.this, MainActivity.class); // Navigate to MainActivity
        } else if (view.getId() == R.id.faculty_member_2) {
            intent = new Intent(HomeActivity.this, OtherActivity.class); // Navigate to OtherActivity
        } else if (view.getId() == R.id.faculty_member_3) {
            intent = new Intent(HomeActivity.this, OtherActivity.class);
        } else if (view.getId() == R.id.faculty_member_4) {
            intent = new Intent(HomeActivity.this, OtherActivity.class);
        } else if (view.getId() == R.id.faculty_member_5) {
            intent = new Intent(HomeActivity.this, OtherActivity.class);
        } else if (view.getId() == R.id.faculty_member_6) {
            intent = new Intent(HomeActivity.this, OtherActivity.class);
        } else if (view.getId() == R.id.faculty_member_7) {
            intent = new Intent(HomeActivity.this, OtherActivity.class);
        } else if (view.getId() == R.id.faculty_member_8) {
            intent = new Intent(HomeActivity.this, OtherActivity.class);
        } else if (view.getId() == R.id.faculty_member_9) {
            intent = new Intent(HomeActivity.this, OtherActivity.class);
        } else if (view.getId() == R.id.faculty_member_10) {
            intent = new Intent(HomeActivity.this, OtherActivity.class);
        } else if (view.getId() == R.id.faculty_member_11) {
            intent = new Intent(HomeActivity.this, OtherActivity.class);
        } else if (view.getId() == R.id.faculty_member_12) {
            intent = new Intent(HomeActivity.this, OtherActivity.class);
        }

        if (intent != null) {
            startActivity(intent);
        }
    }
}
