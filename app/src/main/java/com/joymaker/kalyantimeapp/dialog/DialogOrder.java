package com.joymaker.kalyantimeapp.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.joymaker.kalyantimeapp.R;
import com.joymaker.kalyantimeapp.statetable.TableIsMake;
import com.joymaker.kalyantimeapp.table.ITable;
import com.joymaker.kalyantimeapp.table.Table;
import com.joymaker.kalyantimeapp.utills.LogUtills;

public class DialogOrder extends DialogFragment {
    private RadioGroup radioGroupPrice;
    private RadioGroup radioGroupRawMaterials;
    private RadioGroup radioGroupClient;
    Context context;
    private ITable table;

    public void setTable(ITable table) {
        this.table = table;
    }

    public RadioGroup getRadioGroupPrice() {
        return radioGroupPrice;
    }

    public RadioGroup getRadioGroupRawMaterials() {
        return radioGroupRawMaterials;
    }

    public RadioGroup getRadioGroupClient() {
        return radioGroupClient;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        setCancelable(false);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        return builder
                .setView(R.layout.dialog_order).setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
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
                        RadioButton radioButton = (RadioButton) radioGroupPrice.findViewById(idPrice);
                        int idRaw = radioGroupRawMaterials.getCheckedRadioButtonId();
                        RadioButton radioButtonRaw = (RadioButton) radioGroupRawMaterials.findViewById(idRaw);
                        int idClient = radioGroupClient.getCheckedRadioButtonId();
                        RadioButton radioButtonClient = (RadioButton) radioGroupClient.findViewById(idClient);
                        String order = String.format(" | %s | %s | Статус: ПОДГОТОВКА | ЗАКАЗ: Стоимость - %s, Сырье - %s, Клиент - %s\n",((Table) table).getRoomTables(),((Table) table).getNameTable(),radioButton.getText().toString(),radioButtonRaw.getText().toString(),radioButtonClient.getText().toString());
                        LogUtills.getInstance().writeLog(getActivity(),order,Context.MODE_APPEND);
                    }
                })
                .create();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_order,container,true);
        radioGroupPrice = v.findViewById(R.id.radioGroupPrice);
        radioGroupRawMaterials = v.findViewById(R.id.raw);
        radioGroupClient = v.findViewById(R.id.radioGroupClient);
        return v;
    }
}
