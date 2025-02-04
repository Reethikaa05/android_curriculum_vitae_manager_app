package com.example.cv;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

/** @noinspection deprecation*/
public class GeneralActivity extends AppCompatActivity {

    private EditText phoneEditText, emailEditText;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general);

        // Initialize views
        phoneEditText = findViewById(R.id.phone_edit);
        emailEditText = findViewById(R.id.email_edit);
        Button saveButton = findViewById(R.id.save_button);
        TextView nameTextView = findViewById(R.id.name_text);
        TextView designationTextView = findViewById(R.id.designation_text);
        ImageView profileImageView = findViewById(R.id.profile_image);
        ImageView backButton = findViewById(R.id.back_button);

        // Get the current values from the Intent
        Intent intent = getIntent();
        String currentPhone = intent.getStringExtra("phone");
        String currentEmail = intent.getStringExtra("email");

        // Check if current values are available and set them in EditText fields
        if (currentPhone != null) {
            phoneEditText.setText(currentPhone);
        } else {
            phoneEditText.setText(""); // Default to empty if null
        }

        if (currentEmail != null) {
            emailEditText.setText(currentEmail);
        } else {
            emailEditText.setText(""); // Default to empty if null
        }

        // Set static values for profile image, name, and designation
        profileImageView.setImageResource(R.drawable.ic_faculty); // Example profile image
        nameTextView.setText("Rajesh R S");
        designationTextView.setText("Professor");

        // Handle save button click
        saveButton.setOnClickListener(v -> {
            String updatedPhone = phoneEditText.getText().toString().trim();
            String updatedEmail = emailEditText.getText().toString().trim();

            if (!updatedPhone.isEmpty() && !updatedEmail.isEmpty()) {
                // Return the updated data to the previous activity
                Intent resultIntent = new Intent();
                resultIntent.putExtra("updated_phone", updatedPhone);
                resultIntent.putExtra("updated_email", updatedEmail);
                setResult(RESULT_OK, resultIntent);
                finish(); // Close the activity
            } else {
                Toast.makeText(GeneralActivity.this, "Please enter both phone and email", Toast.LENGTH_SHORT).show();
            }
        });

        // Handle back button click
        backButton.setOnClickListener(v -> onBackPressed()); // This will close the activity
    }
}
