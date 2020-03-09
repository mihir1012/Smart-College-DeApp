package com.mihir1012.smartcollege;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Eventinfo extends AppCompatActivity implements eventAddDialog.eventAddListener {
    ArrayList<eventDesc> eventList;
    Toolbar toolbar;
    DatabaseReference reff;
    private RecyclerView recyclerView;
    private eventAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private String login;
    private RelativeLayout relEvent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventinfo);
        toolbar = findViewById(R.id.EventsToolbar);
        toolbar.setTitle("Events");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        reff = FirebaseDatabase.getInstance().getReference().child("Events");

        Intent intent = getIntent();
        login = intent.getStringExtra("login");

        if(login.substring(0,1).equals("p")) {
            relEvent = findViewById(R.id.linearEvent);
            ImageView img = new ImageView(this);
            img.setImageResource(R.drawable.ic_launcher_round_add_foreground);
            img.setMinimumHeight(50);
            img.setMaxWidth(50);
            img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    OpenDialog();
                }
            });
            RelativeLayout.LayoutParams  params = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
            RelativeLayout.LayoutParams.WRAP_CONTENT
            );
            Resources r = this.getResources();
            int px = (int) TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    110,
                    r.getDisplayMetrics()
            );
            params.setMargins(px,0,0,0);
            params.addRule(RelativeLayout.CENTER_HORIZONTAL);
            img.setLayoutParams(params);

            relEvent.addView(img);
        }
        createList();
        recyclerBuild();
    }
    private void OpenDialog(){
        eventAddDialog eventDialog =new eventAddDialog();
        eventDialog.show(getSupportFragmentManager(),"eventDialog");
    }

    private void createList(){
        eventList = new ArrayList<>();
        reff.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot data : dataSnapshot.getChildren()){
                    eventList.add(new eventDesc(data.child("eventName").getValue().toString(),data.child("eventDate").getValue().toString(),data.child("eventDescription").getValue().toString()));
                }
                mAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }});
    }

    private void recyclerBuild(){
        recyclerView = findViewById(R.id.RecyclerEvent);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        mAdapter = new eventAdapter(eventList);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void getEventInfo(String EventName, String EventDate, String EventDesc) {
        reff.child(EventDate).setValue(new eventDesc(EventName,EventDate,EventDesc));
        eventList.add(new eventDesc(EventName,EventDate,EventDesc));
        mAdapter.notifyDataSetChanged();
    }
}
