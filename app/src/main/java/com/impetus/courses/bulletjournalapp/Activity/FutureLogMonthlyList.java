package com.impetus.courses.bulletjournalapp.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.impetus.courses.bulletjournalapp.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class FutureLogMonthlyList extends AppCompatActivity {
    ListView monthlyLog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_future_log_monthly_list);
        monthlyLog= findViewById(R.id.MonthsGridView);
        SimpleDateFormat dateFormat=new SimpleDateFormat("MMMM YYYY");
        GregorianCalendar date=new GregorianCalendar();
        final String[] dateArray=new String[12];

        for(int day=0;day<12;day++){
            dateArray[day]=dateFormat.format(date.getTime());
            date.roll(Calendar.MONTH,true);
        }

        monthlyLog.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, dateArray));
        monthlyLog.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent= new Intent(FutureLogMonthlyList.this,FutureLogActivity.class);
                intent.putExtra("month",dateArray[position]);
                startActivity(intent);
            }
        });
    }
}
