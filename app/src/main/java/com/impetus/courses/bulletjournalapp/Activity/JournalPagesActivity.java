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
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.impetus.courses.bulletjournalapp.Database.JournalSQLHelper;
import com.impetus.courses.bulletjournalapp.R;

import java.util.ArrayList;
import java.util.HashMap;

public class JournalPagesActivity extends AppCompatActivity {
    GridView JournalGridView;
    JournalSQLHelper helper;
    SQLiteDatabase database;
    SimpleAdapter adapter;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journal_pages);

        JournalGridView= findViewById(R.id.JournalGridView);
        helper=new JournalSQLHelper(this);
        database= helper.getWritableDatabase();

        ArrayList<HashMap<String,String>>  arrayList=new ArrayList<>();
        cursor = helper.getAllJournalData();

        while (cursor.moveToNext()){
            HashMap<String,String> hm= new HashMap<>();
            hm.put("title", cursor.getString(1));
            hm.put("content", cursor.getString(2));
            hm.put("date", cursor.getString(3));
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
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            int index = info.position;
            cursor.moveToPosition(index);
            Intent intent= new Intent(JournalPagesActivity.this, EditJournalActivity.class);
            intent.putExtra("id",cursor.getString(0));
            intent.putExtra("title", cursor.getString(1));
            intent.putExtra("content", cursor.getString(2));
            intent.putExtra("date",cursor.getString(3));
            startActivity(intent);

        } else if(item.getTitle()=="Delete"){
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            int index = info.position;
            cursor.moveToPosition(index);
            String ID_val=cursor.getString(0);
            database.execSQL("DELETE FROM "+ helper.TABLE_NAME + " WHERE ID = ' " + ID_val + " ' " );
            Toast.makeText(getApplicationContext(),"DATA DELETED : successfully",Toast.LENGTH_LONG).show();
            Intent intent= new Intent(
                    JournalPagesActivity.this,JournalPagesActivity.class);
            startActivity(intent);
            finish();
        } else {
            return false;
        }
        return true;
    }
}
