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
//this Detail Activity Is created For Show All Chapter And contents Of All Books
public class DetailActivity extends AppCompatActivity {
/** @noinspection unused*/
String title, content, type, /** @noinspection unused*/
    cover;



TextBookAdapter adapter;
    private final List<TextBook>list = new ArrayList<>();
ViewPager2 viewPager2;
LinearLayout linearLayout;
ProgressBar loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE);

        Bundle bundle= getIntent().getExtras();
        assert bundle != null;
        title= bundle.getString("title");
        content = bundle.getString("content");
        type = bundle.getString("type");
        cover = bundle.getString("cover");
//Implement Logic For The Detail Activity To show The Pdf

linearLayout = findViewById(R.id.pdfViewer1);
loader= findViewById(R.id.pdf_loader1);
viewPager2 = findViewById(R.id.text_book);

if(type.contains("text")){

    linearLayout.setVisibility(View.GONE);
    loader.setVisibility(View.GONE);
    viewPager2.setVisibility(View.VISIBLE);
    initText();
}else{
    linearLayout.setVisibility(View.VISIBLE);
    loader.setVisibility(View.VISIBLE);
    viewPager2.setVisibility(View.GONE);

new Viewpdf(content, linearLayout,loader,DetailActivity.this);


}



    }

    @SuppressLint("NotifyDataSetChanged")
    private void initText() {
        viewPager2 = findViewById(R.id.text_book);
        adapter = new TextBookAdapter(list, DetailActivity.this);
        viewPager2.setAdapter(adapter);
//Break Point  For Chapter  Of All Books Using Tilda
if(content.contains("~~")){

    String[] data = content.split("~~");

    for (String datum : data) {

        String chapterName = datum.split("~")[0];
        String chapterContent = datum.split("~")[1];
list.add(new TextBook(chapterName,chapterContent));
adapter.notifyDataSetChanged();

    }
}else{


    if(content.contains("~")){

        String chapterName = content.split("~")[0];
        String chapterContent = content.split("~")[1];
        list.add(new TextBook(chapterName,chapterContent));
        adapter.notifyDataSetChanged();



    }

}




    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
if(type.contains("pdf")){
        Viewpdf.stopPdf();

}
    }
}
