package com.joymaker.kalyantimeapp.statetable;

import android.content.Context;

import com.joymaker.kalyantimeapp.table.ITable;
import com.joymaker.kalyantimeapp.table.Table;
import com.joymaker.kalyantimeapp.utills.LogUtills;

public class TableService implements TableState {
    @Override
    public void doIt(Context context, ITable table) {

        ((Table)table).starServiceTimer();
    }
}
