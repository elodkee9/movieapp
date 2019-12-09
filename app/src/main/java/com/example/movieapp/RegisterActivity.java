package com.example.movieapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    EditText name;
    EditText age;
    EditText email;
    EditText password1;
    EditText password2;
    TextView loginText;
    Button registerButton;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        db = new DatabaseHelper(this);
        name = (EditText)findViewById(R.id.name);
        age = (EditText)findViewById(R.id.age);
        email = (EditText)findViewById(R.id.email);
        password1 = (EditText)findViewById(R.id.password1);
        password2 = (EditText)findViewById(R.id.password2);
        registerButton = (Button)findViewById(R.id.btn_register);
        loginText = (TextView)findViewById(R.id.loginText);

        loginText.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent loginIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(loginIntent);
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fullName = name.getText().toString().trim();
                String a = age.getText().toString().trim();
                String eemail = email.getText().toString().trim();
                String pwd1 = password1.getText().toString().trim();
                String pwd2 = password2.getText().toString().trim();

                if(pwd1.equals(pwd2)) {
                    db.addUser(fullName, a, eemail,pwd1);
                    Toast.makeText(RegisterActivity.this, "You have registered successfully!", Toast.LENGTH_SHORT).show();
                    Intent moveToLogin = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(moveToLogin);
                }
                else {
                    Toast.makeText(RegisterActivity.this, "Password is not matching!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
