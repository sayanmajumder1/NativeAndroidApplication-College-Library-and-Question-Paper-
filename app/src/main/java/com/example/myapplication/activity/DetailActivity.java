package com.example.myapplication.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.myapplication.R;
import com.example.myapplication.adapter.TextBookAdapter;
import com.example.myapplication.helper.Viewpdf;
import com.example.myapplication.models.TextBook;

import java.util.ArrayList;
import java.util.List;

// This Detail Activity is created to show all chapters and contents of all books
public class DetailActivity extends AppCompatActivity {
    String title, content, type, cover;
    TextBookAdapter adapter;
    private final List<TextBook> list = new ArrayList<>();
    ViewPager2 viewPager2;
    LinearLayout linearLayout;
    ProgressBar loader;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE);

        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        title = bundle.getString("title");
        content = bundle.getString("content");
        type = bundle.getString("type");
        cover = bundle.getString("cover");

        // Implement logic for the Detail Activity to show the PDF
        linearLayout = findViewById(R.id.pdfViewer1);
        loader = findViewById(R.id.pdf_loader1);
        viewPager2 = findViewById(R.id.text_book);


        if (type.contains("text")) {
            linearLayout.setVisibility(View.GONE);
            loader.setVisibility(View.GONE);
            viewPager2.setVisibility(View.VISIBLE);
            initText();
        } else {
            linearLayout.setVisibility(View.VISIBLE);
            loader.setVisibility(View.VISIBLE);
            viewPager2.setVisibility(View.GONE);
            new Viewpdf(content, linearLayout, loader, DetailActivity.this);
        }
    }

    @SuppressLint({"NotifyDataSetChanged", "SetTextI18n"})
    private void initText() {
        adapter = new TextBookAdapter(list, DetailActivity.this);
        viewPager2.setAdapter(adapter);
        viewPager2.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);  // Set horizontal orientation

        // Add PageChangeCallback to update the page number


        // Breakpoint for chapters of all books using the tilde symbol (~)
        if (content.contains("~~")) {
            String[] data = content.split("~~");
            for (String datum : data) {
                String[] chapterData = datum.split("~");
                if (chapterData.length == 2) {
                    String chapterName = chapterData[0];
                    String chapterContent = chapterData[1];
                    list.add(new TextBook(chapterName, chapterContent));
                }
            }
        } else if (content.contains("~")) {
            String[] chapterData = content.split("~");
            if (chapterData.length == 2) {
                String chapterName = chapterData[0];
                String chapterContent = chapterData[1];
                list.add(new TextBook(chapterName, chapterContent));
            }
        }

        // Notify adapter that the dataset has changed
        adapter.notifyDataSetChanged();
        // Set the initial page number

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (type.contains("pdf")) {
            Viewpdf.stopPdf();
        }
    }
}
