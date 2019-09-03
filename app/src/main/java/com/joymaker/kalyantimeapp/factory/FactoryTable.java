package com.joymaker.kalyantimeapp.factory;

import android.content.Context;
import android.view.View;

import com.joymaker.kalyantimeapp.table.ITable;
import com.joymaker.kalyantimeapp.table.Table;

public class FactoryTable implements IFactoryTable {
    @Override
    public ITable create(Context context, int idRoomFragment, View view , View cardTableView, int idNumberTable, int idTableState, int idTimeTable) {
        return new Table(context,idRoomFragment,view ,cardTableView,idNumberTable, idTableState, idTimeTable);
    }
}
