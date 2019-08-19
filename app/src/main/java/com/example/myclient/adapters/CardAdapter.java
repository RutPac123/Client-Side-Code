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

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.CardViewHolder> {

    private ArrayList<String> listimg;
    private ArrayList<String> listdetailstxt;
    private OnItemClickListener listener;
    private Context context;


    public CardAdapter(Context context,ArrayList listimg,ArrayList listdetailstxt){
        this.context = context;
        this.listimg = listimg;
        this.listdetailstxt = listdetailstxt;
    }
    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View v = inflater.inflate(R.layout.card_item,viewGroup,false);
        return new CardViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder cardViewHolder, final int i) {

                Glide.with(context).load(listimg.get(i)).into(cardViewHolder.myimg);
                cardViewHolder.mytxt.setText(listdetailstxt.get(i));
                cardViewHolder.mycard.setOnClickListener(new View.OnClickListener() {
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

    public class CardViewHolder extends RecyclerView.ViewHolder{

        ImageView myimg;
        TextView mytxt;
        CardView mycard;
        public CardViewHolder(@NonNull View itemView) {
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
