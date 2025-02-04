package com.example.cv;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ViewBookActivity extends AppCompatActivity {

    private BookAdapter bookAdapter;
    private List<Book> bookList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_book); // Ensure this layout exists

        RecyclerView recyclerView = findViewById(R.id.view_container_book_list);
        Button backButton = findViewById(R.id.back_button);
        bookList = new ArrayList<>();
        bookAdapter = new BookAdapter(this, bookList);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(bookAdapter);

        // Initialize Firebase Database and Reference
        DatabaseReference booksRef = FirebaseDatabase.getInstance().getReference("Books"); // Ensure this matches your storage structure

        // Retrieve books data from Firebase
        booksRef.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Book book = postSnapshot.getValue(Book.class);
                    if (book != null) {
                        bookList.add(book);

                    }
                }

                // Notify the adapter about data changes
                bookAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle potential errors here
            }
        });

        // Back button functionality
        backButton.setOnClickListener(v -> onBackPressed());
    }
}
