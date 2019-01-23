package com.impetus.courses.bulletjournalapp.Activity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.impetus.courses.bulletjournalapp.Database.JournalSQLHelper;
import com.impetus.courses.bulletjournalapp.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SaveJournalPagesActivity extends AppCompatActivity {
    EditText JTitle,JContent,JDate;
    Button save;
    JournalSQLHelper helper;
    SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_journal_pages);

        JTitle= findViewById(R.id.JournalTitle);
        JContent= findViewById(R.id.JournalContent);
        save= findViewById(R.id.button4);
        JDate=findViewById(R.id.dateJournal);
        helper= new JournalSQLHelper(this);
        database= helper.getWritableDatabase();
        SimpleDateFormat dateFormat= new SimpleDateFormat("dd/MM/yyyy");
        JDate.setText(dateFormat.format(new Date()));

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isInserted=helper.insertJournalData(JTitle.getText().toString(),JContent.getText().toString(),
                        JDate.getText().toString());
                if(isInserted==true){
                    Toast.makeText(SaveJournalPagesActivity.this,"Note inserted",Toast.LENGTH_LONG).show();
                    redirectToJournalPages();
                } else {
                    Toast.makeText(SaveJournalPagesActivity.this,"Note not inserted",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void redirectToJournalPages(){
        Intent intent= new Intent(SaveJournalPagesActivity.this,JournalPagesActivity.class);
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
