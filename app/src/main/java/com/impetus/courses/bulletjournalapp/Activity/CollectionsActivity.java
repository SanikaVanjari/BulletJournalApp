package com.impetus.courses.bulletjournalapp.Activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.impetus.courses.bulletjournalapp.Database.CollectionsSQLHelper;
import com.impetus.courses.bulletjournalapp.Database.JournalSQLHelper;
import com.impetus.courses.bulletjournalapp.R;

import java.util.ArrayList;
import java.util.HashMap;

public class CollectionsActivity extends AppCompatActivity {
    private static final String TAG="CollectionsActivity";
    GridView CollectionGridView;
    SimpleAdapter adapter;
    CollectionsSQLHelper helper;
    SQLiteDatabase database;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collections);

        helper= new CollectionsSQLHelper(this);
        database= helper.getWritableDatabase();
        CollectionGridView = findViewById(R.id.CollectionGridView);

        final ArrayList<HashMap<String,String>> arrayList= new ArrayList<>();


        cursor = helper.getAllData();
        while (cursor.moveToNext()) {
            HashMap<String, String> hm = new HashMap<>();
            hm.put("title", cursor.getString(1));
            hm.put("content", cursor.getString(2) );
            arrayList.add(hm);

        }
        final String[] from={"title","content"};
        int[] to ={R.id.collectionTitle,R.id.collectionContent};

        adapter= new SimpleAdapter(this,arrayList,R.layout.collection_view,from,to);
        CollectionGridView.setAdapter(adapter);
        registerForContextMenu(CollectionGridView);
        CollectionGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext()," Value: "+ cursor.getColumnName(position),Toast.LENGTH_LONG).show();

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.collection_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent= new Intent(CollectionsActivity.this, SaveCollectionsActivity.class);
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
            Intent intent= new Intent(CollectionsActivity.this, EditCollectionActivity.class);
            intent.putExtra("id",cursor.getString(0));
            intent.putExtra("title", cursor.getString(1));
            intent.putExtra("content", cursor.getString(2));
            startActivity(intent);

        } else if(item.getTitle()=="Delete"){
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            int index = info.position;
            cursor.moveToPosition(index);
            String ID_val=cursor.getString(0);
            database.execSQL("DELETE FROM "+ helper.TABLE_NAME + " WHERE ID = ' " + ID_val + " ' " );
            Toast.makeText(getApplicationContext(),"DATA DELETED : successfully",Toast.LENGTH_LONG).show();
            Intent intent= new Intent(
                    CollectionsActivity.this,CollectionsActivity.class);
            startActivity(intent);
        } else {
            return false;
        }
        return true;
    }
}

