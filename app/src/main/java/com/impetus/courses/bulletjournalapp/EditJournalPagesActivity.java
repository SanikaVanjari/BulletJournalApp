package com.impetus.courses.bulletjournalapp;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.impetus.courses.bulletjournalapp.Database.JournalSQLHelper;

public class EditJournalPagesActivity extends AppCompatActivity {
    EditText JTitle,JContent;
    Button save;
    JournalSQLHelper helper;
    SQLiteDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_journal_pages);

        JTitle= findViewById(R.id.JournalTitle);
        JContent= findViewById(R.id.JournalContent);
        save= findViewById(R.id.button4);

        helper= new JournalSQLHelper(this);
        database= helper.getWritableDatabase();


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isInserted=helper.insertJournalData(JTitle.getText().toString(),JContent.getText().toString());
                if(isInserted==true){
                    Toast.makeText(EditJournalPagesActivity.this,"Note inserted",Toast.LENGTH_LONG).show();
                    redirectToJournalPages();
                } else {
                    Toast.makeText(EditJournalPagesActivity.this,"Note not inserted",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void redirectToJournalPages(){
        Intent intent= new Intent(EditJournalPagesActivity.this,JournalPagesActivity.class);
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
