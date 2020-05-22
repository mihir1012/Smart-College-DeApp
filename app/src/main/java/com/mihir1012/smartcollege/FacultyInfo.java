package com.mihir1012.smartcollege;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class FacultyInfo extends AppCompatActivity {
    TextView[] allStdInfoText= new TextView[6];
    TextView facultyName;
    DatabaseReference reff;
    ImageButton left_btn,right_btn;
    int i = 1,faculty_cnt;
// i is current faculty count
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_info);
        toolbar = findViewById(R.id.FacultyToolbar);
        toolbar.setTitle("Faculties");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        facultyName = findViewById(R.id.showFacName);
        allStdInfoText[0] = findViewById(R.id.showDesignation);
        allStdInfoText[1] = findViewById(R.id.showQualification);
        allStdInfoText[2] = findViewById(R.id.showExperience);
        allStdInfoText[3] = findViewById(R.id.showAoi);
        allStdInfoText[4] = findViewById(R.id.showEmail);
        allStdInfoText[5] = findViewById(R.id.showContact);

        left_btn = findViewById(R.id.leftbtn);
        right_btn = findViewById(R.id.rightbtn);

// NEVER USE ( i++ ) IN ANDROID STUDIO ITS NOT WORK
        i = nextinfo(i);
            left_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) { i = nextinfo(i <= 1 ? i : --i);
                }
            });
            right_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) { i = nextinfo(++i); }
            });
    }

    public int nextinfo(final int i){

        reff = FirebaseDatabase.getInstance().getReference().child("FacultyInfo");
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                facultyName.setText("Name: " + dataSnapshot.child("faculty: " + i).child("Name").getValue().toString());
                allStdInfoText[0].setText("Designation: " + dataSnapshot.child("faculty: " + i).child("Designation").getValue().toString());
                allStdInfoText[1].setText("Qualification: " + dataSnapshot.child("faculty: " + i).child("Qualification").getValue().toString());
                allStdInfoText[2].setText("Experience: " + dataSnapshot.child("faculty: " + i).child("Experience").getValue().toString());
                allStdInfoText[3].setText("AOI: " + dataSnapshot.child("faculty: " + i).child("Aoi").getValue().toString());
                allStdInfoText[4].setText("Email: " + dataSnapshot.child("faculty: " + i).child("Email").getValue().toString());
                allStdInfoText[5].setText("Contact No: " + dataSnapshot.child("faculty: " + i).child("Contact").getValue().toString());

                faculty_cnt = (int) dataSnapshot.getChildrenCount();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    return faculty_cnt<i ? i:faculty_cnt-1 ;
    }
}
