package com.example.cv;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import java.util.Objects;

/** @noinspection ALL*/
public class ProfessionalActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_professional);

        // Set up the Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        // Initialize DrawerLayout and NavigationView
        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.navigation_view);

        // Set up the Drawer Toggle
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // Initialize Sections
        LinearLayout invitedLecturesSection = findViewById(R.id.invited_lectures);
        LinearLayout programsAttendedSection = findViewById(R.id.programs_attended);

        // Back Button
        ImageButton backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(v -> onBackPressed());

        // Set up click listeners
        invitedLecturesSection.setOnClickListener(v -> navigateToInvitedLecturesActivity());
        programsAttendedSection.setOnClickListener(v -> navigateToProgramsAttendedActivity());

        // Handle navigation item clicks
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.nav_profile) {
                    startActivity(new Intent(ProfessionalActivity.this, ViewProfileActivity.class));
                } else if (id == R.id.nav_publication) {
                    startActivity(new Intent(ProfessionalActivity.this, PublicationActivity.class));
                } else if (id == R.id.nav_professional_activity) {
                    // Current Activity
                } else if (id == R.id.nav_project) {
                    startActivity(new Intent(ProfessionalActivity.this, ProjectActivity.class));
                } else if (id == R.id.nav_recognition) {
                    startActivity(new Intent(ProfessionalActivity.this, RecognitionActivity.class));
                } else if (id == R.id.nav_guidance) {
                    startActivity(new Intent(ProfessionalActivity.this, GuidanceActivity.class));
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START);
            } else {
                drawerLayout.openDrawer(GravityCompat.START);
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void navigateToInvitedLecturesActivity() {
        // Navigate to Invited Lectures Activity
        Intent intent = new Intent(ProfessionalActivity.this, InvitedLecturesActivity.class);
        startActivity(intent);
    }

    private void navigateToProgramsAttendedActivity() {
        // Navigate to Programs Attended Activity
        Intent intent = new Intent(ProfessionalActivity.this, ProgramsAttendedActivity.class);
        startActivity(intent);
    }
}
