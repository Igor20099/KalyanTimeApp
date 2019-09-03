package com.joymaker.kalyantimeapp.statetable;

import android.content.Context;

import com.joymaker.kalyantimeapp.table.ITable;
import com.joymaker.kalyantimeapp.utills.LogUtills;
import com.joymaker.kalyantimeapp.table.Table;

public class TableIsFree implements TableState {
    @Override
    public void doIt(Context context, ITable table) {
        ((Table) table).stopTimerService();
        String data = String.format(" | %s | %s | Статус: СВОБОДЕН\n",((Table)table).getRoomTables(),((Table)table).getNameTable());
        LogUtills.getInstance().writeLog(context,data,Context.MODE_APPEND);
    }
}
