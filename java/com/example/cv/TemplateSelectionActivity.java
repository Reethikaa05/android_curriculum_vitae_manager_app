package com.example.cv;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TemplateSelectionActivity extends AppCompatActivity {

    private LinearLayout templateLayout;
    private DatabaseReference databaseReference;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_template_selection);

        templateLayout = findViewById(R.id.template_layout);
        databaseReference = FirebaseDatabase.getInstance().getReference("Templates");

        fetchTemplates();
    }

    private void fetchTemplates() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                templateLayout.removeAllViews();
                for (DataSnapshot templateSnapshot : dataSnapshot.getChildren()) {
                    String templateName = templateSnapshot.child("name").getValue(String.class);
                    String templateFilePath = templateSnapshot.child("filePath").getValue(String.class);
                    addTemplateButton(templateName, templateFilePath);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle error
            }
        });
    }

    private void addTemplateButton(String templateName, String templateFilePath) {
        Button templateButton = new Button(this);
        templateButton.setText(templateName);
        templateButton.setOnClickListener(v -> {
            Intent intent = new Intent(TemplateSelectionActivity.this, ViewCVActivity.class);
            intent.putExtra("TemplateFilePath", templateFilePath);
            startActivity(intent);
        });
        templateLayout.addView(templateButton);
    }
}
