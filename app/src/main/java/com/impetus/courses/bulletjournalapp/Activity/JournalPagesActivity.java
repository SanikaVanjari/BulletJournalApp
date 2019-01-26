package com.impetus.courses.bulletjournalapp.Activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.SimpleAdapter;

import com.impetus.courses.bulletjournalapp.Database.JournalSQLHelper;
import com.impetus.courses.bulletjournalapp.R;

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
            hm.put("date",cursor.getString(3));
            arrayList.add(hm);
        }
        String[] from={"title","content","date"};
        int[] to={R.id.textView7,R.id.textView8,R.id.textView6};

        adapter= new SimpleAdapter(this,arrayList,R.layout.journal_view,from,to);
        JournalGridView.setAdapter(adapter);
        registerForContextMenu(JournalGridView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.collection_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent= new Intent(this, SaveJournalPagesActivity.class);
        startActivity(intent);
        return true;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Select an option..");
        menu.add(0,v.getId(),0,"Edit");
        menu.add(0,v.getId(),0,"Delete");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if(item.getTitle()=="Edit"){
            String id_journal= null;// add id value here
            Intent intent= new Intent(JournalPagesActivity.this, EditJournalActivity.class);
            intent.putExtra("ID",id_journal);
            startActivity(intent);

        } else if(item.getTitle()=="Delete"){
            String id_journal= null;// add id value here
            database.execSQL(" DELETE FROM "+ JournalSQLHelper.TABLE_NAME + " WHERE ID = " + id_journal );
            // HOW TO MAKE IT DISAPPEAR FROM THE LIST VIEW
        } else {
            return false;
        }
        return true;
    }
}
