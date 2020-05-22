package com.mihir1012.smartcollege;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class eventAddDialog extends AppCompatDialogFragment {
    int club_active=0;

   public eventAddDialog(int event_off){
        club_active=event_off;
    }
    public interface eventAddListener{
     void getEventInfo(String EventName,String EventDate,String EventDesc);
    }
    public eventAddListener listener;

   @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener= (eventAddListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()+" must implement getEventInfo ");
        }
    }
    private TextView Name,Desc,Date;
    private EditText eventDate,eventName,eventDesc;
    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
            View view = inflater.inflate(R.layout.enter_event, null);
            Date = view.findViewById(R.id.dialogTextDate);
            Desc = view.findViewById(R.id.dialogTextDesc);
            Name = view.findViewById(R.id.dialogTextName);
            eventDate = view.findViewById(R.id.dialogEditDate);
            eventDesc = view.findViewById(R.id.dialogEditDesc);
            eventName = view.findViewById(R.id.dialogEditName);
//          "IF" IS FOR CLUBS ADD DIALOG AND "ELSE" IS FOR EVENT ADD DIALOG
            if(club_active==1){
                Name.setText("Enter Name:");
                Desc.setText("Enter Activity Description:");
                Date.setText("Enter Activity Name:");
                eventDate.setHint("Club Activity");
                eventName.setHint("Club Name");
                eventDesc.setHint("Club Activity Description");
            }
            else {
                Name.setText("Enter Name:");
                Desc.setText("Enter Description:");
                Date.setText("Enter Date:");
                eventDate.setHint("Event Date");
                eventName.setHint("Event Name");
                eventDesc.setHint("Event Description");
            }

            builder.setView(view)
                    .setTitle("Login")
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    })
                    .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            String name = eventName.getText().toString();
                            String date = eventDate.getText().toString();
                            String desc = eventDesc.getText().toString();
                            listener.getEventInfo(name, date, desc);
                        }
                    });

        return builder.create();
    }
}
