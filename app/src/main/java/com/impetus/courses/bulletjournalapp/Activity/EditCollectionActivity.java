package com.impetus.courses.bulletjournalapp.Activity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.impetus.courses.bulletjournalapp.Database.CollectionsSQLHelper;
import com.impetus.courses.bulletjournalapp.R;

public class EditCollectionActivity extends AppCompatActivity {
    EditText updateTitle,updateContent;
    Button updateCollection;
    CollectionsSQLHelper helper;
    SQLiteDatabase database;
    String id_for_update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_collection);
        updateTitle=findViewById(R.id.editText5);
        updateContent=findViewById(R.id.editText6);
        updateCollection=findViewById(R.id.button3);

        Intent data =getIntent();
        updateTitle.setText(data.getStringExtra("title"));
        updateContent.setText(data.getStringExtra("content"));
        id_for_update = data.getStringExtra("id");

        helper=new CollectionsSQLHelper(this);
        database=helper.getWritableDatabase();

        updateCollection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (updateTitle.getText().toString().length()==0||updateContent.getText().toString().length()==0){
                    Toast.makeText(getApplicationContext(),"Enter all values",Toast.LENGTH_LONG).show();
                }
                else {
                    database.execSQL("UPDATE "+helper.TABLE_NAME+ " SET "+ helper.TITLE + " =' " + updateTitle.getText().toString()+ "' ,"+ helper.CONTENT + " = ' "+
                            updateContent.getText().toString()+ " ' WHERE ID ='" + id_for_update+"'" );
                    Toast.makeText(getApplicationContext(),"DATA UPDATED: successful",Toast.LENGTH_LONG).show();
                    Intent intent= new Intent(
                            EditCollectionActivity.this,CollectionsActivity.class);
                    startActivity(intent);
                }
            }
        });

    }
}
