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
//Book Adapter For The Pdf
public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookHolder> {


    private final List<Books> list;


final Context context;

    public BookAdapter(List<Books>list, Context  context) {
        this.list=list;

        this.context= context;
    }





    @NonNull
    @Override
    public BookHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return  new BookHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_books,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull BookHolder holder, int position) {
Books books = list.get(position);
holder.title.setText(books.getTitle());
Glide.with(context)
                .load(books.getCover())
        .skipMemoryCache(true)
                        .transition(DrawableTransitionOptions.withCrossFade())
                                .into(holder.cover);
        Glide.with(context)
                        .load(books.getCover())
                                .skipMemoryCache(true)
                                        .transition(DrawableTransitionOptions.withCrossFade())
                                                .into(holder.cover);

holder.itemView.setOnClickListener(v -> {
    Intent intent = new Intent(context, DetailActivity.class);

    intent.putExtra("title",books.getTitle());
    intent.putExtra("content",books.getContent());
    intent.putExtra("cover",books.getCover());
    intent.putExtra("type",books.getType());
    context.startActivity(intent);




});
    }




    @Override
    public int getItemCount() {
        return list.size();
    }

    public static  class  BookHolder extends RecyclerView.ViewHolder{
 final ImageView cover;
 final TextView title;

        public BookHolder(@NonNull View itemView) {
            super(itemView);
            cover =itemView.findViewById(R.id.book_cover);
            title=itemView.findViewById(R.id.book_t);



        }


        }
}
