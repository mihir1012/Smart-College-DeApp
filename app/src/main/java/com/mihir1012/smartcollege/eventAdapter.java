package com.mihir1012.smartcollege;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class eventAdapter extends RecyclerView.Adapter<eventAdapter.eventViewHolder> {
    private ArrayList<eventDesc> mEventDesc;

    @NonNull
    @Override
    public eventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.individual_event_card,parent,false);
            eventViewHolder evh =new eventViewHolder(v);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull eventViewHolder holder, int position) {

        eventDesc eventDesc = mEventDesc.get(position);
        holder.textViewName.setText(eventDesc.getEventName());
        holder.textViewDate.setText(eventDesc.getEventDate());
        holder.textViewDesc.setText(eventDesc.getEventDescription());
    }

    @Override
    public int getItemCount() {
        return mEventDesc.size();
    }

    public static class eventViewHolder extends RecyclerView.ViewHolder{
        public TextView textViewName,textViewDate,textViewDesc;

        public eventViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textViewEventName);
            textViewDate = itemView.findViewById(R.id.textViewEventDate);
            textViewDesc = itemView.findViewById(R.id.textViewEventDesc);
            //Toast.makeText(this, "enter the data", Toast.LENGTH_SHORT).show();

        }
    }

    public  eventAdapter(ArrayList<eventDesc> exampleList){
        mEventDesc = exampleList;
    }
}
