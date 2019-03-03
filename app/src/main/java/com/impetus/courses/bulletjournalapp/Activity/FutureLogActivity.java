package com.impetus.courses.bulletjournalapp.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import com.impetus.courses.bulletjournalapp.Database.JournalSQLHelper;
import com.impetus.courses.bulletjournalapp.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class FutureLogActivity extends AppCompatActivity {
    CalendarView calendarView;
    JournalSQLHelper helper;
    SQLiteDatabase database;
    TextView noteTitle,noteContent,dateSelected;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_future_log);
        calendarView = findViewById(R.id.calendarView);
        noteTitle=findViewById(R.id.notePerDayTitle);
        noteContent=findViewById(R.id.notePerDayContent);
        dateSelected=findViewById(R.id.Dateselected);

        Intent data = getIntent();
        String dateVal = data.getStringExtra("month");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMMM YYYY");
//        try {
//            Date date = simpleDateFormat.parse(dateVal);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }

        helper= new JournalSQLHelper(this);
        database= helper.getWritableDatabase();
        long date=Calendar.getInstance().getTimeInMillis();
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("DD/MM/YYYY");
         String valDate=simpleDateFormat2.format(date);
//         dateSelected.setText(valDate);
        dateSelected.setText(simpleDateFormat2.format(calendarView.getDate()));
        noteTitle.setText(valDate);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {

                String[] param=new String[]{dayOfMonth + "/" + month + "/" + year};
                dateSelected.setText(param[0]);
                Cursor cursor=database.rawQuery("SELECT * FROM " + JournalSQLHelper.TABLE_NAME + " where "+ JournalSQLHelper.DATE + "= ? ",param );
                if(cursor.moveToNext()){
                    noteTitle.setText(cursor.getString(1));
                    noteContent.setText(cursor.getString(2));
                } else {
                    noteTitle.setText(" No notes present");
                    noteContent.setText("");
                }

            }
        });
    }


}

