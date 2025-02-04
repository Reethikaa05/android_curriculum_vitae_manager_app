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
public class PublicationActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publication);

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
        LinearLayout journalSection = findViewById(R.id.journal);
        LinearLayout bookSection = findViewById(R.id.book);
        LinearLayout bookChapterSection = findViewById(R.id.book_chapter);
        LinearLayout citationSection = findViewById(R.id.citation);

        // Back Button
        ImageButton backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(v -> onBackPressed());

        // Set up click listeners
        journalSection.setOnClickListener(v -> navigateToJournalActivity());
        bookSection.setOnClickListener(v -> navigateToBookActivity());
        bookChapterSection.setOnClickListener(v -> navigateToBookChapterActivity());
        citationSection.setOnClickListener(v -> navigateToCitationActivity());

        // Handle navigation item clicks
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.nav_profile) {
                    // Navigate to Profile Activity
                    startActivity(new Intent(PublicationActivity.this, ViewProfileActivity.class));
                } else if (id == R.id.nav_publication) {
                    // Current Activity
                } else if (id == R.id.nav_professional_activity) {
                    startActivity(new Intent(PublicationActivity.this, ProfessionalActivity.class));
                } else if (id == R.id.nav_project) {
                    startActivity(new Intent(PublicationActivity.this, ProjectActivity.class));
                } else if (id == R.id.nav_recognition) {
                    startActivity(new Intent(PublicationActivity.this, RecognitionActivity.class));
                } else if (id == R.id.nav_guidance) {
                    startActivity(new Intent(PublicationActivity.this, GuidanceActivity.class));
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

    private void navigateToJournalActivity() {
        // Navigate to Journal Activity
        Intent intent = new Intent(PublicationActivity.this, JournalActivity.class);
        startActivity(intent);
    }

    private void navigateToBookActivity() {
        // Navigate to Book Activity
        Intent intent = new Intent(PublicationActivity.this, BookActivity.class);
        startActivity(intent);
    }

    private void navigateToBookChapterActivity() {
        // Navigate to Book Chapter Activity
        Intent intent = new Intent(PublicationActivity.this, BookChapterActivity.class);
        startActivity(intent);
    }

    private void navigateToCitationActivity() {
        // Navigate to Citation Activity
        Intent intent = new Intent(PublicationActivity.this, CitationActivity.class);
        startActivity(intent);
    }
}
