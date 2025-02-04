package com.example.cv;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;
import java.util.UUID;

/** @noinspection ALL*/
public class PersonalInformationActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1000;
    private static final int CAMERA_REQUEST = 1001;
    private static final int REQUEST_PERMISSION = 2000;

    private EditText etFacultyName, etDesignation, etDepartment, etAge, etGender, etContact, etEmail;
    private ImageView ivProfileImage;
    private DatabaseReference facultyRef;
    private StorageReference storageRef;
    private String facultyId;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);

        // Initialize Firebase and Auth
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        FirebaseStorage storage = FirebaseStorage.getInstance();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        storageRef = storage.getReference();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null) {
            facultyId = currentUser.getUid();
            facultyRef = database.getReference("faculty").child(facultyId);
        } else {
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show();
            return;
        }

        etFacultyName = findViewById(R.id.form_personal_et_name);
        etDesignation = findViewById(R.id.form_personal_et_designation);
        etDepartment = findViewById(R.id.form_personal_et_department);
        etAge = findViewById(R.id.form_personal_et_age);
        etGender = findViewById(R.id.form_personal_et_gender);
        etContact = findViewById(R.id.form_personal_et_contact);
        etEmail = findViewById(R.id.form_personal_et_email);
        ivProfileImage = findViewById(R.id.form_personal_iv_img);
        Button btnSave = findViewById(R.id.form_personal_btn_save);
        Button btnViewDetails = findViewById(R.id.form_personal_btn_view_details);
        ImageButton btnBack = findViewById(R.id.back_button);

        btnBack.setOnClickListener(v -> finish());

        // Set up click listeners
        btnSave.setOnClickListener(v -> {
            if (imageUri != null) {
                uploadImageAndSaveData();
            } else {
                savePersonalInformation("rajesh"); // Save data without image URL if no image selected
            }
        });

        btnViewDetails.setOnClickListener(v -> {
            Intent intent = new Intent(PersonalInformationActivity.this, DisplayPersonalInformationActivity.class);
            startActivity(intent);
        });

        ivProfileImage.setOnClickListener(v -> showImageChooserDialog());
    }

    // Method to show image chooser dialog
    private void showImageChooserDialog() {
        String[] options = {"Choose from Gallery", "Capture with Camera"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Image");
        builder.setItems(options, (dialog, which) -> {
            if (which == 0) {
                if (checkPermission()) {
                    // Choose from gallery
                    Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST);
                } else {
                    requestPermission();
                }
            } else if (which == 1) {
                if (checkPermission()) {
                    // Capture with camera
                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, CAMERA_REQUEST);
                } else {
                    requestPermission();
                }
            }
        });
        builder.show();
    }

    private boolean checkPermission() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA
        }, REQUEST_PERMISSION);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, show chooser again
                showImageChooserDialog();
            } else {
                Toast.makeText(this, "Permissions are required to select an image", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == PICK_IMAGE_REQUEST && data != null && data.getData() != null) {
                imageUri = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                    ivProfileImage.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(this, "Failed to load image", Toast.LENGTH_SHORT).show();
                }
            } else if (requestCode == CAMERA_REQUEST && data != null && data.getExtras() != null) {
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                ivProfileImage.setImageBitmap(photo);
            }
        }
    }

    private void uploadImageAndSaveData() {
        if (imageUri != null) {
            String imageFileName = "profile_images/" + UUID.randomUUID().toString();
            StorageReference imageRef = storageRef.child(imageFileName);

            imageRef.putFile(imageUri).addOnSuccessListener(taskSnapshot ->
                    imageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                        String imageUrl = uri.toString();
                        savePersonalInformation(imageUrl);
                    })
            ).addOnFailureListener(e ->
                    Toast.makeText(PersonalInformationActivity.this, "Failed to upload image", Toast.LENGTH_SHORT).show()
            );
        }
    }

    private void savePersonalInformation(String imageUrl) {
        String name = etFacultyName.getText().toString();
        String designation = etDesignation.getText().toString();
        String department = etDepartment.getText().toString();
        String age = etAge.getText().toString();
        String gender = etGender.getText().toString();
        String contact = etContact.getText().toString();
        String email = etEmail.getText().toString();

        if (name.isEmpty() || designation.isEmpty() || department.isEmpty() || age.isEmpty() ||
                gender.isEmpty() || contact.isEmpty() || email.isEmpty()) {
            Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
            return;
        }

        Faculty faculty = new Faculty(facultyId, name, designation, department, age, gender, contact, email, imageUrl);

        facultyRef.setValue(faculty).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(PersonalInformationActivity.this, "Data saved successfully", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(PersonalInformationActivity.this, "Failed to save data", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
