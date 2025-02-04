package com.example.cv;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseFirestore db; // Firestore instance
    private boolean isPasswordVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance(); // Firebase Auth
        db = FirebaseFirestore.getInstance(); // Firestore instance

        EditText usernameEditText = findViewById(R.id.username); // Now username instead of name
        EditText emailEditText = findViewById(R.id.email);
        EditText passwordEditText = findViewById(R.id.password);
        Button signupButton = findViewById(R.id.signup_button);
        TextView signinLinkTextView = findViewById(R.id.signin_link);
        ImageView visibilityToggle = findViewById(R.id.visibility_toggle);

        // Toggle password visibility
        visibilityToggle.setOnClickListener(v -> togglePasswordVisibility(passwordEditText, visibilityToggle));

        signupButton.setOnClickListener(v -> handleSignUp(usernameEditText, emailEditText, passwordEditText));

        signinLinkTextView.setOnClickListener(v -> {
            Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
            startActivity(intent);
            finish(); // Close the SignUpActivity so user can't go back
        });
    }

    private void handleSignUp(EditText usernameEditText, EditText emailEditText, EditText passwordEditText) {
        String username = usernameEditText.getText().toString().trim(); // Username instead of name
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Check if email is valid
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Please enter a valid email address", Toast.LENGTH_SHORT).show();
            return;
        }

        // Validate password
        if (!isPasswordValid(password)) {
            Toast.makeText(this, "Password must be at least 8 characters long, contain an uppercase letter, " +
                    "a lowercase letter, a digit, and a special character.", Toast.LENGTH_LONG).show();
            return;
        }

        // Firebase user registration
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();

                        // Create a user object without name
                        User newUser = new User(username, email);

                        // Store user data in Firestore
                        db.collection("users").document(user.getUid())
                                .set(newUser)
                                .addOnSuccessListener(aVoid -> {
                                    Toast.makeText(SignUpActivity.this, "User data saved successfully", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                    finish(); // Close the SignUpActivity
                                })
                                .addOnFailureListener(e -> {
                                    Toast.makeText(SignUpActivity.this, "Error saving user data: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                });
                    } else {
                        Toast.makeText(SignUpActivity.this, "Sign Up Failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private boolean isPasswordValid(String password) {
        Pattern uppercase = Pattern.compile("[A-Z]");
        Pattern lowercase = Pattern.compile("[a-z]");
        Pattern digit = Pattern.compile("[0-9]");
        Pattern specialChar = Pattern.compile("[^a-zA-Z0-9]");
        return password.length() >= 8 &&
                uppercase.matcher(password).find() &&
                lowercase.matcher(password).find() &&
                digit.matcher(password).find() &&
                specialChar.matcher(password).find();
    }

    private void togglePasswordVisibility(EditText passwordEditText, ImageView visibilityToggle) {
        if (isPasswordVisible) {
            passwordEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            visibilityToggle.setImageResource(R.drawable.ic_visibility_off); // Replace with actual icon
        } else {
            passwordEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            visibilityToggle.setImageResource(R.drawable.ic_visibility); // Replace with actual icon
        }
        // Move the cursor to the end of the text
        passwordEditText.setSelection(passwordEditText.getText().length());
        isPasswordVisible = !isPasswordVisible;
    }
}
