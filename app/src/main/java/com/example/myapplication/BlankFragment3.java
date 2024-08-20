package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;


public class BlankFragment3 extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View  view= inflater.inflate(R.layout.fragment_blank3, container, false);

        CardView c1, c2, c3, c4;



            c1=view.findViewById(R.id.d1);
            c2=view.findViewById(R.id.d2);
            c3=view.findViewById(R.id.d3);
            c4=view.findViewById(R.id.d4);

            c1.setOnClickListener(v -> {
                Intent i= new Intent(getActivity(), BookActivity2.class);
                startActivity(i);
            });
            c2.setOnClickListener(v -> {
                Intent j= new Intent(getActivity(), BookActivity3.class);
                startActivity(j);
            });

            c3.setOnClickListener(v -> {
                Intent k= new Intent(getActivity(), BookActivity4.class);
                startActivity(k);
            });

            c4.setOnClickListener(v -> {
                Intent l= new Intent(getActivity(), BookActivity5.class);
                startActivity(l);
            });







            return view;


    }
}