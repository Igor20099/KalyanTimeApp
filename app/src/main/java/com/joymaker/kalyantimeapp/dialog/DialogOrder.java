package com.joymaker.kalyantimeapp.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.joymaker.kalyantimeapp.R;
import com.joymaker.kalyantimeapp.statetable.TableIsMake;
import com.joymaker.kalyantimeapp.table.ITable;
import com.joymaker.kalyantimeapp.table.Table;
import com.joymaker.kalyantimeapp.utills.LogUtills;

public class DialogOrder extends AppCompatDialogFragment {
    private RadioGroup radioGroupPrice;
    private RadioGroup radioGroupRawMaterials;
    private RadioGroup radioGroupClient;
    private ITable table;


    public void setTable(ITable table) {
        this.table = table;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        final View v = layoutInflater.inflate(R.layout.dialog_order, null);
        setCancelable(false);
        radioGroupPrice = v.findViewById(R.id.radioGroupPrice);
        radioGroupRawMaterials = v.findViewById(R.id.raw);
        radioGroupClient = v.findViewById(R.id.radioGroupClient);
        builder.setView(v).setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        })
                .setPositiveButton("Начать", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ((Table) table).setTableState(new TableIsMake());
                        ((Table) table).startState();
                        int idPrice = radioGroupPrice.getCheckedRadioButtonId();
                        RadioButton radioButtonPrice = v.findViewById(idPrice);
                        int idRaw = radioGroupRawMaterials.getCheckedRadioButtonId();
                        RadioButton radioButtonRaw = v.findViewById(idRaw);
                        int idClient = radioGroupClient.getCheckedRadioButtonId();
                        RadioButton radioButtonClient = v.findViewById(idClient);
                        String order = String.format(" | %s | %s | Статус: ПОДГОТОВКА | ЗАКАЗ: Стоимость - %s, Сырье - %s, Клиент - %s\n", ((Table) table).getRoomTables(), ((Table) table).getNameTable(), radioButtonPrice.getText().toString(), radioButtonRaw.getText().toString(), radioButtonClient.getText().toString());
                        LogUtills.getInstance().writeLog(getActivity(), order, Context.MODE_APPEND);
                    }
                });
        return builder.create();
    }


}
