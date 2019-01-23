package com.impetus.courses.bulletjournalapp.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.impetus.courses.bulletjournalapp.R;
import com.impetus.courses.bulletjournalapp.ResendVerificationDialog;

public class LogInActivity extends AppCompatActivity implements View.OnClickListener {
    Button logIn;
    EditText email,password;
    TextView register,forgot_password,resend_mail;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private ProgressBar mProgressBar;

    private static final String TAG="LogInActivity";

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

        mProgressBar =  findViewById(R.id.progressBar);

        setupFirebaseAuth();


        }

    @Override
    public void onClick(View v) {
        if(v== logIn){
            if(!isEmpty(email.getText().toString())
                    && !isEmpty(password.getText().toString())){
                Log.d(TAG, "onClick: attempting to authenticate.");

                showDialog();

                FirebaseAuth.getInstance().signInWithEmailAndPassword(email.getText().toString(),
                        password.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                hideDialog();

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(LogInActivity.this, "Authentication Failed", Toast.LENGTH_SHORT).show();
                        hideDialog();
                    }
                });
            }else{
                Toast.makeText(LogInActivity.this, "You didn't fill in all the fields.", Toast.LENGTH_SHORT).show();
            }
            hideSoftKeyboard();
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
            ResendVerificationDialog dialog = new ResendVerificationDialog();
            dialog.show(getSupportFragmentManager(), "dialog_resend_email_verification");
        }
    }

    /**
     * Return true if the @param is null
     * @param string
     * @return
     */
    private boolean isEmpty(String string){
        return string.equals("");
    }


    private void showDialog(){
        mProgressBar.setVisibility(View.VISIBLE);

    }

    private void hideDialog(){
        if(mProgressBar.getVisibility() == View.VISIBLE){
            mProgressBar.setVisibility(View.INVISIBLE);
        }
    }

    private void hideSoftKeyboard(){
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    /*
        ----------------------------- Firebase setup ---------------------------------
     */
    private void setupFirebaseAuth(){
        Log.d(TAG, "setupFirebaseAuth: started.");

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {

                    //check if email is verified
                    if(user.isEmailVerified()){
                        Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                        Toast.makeText(LogInActivity.this, "Authenticated with: " + user.getEmail(), Toast.LENGTH_SHORT).show();
                        Intent intent= new Intent(LogInActivity.this,HomeActivity.class);
                        startActivity(intent);
                        finish();

                    }else{
                        Toast.makeText(LogInActivity.this, "Email is not Verified\nCheck your Inbox", Toast.LENGTH_SHORT).show();
                        FirebaseAuth.getInstance().signOut();
                    }

                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseAuth.getInstance().addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            FirebaseAuth.getInstance().removeAuthStateListener(mAuthListener);
        }
    }
}
