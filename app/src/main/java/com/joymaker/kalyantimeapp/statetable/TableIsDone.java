package com.joymaker.kalyantimeapp.statetable;

import android.content.Context;
import android.util.Log;

import com.joymaker.kalyantimeapp.table.ITable;
import com.joymaker.kalyantimeapp.utills.LogUtills;
import com.joymaker.kalyantimeapp.table.Table;

public class TableIsDone implements TableState {
    @Override
    public void doIt(Context context, ITable table) {
       String time =  table.stopTimerAndGetTime();
        String data = String.format(" | %s | %s | Статус: ЗАНЯТ%s\n",((Table)table).getRoomTables(),((Table)table).getNameTable(),time);
        LogUtills.getInstance().writeLog(context,data,Context.MODE_APPEND);
    }
}
