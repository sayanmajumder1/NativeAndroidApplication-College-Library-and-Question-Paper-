package com.example.myapplication.helper;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import es.voghdev.pdfviewpager.library.RemotePDFViewPager;
import es.voghdev.pdfviewpager.library.adapter.PDFPagerAdapter;
import es.voghdev.pdfviewpager.library.remote.DownloadFile;
import es.voghdev.pdfviewpager.library.util.FileUtil;

public class Viewpdf {
    @SuppressLint("StaticFieldLeak")
    static PDFPagerAdapter pagerAdapter;
    RemotePDFViewPager  remotePDFViewPager;
    public Viewpdf(String url, LinearLayout pdflayout , ProgressBar loader, Activity activity) {

        DownloadFile.Listener listener=new DownloadFile.Listener() {
            @Override
            public void onSuccess(String url, String destinationPath) {
                pagerAdapter = new PDFPagerAdapter(activity, FileUtil.extractFileNameFromURL(url));
                remotePDFViewPager.setAdapter(pagerAdapter);

                refreshLayout(pdflayout);
                loader.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Exception e) {

            }

            @Override
            public void onProgressUpdate(int progress, int total) {

            }
        };
remotePDFViewPager = new RemotePDFViewPager(activity, url,listener);

    }
public static void  stopPdf(){
        if(pagerAdapter !=null){


            pagerAdapter.close();



        }




}
    private void refreshLayout(LinearLayout pdflayout) {

        pdflayout.addView(remotePDFViewPager,LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);


    }
}









