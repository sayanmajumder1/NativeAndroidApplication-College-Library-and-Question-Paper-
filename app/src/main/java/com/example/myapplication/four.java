package com.example.myapplication;



import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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

public class four extends AppCompatActivity {
// Create Array list
private final   List<Books> list = new ArrayList<>();
    RecyclerView recyclerView;
    BookAdapter bookAdapter;



// Implement  image button for back button
    ImageButton button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_four);

        button=findViewById(R.id.button8);
        button.setOnClickListener(v -> finish());
        // implement recycler view For the  Book or Question Paper  Contents
        recyclerView = findViewById(R.id.recyle36);
        // Implement  book Adapter
        bookAdapter = new BookAdapter(list,four.this);
        recyclerView.setAdapter(bookAdapter);
        loadData();
    }

    private void loadData() {
        FirebaseDatabase.getInstance().getReference()
                .child("books")
                .addValueEventListener(new ValueEventListener() {

                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        list.clear();
                        bookAdapter.notifyDataSetChanged();

                        for(DataSnapshot dataSnapshot: snapshot.getChildren()){

                            String title = Objects.requireNonNull(dataSnapshot.child("title").getValue()).toString();
                            String cover = Objects.requireNonNull(dataSnapshot.child("cover").getValue()).toString();
                            String content = Objects.requireNonNull(dataSnapshot.child("content").getValue()).toString();
                            String type = Objects.requireNonNull(dataSnapshot.child("type").getValue()).toString();

                            list.add(new Books(title, cover, content , type));
                            bookAdapter.notifyDataSetChanged();








                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


    }
}