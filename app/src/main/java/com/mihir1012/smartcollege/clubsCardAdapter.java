package com.mihir1012.smartcollege;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class clubsCardAdapter extends RecyclerView.Adapter<clubsCardAdapter.clubsCardViewHolder> {
    ArrayList<clubsCardInfo> mClubsInput;
    //constructor
    public clubsCardAdapter(ArrayList<clubsCardInfo> list){
        mClubsInput = list;
    }

    @NonNull
    @Override
    public clubsCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.clubs_cards,parent,false);
        clubsCardViewHolder ccvh=new clubsCardViewHolder(v);
        return ccvh;
    }

    @Override
    public void onBindViewHolder(@NonNull clubsCardViewHolder holder, int position) {
        clubsCardInfo cci=mClubsInput.get(position);
        holder.ClubViewName.setText(cci.getClubName());
        holder.CardViewName.setText(cci.getClubActivityName());
        holder.CardViewDesc.setText(cci.getClubActivityDesc());
    }

    @Override
    public int getItemCount() {
        return mClubsInput.size();
    }

    public static class clubsCardViewHolder extends RecyclerView.ViewHolder{
        public TextView ClubViewName,CardViewName,CardViewDesc;
        public clubsCardViewHolder(View itemView){
                super(itemView);
                ClubViewName =itemView.findViewById(R.id.cardClubName);
                CardViewName =itemView.findViewById(R.id.cardClubActivityName);
                CardViewDesc=itemView.findViewById(R.id.cardClubActivityDesc);
            }
        }
}
