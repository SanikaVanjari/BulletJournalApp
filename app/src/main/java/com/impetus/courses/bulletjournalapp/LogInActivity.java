package com.impetus.courses.bulletjournalapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LogInActivity extends AppCompatActivity implements View.OnClickListener {
    Button logIn;
    EditText email,password;
    TextView register,forgot_password,resend_mail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        //initiating the xml
        logIn=findViewById(R.id.button);
        email=findViewById(R.id.editText);
        password=findViewById(R.id.editText2);
        register=findViewById(R.id.textView);
        forgot_password=findViewById(R.id.textView2);
        resend_mail=findViewById(R.id.textView3);

        //onClick listeners
        logIn.setOnClickListener(this);
        register.setOnClickListener(this);
        forgot_password.setOnClickListener(this);
        resend_mail.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        if(v== logIn){
            Intent intent= new Intent(this,HomeActivity.class);
            startActivity(intent);
        }
        if(v== register){
            Intent intent= new Intent(this,RegisterActivity.class);
            startActivity(intent);
        }
        if(v== forgot_password){
            // code for forgot password popup
        }
        if(v== resend_mail){
            //code for resend mail popup
        }
    }
}
