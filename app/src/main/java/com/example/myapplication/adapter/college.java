package com.example.myapplication.adapter;
import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myapplication.R;
import com.example.myapplication.models.Card;
import java.util.ArrayList;
public class college extends RecyclerView.Adapter<college.MyViewHolder> {
    public final userClickListener user;
    /** @noinspection unused*/
    final Context context;
    ArrayList<Card> arrayList;

    /** @noinspection unused*/
    final LayoutInflater layoutInflater;

    public  interface  userClickListener{

        void  selectedUser(Card card);



    }
    public  college(Context context,ArrayList<Card> arrayList, userClickListener user){

        this.context=context;
        this.arrayList=arrayList;
        layoutInflater=LayoutInflater.from(context);
        this.user=user;






    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {



        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Card currentCard = arrayList.get(position);
        holder.cardName.setText(arrayList.get(position).getCardName());
        holder.img.setImageResource(arrayList.get(position).getImg());
        holder.itemView.setOnClickListener(v -> user.selectedUser(currentCard ));
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
    @SuppressLint("NotifyDataSetChanged")
    public void updateList(ArrayList<Card> newList) {
        arrayList = new ArrayList<>(newList);
        notifyDataSetChanged();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        final TextView cardName;
        final ImageView img;
        public MyViewHolder(@NonNull View itemView) {




            super(itemView);
            cardName=itemView.findViewById(R.id.txt1);
            img=itemView.findViewById(R.id.image90);
        }
    }
}
