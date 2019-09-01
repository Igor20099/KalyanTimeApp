package com.joymaker.kalyantimeapp.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.joymaker.kalyantimeapp.R;


public class DialogPasswordSetTime extends DialogFragment {
    String editTextPass;
    FragmentManager fragmentManager;
    SharedPreferences sharedPreferences;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        fragmentManager = getFragmentManager();
        return builder.setTitle("Пароль для доступа").setPositiveButton("Ок",null).setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        }).setView(R.layout.dialog_password).create();
    }



    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.dialog_password, container, true);
        getDialog().requestWindowFeature(STYLE_NO_TITLE);
        setCancelable(false);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        final AlertDialog D = (AlertDialog) getDialog();
        if (D != null) {
            Button positive = (Button) D.getButton(Dialog.BUTTON_POSITIVE);
            positive.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    EditText editText = getDialog().findViewById(R.id.editText2);
                    Context context;
                    editTextPass = editText.getText().toString();
                    sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
                    String pass = sharedPreferences.getString("password","");

                    if (!editTextPass.equals(pass)) {
                        Toast.makeText(getActivity(), "Пароль не верен!",Toast.LENGTH_SHORT).show();
                    } else {
                       DialogSetTime dialogSetTime = new DialogSetTime();
                        dialogSetTime.show(fragmentManager,"dd");
                        D.dismiss(); //dissmiss dialog

                    }
                }
            });
        }
    }


}
