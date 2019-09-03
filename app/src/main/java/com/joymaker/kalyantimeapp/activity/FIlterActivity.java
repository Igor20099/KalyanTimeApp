package com.joymaker.kalyantimeapp.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.icu.util.Calendar;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.Toast;

import com.joymaker.kalyantimeapp.R;
import com.joymaker.kalyantimeapp.utills.DateAndTimeUtills;
import com.joymaker.kalyantimeapp.utills.LogUtills;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FIlterActivity extends AppCompatActivity {
    private CalendarView calendarViewFilter;
    private CheckBox checkBoxRoomOne;
    private CheckBox checkBoxRoomTwo;
    private Spinner spinnerTables;
    private String date;
    private Toolbar toolbar;
    private String filterLog;
    private String log;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        calendarViewFilter = findViewById(R.id.calendarViewFilter);
        checkBoxRoomOne = findViewById(R.id.checkBoxRoomOne);
        checkBoxRoomTwo = findViewById(R.id.checkBoxRoomTwo);
        spinnerTables = findViewById(R.id.spinnerTables);
        toolbar = findViewById(R.id.toolbar3);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        date = DateAndTimeUtills.getInstance().getCurrentDate();
        log = LogUtills.getInstance().readLog(this);

        calendarViewFilter.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                if (!date.isEmpty()) {
                    int year = i;
                    int month = i1 + 1;
                    int day = i2;
                    String selectedDate = String.format("%02d.%02d.%04d", day, month, year);
                    date = selectedDate;
                    Toast.makeText(getApplicationContext(), selectedDate, Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void onClickShowResult(View view) {
        String regex = "";
        String table = spinnerTables.getSelectedItem().toString();
        if ((checkBoxRoomOne.isChecked() && checkBoxRoomTwo.isChecked() && table.equals("Все")) || (!checkBoxRoomOne.isChecked() && !checkBoxRoomTwo.isChecked() && table.equals("Все"))) {
            regex = date + "(.*?)(\\w).*";
        }
        if (checkBoxRoomOne.isChecked() && table.equals("Все") && !checkBoxRoomTwo.isChecked()) {
            regex = date + "(.*?)" + checkBoxRoomOne.getText().toString() + "(.*?)(\\w).*";
        }
        if (checkBoxRoomTwo.isChecked() && table.equals("Все") && !checkBoxRoomOne.isChecked()) {
            regex = date + "(.*?)" + checkBoxRoomTwo.getText().toString() + "(.*?)(\\w).*";
        }
        if ((checkBoxRoomOne.isChecked() && checkBoxRoomTwo.isChecked() && !table.equals("Все")) || (!checkBoxRoomOne.isChecked() && !checkBoxRoomTwo.isChecked() && !table.equals("Все"))) {
            if(table.equals("Стол 1")) {
                regex = date + "(.*?)" +  "Стол 1 \\| (.*?)(\\w).*";
            } else {
                regex = date + "(.*?)" + table + "(.*?)(\\w).*";
            }
        }
        if (checkBoxRoomOne.isChecked() && !table.equals("Все") && !checkBoxRoomTwo.isChecked()) {
            if(table.equals("Стол 1")) {
                regex = date + "(.*?)" + checkBoxRoomOne.getText().toString() + "(.*?)" + table + " \\| (.*?)(\\w).*";
            } else {
                regex = date + "(.*?)" + checkBoxRoomOne.getText().toString() + "(.*?)" + table + "(.*?)(\\w).*";
            }

        }
        if (checkBoxRoomTwo.isChecked() && !table.equals("Все") && !checkBoxRoomOne.isChecked()) {
            if(table.equals("Стол 1")) {
                regex = date + "(.*?)" + checkBoxRoomTwo.getText().toString() + "(.*?)" + table + " \\| (.*?)(\\w).*";
            } else {
                regex = date + "(.*?)" + checkBoxRoomTwo.getText().toString() + "(.*?)" + table + "(.*?)(\\w).*";
            }
        }



        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(log);
        StringBuilder stringBuilder = new StringBuilder();
        while (matcher.find()) {
            stringBuilder.append(matcher.group(0)).append("\n");
        }
        filterLog = stringBuilder.toString();
        Intent intent = getIntent();
        intent.putExtra("filterlog", filterLog);
        setResult(RESULT_OK, intent);
        finish();
    }


    public void onClickResetFilter(View view) {
        filterLog = LogUtills.getInstance().readLog(this);
        Intent intent = getIntent();
        intent.putExtra("filterlog", filterLog);
        setResult(RESULT_OK, intent);
        finish();
    }
}
