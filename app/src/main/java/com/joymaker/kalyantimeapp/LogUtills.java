package com.joymaker.kalyantimeapp;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class LogUtills {
    private static LogUtills instance;

    private LogUtills() {

    }
    public synchronized static LogUtills getInstance() {
        if (instance == null) {
            instance = new LogUtills();
        }
        return instance;
    }


    public void writeLog(Context context,String data) {
        try {
            BufferedWriter bf = new BufferedWriter(new OutputStreamWriter(context.getApplicationContext().openFileOutput("log.txt", Context.MODE_APPEND)));
            bf.write(data);
            bf.flush();
            bf.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
