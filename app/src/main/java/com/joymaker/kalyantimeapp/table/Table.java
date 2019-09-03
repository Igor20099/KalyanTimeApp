package com.joymaker.kalyantimeapp.table;

import android.content.Context;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;

import com.joymaker.kalyantimeapp.R;
import com.joymaker.kalyantimeapp.statetable.TableIsDone;
import com.joymaker.kalyantimeapp.statetable.TableIsFree;
import com.joymaker.kalyantimeapp.statetable.TableIsMake;
import com.joymaker.kalyantimeapp.statetable.TableState;
import com.joymaker.kalyantimeapp.utills.DateAndTimeUtills;

import java.util.Locale;

public class Table implements ITable {
    Context context;
    public String getNameTable() {
        return this.numberTableTextView.getText().toString();
    }
    private CardView tableCardView;
    private String roomTables;
    private TableState tableState;
    private TextView numberTableTextView;
    private TextView tableStateTextView;
    private CountDownTimer countDownTimer;
    private long countTime;
    private TextView timeTableTextView;

    public String getTstate() {
        return this.tableStateTextView.getText().toString();
    }


    public void setTableState(TableState tableState) {
        this.tableState = tableState;
        if (tableState instanceof TableIsFree) {
            this.tableStateTextView.setText("Свободен");
        } else if (tableState instanceof TableIsMake) {
            this.tableStateTextView.setText("Подготовка");
        } else if (tableState instanceof TableIsDone) {
            this.tableStateTextView.setText("Занят");
        }
    }

    public String getRoomTables() {
        return roomTables;
    }

    public CardView getTableCardView() {
        return tableCardView;
    }

    public Table(Context context, int idFragmentRoom, View view, View cardViewTable, int idNumberTable, int idTableState, int idTimeTable) {
        if (idFragmentRoom == R.layout.fragment_first_room) {
            roomTables = "Зал 1";
        } else {
            roomTables = "Зал 2";
        }
        this.context = context;
        View v = view;
        this.tableCardView = (CardView) cardViewTable;
        this.numberTableTextView = v.findViewById(idNumberTable);
        this.tableStateTextView = v.findViewById(idTableState);
        this.timeTableTextView = v.findViewById(idTimeTable);
    }


    public void startState() {
        tableState.doIt(context, this);
    }

    public void startTimer() {
        timeTableTextView.setVisibility(View.VISIBLE);
        final long time = DateAndTimeUtills.getInstance().getAllTime(context);
        countDownTimer = new CountDownTimer(time, 1000) {
            @Override
            public void onTick(long l) {
                countTime += 1000;
                timeTableTextView.setText(String.format(Locale.getDefault(), "%02d:%02d", (l / 60000), (l % 60000 / 1000)));
            }

            @Override
            public void onFinish() {

            }
        }.start();
    }

    public String stopTimerAndGetTime() {
        countDownTimer.cancel();
        timeTableTextView.setTextColor(Color.BLACK);
        timeTableTextView.setVisibility(View.INVISIBLE);
        String time = String.format(Locale.getDefault(), " | Время за которое сделан кальян: %02d:%02d", (countTime / 60000), (countTime % 60000 / 1000));
        return time;
    }
}
