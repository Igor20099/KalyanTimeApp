package com.joymaker.kalyantimeapp.utills;

import android.content.Context;
import android.content.SharedPreferences;
import android.icu.util.Calendar;
import android.preference.PreferenceManager;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class DateAndTimeUtills {
    private static DateAndTimeUtills dateAndTimeUtills;

    private DateAndTimeUtills() {

    }

    public static DateAndTimeUtills getInstance() {
        if (dateAndTimeUtills == null) {
            dateAndTimeUtills = new DateAndTimeUtills();
        }
        return dateAndTimeUtills;
    }

    public String getCurrentDateAndTime() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat dateformat = new SimpleDateFormat("dd.MM.yyyy | HH:mm:ss", Locale.getDefault());
        return dateformat.format(c.getTime());
    }

   public long getAllTime(Context context) {
        long allTime;
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String t = sharedPreferences.getString("totaltime","60");
        int min = Integer.parseInt(t);
        allTime = min * 60000L;
        return allTime;
    }

    public String getCurrentDate() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat dateformat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        return dateformat.format(c.getTime());
    }
}
