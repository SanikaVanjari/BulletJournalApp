package com.impetus.courses.bulletjournalapp.Activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.impetus.courses.bulletjournalapp.Database.CollectionsSQLHelper;
import com.impetus.courses.bulletjournalapp.Database.JournalSQLHelper;
import com.impetus.courses.bulletjournalapp.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class HomeActivity extends AppCompatActivity {
    private static final String TAG = "HomeActivity";
    TextView CurrentDate,HomeToDoTitle,HomeToDoList,HabitTrackerTitle,HabitTrackerList,JournalTitleHome,JournalContentHome;
    CardView ToDo,HabitTracker,Journal;
    JournalSQLHelper helper;
    SQLiteDatabase database;
    //Firebase
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        CurrentDate=findViewById(R.id.textView4);
        HomeToDoTitle=findViewById(R.id.HomeTODOTitle);
        HomeToDoList=findViewById(R.id.HomeTODOList);
        HabitTrackerTitle=findViewById(R.id.HabitTrackerTitleHome);
        HabitTrackerList=findViewById(R.id.HabitTrackerContentHome);
        JournalTitleHome=findViewById(R.id.JournalTitle);
        JournalContentHome=findViewById(R.id.JournalContentHome);
        ToDo=findViewById(R.id.CardToDo);
        HabitTracker=findViewById(R.id.CardHabitTracker);
        Journal=findViewById(R.id.CardJournal);
        helper=new JournalSQLHelper(this);
        database= helper.getWritableDatabase();

        // TO view current date
        long date = System.currentTimeMillis();

        SimpleDateFormat sdf = new SimpleDateFormat("MMM MM dd, yyyy h:mm a");
        String dateString = sdf.format(date);
        CurrentDate.setText(dateString);
        setupFirebaseAuth();
//        viewJournalForCurrentDate();

    }


    private void viewJournalForCurrentDate() {
        String[] params=new String[]{CurrentDate.getText().toString()};
        Cursor c=database.rawQuery("SELECT " + JournalSQLHelper.CONTENT + " FROM "+ JournalSQLHelper.TABLE_NAME + " WHERE date = ?",params);
        if (c.moveToNext()){
            JournalContentHome.setText(c.getString(2));
        } else {
            JournalContentHome.setText(getString(R.string.no_journal_entry_warning));
        }
    }

    /**
     * Sign out the current user
     */
    private void signOut(){
        Log.d(TAG, "signOut: signing out");
        FirebaseAuth.getInstance().signOut();
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkAuthenticationState();
    }

    private void checkAuthenticationState(){
        Log.d(TAG, "checkAuthenticationState: checking authentication state.");

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if(user == null){
            Log.d(TAG, "checkAuthenticationState: user is null, navigating back to login screen.");

            Intent intent = new Intent(HomeActivity.this, LogInActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }else{
            Log.d(TAG, "checkAuthenticationState: user is authenticated.");
        }
    }

    /*
           ----------------------------- Firebase setup ---------------------------------
        */
    private void setupFirebaseAuth(){
        Log.d(TAG, "setupFireBaseAuth: started.");

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {

                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());

                } else {
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                    Intent intent = new Intent(HomeActivity.this, LogInActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.drawer_view,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id= item.getItemId();
        switch (id){
            case R.id.FutureLog:
                Intent intentToFutureLog= new Intent(HomeActivity.this,FutureLogMonthlyList.class);
                startActivity(intentToFutureLog);
                return true;
            case R.id.Collections:
                Intent intentToCollections= new Intent(HomeActivity.this,CollectionsActivity.class);
                startActivity(intentToCollections);
                return true;

            case R.id.HabitTracker:
                // to be added
                return true;
            case R.id.JournalPages:
                Intent intentToJournalPages= new Intent(HomeActivity.this,JournalPagesActivity.class);
                startActivity(intentToJournalPages);
                return true;

            case R.id.logOut:
                signOut();
                return true;

            case R.id.Profile:
                Intent intentToProfile= new Intent(HomeActivity.this,ProfileActivity.class);
                startActivity(intentToProfile);
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }
}
