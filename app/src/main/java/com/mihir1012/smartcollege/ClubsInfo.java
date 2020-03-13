package com.mihir1012.smartcollege;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.content.res.Resources;
import android.media.Image;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class
ClubsInfo extends AppCompatActivity implements eventAddDialog.eventAddListener {
    ArrayList<clubsCardInfo> mclubsadd;
    public clubsCardAdapter mclubsadpter;
    private RecyclerView.LayoutManager layoutManager;
    RecyclerView recyclerView;
    SharedPreferences pref;
    private String login;
    Toolbar toolbar;
    DatabaseReference reff;

    ImageView img;
    private RelativeLayout relclub;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clubs_info);
        toolbar = findViewById(R.id.ClubsToolbar);
        toolbar.setTitle("Clubs");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        relclub = findViewById(R.id.ClubsRel);
        reff = FirebaseDatabase.getInstance().getReference().child("Clubs");

        pref=getSharedPreferences("myPreferences",MODE_PRIVATE);
        login = pref.getString("Enrolment","0001");

        if(login.substring(0,1).equals("p")) {
            img = new ImageView(this);
            img.setImageResource(R.drawable.ic_launcher_round_add_foreground);
            img.setMinimumHeight(50);
            img.setMaxWidth(50);
            img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    OpenDialog();
                }
            });
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT

            );
            Resources r = this.getResources();
            int px = (int) TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    150,
                    r.getDisplayMetrics()
            );
            params.setMargins(px, 50, 0, 0);
            params.addRule(RelativeLayout.CENTER_HORIZONTAL);
            img.setLayoutParams(params);
            relclub.addView(img);
        }
        createlist();
        recyclerBuild();
    }

    private void OpenDialog(){
        eventAddDialog eventDialog =new eventAddDialog();
        eventDialog.show(getSupportFragmentManager(),"eventDialog");
    }

    public void createlist() {
        mclubsadd=new ArrayList<>();
        reff.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot data: dataSnapshot.getChildren()){
                    mclubsadd.add(new clubsCardInfo(data.child("clubName").getValue().toString(),data.child("clubActivity").getValue().toString(),data.child("clubDescription").getValue().toString()));
                }
                mclubsadpter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
    public void recyclerBuild(){
        recyclerView=findViewById(R.id.clubsrecyclerview);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        // its sends the array of object of clubscardinfo which has name mclubsadd
        mclubsadpter=new clubsCardAdapter(mclubsadd);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mclubsadpter);
    }

    public void getEventInfo(String EventName, String EventDate, String EventDesc) {
      //  reff.child(EventDate).setValue(new eventDesc(EventName,EventDate,EventDesc));
        mclubsadd.add(new clubsCardInfo(EventName,EventDate,EventDesc));
        mclubsadpter.notifyDataSetChanged();
    }

}