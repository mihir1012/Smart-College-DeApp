package com.mihir1012.smartcollege;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomePageActivity extends AppCompatActivity {
    DatabaseReference Reff;
    DrawerLayout drawerLayout;
    TextView t;
    Button B;
    View v;
    String Enroll;
    Toolbar toolbar;
    ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;
    SharedPreferences pref;
    String message;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        SetupToolbar();
        pref = getSharedPreferences("myPreferences", MODE_PRIVATE);
        navigationView = findViewById(R.id.Nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch  (menuItem.getItemId()){
                    case R.id.home_menu:
                        return true;
                    case R.id.about_menu:
                        Intent Aboutintent = new Intent(HomePageActivity.this,AboutDev.class);
                        startActivity(Aboutintent);
                        return true;
                    case R.id.setting_menu:
                        Intent Settingintent = new Intent(HomePageActivity.this,SettingsActivity.class);
                        startActivity(Settingintent);
                        return true;
                    case R.id.Logout_menu:
                        pref = getSharedPreferences("myPreferences", MODE_PRIVATE);
                        SharedPreferences.Editor editor = pref.edit();
                        editor.putBoolean("LoggedIn", false);
                        editor.putString("Enrolment", "0");
                        editor.commit();
                        Intent Loginintent = new Intent(HomePageActivity.this, MainActivity.class);
                        startActivity(Loginintent);
                        finish();
                        return true;
                }
                return false;
            }
        });
        t= findViewById(R.id.TextViewName);
        B= findViewById(R.id.buttonMap);
//        Intent intent= getIntent();
//        message = intent.getStringExtra("message");
        message = pref.getString("Enrolment","0001");
        if(message.equals("admin")){
            Enroll = message;
            t.setText(Enroll);
        }
        else if(message.contains("p")){
            Reff = FirebaseDatabase.getInstance().getReference().child("ProfInfo");
        }
        else {
            Reff = FirebaseDatabase.getInstance().getReference().child("LoginStudent").child(message);
            Log.i("ERROR*",message);
            Reff.addValueEventListener(new ValueEventListener() {

                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Enroll = dataSnapshot.child("nameStudent").getValue().toString();
                    t.setText(Enroll);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
        }


    }

    public void launchmap(View v) {

    }

    public void GotoEvents(View v){
        Intent eventintent = new Intent(HomePageActivity.this, Eventinfo.class);
        eventintent.putExtra("login",message);
        startActivity(eventintent);
    }
    public void GotoInfo(View v){
        Intent infointent = new Intent(HomePageActivity.this, AboutInfo.class);
        infointent.putExtra("message",Enroll);
        startActivity(infointent);
    }
    public void GotoClubs(View v){
        Intent clubintent = new Intent(HomePageActivity.this, ClubsInfo.class);
        startActivity(clubintent);
    }

    private void SetupToolbar()
    {
        drawerLayout= (DrawerLayout) findViewById(R.id.drawerLayoutHomePage);
        toolbar =(Toolbar)findViewById(R.id.My_Toolbar);
        toolbar.setTitle("Home Page");
        setSupportActionBar(toolbar);
        actionBarDrawerToggle = new ActionBarDrawerToggle(HomePageActivity.this,drawerLayout,toolbar,R.string.app_name,R.string.app_name);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }
}
