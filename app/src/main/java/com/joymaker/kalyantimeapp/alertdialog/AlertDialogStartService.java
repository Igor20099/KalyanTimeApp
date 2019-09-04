package com.joymaker.kalyantimeapp.alertdialog;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.joymaker.kalyantimeapp.statetable.TableIsFree;
import com.joymaker.kalyantimeapp.statetable.TableService;
import com.joymaker.kalyantimeapp.table.ITable;
import com.joymaker.kalyantimeapp.table.Table;
import com.joymaker.kalyantimeapp.utills.LogUtills;

public class AlertDialogStartService {
    static  String serv;
    public static AlertDialog getAlertDialogStartService(final Context context, final ITable table) {
        String[] services = {"Cервис 1","Cервис 2","Cервис 3","Cервис 4","Cервис 5","Cервис 6"};
        serv = services[0];
       switch (((Table) table).getTstate()) {
           case "Сервис 1":
               serv = services[1];
               break;
           case "Сервис 2":
               serv = services[2];
               break;
           case "Сервис 3":
               serv = services[3];
               break;
           case "Сервис 4":
               serv = services[4];
               break;
           case "Сервис 5":
               serv = services[5];
               break;
           case "Сервис 6":
               break;

       }


        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        return builder.setTitle(((Table) table).getNameTable()).setMessage("Начать " + serv).setCancelable(false)
                .setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).setPositiveButton("Начать", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(!((Table)table).getTstate().equals("Готов")) {
                            String stop = ((Table) table).stopTimerService();
                            LogUtills.getInstance().writeLog(context, stop, Context.MODE_APPEND);
                        }
                        ((Table) table).setTableState(new TableService());
                        ((Table) table).startState();
                        String data = String.format(" | %s | %s | Статус: Начало %s\n",((Table)table).getRoomTables(),((Table)table).getNameTable(),serv);
                        LogUtills.getInstance().writeLog(context,data,Context.MODE_APPEND);
                    }
                })
                .setNeutralButton("Стол свободен", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        AlertDialog alertDialogKalyanIsFree = AlertDialogKalyanIsFree.getAlertDialogIsFree(context,table);
                        alertDialogKalyanIsFree.show();
                    }
                })
                .create();
    }
}
