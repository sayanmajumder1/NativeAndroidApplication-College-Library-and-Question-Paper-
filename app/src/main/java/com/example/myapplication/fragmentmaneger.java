package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

//This  Java Code Is Create For Fragment mangers To Manages All Fragments Which Is Present In tabs
public class fragmentmaneger extends FragmentStateAdapter {
    private static  final int tabCount = 3 ;
    public fragmentmaneger(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);

    }
// Where we Use The Switch  Case Because  It  basically Switch The All Fragments Which Are Is Is Presents On tabs  and return the particular tab
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:return  new BlankFragment1();
            case 1:return  new BlankFragment2();
            case 2: return  new BlankFragment3();
            default:
                throw new IllegalArgumentException("Invalid position:" + position);



        }
    }

    @Override
    public int getItemCount() {
        return tabCount ;
    }
}


