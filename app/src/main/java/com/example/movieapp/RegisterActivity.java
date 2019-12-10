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
                String fullName = name.getText().toString();
                String a = age.getText().toString();
                String eemail = email.getText().toString();
                String pwd1 = password1.getText().toString();
                String pwd2 = password2.getText().toString();

                if(fullName.equals("")||a.equals("")||eemail.equals("")||pwd1.equals("")||pwd2.equals("")){
                    Toast.makeText(getApplicationContext(),"Fields are empty!", Toast.LENGTH_SHORT).show();
                }
                else{
                    if(pwd1.equals(pwd2)){
                        Boolean checkemail = db.checkEmail(eemail);
                        if(checkemail==true){
                            Boolean insert = db.addUser(fullName, a, eemail, pwd1);
                            if(insert==true){
                                Toast.makeText(getApplicationContext(), "Successfully registered!", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else{
                            Toast.makeText(getApplicationContext(),"Email is already exists!", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"Password not matching", Toast.LENGTH_SHORT).show();

                    }
                }

            }
        });
    }
}
