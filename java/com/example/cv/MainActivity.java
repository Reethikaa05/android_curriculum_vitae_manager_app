package com.example.cv;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

/** @noinspection ALL*/
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final int REQUEST_CODE = 1; // Define a request code

    private TextView phoneTextView;
    private TextView emailTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        CardView cardView = findViewById(R.id.card_view);
        if (cardView == null) {
            Log.e(TAG, "CardView with ID 'card_view' not found.");
            return;
        }

        // Initialize TextViews for phone and email
        phoneTextView = findViewById(R.id.phone_text_view); // Change ID as per your layout
        emailTextView = findViewById(R.id.email_text_view); // Change ID as per your layout

        // Set default values
        phoneTextView.setText("9443869904");
        emailTextView.setText("rsrajesh@msuniv.ac.in");

        // Initialize and handle edit button click
        ImageButton editButton = findViewById(R.id.edit_button); // Ensure this button exists in your layout
        editButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ViewCVActivity.class);
            startActivity(intent);
        });

        // Initialize and handle home icon click
        ImageButton homeIcon = cardView.findViewById(R.id.home_icon);
        if (homeIcon == null) {
            Log.e(TAG, "Home icon with ID 'home_icon' not found.");
            return;
        }

        homeIcon.setOnClickListener(v -> {
            Log.d(TAG, "Home icon clicked");
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            // Retrieve updated data from the intent
            String updatedPhone = data.getStringExtra("updated_phone");
            String updatedEmail = data.getStringExtra("updated_email");

            // Update the UI with the new data
            if (phoneTextView != null && updatedPhone != null) {
                phoneTextView.setText(updatedPhone);
            }

            if (emailTextView != null && updatedEmail != null) {
                emailTextView.setText(updatedEmail);
            }
        }
    }
}
