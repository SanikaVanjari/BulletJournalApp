package com.impetus.courses.bulletjournalapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        drawerLayout=findViewById(R.id.drawer_layout);
        navigationView=findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id= menuItem.getItemId();
                switch (id){
                    case R.id.FutureLog:
                        Intent intentToFutureLog= new Intent(HomeActivity.this,FutureLogActivity.class);
                        startActivity(intentToFutureLog);
                        break;
                    case R.id.Collections:
                        Intent intentToCollections= new Intent(HomeActivity.this,CollectionsActivity.class);
                        startActivity(intentToCollections);
                        break;
                    case R.id.HabitTracker:
                        // to be added
                        break;
                    case R.id.JournalPages:
                        //to be added
                        break;

                }

                menuItem.setChecked(true);
                Toast.makeText(HomeActivity.this," item selected" + menuItem.getTitle().toString(),Toast.LENGTH_LONG).show();
                drawerLayout.closeDrawers();

                return true;
            }
        });
    }
}
