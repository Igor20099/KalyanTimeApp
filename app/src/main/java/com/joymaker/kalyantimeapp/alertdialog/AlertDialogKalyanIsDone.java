package com.joymaker.kalyantimeapp.alertdialog;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.joymaker.kalyantimeapp.statetable.TableIsDone;
import com.joymaker.kalyantimeapp.table.ITable;
import com.joymaker.kalyantimeapp.table.Table;

public class AlertDialogKalyanIsDone {

    public static AlertDialog getAlertDialogIsDone(Context context, final ITable table){

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        return builder.setTitle(((Table)table).getNameTable()).setMessage("Кальян готов?").setCancelable(false)
                .setNegativeButton("Нет", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).setPositiveButton("Да", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ((Table) table).setTableState(new TableIsDone());
                        ((Table) table).startState();
                    }
                })
                .create();
    }
}
