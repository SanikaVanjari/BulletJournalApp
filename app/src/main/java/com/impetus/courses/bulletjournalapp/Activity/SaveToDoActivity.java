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
import com.impetus.courses.bulletjournalapp.Database.ToDoListSQLHelper;
import com.impetus.courses.bulletjournalapp.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SaveToDoActivity extends AppCompatActivity {
    EditText TContent,TDate;
    Button save;
    ToDoListSQLHelper helper;
    SQLiteDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_to_do);

        TContent=findViewById(R.id.editText10);
        TDate=findViewById(R.id.editText9);
        save=findViewById(R.id.button6);
        helper=new ToDoListSQLHelper(this);
        database=helper.getWritableDatabase();

        SimpleDateFormat dateFormat= new SimpleDateFormat("dd/MM/yyyy");
        TDate.setText(dateFormat.format(new Date()));

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isInserted=helper.insertTODOData(TContent.getText().toString(),
                        TDate.getText().toString());
                if(isInserted==true){
                    Toast.makeText(SaveToDoActivity.this,"LIST inserted",Toast.LENGTH_LONG).show();
                    redirectToTODOPages();
                } else {
                    Toast.makeText(SaveToDoActivity.this,"LIST not inserted",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    public void redirectToTODOPages(){
        Intent intent= new Intent(SaveToDoActivity.this,ToDoListActivity.class);
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
