package com.example.movieapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;

import android.os.Bundle;

import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ProfileActivity extends AppCompatActivity {

    DatabaseHelper db;
    Button btnUserDetails;
    Button btnResetPassword;
    EditText e,pw1,pw2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        db = new DatabaseHelper(this);
        btnUserDetails = (Button)findViewById(R.id.btn_show);
        btnResetPassword = (Button)findViewById(R.id.btn_pass);
        e = (EditText)findViewById(R.id.email);
        pw1 = (EditText)findViewById(R.id.password1);
        pw2 = (EditText)findViewById(R.id.password2);

        viewDetails();

        updatePass();

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);

        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.nav_home:
                        Intent intent1 = new Intent(ProfileActivity.this, MainActivity.class);
                        startActivity(intent1);
                        break;
                    case R.id.nav_fav:
                        Intent intent2 = new Intent(ProfileActivity.this, FavoritesActivity.class);
                        startActivity(intent2);
                        break;
                    case R.id.nav_profile:
                        Intent intent3 = new Intent(ProfileActivity.this, ProfileActivity.class);
                        startActivity(intent3);
                        break;
                }

                return false;
            }
        });


    }

    public void updatePass(){
        btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isUpdate = db.updatePassword(e.getText().toString(), pw1.getText().toString());
                if(pw1.equals(pw2)){
                    if(isUpdate==true){
                        Toast.makeText(ProfileActivity.this, "Changed password",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(ProfileActivity.this, "Not changed password",Toast.LENGTH_SHORT).show();

                    }
                }
            }
        });
    }

    public void viewDetails(){
        btnUserDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res = db.getUsers();
                if(res.getCount() == 0){
                    showMessage("Error","No data found");
                    return;
                }

                StringBuffer buffer = new StringBuffer();
                while(res.moveToNext()){
                    buffer.append("ID :"+ res.getString(0)+"\n");
                    buffer.append("Name :"+ res.getString(1)+"\n");
                    buffer.append("Age :"+ res.getString(2)+"\n");
                    buffer.append("Email :"+ res.getString(3)+"\n");
                }
                showMessage("Data", buffer.toString());
            }
        });
    }

    public void showMessage(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

}
