package com.impetus.courses.bulletjournalapp;

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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.impetus.courses.bulletjournalapp.models.User;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG="RegisterActivity";
    Button user_register;
    EditText user_name,user_phone,user_email,user_password,user_confirm_password;
    EditText user_DOB;

    private ProgressBar mProgressBar;




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
        mProgressBar = findViewById(R.id.progressBar);

        //onClick listeners
        user_register.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        if(v==user_register){
            Log.d(TAG, "onClick: attempting to register.");

            //check for null valued EditText fields
            if(!isEmpty(user_email.getText().toString())
                    && !isEmpty(user_password.getText().toString())
                    && !isEmpty(user_confirm_password.getText().toString())){

                    //check if passwords match
                    if(doStringsMatch(user_password.getText().toString(), user_confirm_password.getText().toString())){

                        //Initiate registration task
                        registerNewEmail(user_email.getText().toString(), user_password.getText().toString());
                    }else{
                        Toast.makeText(RegisterActivity.this, "Passwords do not Match", Toast.LENGTH_SHORT).show();
                    }


            }else{
                Toast.makeText(RegisterActivity.this, "You must fill out all the fields", Toast.LENGTH_SHORT).show();
            }
        }
        hideSoftKeyboard();

    }

    /**
     * Register a new email and password to Firebase Authentication
     * @param email
     * @param password
     */
    public void registerNewEmail(final String email, String password){
        showDialog();
        final String phone = user_phone.getText().toString();
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                        if (task.isSuccessful()){
                            Log.d(TAG, "onComplete: AuthState: " + FirebaseAuth.getInstance().getCurrentUser().getUid());


                            //send email verificaiton
                            sendVerificationEmail();

                            User user=new User();
                            user.setName(user_name.getText().toString());
                            user.setEmail(email);
                            user.setDob(user_DOB.getText().toString());
                            user.getPhone();
                            user.setUid(FirebaseAuth.getInstance().getCurrentUser().getUid());

                            FirebaseDatabase.getInstance().getReference().child(getString(R.string.db_user))
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user).addOnCompleteListener
                                    (new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    FirebaseAuth.getInstance().signOut();
                                    //redirect the user to the login screen
                                    redirectLoginScreen();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    FirebaseAuth.getInstance().signOut();
                                    //redirect the user to the login screen
                                    redirectLoginScreen();
                                    Toast.makeText(getApplicationContext(),"Something went wrong..",Toast.LENGTH_LONG).show();
                                }
                            });


                        }
                        if (!task.isSuccessful()) {
                            Toast.makeText(RegisterActivity.this, "Unable to Register",
                                    Toast.LENGTH_SHORT).show();
                        }
                        hideDialog();

                        // ...
                    }
                });
    }


    /**
     * sends an email verification link to the user
     */
    private void sendVerificationEmail() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            user.sendEmailVerification()
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(RegisterActivity.this, "Sent Verification Email", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Toast.makeText(RegisterActivity.this, "Couldn't Verification Send Email", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }

    }

    /**
     * Redirects the user to the login screen
     */
    private void redirectLoginScreen(){
        Log.d(TAG, "redirectLoginScreen: redirecting to login screen.");

        Intent intent = new Intent(RegisterActivity.this, LogInActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * Return true if @param 's1' matches @param 's2'
     * @param s1
     * @param s2
     * @return
     */
    private boolean doStringsMatch(String s1, String s2){
        return s1.equals(s2);
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
}
