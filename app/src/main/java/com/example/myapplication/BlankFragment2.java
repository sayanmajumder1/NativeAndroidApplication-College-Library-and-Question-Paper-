package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.adapter.CardAdapter;
import com.example.myapplication.models.Card;

import java.util.ArrayList;

public class BlankFragment2 extends Fragment implements CardAdapter.userClickListener {
    SearchView text;
RecyclerView recyclerView;
final ArrayList<Card> arrayList = new ArrayList<>();
ArrayList<Card> searchList;
final String[] CardList=new String[]{ "BioTechnology(BardhamanUniversity)", "BCA(BardhamanUniversity)", "BioChemistry(BardhamanUniversity)", "BBA(BardhamanUniversity)" };
final int[] imgList=new int[]{R.drawable.c, R.drawable.oh, R.drawable.kkkh, R.drawable.kiou};


@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=  inflater.inflate(R.layout.fragment_blank2, container, false);


     text=view.findViewById(R.id.editTextSearch);
     recyclerView=view.findViewById(R.id.recyclerView789);
     searchList = new ArrayList<>();

for( int i=0 ;i<CardList.length;i++){
 Card card=new Card();
 card.setCardName(CardList[i]);
    if (imgList.length > i) { // To prevent ArrayIndexOutOfBoundsException
        card.setImg(imgList[i]);
    }

 arrayList.add(card);

}
RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
recyclerView.setLayoutManager(layoutManager);
CardAdapter  cardAdapter = new CardAdapter(getContext(), arrayList,this);
recyclerView.setAdapter(cardAdapter);
text.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
    @Override
    public boolean onQueryTextSubmit(String query) {

        searchList.clear(); // Clear previous search results
        if (!query.isEmpty()) {
            for (int i = 0; i < arrayList.size(); i++) {
                if (arrayList.get(i).getCardName().toUpperCase().contains(query.toUpperCase())) {
                    searchList.add(arrayList.get(i));
                }
            }
            }


        cardAdapter.updateList(searchList);

        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {


        searchList.clear(); // Clear previous search results
        if (!newText.isEmpty()) {
            for (int i = 0; i < arrayList.size(); i++) {
                if (arrayList.get(i).getCardName().toUpperCase().contains(newText.toUpperCase())) {
                    searchList.add(arrayList.get(i));
                }
            }
        }




 cardAdapter.updateList(searchList);
        return false;
    }
});

return  view ;
    }

    @Override
    public void selectedUser(Card card) {

       String cardName= card.getCardName();
       Intent intent=null;
       switch (cardName){

           case "BCA(BardhamanUniversity)":
               intent=new Intent(getActivity(), three.class);
               break;

           case "BioTechnology(BardhamanUniversity)":
               intent=new Intent(getActivity(), one.class);
               break;

           case "BioChemistry(BardhamanUniversity)":
               intent=new Intent(getActivity(), four.class);
               break;
           case "BBA(BardhamanUniversity)":
               intent=new Intent(getActivity(), two.class);
               break;

       }
        if (intent != null) {
            startActivity(intent);
        } else {
            Toast.makeText(getActivity(), "No activity found for " + cardName, Toast.LENGTH_SHORT).show();
            // Handle the case when no matching activity is found for the cardName
            // You can display a Toast message or handle it in any other appropriate way
        }
    }
}



