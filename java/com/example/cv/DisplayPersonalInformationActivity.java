package com.example.cv;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class DisplayPersonalInformationActivity extends AppCompatActivity {

    private ImageView ivProfileImage;
    private TextView tvFacultyName, tvDesignation, tvDepartment, tvAge, tvGender, tvContact, tvEmail;
    private DatabaseReference facultyRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_personal);

        // Initialize Firebase and Auth
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null) {
            String facultyId = currentUser.getUid();  // Assuming faculty_id is the same as the Firebase UID
            facultyRef = database.getReference("faculty").child(facultyId);
        }

        // Initialize views
        ivProfileImage = findViewById(R.id.display_personal_iv_img);
        tvFacultyName = findViewById(R.id.display_personal_tv_faculty_name);
        tvDesignation = findViewById(R.id.display_personal_tv_designation);
        tvDepartment = findViewById(R.id.display_personal_tv_department);
        tvAge = findViewById(R.id.display_personal_tv_age);
        tvGender = findViewById(R.id.display_personal_tv_gender);
        tvContact = findViewById(R.id.display_personal_tv_contact);
        tvEmail = findViewById(R.id.display_personal_tv_email);
        Button btnBack = findViewById(R.id.display_personal_btn_back);

        // Load data from Firebase
        loadPersonalInformation();

        // Back button
        btnBack.setOnClickListener(v -> finish());
    }

    private void loadPersonalInformation() {
        facultyRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Faculty faculty = dataSnapshot.getValue(Faculty.class);
                    if (faculty != null) {
                        tvFacultyName.setText(faculty.getName());
                        tvDesignation.setText(faculty.getDesignation());
                        tvDepartment.setText(faculty.getDepartment());
                        tvAge.setText(faculty.getAge());
                        tvGender.setText(faculty.getGender());
                        tvContact.setText(faculty.getContact());
                        tvEmail.setText(faculty.getEmail());

                        // Load profile image with null/empty check
                        String imagePath = dataSnapshot.child("image_path").getValue(String.class);

                        if (imagePath != null && !imagePath.isEmpty()) {
                            Picasso.get()
                                    .load(imagePath)
                                    .placeholder(R.drawable.rajesh) // Default placeholder image
                                    .error(R.drawable.ic_person) // Optional error image if loading fails
                                    .into(ivProfileImage);
                        } else {
                            // Load a default image if no image path is provided
                            Picasso.get()
                                    .load(R.drawable.ic_faculty) // Placeholder for missing image
                                    .into(ivProfileImage);
                        }
                    }
                } else {
                    Toast.makeText(DisplayPersonalInformationActivity.this, "No data found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(DisplayPersonalInformationActivity.this, "Failed to load data", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
