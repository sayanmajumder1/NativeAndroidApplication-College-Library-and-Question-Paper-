package com.example.myapplication;

import android.annotation.SuppressLint;
import android.os.Bundle;
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
public class BookActivity4 extends AppCompatActivity {
    private BookAdapter bookAdapter;
    private final List<Books> list = new ArrayList<>();
    RecyclerView recyclerView;
    ImageButton button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book4);
        button=findViewById(R.id.button03);
        button.setOnClickListener(v -> finish());
        recyclerView = findViewById(R.id.recyle39);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        bookAdapter = new BookAdapter(list, BookActivity4.this);
        recyclerView.setAdapter(bookAdapter);
        loadData();

    }

    private void loadData() {
        FirebaseDatabase.getInstance().getReference()
                // Database Field   Name Is Set Here -> books
                .child("books")
                .addValueEventListener(new ValueEventListener() {

                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        list.clear();
                        bookAdapter.notifyDataSetChanged();

                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            String title = Objects.requireNonNull(dataSnapshot.child("title").getValue()).toString();
                            String cover = Objects.requireNonNull(dataSnapshot.child("cover").getValue()).toString();
                            String content = Objects.requireNonNull(dataSnapshot.child("content").getValue()).toString();
                            String type = Objects.requireNonNull(dataSnapshot.child("type").getValue()).toString();
                            list.add(new Books(title, cover, content, type));
                        }
                        bookAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });

    }
}