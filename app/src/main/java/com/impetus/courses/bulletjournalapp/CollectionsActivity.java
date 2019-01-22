package com.impetus.courses.bulletjournalapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.SimpleAdapter;

import com.impetus.courses.bulletjournalapp.Database.CollectionsSQLHelper;

import java.util.ArrayList;
import java.util.HashMap;

public class CollectionsActivity extends AppCompatActivity {
    private static final String TAG="CollectionsActivity";
    GridView CollectionGridView;
    SimpleAdapter adapter;
    CollectionsSQLHelper helper;
    SQLiteDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collections);

        helper= new CollectionsSQLHelper(this);
        database= helper.getWritableDatabase();
        CollectionGridView = findViewById(R.id.CollectionGridView);

        ArrayList<HashMap<String,String>> arrayList= new ArrayList<>();

        Cursor cursor= helper.getAllData();
        while (cursor.moveToNext()) {
            HashMap<String, String> hm = new HashMap<>();

            hm.put("title", cursor.getString(1));
            hm.put("content", cursor.getString(2) );
            arrayList.add(hm);

        }
        String[] from={"title","content"};
        int[] to ={R.id.collectionTitle,R.id.collectionContent};

        adapter= new SimpleAdapter(this,arrayList,R.layout.collection_view,from,to);
        CollectionGridView.setAdapter(adapter);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.collection_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent= new Intent(CollectionsActivity.this,EditCollectionsActivity.class);
        startActivity(intent);
        return true;
    }
}

