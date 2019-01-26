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

public class FutureLogMonthlyList extends AppCompatActivity {
    ListView monthlyLog;
        ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_future_log_monthly_list);
        final String[] months={"JAN 2019","FEB 2019", "MARCH 2019","APRIL 2019","MAY 2019","JUNE 2019","JULY 2019"};
        monthlyLog= findViewById(R.id.MonthsGridView);
        adapter= new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, months);
        monthlyLog.setAdapter(adapter);
                monthlyLog.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent= new Intent(FutureLogMonthlyList.this,FutureLogActivity.class);
                intent.putExtra("month",months[position]);
                startActivity(intent);
            }
        });

    }
}
