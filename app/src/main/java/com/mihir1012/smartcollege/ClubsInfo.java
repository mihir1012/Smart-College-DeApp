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
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ClubsInfo extends AppCompatActivity implements eventAddDialog.eventAddListener {
    ArrayList<eventDesc> mclubsadd;
    Toolbar toolbar;
    private eventAdapter mclubsadpter;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView recyclerView;
    private String login;
    private FirebaseFirestore db;
    private CollectionReference dbClubs;

    // DatabaseReference reff;
    private SharedPreferences pref;
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
        db = FirebaseFirestore.getInstance();
        dbClubs = db.collection("Clubs");

        pref = getSharedPreferences("myPreferences", MODE_PRIVATE);
        login = pref.getString("Enrolment","0001");

        if (login.substring(0,1).equals("p")) {
            relclub = findViewById(R.id.linearClub);
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
                    RelativeLayout.LayoutParams.WRAP_CONTENT );
            Resources r = this.getResources();
            int px = (int) TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    110,
                    r.getDisplayMetrics()
            );
            params.setMargins(px, 0, 0, 0);
            params.addRule(RelativeLayout.CENTER_HORIZONTAL);
            img.setLayoutParams(params);
            relclub.addView(img);
        }
        createList();
        recyclerBuild();
    }


    private void OpenDialog() {
        eventAddDialog eventDialog = new eventAddDialog(1);
        eventDialog.show(getSupportFragmentManager(), "eventDialog");
    }

    private void createList() {
        mclubsadd = new ArrayList<>();

        dbClubs.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                        mclubsadd.add(documentSnapshot.toObject(eventDesc.class));
      //                  Log.e("TAG ",documentSnapshot.toObject(eventDesc.class).toString());
                    }
                    mclubsadpter.notifyDataSetChanged();
                }
            }
        });
    }

    public void recyclerBuild() {
        recyclerView = findViewById(R.id.clubsrecyclerview);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        // its sends the array of object of clubscardinfo which has name mclubsadd
        mclubsadpter = new eventAdapter(mclubsadd);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mclubsadpter);
    }

    public void getEventInfo(String EventName, String EventDate, String EventDesc) {
       dbClubs.add(new eventDesc(EventName,EventDate,EventDesc));
        mclubsadd.add(new eventDesc(EventName, EventDate, EventDesc));
        mclubsadpter.notifyDataSetChanged();
    }

}