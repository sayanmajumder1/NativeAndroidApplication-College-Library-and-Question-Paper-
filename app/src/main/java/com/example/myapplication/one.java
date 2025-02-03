package com.example.myapplication;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.ImageButton;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myapplication.adapter.BookAdapter;
import com.example.myapplication.models.Books;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
public class one extends AppCompatActivity {
    private final List<Books> bookList = new ArrayList<>();
    private BookAdapter bookAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one);
        // Initialize UI components
        ImageButton backButton = findViewById(R.id.button1);
        backButton.setOnClickListener(v -> finish());
        RecyclerView recyclerView = findViewById(R.id.recyle33);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        bookAdapter = new BookAdapter(bookList, one.this);
        recyclerView.setAdapter(bookAdapter);
        // Load data from Firebase
        loadData();
    }
    // Method to load data from Firebase
    private void loadData() {
        FirebaseDatabase.getInstance().getReference()
                .child("biotechnology")
                .addValueEventListener(new ValueEventListener() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        bookList.clear();  // Clear the list to prevent duplicates
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            String title = Objects.requireNonNull(dataSnapshot.child("title").getValue()).toString();
                            String cover = Objects.requireNonNull(dataSnapshot.child("cover").getValue()).toString();
                            String content = Objects.requireNonNull(dataSnapshot.child("content").getValue()).toString();
                            String type = Objects.requireNonNull(dataSnapshot.child("type").getValue()).toString();
                            // Add book data to the list
                            bookList.add(new Books(title, cover, content, type));
                        }
                        // Ensure updates happen on the main thread
                        new Handler(Looper.getMainLooper()).post(() -> bookAdapter.notifyDataSetChanged());
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // Handle Firebase errors
                        // Optionally log or display error message
                    }
                });
    }
}
