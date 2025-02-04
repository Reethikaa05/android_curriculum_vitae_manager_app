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

public class EditProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        // Initialize views
        ImageButton backButton = findViewById(R.id.back_button);
        ImageView emailIcon = findViewById(R.id.email_icon);
        ImageView phoneIcon = findViewById(R.id.phone_icon);

        Spinner profileDropdown = findViewById(R.id.profile_dropdown1);
        Spinner publicationDropdown = findViewById(R.id.publication_dropdown1);
        Spinner professionalDropdown = findViewById(R.id.professional_dropdown1);
        Spinner projectsDropdown = findViewById(R.id.projects_dropdown1);
        Spinner recognitionDropdown = findViewById(R.id.recognition_dropdown1);
        Spinner guidanceDropdown = findViewById(R.id.guidance_dropdown1);

        // Back Button Click
        backButton.setOnClickListener(v -> finish());

        // Email Icon Click
        emailIcon.setOnClickListener(v -> Toast.makeText(EditProfileActivity.this, "Email: rajesh@gmail.com", Toast.LENGTH_SHORT).show());

        // Phone Icon Click
        phoneIcon.setOnClickListener(v -> Toast.makeText(EditProfileActivity.this, "Phone: 9037462784", Toast.LENGTH_SHORT).show());

        // Set up dropdown navigation
        setupDropdownNavigation(profileDropdown, new Class<?>[]{
                null, // Default item
                PersonalInformationActivity.class,
                ExperienceActivity.class,
                EducationActivity.class,
                MembershipActivity.class,
                ResearchInterestActivity.class
        });

        setupDropdownNavigation(publicationDropdown, new Class<?>[]{
                null, // Default item
                BookActivity.class,
                BookChapterActivity.class,
                JournalActivity.class,
                ConferenceActivity.class,
                CitationActivity.class
        });

        setupDropdownNavigation(professionalDropdown, new Class<?>[]{
                null, // Default item
                InvitedLecturesActivity.class,
                ProgramsAttendedActivity.class
        });

        setupDropdownNavigation(projectsDropdown, new Class<?>[]{
                null, // Default item
                ConsultancyActivity.class
        });

        setupDropdownNavigation(recognitionDropdown, new Class<?>[]{
                null, // Default item
                AwardsActivity.class,
                PatentActivity.class,
        });

        setupDropdownNavigation(guidanceDropdown, new Class<?>[]{
                null, // Default item
                PhdActivity.class
        });
    }

    /**
     * Sets up navigation for dropdowns to specified activities or fragments.
     */
    private void setupDropdownNavigation(Spinner dropdown, Class<?>[] destinations) {
        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (position > 0) {
                    Class<?> destination = destinations[position];

                    // Handle activity navigation
                    Intent intent = new Intent(EditProfileActivity.this, destination);
                    startActivity(intent);

                    // Optionally, reset dropdown to default position
                    dropdown.setSelection(0);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing
            }
        });
    }
}
