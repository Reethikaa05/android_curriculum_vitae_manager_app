package com.example.cv;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText passwordEditText;
    private FirebaseAuth mAuth;
    private ImageView togglePasswordVisibility;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Log.d("LoginActivity", "LoginActivity started");

        mAuth = FirebaseAuth.getInstance();  // Initialize FirebaseAuth instance

        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);
        Button loginButton = findViewById(R.id.login_button);
        TextView signUpLink = findViewById(R.id.signup_link);
        TextView forgotPasswordLink = findViewById(R.id.forgot_password);
        togglePasswordVisibility = findViewById(R.id.toggle_password_visibility);

        loginButton.setOnClickListener(v -> handleLogin());

        // Add click listener for the sign-up link
        signUpLink.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
            startActivity(intent);
        });

        // Add click listener for the forgot password link
        forgotPasswordLink.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
            startActivity(intent);
        });

        // Add click listener for password visibility toggle
        togglePasswordVisibility.setOnClickListener(v -> {
            if (passwordEditText.getInputType() == (InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD)) {
                // Show password
                passwordEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                togglePasswordVisibility.setImageResource(R.drawable.ic_visibility); // Change to visible icon
            } else {
                // Hide password
                passwordEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                togglePasswordVisibility.setImageResource(R.drawable.ic_visibility_off); // Change to invisible icon
            }

            // Move the cursor to the end of the text
            passwordEditText.setSelection(passwordEditText.getText().length());
        });
    }

    private void handleLogin() {
        String name = usernameEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        if (name.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please enter both email and password", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!isPasswordValid(password)) {
            Toast.makeText(this, "Password must be at least 8 characters long, start with an uppercase letter, and contain lowercase letters, numbers, and one special character (@, _, #)", Toast.LENGTH_LONG).show();
            return;
        }

        // Firebase sign in
        mAuth.signInWithEmailAndPassword(name, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, navigate to the dashboard
                        Log.d("LoginActivity", "signInWithEmail:success");
                        FirebaseUser user = mAuth.getCurrentUser();
                        Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                        startActivity(intent);
                        finish(); // Optional: Close the login activity so the user can't go back
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w("LoginActivity", "signInWithEmail:failure", task.getException());
                        Toast.makeText(LoginActivity.this, "Authentication failed. Invalid username or password.",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private boolean isPasswordValid(String password) {
        if (password.length() < 8) {
            return false;
        }

        if (!Character.isUpperCase(password.charAt(0))) {
            return false;
        }

        boolean hasLowerCase = false;
        boolean hasDigit = false;
        boolean hasSpecialChar = false;

        for (int i = 1; i < password.length(); i++) {
            char c = password.charAt(i);

            if (Character.isLowerCase(c)) {
                hasLowerCase = true;
            } else if (Character.isDigit(c)) {
                hasDigit = true;
            } else if (c == '@' || c == '_' || c == '#') {
                hasSpecialChar = true;
            }

            if (hasLowerCase && hasDigit && hasSpecialChar) {
                return true;
            }
        }

        return false;
    }
}
