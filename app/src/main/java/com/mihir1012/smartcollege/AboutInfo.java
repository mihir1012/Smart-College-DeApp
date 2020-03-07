package com.mihir1012.smartcollege;


import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK;
import static com.mihir1012.smartcollege.R.menu.navigation_menu;

public class AboutInfo extends AppCompatActivity {
    TextView AboutName;
    RelativeLayout Relative;
    DatabaseReference reff;
    SharedPreferences pref;
    String EnrollName, Enroll = "170420107027";
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_info);
        SetupToolbar();
        navigationView = findViewById(R.id.aboutInfoNavID);
        //setContentView(navigation_menu);
        Relative = findViewById(R.id.RelativeIfo);
        AboutName = findViewById(R.id.textViewNameAbout);

        pref = getSharedPreferences ("myPreferences", MODE_PRIVATE);
        if (!pref.getBoolean("LoggedIn", false)) {
            Intent Loginintent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(Loginintent);
            finish();
        } else {
            if(pref.getString("Enrolment","0001").equals("0001")){
                SharedPreferences.Editor editor= pref.edit();
                editor.putBoolean("LoggedIn",false);
                Intent Loginintent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(Loginintent);
                finish();
            }
            else if(pref.getString("Enrolment","0001").equals("admin")){
                EnrollName = "ADMINISTRATOR";
                Enroll = "admin";
                AboutName.setText(EnrollName);
            }
            else if(pref.getString("Enrolment","0001").contains("p")){
                EnrollName="prof";
                Enroll = "prof";
            }
            else {
                reff = FirebaseDatabase.getInstance().getReference().child("StudentInfo")
                        .child(pref.getString("Enrolment", "0001"));
                reff.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        EnrollName = dataSnapshot.child("fullNameStudent").getValue().toString();
                        Enroll = dataSnapshot.child("enrolmentStudent").getValue().toString();
                        AboutName.setText(EnrollName);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
            }

            navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    switch (menuItem.getItemId()) {
                        case R.id.nav_StudentInfo:
                            Intent intent = new Intent(AboutInfo.this, AcademicsInfo.class);
                            startActivity(intent);
                            return true;
                        case R.id.nav_FacultyInfo:
                            Intent Facintent = new Intent(AboutInfo.this, FacultyInfo.class);
                            startActivity(Facintent);
                            return true;
                        case R.id.nav_ClassGrp:
                            Intent clsintent = new Intent(AboutInfo.this, ClassGroup.class);
                            startActivity(clsintent);
                            return true;
                        case R.id.nav_Home:
                            Intent homeintent = new Intent(AboutInfo.this, HomePageActivity.class);
                            homeintent.putExtra("message", Enroll);
                            startActivity(homeintent);
                            return true;
                        case R.id.nav_Logout:
                            pref = getSharedPreferences("myPreferences", MODE_PRIVATE);
                            SharedPreferences.Editor editor = pref.edit();
                            editor.putBoolean("LoggedIn", false);
                            editor.putString("Enrolment", "0");
                            editor.commit();
                            Intent Loginintent = new Intent(AboutInfo.this, MainActivity.class);
                            startActivity(Loginintent);
                            finish();
                            return true;
                    }
                    return false;
                }
            });
        }
    }
    public void LetsLogout (View view)
    {
        pref = getSharedPreferences("myPreferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("LoggedIn", false);
        editor.putString("Enrolment", "0");
        editor.commit();
        Intent intent = new Intent(AboutInfo.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
    private void SetupToolbar()
    {
        drawerLayout = (DrawerLayout) findViewById(R.id.DrawerLayoutAboutInfo);
        toolbar = (Toolbar) findViewById(R.id.aboutInfoToolbar);
        toolbar.setTitle("Student Information");
        setSupportActionBar(toolbar);
        actionBarDrawerToggle = new ActionBarDrawerToggle(AboutInfo.this, drawerLayout, toolbar, R.string.app_name, R.string.app_name);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }
}