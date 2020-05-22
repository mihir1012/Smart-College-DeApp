package com.mihir1012.smartcollege;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity {
    EditText EnrolmentNo;
    EditText Pass;
    Button Login;
    LoginStudent loginStu;
    DatabaseReference Reff;
    int flag;
    String dataPass;
    SharedPreferences pref;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EnrolmentNo = findViewById(R.id.editTextEnrolment);
        Pass = findViewById(R.id.editTextPass);
        Login = findViewById(R.id.login);
        loginStu = new LoginStudent();
        Reff = FirebaseDatabase.getInstance().getReference().child("LoginStudent");//.child(EnrolmentNo.getText().toString());
        pref = getSharedPreferences("myPreferences", Context.MODE_PRIVATE);
    }
    boolean isEmpty( EditText text ) {
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }
    boolean checkDataEntered(){
        if(isEmpty(EnrolmentNo)){
            Toast.makeText(this,"Please Enter Enrolment",Toast.LENGTH_SHORT).show();
            return false;
        }
        else{
            if(isEmpty(Pass)){
                Toast.makeText(this,"Please Enter Password",Toast.LENGTH_SHORT).show();
                return false;
            }
            else{  return true; }
        }
    }

    public void whatever(View v) {
        if (checkDataEntered()) {
//          To Add Data into DataBase
//            Long Enroll = Long.parseLong(EnrolmentNo.getText().toString().trim());
//            Log.setEnrollmentStudent( Enroll);
//            Log.setPasswordStudent(Pass.getText().toString().trim());
//            Reff.child(EnrolmentNo.getText().toString().trim()).setValue(Log);
//          TO check Existing Data
            if( EnrolmentNo.getText().toString().equals("admin")&& Pass.getText().toString().equals("admin")){
                SharedPreferences.Editor editor= pref.edit();
                editor.putBoolean("LoggedIn",true);
                editor.putString("Enrolment",EnrolmentNo.getText().toString());
                editor.apply();
                Intent intent = new Intent(MainActivity.this, HomePageActivity.class);
//                intent.putExtra("message", EnrolmentNo.getText().toString());
                startActivity(intent);
                Toast.makeText(getBaseContext(), "Login successful", Toast.LENGTH_SHORT).show();
                finish();
            }
            else if(EnrolmentNo.getText().toString().contains("p")){
                Reff = FirebaseDatabase.getInstance().getReference().child("ProfInfo");
                Reff.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot ds : dataSnapshot.getChildren())
                            if (EnrolmentNo.getText().toString().equals(ds.child("profID").getValue().toString())) {
                                if (Pass.getText().toString().equals(ds.child("profPass").getValue().toString())) {
                                    SharedPreferences.Editor editor = pref.edit();
                                    editor.putBoolean("LoggedIn",true);
                                    editor.putString("Enrolment", EnrolmentNo.getText().toString());
                                    editor.commit();
                                    Intent intent = new Intent(MainActivity.this, HomePageActivity.class);
//                                intent.putExtra("message", EnrolmentNo.getText().toString());
                                    startActivity(intent);
                                    Toast.makeText(getBaseContext(), "Login successful", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
            else{
                Reff.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot data : dataSnapshot.getChildren()) {
                            if (!data.child("enrolStudent").getValue().toString().equals(EnrolmentNo.getText().toString())) {
                                flag = 0;
                            } else {
                                flag = 1;
                                dataPass = data.child("passwordStudent").getValue().toString();
                                break;
                            }
                        }
                        if (flag==0) {
                            Toast.makeText(MainActivity.this, "Enrolment is not registered", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            //Log.i("THE PASSWORD","The PassWord"+dataSnapshot.child("passwordStudent").getValue().toString());
                            if (dataPass.equals(Pass.getText().toString())){
                                SharedPreferences.Editor editor= pref.edit();
                                editor.putBoolean("LoggedIn",true);
                                editor.putString("Enrolment",EnrolmentNo.getText().toString());
                                editor.apply();
                                Intent intent = new Intent(MainActivity.this, HomePageActivity.class);
//                                intent.putExtra("message", EnrolmentNo.getText().toString());
                                startActivity(intent);
                                Toast.makeText(getBaseContext(), "Login successful", Toast.LENGTH_SHORT).show();
                                FirebaseMessaging.getInstance().subscribeToTopic("eventupdated");
                                finish();
                            }
                            else{
                                Toast.makeText(MainActivity.this, "Wrong Password", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
            }
        }
    }
    public void Registration(View v){
        Intent intent = new Intent(MainActivity.this, RegistrationActivity.class);
        startActivity(intent);
    }
}

