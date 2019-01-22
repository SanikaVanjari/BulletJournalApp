package com.impetus.courses.bulletjournalapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.SimpleAdapter;

import com.impetus.courses.bulletjournalapp.Database.JournalSQLHelper;

import java.util.ArrayList;
import java.util.HashMap;

public class JournalPagesActivity extends AppCompatActivity {
    GridView JournalGridView;
    JournalSQLHelper helper;
    SQLiteDatabase database;
    SimpleAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journal_pages);

        JournalGridView= findViewById(R.id.JournalGridView);
        helper=new JournalSQLHelper(this);
        database= helper.getWritableDatabase();

        ArrayList<HashMap<String,String>>  arrayList=new ArrayList<>();
        Cursor cursor=helper.getAllJournalData();

        while (cursor.moveToNext()){
            HashMap<String,String> hm= new HashMap<>();
            hm.put("title",cursor.getString(1));
            hm.put("content",cursor.getString(2));

            arrayList.add(hm);
        }
        String[] from={"title","content"};
        int[] to={R.id.textView7,R.id.textView8};

        adapter= new SimpleAdapter(this,arrayList,R.layout.journal_view,from,to);
        JournalGridView.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.collection_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent= new Intent(this,EditJournalPagesActivity.class);
        startActivity(intent);
        return true;
    }
}
