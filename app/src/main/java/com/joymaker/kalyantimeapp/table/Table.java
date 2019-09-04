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
import com.joymaker.kalyantimeapp.statetable.TableService;
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
    private CountDownTimer upDownTimer;
    private long countTime;
    private String state;
    private boolean isUpTimer=false;
    long time;

    public void setState(String state) {
        this.state =state;
    }

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
            this.tableStateTextView.setText("Готов");
        } else if (tableState instanceof TableService) {
            if (tableStateTextView.getText().toString().equals("Сервис 1")) {
                this.tableStateTextView.setText("Сервис 2");
            } else if (tableStateTextView.getText().toString().equals("Сервис 2")) {
                this.tableStateTextView.setText("Сервис 3");
            }else if (tableStateTextView.getText().toString().equals("Сервис 3")) {
                this.tableStateTextView.setText("Сервис 4");
            }
            else if (tableStateTextView.getText().toString().equals("Сервис 4")) {
                this.tableStateTextView.setText("Сервис 5");
            }
            else if (tableStateTextView.getText().toString().equals("Сервис 5")) {
                this.tableStateTextView.setText("Сервис 6");
            }

            else {
                this.tableStateTextView.setText("Сервис 1");
            }
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
         time = DateAndTimeUtills.getInstance().getAllTime(context);
        countDownTimer = new CountDownTimer(time, 1000) {
            @Override
            public void onTick(long l) {
                countTime += 1000;
                timeTableTextView.setText(String.format(Locale.getDefault(), "%02d:%02d", (l / 60000), (l % 60000 / 1000)));
            }

            @Override
            public void onFinish() {
               isUpTimer= startUpTimer();
            }
        }.start();
    }

    public String stopTimerAndGetTime() {
        countDownTimer.cancel();
        if(isUpTimer) {
            upDownTimer.cancel();
        }
        timeTableTextView.setTextColor(Color.BLACK);
        timeTableTextView.setVisibility(View.INVISIBLE);
        String timeStr ="";
            if(countTime<=time ) {
                timeStr = String.format(Locale.getDefault(), " | Время за которое сделан кальян: %02d:%02d", (countTime / 60000), (countTime % 60000 / 1000));
            } else if (countTime > time) {
                timeStr = String.format(Locale.getDefault(), " | ПРЕВЫШЕНО ВРЕМЯ ПОГОТОВКИ НА: %02d:%02d | Время за которое сделан кальян: %02d:%02d",((countTime - time) / 60000), ((countTime - time) % 60000 / 1000), (countTime / 60000), (countTime % 60000 / 1000));
            }
            countTime=0;
        return timeStr;
    }

    public void starServiceTimer() {
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
                isUpTimer= startUpTimer();
            }
        }.start();
    }

    public String stopTimerService() {
        countDownTimer.cancel();
        String timeStr ="";
        if(isUpTimer) {
            upDownTimer.cancel();
        }
        timeTableTextView.setTextColor(Color.BLACK);
        timeTableTextView.setVisibility(View.INVISIBLE);
        if (countTime <= time) {
            timeStr = String.format(Locale.getDefault(), " | %s | %s | Cтатус: Завершен %s | Время сервиса: %02d:%02d\n", this.getRoomTables(), this.getNameTable(), this.tableStateTextView.getText().toString(), (countTime / 60000), (countTime % 60000 / 1000));

        } else if (countTime > time) {
            timeStr = String.format(Locale.getDefault(), " | %s | %s | Cтатус: Завершен %s |ПРЕВЫШЕНО ВРЕМЯ СЕРВИСА НА: %02d:%02d | Время сервиса: %02d:%02d\n",this.getRoomTables(),this.getNameTable(),this.tableStateTextView.getText().toString(),((countTime - time) / 60000), ((countTime - time) % 60000 / 1000), (countTime / 60000), (countTime % 60000 / 1000));
        }
        countTime = 0;
        return timeStr;

    }
    public boolean startUpTimer() {
        upDownTimer = new CountDownTimer(Integer.MAX_VALUE,1000) {
            int min = 0;
            int sec = 0;
            @Override
            public void onTick(long l) {
                countTime += 1000;

                sec++;
                if (sec>59) {
                    min++;
                    sec=0;
                }
                timeTableTextView.setTextColor(Color.RED);
                timeTableTextView.setText(String.format(Locale.getDefault(),"%02d:%02d",min,sec));
            }

            @Override
            public void onFinish() {
            }

        }.start();
        return true;
    }
}
