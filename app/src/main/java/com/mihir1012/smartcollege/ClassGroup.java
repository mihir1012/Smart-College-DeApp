package com.mihir1012.smartcollege;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ClassGroup extends AppCompatActivity {
Toolbar toolbar;
SharedPreferences pref;
DatabaseReference ref;
String Classname="CO-1";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_group);
        pref = getSharedPreferences("myPreferences", MODE_PRIVATE);
        ref = FirebaseDatabase.getInstance().getReference().child("StudentInfo").child(pref.getString("Enrolment", "170420107027"));

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Classname= dataSnapshot.child("branch").getValue().toString()+"-"+dataSnapshot.child("yearStudent").getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        toolbar = (Toolbar) findViewById(R.id.CLassToolbar);
        toolbar.setTitle(Classname);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
}
