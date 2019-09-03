package com.joymaker.kalyantimeapp.factory;

import android.content.Context;
import android.view.View;

import com.joymaker.kalyantimeapp.table.ITable;

public interface IFactoryTable {
    ITable create(Context context, int idRoomFrament, View view, View cardTableView, int idNumberTable, int idTableState, int idTimeTable);
}
