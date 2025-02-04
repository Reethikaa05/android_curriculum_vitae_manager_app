package com.example.cv;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class ForgotPasswordActivity extends AppCompatActivity {

    private EditText forgotPasswordEmailEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot); // Ensure this matches your XML layout file

        forgotPasswordEmailEditText = findViewById(R.id.forgot_password_email);
        Button submitButton = findViewById(R.id.submit_button);
        TextView signInLink = findViewById(R.id.signin_link);

        submitButton.setOnClickListener(v -> handleForgotPassword());

        // Set OnClickListener for the Sign In link
        signInLink.setOnClickListener(v -> {
            // Navigate to the LoginActivity
            Intent intent = new Intent(ForgotPasswordActivity.this, LoginActivity.class);
            startActivity(intent);
            finish(); // Optional: Close the ForgotPasswordActivity so user can't go back
        });
    }

    private void handleForgotPassword() {
        String email = forgotPasswordEmailEditText.getText().toString().trim();

        if (email.isEmpty()) {
            Toast.makeText(this, "Please enter your email", Toast.LENGTH_SHORT).show();
            return;
        }

        // Handle password reset logic here
        Toast.makeText(this, "Password reset link sent to " + email, Toast.LENGTH_SHORT).show();
    }
}
