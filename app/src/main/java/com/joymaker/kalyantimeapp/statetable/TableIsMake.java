package com.joymaker.kalyantimeapp.statetable;

import android.content.Context;

import com.joymaker.kalyantimeapp.table.ITable;
import com.joymaker.kalyantimeapp.utills.LogUtills;
import com.joymaker.kalyantimeapp.table.Table;

public class TableIsMake implements TableState {
    @Override
    public void doIt(Context context, ITable table) {
        String data = String.format(" | %s | %s | Статус: ПОДГОТОВКА КАЛЬЯНА\n",((Table)table).getRoomTables(),((Table)table).getNameTable());
        table.startTimer();
        LogUtills.getInstance().writeLog(context,data,Context.MODE_APPEND);
    }
}
