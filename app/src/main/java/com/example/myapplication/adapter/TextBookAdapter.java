package com.example.myapplication.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.models.TextBook;

import java.util.List;

/**
 * @noinspection unused
 */
public class TextBookAdapter extends RecyclerView.Adapter<TextBookAdapter.TextBoooksHolder> {
    private final List<TextBook> list;
     /** @noinspection unused*/
     final Activity activity;

    public TextBookAdapter(List<TextBook> list, Activity activity) {

        this.list = list;
        this.activity = activity;
    }

    @NonNull
    @Override
    public TextBoooksHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TextBoooksHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.iteam_text,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull TextBoooksHolder holder, int position) {



       TextBook textBook =list.get(position);
       holder.chapterName.setText(textBook.getChapterName());
       holder.chapter.setText(textBook.getChapter());


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    /** @noinspection unused*/
    public static  class TextBoooksHolder extends RecyclerView.ViewHolder {

        final TextView chapterName;
        final TextView chapter;
        /** @noinspection unused*/
        public TextBoooksHolder(@NonNull View itemView) {
            super(itemView);
            chapterName=itemView.findViewById(R.id.textBookchapterName);
            chapter=itemView.findViewById(R.id.content);
        }
    }
}
