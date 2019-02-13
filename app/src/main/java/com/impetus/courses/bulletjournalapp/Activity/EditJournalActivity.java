package com.impetus.courses.bulletjournalapp.Activity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.impetus.courses.bulletjournalapp.Database.JournalSQLHelper;
import com.impetus.courses.bulletjournalapp.R;

public class EditJournalActivity extends AppCompatActivity {
    EditText editTitle,editContent;
    TextView constantDate;
    Button updateJ;
    JournalSQLHelper helper;
    SQLiteDatabase database;
    String id_for_update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_journal);

        editTitle=findViewById(R.id.editText7);
        editContent=findViewById(R.id.editText8);
        constantDate=findViewById(R.id.textView9);
        updateJ=findViewById(R.id.button5);

        helper= new JournalSQLHelper(this);
        database= helper.getWritableDatabase();

        Intent data =getIntent();
        editTitle.setText(data.getStringExtra("title"));
        editContent.setText(data.getStringExtra("content"));
        constantDate.setText(data.getStringExtra("date"));
        id_for_update = data.getStringExtra("id");



        updateJ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editTitle.getText().toString().length()==0||editContent.getText().toString().length()==0){
                    Toast.makeText(getApplicationContext(),"Enter all values",Toast.LENGTH_LONG).show();
                }
                else {
                    database.execSQL("UPDATE "+helper.TABLE_NAME+ " SET "+ helper.TITLE + " =' " + editTitle.getText().toString()+ "' ,"+ helper.CONTENT + " = ' "+
                            editContent.getText().toString()+ " ' WHERE ID ='" + id_for_update+"'" );
                    Toast.makeText(getApplicationContext(),"DATA UPDATED: successful",Toast.LENGTH_LONG).show();
                    Intent intent= new Intent(
                            EditJournalActivity.this,JournalPagesActivity.class);
                    startActivity(intent);
                }
            }
        });


    }
}
