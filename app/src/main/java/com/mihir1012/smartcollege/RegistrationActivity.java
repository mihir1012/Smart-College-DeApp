package com.mihir1012.smartcollege;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.renderscript.Script;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistrationActivity extends AppCompatActivity {
    LoginStudent logStu;
    StudentInformation Stuinfo;
    EditText Enroll;
    EditText NameStu;
    EditText Pass;
    EditText ConfPass;
    EditText Contact;
    EditText Email;
    Spinner myspinner;
    EditText year;
    EditText AOI;
    SharedPreferences pref;
    DatabaseReference reff,reference;
    String branchString;
    Button b1;
    View view;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        toolbar = findViewById(R.id.RegisterToolbar);
        toolbar.setTitle("Registrations");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        myspinner = findViewById(R.id.spinner);
        ArrayAdapter<String> myAdapt= new ArrayAdapter<String>(RegistrationActivity.this,android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.branch));
        myAdapt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        myspinner.setAdapter(myAdapt);
        myspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                branchString= myspinner.getSelectedItem().toString().trim();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
                Toast.makeText(RegistrationActivity.this,"Non selected",Toast.LENGTH_SHORT).show();
            }
        });
        Enroll= (EditText) findViewById(R.id.enrolReg);
        Email = findViewById(R.id.studentEmailReg);
        NameStu= (EditText) findViewById(R.id.studentNameReg);
        Pass = (EditText) findViewById(R.id.studentPassword);
        year = (EditText) findViewById(R.id.editTextYear);
        AOI = (EditText)findViewById(R.id.editTextAOI);
        Contact = (EditText)findViewById(R.id.studentContactReg);
        ConfPass = (EditText)findViewById(R.id.studentConfirmPass);
        b1 = findViewById(R.id.buttonRegister);
        logStu = new LoginStudent();
        Stuinfo = new StudentInformation();
        reff = FirebaseDatabase.getInstance().getReference().child("StudentInfo");
        reference = FirebaseDatabase.getInstance().getReference().child("LoginStudent");//.child(Enroll.getText().toString());

        pref=getSharedPreferences("myPreferences",MODE_PRIVATE);
    }

    public void RegisterHello(View view) {
        Toast.makeText(RegistrationActivity.this,"Hello",Toast.LENGTH_SHORT).show();
        Long Eaa = Long.parseLong(Enroll.getText().toString().trim());
        Stuinfo.setEnrolmentStudent(Eaa);
        Stuinfo.setFullNameStudent(NameStu.getText().toString().trim());
        Stuinfo.setEmailStudent(Email.getText().toString().trim());
        Stuinfo.setYearStudent(Integer.parseInt(year.getText().toString().trim()));
        Stuinfo.setBranch(branchString);
        Stuinfo.setContact(Long.parseLong(Contact.getText().toString().trim()));
        Stuinfo.setAreaofInterest(AOI.getText().toString());
        //Log.i("THE Error",Stuinfo.getAreaofInterest()+Stuinfo.getEnrolmentStudent().toString());
        reff.child(Enroll.getText().toString().trim()).setValue(Stuinfo);
        logStu.setEnrolStudent(Eaa);
        logStu.setPasswordStudent(Pass.getText().toString().trim());
        logStu.setNameStudent(NameStu.getText().toString().trim());
        reference.child(Enroll.getText().toString().trim()).setValue(logStu);

        SharedPreferences.Editor edit= pref.edit();
        edit.putString("Enrolment",Enroll.getText().toString().trim());
        edit.putBoolean("LoggedIn",true);
        edit.apply();

        Toast.makeText(RegistrationActivity.this,"Registration Successful",Toast.LENGTH_SHORT).show();
        Intent homepageintent = new Intent(getApplicationContext(), HomePageActivity.class);
        startActivity(homepageintent);
        finish();

    }
}
