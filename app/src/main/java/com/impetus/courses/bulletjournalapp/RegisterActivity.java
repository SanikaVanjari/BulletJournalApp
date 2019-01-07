package com.impetus.courses.bulletjournalapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    Button user_register;
    EditText user_name,user_phone,user_DOB,user_email,user_password,user_confirm_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        user_register=findViewById(R.id.registerUserButton);
        user_name=findViewById(R.id.userName);
        user_phone=findViewById(R.id.userPhone);
        user_DOB=findViewById(R.id.userDOB);
        user_email=findViewById(R.id.userEmail);
        user_password=findViewById(R.id.userPassword);
        user_confirm_password=findViewById(R.id.userConfirmPassword);

        //onClick listeners
        user_register.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v==user_register){

        }
    }
}
