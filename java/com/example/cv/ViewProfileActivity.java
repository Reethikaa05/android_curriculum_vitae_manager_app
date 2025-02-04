package com.example.cv;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ViewProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        // Initialize views
        ImageButton backButton = findViewById(R.id.back_button);
        ImageView emailIcon = findViewById(R.id.email_icon);
        ImageView phoneIcon = findViewById(R.id.phone_icon);

        Spinner profileDropdown = findViewById(R.id.profile_dropdown);
        Spinner publicationDropdown = findViewById(R.id.publication_dropdown);
        Spinner professionalDropdown = findViewById(R.id.professional_dropdown);
        Spinner projectsDropdown = findViewById(R.id.projects_dropdown);
        Spinner recognitionDropdown = findViewById(R.id.recognition_dropdown);
        Spinner guidanceDropdown = findViewById(R.id.guidance_dropdown);

        // Back Button Click
        backButton.setOnClickListener(v -> {
            finish(); // Go back to the previous activity
        });

        // Email Icon Click - Show email as text
        emailIcon.setOnClickListener(v -> Toast.makeText(ViewProfileActivity.this, "Email: rajesh@gmail.com", Toast.LENGTH_SHORT).show());

        // Phone Icon Click - Show phone number as text
        phoneIcon.setOnClickListener(v -> Toast.makeText(ViewProfileActivity.this, "Phone: 9037462784", Toast.LENGTH_SHORT).show());

        // Set up dropdown navigation for each dropdown
        setupDropdownNavigation(profileDropdown, new Class<?>[]{
                null, // Default item
                DisplayPersonalInformationActivity.class,
                ExperienceDetailsActivity.class,
                ViewEducationActivity.class,
                MembershipDetailsActivity.class,
                ResearchDetailsActivity.class
        });

        setupDropdownNavigation(publicationDropdown, new Class<?>[]{
                null, // Default item
                ViewBookActivity.class,
                ViewBookChaptersActivity.class,
                ViewJournalsActivity.class,
                ConferenceActivity.class,
                ViewCitationActivity.class
        });

        setupDropdownNavigation(professionalDropdown, new Class<?>[]{
                null, // Default item
                ViewInvitedLecturesActivity.class,
                ViewProgramActivity.class
        });

        setupDropdownNavigation(projectsDropdown, new Class<?>[]{
                null, // Default item
                ViewConsultancyActivity.class
        });

        setupDropdownNavigation(recognitionDropdown, new Class<?>[]{
                null, // Default item
                ViewAwardActivity.class,
                ViewPatentsActivity.class

        });

        setupDropdownNavigation(guidanceDropdown, new Class<?>[]{
                null, // Default item
                ViewPhDActivity.class
        });
    }

    // Method to handle dropdown navigation
    private void setupDropdownNavigation(Spinner dropdown, Class<?>[] targetActivities) {
        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (position != 0) { // Ignore the default (first) selection
                    if (position < targetActivities.length && targetActivities[position] != null) {
                        // Show a toast indicating navigation
                        Toast.makeText(ViewProfileActivity.this, "Navigating to " + parentView.getItemAtPosition(position), Toast.LENGTH_SHORT).show();

                        // Navigate to the target activity
                        Intent intent = new Intent(ViewProfileActivity.this, targetActivities[position]);
                        startActivity(intent);
                    } else {
                        Toast.makeText(ViewProfileActivity.this, "Activity not implemented for this selection", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing
            }
        });
    }
}
