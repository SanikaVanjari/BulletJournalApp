package com.impetus.courses.bulletjournalapp.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.Toast;

import com.impetus.courses.bulletjournalapp.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class FutureLogActivity extends AppCompatActivity {
    CalendarView calendarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_future_log);
        calendarView = findViewById(R.id.calendarView);

        Intent data = getIntent();
        String dateVal = data.getStringExtra("month");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMMM YYYY");
        try {
            Date date = simpleDateFormat.parse(dateVal);
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }
}

