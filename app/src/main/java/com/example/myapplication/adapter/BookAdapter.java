package com.example.myapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.example.myapplication.R;
import com.example.myapplication.activity.DetailActivity;
import com.example.myapplication.models.Books;
import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookHolder> {

    private final List<Books> bookList;
    private final Context context;

    public BookAdapter(List<Books> bookList, Context context) {
        this.bookList = bookList;
        this.context = context;
    }

    @NonNull
    @Override
    public BookHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_books, parent, false);
        return new BookHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookHolder holder, int position) {
        Books book = bookList.get(position);
        holder.title.setText(book.getTitle());

        // Load the book cover using Glide
        Glide.with(context)
                .load(book.getCover())
                .skipMemoryCache(true)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(holder.cover);

        // Set click listener for the book item
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra("title", book.getTitle());
            intent.putExtra("content", book.getContent());
            intent.putExtra("cover", book.getCover());
            intent.putExtra("type", book.getType());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    public static class BookHolder extends RecyclerView.ViewHolder {
        final ImageView cover;
        final TextView title;
        public BookHolder(@NonNull View itemView) {
            super(itemView);
            cover = itemView.findViewById(R.id.book_cover);
            title = itemView.findViewById(R.id.book_t);
        }
    }
}
