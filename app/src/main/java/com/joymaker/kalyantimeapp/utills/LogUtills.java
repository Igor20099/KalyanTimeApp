package com.joymaker.kalyantimeapp.utills;

import android.content.Context;
import android.icu.util.Calendar;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Locale;

//Класс Singleton для записи в лог и чтении из лога
public class LogUtills {
    private static LogUtills instance;

    private LogUtills() {

    }

    public static LogUtills getInstance() {
        if (instance == null) {
            instance = new LogUtills();
        }
        return instance;
    }


    public void writeLog(Context context, String data,int contextMode) {
        try {
            String allData;
            BufferedWriter bf = new BufferedWriter(new OutputStreamWriter(context.getApplicationContext().openFileOutput("log.txt", contextMode)));
            String dateAndTime = DateAndTimeUtills.getInstance().getCurrentDateAndTime();
            if(!data.equals("")) {
                allData = dateAndTime + data;
            } else
            {
                allData = "";
            }
            bf.write(allData);
            bf.flush();
            bf.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public String readLog(Context context) {
        BufferedReader br = null;
        String log = "";
        try {
            br = new BufferedReader(new InputStreamReader(
                    context.getApplicationContext().openFileInput("log.txt")));
            StringBuffer stringBuffer = new StringBuffer();
            String str = "";
            // читаем содержимое
            while ((str = br.readLine()) != null) {
                stringBuffer.append(str).append("\n");
            }
            log = stringBuffer.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return log;
    }



}
