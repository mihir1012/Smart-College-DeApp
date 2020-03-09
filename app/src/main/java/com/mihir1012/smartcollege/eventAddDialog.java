package com.mihir1012.smartcollege;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class eventAddDialog extends AppCompatDialogFragment {
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

    private EditText eventDate,eventName,eventDesc;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder =  new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view =  inflater.inflate(R.layout.enter_event,null);
        eventDate = view.findViewById(R.id.dialogEditDate);
        eventDesc = view.findViewById(R.id.dialogEditDesc);
        eventName = view.findViewById(R.id.dialogEditName);
        builder.setView(view)
                .setTitle("Login")
                .setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String name  = eventName.getText().toString();
                        String date = eventDate.getText().toString();
                        String desc = eventDesc.getText().toString();
                        listener.getEventInfo(name,date,desc);
                    }
                });

        return builder.create();
    }
}
