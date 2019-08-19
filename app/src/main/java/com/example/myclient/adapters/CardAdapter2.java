package com.example.myclient.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.myclient.R;

import java.util.ArrayList;

public class CardAdapter2 extends RecyclerView.Adapter<CardAdapter2.CardViewHolder2> {

    private ArrayList<String> listimg;
    private ArrayList<String> listdetailstxt;
    private OnItemClickListener listener;
    private Context context;


    public CardAdapter2(Context context,ArrayList listimg,ArrayList listdetailstxt){
        this.context = context;
        this.listimg = listimg;
        this.listdetailstxt = listdetailstxt;
    }



    @NonNull
    @Override
    public CardViewHolder2 onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View v = inflater.inflate(R.layout.card_item2,viewGroup,false);
        return new CardAdapter2.CardViewHolder2(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder2 cardViewHolder2, final int i) {
        Glide.with(context).load(listimg.get(i)).into(cardViewHolder2.myimg);
        cardViewHolder2.mytxt.setText(listdetailstxt.get(i));
        cardViewHolder2.mycard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(v,i);
            }
        });
    }


    @Override
    public int getItemCount() {
        return listimg.size();
    }

    public class CardViewHolder2 extends RecyclerView.ViewHolder{

        ImageView myimg;
        TextView mytxt;
        CardView mycard;
        public CardViewHolder2(@NonNull View itemView) {
            super(itemView);

            myimg = itemView.findViewById(R.id.img);
            mytxt = itemView.findViewById(R.id.txt);
            mycard = itemView.findViewById(R.id.card);
        }
    }

    public interface OnItemClickListener{
        void onItemClick(View view, int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

}
