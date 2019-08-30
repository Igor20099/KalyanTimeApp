package com.joymaker.kalyantimeapp;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.icu.util.Calendar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class LogActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, android.support.v7.widget.Toolbar.OnMenuItemClickListener {
    private TextView textView;
    private android.support.v7.widget.Toolbar toolbar;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {

            case 1:
                if(resultCode == RESULT_OK) {
                    String res = data.getStringExtra("filterlog");
                    Log.i("filter", res);
                    textView.setText(res);
                }
            break;
        }


        }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.inflateMenu(R.menu.menu_log);
        toolbar.setOnMenuItemClickListener(this);
        textView = findViewById(R.id.textViewLog);
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(
                    openFileInput("log.txt")));
            StringBuffer stringBuffer = new StringBuffer();
            String str = "";
            // читаем содержимое
            while ((str = br.readLine()) != null) {
               stringBuffer.append(str).append("\n");
            }
            textView.setText(stringBuffer.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }




    private void clearLog() {
        try {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(this.openFileOutput("log.txt",MODE_PRIVATE)));
            bw.write("");
            // закрываем поток
            bw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(
                    openFileInput("log.txt")));
            StringBuffer stringBuffer = new StringBuffer();
            String str = "";
            // читаем содержимое
            while ((str = br.readLine()) != null) {
                stringBuffer.append(str);
            }
            textView.setText(stringBuffer.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_log, menu);
        return true;
    }


    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()){
            case R.id.clearLog:
                showClearLogDialog();
                break;
            case R.id.filterLog:
                Intent intent = new Intent(this,FIlterActivity.class);
                startActivityForResult(intent,1);
                break;
        }
        return false;
    }

    private void showClearLogDialog() {
        AlertDialog.Builder builder =  new AlertDialog.Builder(this);
        builder.setTitle("Очистить лог")
                .setMessage("Вы уверены что хотите очистить лог?").setCancelable(false).setPositiveButton("Да", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                clearLog();
                Toast.makeText(LogActivity.this, "Лог очищен!", Toast.LENGTH_SHORT).show();
            }
        }).setNegativeButton("Нет", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
