package com.example.cv;

import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;

/** @noinspection ALL*/
public class DashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        FrameLayout btnHome = findViewById(R.id.btnHome);
        FrameLayout btnViewProfile = findViewById(R.id.btnViewProfile);
        FrameLayout btnReminder = findViewById(R.id.btnReminder); // Changed from btnEditProfile to btnReminder
        FrameLayout btnEdit = findViewById(R.id.btnEdit);
        FrameLayout btnCv = findViewById(R.id.btnCv);
        FrameLayout btnLogout = findViewById(R.id.btnLogout);


        btnHome.setOnClickListener(v -> {
            // Navigate to GeneralInfoActivity when Home button is clicked
            Intent intent = new Intent(DashboardActivity.this, GeneralActivity.class);
            startActivity(intent);
        });

        btnViewProfile.setOnClickListener(v -> {
            Intent intent = new Intent(DashboardActivity.this, ViewProfileActivity.class);
            startActivity(intent);
        });

        btnReminder.setOnClickListener(v -> {
            Intent intent = new Intent(DashboardActivity.this, ReminderActivity.class);
            startActivity(intent);
        });

        btnEdit.setOnClickListener(v -> {
            Intent intent = new Intent(DashboardActivity.this, EditProfileActivity.class);
            startActivity(intent);

        });

        btnCv.setOnClickListener(v -> {
            Intent intent = new Intent(DashboardActivity.this, ViewCVActivity.class);
            startActivity(intent);

        });

        btnLogout.setOnClickListener(v -> {
            Intent intent = new Intent(DashboardActivity.this, LoginActivity.class);
            startActivity(intent);
            finish(); // Close DashboardActivity
        });
    }
}
