package com.example.myclient.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.myclient.R;
import com.example.myclient.activities.OTP;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder>{
    Context context;
    ArrayList<String> clinetsList;
    public String myClient;

    class SearchViewHolder extends RecyclerView.ViewHolder{
        TextView nametxt;

        public SearchViewHolder(@NonNull View itemView) {
            super(itemView);
            nametxt = itemView.findViewById(R.id.clientName);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();

                    if (position != RecyclerView.NO_POSITION){
                        myClient = clinetsList.get(position);
                        OTP.cName.setText(myClient);
                    }
                }
            });

        }
    }
    public SearchAdapter(Context context, ArrayList<String> clinetsList) {
        this.context = context;
        this.clinetsList = clinetsList;
    }

    @NonNull
    @Override
    public SearchAdapter.SearchViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View myView = LayoutInflater.from(context).inflate(R.layout.client_search_layout,viewGroup,false);
        return new SearchViewHolder(myView);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder searchViewHolder, int i) {
        searchViewHolder.nametxt.setText(clinetsList.get(i));
    }

    @Override
    public int getItemCount() {
        return clinetsList.size();
    }


}
