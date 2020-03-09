package com.mihir1012.smartcollege;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.Resources;
import android.media.Image;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class
ClubsInfo extends AppCompatActivity implements eventAddDialog.eventAddListener {
    ArrayList<clubsCardInfo> mclubsadd;
    public clubsCardAdapter mclubsadpter;
    private RecyclerView.LayoutManager layoutManager;
    RecyclerView recyclerView;
    Toolbar toolbar;
   // DatabaseReference reff;

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
        img= new ImageView(this);
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
                150,
                r.getDisplayMetrics()
        );
        params.setMargins(px,50,0,0);
        params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        img.setLayoutParams(params);
        relclub.addView(img);
        createlist();
        recyclerBuild();
    }

    private void OpenDialog(){
        eventAddDialog eventDialog =new eventAddDialog();
        eventDialog.show(getSupportFragmentManager(),"eventDialog");
    }

    public void createlist() {
        mclubsadd=new ArrayList<>();
        mclubsadd.add(new clubsCardInfo("hackaton", "hiiii", "hru"));
        mclubsadd.add(new clubsCardInfo ("hackaton2", "hiiii2", "hru2"));
        mclubsadd.add(new clubsCardInfo("hackaton3", "hiiii3", "hru3"));
        mclubsadd.add(new clubsCardInfo ("hackaton4", "hiiii4", "hru24"));
        mclubsadd.add(new clubsCardInfo("hackaton5", "hiiii5", "hru5"));

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