package com.impetus.courses.bulletjournalapp.Activity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.impetus.courses.bulletjournalapp.Database.CollectionsSQLHelper;
import com.impetus.courses.bulletjournalapp.R;

public class SaveCollectionsActivity extends AppCompatActivity {
    public static final String TAG="SaveCollectionsActivity";
    EditText colTitle,colDescription;
    CollectionsSQLHelper helper;
SQLiteDatabase database;

    Button save;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_collections);
        colTitle=findViewById(R.id.editText3);
        colDescription=findViewById(R.id.editText4);
        save=findViewById(R.id.button2);
helper= new CollectionsSQLHelper(this);
database= helper.getWritableDatabase();

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!(colTitle.getText().toString().trim().length()==0||
                        colDescription.getText().toString().trim().length()==0)){
                    boolean isInserted= helper.insertData(colTitle.getText().toString(),colDescription.getText().toString());
                    if(isInserted==true){
                        Toast.makeText(getApplicationContext(),"Collection Added",Toast.LENGTH_LONG).show();
                        redirectCollectionScreen();
                    } else{
                        Toast.makeText(getApplicationContext(),"Facing issues",Toast.LENGTH_LONG).show();

                    }

                } else {
                    Toast.makeText(getApplicationContext(),"Please enter all values",Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    private void redirectCollectionScreen(){
        Log.d(TAG, "redirectCollectionScreen: redirecting to login screen.");

        Intent intent = new Intent(SaveCollectionsActivity.this, CollectionsActivity.class);
        startActivity(intent);
        finish();
    }




    @Override
    protected void onDestroy() {
        super.onDestroy();
        database.close();
        helper.close();
    }


}

