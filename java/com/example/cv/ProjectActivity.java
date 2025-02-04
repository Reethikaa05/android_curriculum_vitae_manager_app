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
public class ProjectActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project);

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
        LinearLayout consultancySection = findViewById(R.id.consultancy);

        // Back Button
        ImageButton backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(v -> onBackPressed());

        // Set up click listeners
        consultancySection.setOnClickListener(v -> navigateToConsultancyActivity());

        // Handle navigation item clicks
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.nav_profile) {
                    startActivity(new Intent(ProjectActivity.this, ViewProfileActivity.class));
                } else if (id == R.id.nav_publication) {
                    startActivity(new Intent(ProjectActivity.this, PublicationActivity.class));
                } else if (id == R.id.nav_professional_activity) {
                    startActivity(new Intent(ProjectActivity.this, ProfessionalActivity.class));
                } else if (id == R.id.nav_project) {
                    // Current Activity
                } else if (id == R.id.nav_recognition) {
                    startActivity(new Intent(ProjectActivity.this, RecognitionActivity.class));
                } else if (id == R.id.nav_guidance) {
                    startActivity(new Intent(ProjectActivity.this, GuidanceActivity.class));
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

    private void navigateToConsultancyActivity() {
        // Navigate to Consultancy Activity
        Intent intent = new Intent(ProjectActivity.this, ConsultancyActivity.class);
        startActivity(intent);
    }
}
