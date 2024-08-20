package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;


public class BlankFragment1 extends Fragment {

    /** @noinspection unused*/
    MainActivity mainActivity;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_blank1, container, false);



        CardView card=view.findViewById(R.id.c1);
        CardView card1=view.findViewById(R.id.c2);
        CardView card2=view.findViewById(R.id.c3);
        CardView card3=view.findViewById(R.id.c4);
        mainActivity=(MainActivity)getActivity();
        // Applying set On Click Listener For click functions  All Cards
        card.setOnClickListener(view1 -> {
            Intent i=new Intent(getActivity(), one.class);
            startActivity(i);

        });
        card1.setOnClickListener(v -> {
            Intent q= new Intent(getActivity(),two.class);
            startActivity(q);

        });
        card2.setOnClickListener(v -> {
            Intent u=new Intent(getActivity(),three.class);
            startActivity(u);
        });
        card3.setOnClickListener(v -> {
            Intent y=new Intent(getActivity(),four.class);
            startActivity(y);
        });




return  view;





    }
}