package com.joymaker.kalyantimeapp.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.joymaker.kalyantimeapp.R;

public class DialogCreatePassword extends DialogFragment {
    String pass;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        return builder.setTitle("Установка пароля").setPositiveButton("Ок",null).setView(R.layout.dialog_create_password).create();
    }



    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.dialog_create_password, container, true);
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
                    EditText editText = (EditText) getDialog().findViewById(R.id.editText);
                    pass = editText.getText().toString();
                    if (pass.equals("")) {
                        Toast.makeText(getActivity(), "Вы не установили пароль!",Toast.LENGTH_SHORT).show();
                    } else {
                        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
                        sharedPreferences.edit().putString("password", pass).apply();
                        Toast.makeText(getContext(), "Пароль был успешно установлен!", Toast.LENGTH_SHORT).show();
                        D.dismiss(); //dissmiss dialog
                    }
                }
            });
        }
    }
}
