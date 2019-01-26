package com.impetus.courses.bulletjournalapp.Activity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.impetus.courses.bulletjournalapp.Database.JournalSQLHelper;
import com.impetus.courses.bulletjournalapp.R;

public class EditJournalActivity extends AppCompatActivity {
    EditText editTitle,editContent;
    TextView constantDate;
    Button updateJ;
    JournalSQLHelper helper;
    SQLiteDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_journal);

        editTitle=findViewById(R.id.editText7);
        editContent=findViewById(R.id.editText8);
        constantDate=findViewById(R.id.textView9);
        helper= new JournalSQLHelper(this);
        database= helper.getWritableDatabase();
        Bundle bundle= getIntent().getExtras();
        final String id= bundle.getString("ID");

        updateJ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database.execSQL("UPDATE " + JournalSQLHelper.TABLE_NAME + " SET title='" + editTitle.getText().toString()
                        + "',content='" + editContent.getText().toString() +
                        "' WHERE ID='" + Integer.parseInt(id) + "'");// need to add data
                redirectToJournalPages();
            }
        });


    }

    public void redirectToJournalPages(){
        Intent intent= new Intent(EditJournalActivity.this, JournalPagesActivity.class);
        startActivity(intent);
        finish();
    }
}
