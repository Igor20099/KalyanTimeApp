package com.joymaker.kalyantimeapp.fragmentsroom;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.joymaker.kalyantimeapp.alertdialog.AlertDialogKalyanIsDone;
import com.joymaker.kalyantimeapp.alertdialog.AlertDialogKalyanIsFree;
import com.joymaker.kalyantimeapp.factory.FactoryTable;
import com.joymaker.kalyantimeapp.factory.IFactoryTable;
import com.joymaker.kalyantimeapp.table.ITable;
import com.joymaker.kalyantimeapp.R;
import com.joymaker.kalyantimeapp.table.Table;
import com.joymaker.kalyantimeapp.statetable.TableIsDone;
import com.joymaker.kalyantimeapp.statetable.TableIsFree;
import com.joymaker.kalyantimeapp.statetable.TableIsMake;

import java.util.ArrayList;

public class SecondRoomTablesFragment extends Fragment {
    private ArrayList<ITable> tablesTest;

    private ArrayList<ITable> getAllTables(View view) {
        ArrayList<ITable> tables = new ArrayList<>();
        IFactoryTable factoryTable = new FactoryTable();
        tables.add(factoryTable.create(getContext(), R.layout.fragment_second_room, view, view.findViewById(R.id.table1), R.id.TableName1, R.id.TableStatus1, R.id.TableTime1));
        tables.add(factoryTable.create(getContext(), R.layout.fragment_second_room, view, view.findViewById(R.id.table2), R.id.TableName2, R.id.TableStatus2, R.id.TableTime2));
        tables.add(factoryTable.create(getContext(), R.layout.fragment_second_room, view, view.findViewById(R.id.table3), R.id.TableName3, R.id.TableStatus3, R.id.TableTime3));
        tables.add(factoryTable.create(getContext(), R.layout.fragment_second_room, view, view.findViewById(R.id.table4), R.id.TableName4, R.id.TableStatus4, R.id.TableTime4));
        tables.add(factoryTable.create(getContext(), R.layout.fragment_second_room, view, view.findViewById(R.id.table5), R.id.TableName5, R.id.TableStatus5, R.id.TableTime5));
        tables.add(factoryTable.create(getContext(), R.layout.fragment_second_room, view, view.findViewById(R.id.table6), R.id.TableName6, R.id.TableStatus6, R.id.TableTime6));
        tables.add(factoryTable.create(getContext(), R.layout.fragment_second_room, view, view.findViewById(R.id.table7), R.id.TableName7, R.id.TableStatus7, R.id.TableTime7));
        tables.add(factoryTable.create(getContext(), R.layout.fragment_second_room, view, view.findViewById(R.id.table8), R.id.TableName8, R.id.TableStatus8, R.id.TableTime8));
        tables.add(factoryTable.create(getContext(), R.layout.fragment_second_room, view, view.findViewById(R.id.table9), R.id.TableName9, R.id.TableStatus9, R.id.TableTime9));
        tables.add(factoryTable.create(getContext(), R.layout.fragment_second_room, view, view.findViewById(R.id.table10), R.id.TableName10, R.id.TableStatus10, R.id.TableTime10));
        return tables;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_second_room, container, false);
        tablesTest = getAllTables(rootView);
        for (final ITable tab : tablesTest) {
            ((Table) tab).getTableCardView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    switch (((Table) tab).getTstate()) {
                        case "Свободен":
                            ((Table) tab).setTableState(new TableIsMake());
                            ((Table) tab).startState();
                            break;
                        case "Подготовка":
                            AlertDialog alertDialog = AlertDialogKalyanIsDone.getAlertDialogIsDone(getContext(),tab);
                            alertDialog.show();
                            break;
                        case "Занят":
                            AlertDialog alertDialogIsFree = AlertDialogKalyanIsFree.getAlertDialogIsFree(getContext(),tab);
                            alertDialogIsFree.show();
                            break;
                    }
                }
            });
        }
        return rootView;
    }
}
