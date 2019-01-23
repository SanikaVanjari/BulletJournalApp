package com.impetus.courses.bulletjournalapp.Activity;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.impetus.courses.bulletjournalapp.R;
import com.impetus.courses.bulletjournalapp.models.User;

public class ProfileActivity extends AppCompatActivity {
    EditText p_name,p_phone;
    TextView p_email;
    private static final String TAG="ProfileActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        p_name=findViewById(R.id.ProfileName);
        p_phone=findViewById(R.id.ProfilNumber);
        p_email=findViewById(R.id.emailTV);

        getUsersAccountData();
    }

    public void getUsersAccountData(){
        Log.d(TAG,"getUsersAccountData : getting the users account information");
        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference();
        Query query=databaseReference.child(getString(R.string.db_user)).orderByKey().
                equalTo(FirebaseAuth.getInstance().getCurrentUser().getUid());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot singleSnapshot : dataSnapshot.getChildren()){
                    User user = singleSnapshot.getValue(User.class);
                    Log.d(TAG," QUERY METHOD 1 found user: " + user.toString());

                    p_name.setText(user.getName());
                    p_phone.setText(user.getPhone());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        p_email.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());
    }
}
