package com.mihir1012.smartcollege;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;

public class AcademicsInfo extends AppCompatActivity {
DrawerLayout drawerLayout;
Toolbar toolbar;
ActionBarDrawerToggle actionBarDrawerToggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_academics_info);
        SetupToolbar();
    }
    private void SetupToolbar()
    {
        drawerLayout= (DrawerLayout) findViewById(R.id.drawerLayoutAcademicsInfo);
        toolbar =(Toolbar)findViewById(R.id.academicsToolbar);
        toolbar.setTitle("Academics Information");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
