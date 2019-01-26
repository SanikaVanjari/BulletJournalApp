package com.impetus.courses.bulletjournalapp.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.Toast;

import com.impetus.courses.bulletjournalapp.R;

import java.util.Calendar;
import java.util.Date;

public class FutureLogActivity extends AppCompatActivity {
    CalendarView calendarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_future_log);
        calendarView=findViewById(R.id.calendarView);

        Bundle bundle= getIntent().getExtras();
        final String month= bundle.getString("month");
        // How to use the month selected in the previous list view to display in the calender view?
            }
}
