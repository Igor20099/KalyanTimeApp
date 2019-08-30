package com.joymaker.kalyantimeapp.fragments;


import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.joymaker.kalyantimeapp.R;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class SecondRoomTablesFragment extends Fragment {
    private ArrayList<CardView> tables = new ArrayList<>();
    private TextView tableStatus1;
    private TextView tableStatus2;
    private TextView tableStatus3;
    private TextView tableStatus4;
    private TextView tableStatus5;
    private TextView tableStatus6;
    private TextView tableStatus7;
    private TextView tableStatus8;
    private TextView tableStatus9;
    private TextView tableStatus10;

    private TextView tableTime1;
    private TextView tableTime2;
    private TextView tableTime3;
    private TextView tableTime4;
    private TextView tableTime5;
    private TextView tableTime6;
    private TextView tableTime7;
    private TextView tableTime8;
    private TextView tableTime9;
    private TextView tableTime10;
    private long getAllTime() {
        long allTime;
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        String t = sharedPreferences.getString("totaltime","60");
        int min = Integer.parseInt(t);
        allTime = min * 60000L;
        return allTime;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_second_room, container, false);
        final String roomTwo = "ЗАЛ 2";
        final String tab1 = "Стол 1";
        final String tab2 = "Стол 2";
        final String tab3 = "Стол 3";
        final String tab4 = "Стол 4";
        final String tab5 = "Стол 5";
        final String tab6 = "Стол 6";
        final String tab7 = "Стол 7";
        final String tab8 = "Стол 8";
        final String tab9 = "Стол 9";
        final String tab10 = "Стол 10";

        for (int i = 1; i < 11; i++) {
            CardView cardView = (CardView) rootView.findViewWithTag(String.valueOf(i));
            tables.add(cardView);
        }
        tableStatus1 = rootView.findViewById(R.id.TableStatus1);
        tableStatus2 = rootView.findViewById(R.id.TableStatus2);
        tableStatus3 = rootView.findViewById(R.id.TableStatus3);
        tableStatus4 = rootView.findViewById(R.id.TableStatus4);
        tableStatus5 = rootView.findViewById(R.id.TableStatus5);
        tableStatus6 = rootView.findViewById(R.id.TableStatus6);
        tableStatus7 = rootView.findViewById(R.id.TableStatus7);
        tableStatus8 = rootView.findViewById(R.id.TableStatus8);
        tableStatus9 = rootView.findViewById(R.id.TableStatus9);
        tableStatus10 = rootView.findViewById(R.id.TableStatus10);

        tableTime1 = rootView.findViewById(R.id.TableTime1);
        tableTime2 = rootView.findViewById(R.id.TableTime2);
        tableTime3 = rootView.findViewById(R.id.TableTime3);
        tableTime4 = rootView.findViewById(R.id.TableTime4);
        tableTime5 = rootView.findViewById(R.id.TableTime5);
        tableTime6 = rootView.findViewById(R.id.TableTime6);
        tableTime7 = rootView.findViewById(R.id.TableTime7);
        tableTime8 = rootView.findViewById(R.id.TableTime8);
        tableTime9 = rootView.findViewById(R.id.TableTime9);
        tableTime10 = rootView.findViewById(R.id.TableTime10);

        for (final CardView table : tables) {
            table.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int id = view.getId();
                    switch (id) {
                        case R.id.table1:
                            switch (tableStatus1.getText().toString()) {

                                case "Свободен":
                                    String freeLineLog = String.format("%s | %s | %s | Статус:ПОДГОТОВКА КАЛЬЯНА\n", getCurrentDateAndTime(), roomTwo, tab1);
                                    WriteLogFIle(freeLineLog);
                                    tableTime1.setVisibility(View.VISIBLE);
                                    final long time = getAllTime();

                                    tableStatus1.setText("Подготовка");
                                    new CountDownTimer(time, 1000) {
                                        long countTime = 0L;

                                        @Override
                                        public void onTick(long l) {

                                            if (tableStatus1.getText() != "Занят" && tableStatus1.getText() != "Свободен") {
                                                countTime += 1000;
                                                tableTime1.setText(String.format(Locale.getDefault(), "%02d:%02d", (l / 60000), (l % 60000 / 1000)));
                                            } else {
                                                cancel();
                                                String readyLineLog = String.format(Locale.getDefault(), "%s | %s | %s | Статус:СТОЛ ЗАНЯТ | Время за которое сделан кальян %02d:%02d\n", getCurrentDateAndTime(), roomTwo, tab1, (countTime / 60000), (countTime % 60000 / 1000));
                                                WriteLogFIle(readyLineLog);
                                                tableTime1.setTextColor(Color.BLACK);
                                                Log.i("time", String.format(Locale.getDefault(), "Время за которое сделан кальян %02d:%02d", (countTime / 60000), (countTime % 60000 / 1000)));

                                            }

                                        }

                                        @Override
                                        public void onFinish() {
                                            new CountDownTimer(Integer.MAX_VALUE, 1000) {
                                                int s = 0;
                                                int m = 0;

                                                @Override
                                                public void onTick(long l) {
                                                    tableTime1.setTextColor(Color.RED);
                                                    if (tableStatus1.getText() != "Занят" && tableStatus1.getText() != "Свободен") {
                                                        countTime += 1000;
                                                        s += 1;
                                                        if (s > 59) {
                                                            m += 1;
                                                            s = 0;
                                                        }
                                                        tableTime1.setText(String.format(Locale.getDefault(), "%02d:%02d", m, s));
                                                    } else {
                                                        cancel();
                                                        String readyLineLog = String.format(Locale.getDefault(), "%s | %s | %s | Статус:СТОЛ ЗАНЯТ | Время за которое сделан кальян %02d:%02d\n", getCurrentDateAndTime(), roomTwo, tab1, (countTime / 60000), (countTime % 60000 / 1000));
                                                        WriteLogFIle(readyLineLog);
                                                        tableTime1.setTextColor(Color.BLACK);
                                                        Log.i("time", String.format(Locale.getDefault(), "Время за которое сделан кальян %02d:%02d", (countTime / 60000), (countTime % 60000 / 1000)));

                                                    }


                                                }

                                                @Override
                                                public void onFinish() {

                                                }
                                            }.start();
                                        }
                                    }.start();


                                    break;
                                case "Подготовка":
                                    AlertDialog.Builder builder2 = new AlertDialog.Builder(getContext());
                                    builder2.setTitle("Стол 1")
                                            .setMessage("Кальян готов?")
                                            .setCancelable(false)
                                            .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                    tableStatus1.setText("Занят");
                                                    tableTime1.setVisibility(View.INVISIBLE);
                                                }
                                            })
                                            .setNegativeButton("Нет",
                                                    new DialogInterface.OnClickListener() {
                                                        public void onClick(DialogInterface dialog, int id) {
                                                            dialog.cancel();
                                                        }
                                                    });
                                    AlertDialog alert2 = builder2.create();
                                    alert2.show();


                                    break;
                                case "Занят":
                                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                    builder.setTitle("Стол 1")
                                            .setMessage("Стол свободен?")
                                            .setCancelable(false)
                                            .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                    tableStatus1.setText("Свободен");
                                                    tableTime1.setVisibility(View.INVISIBLE);
                                                    if (tableStatus1.getText().toString().equals("Свободен")) {
                                                        String LineLog = String.format("%s | %s | %s | Статус:СТОЛ СВОБОДЕН\n", getCurrentDateAndTime(), roomTwo, tab1);
                                                        WriteLogFIle(LineLog);
                                                    }
                                                }
                                            })
                                            .setNegativeButton("Нет",
                                                    new DialogInterface.OnClickListener() {
                                                        public void onClick(DialogInterface dialog, int id) {
                                                            dialog.cancel();
                                                        }
                                                    });
                                    AlertDialog alert = builder.create();
                                    alert.show();

                                    break;
                            }
                            break;
                        case R.id.table2:
                            switch (tableStatus2.getText().toString()) {

                                case "Свободен":
                                    String freeLineLog = String.format("%s | %s | %s | Статус:ПОДГОТОВКА КАЛЬЯНА\n", getCurrentDateAndTime(), roomTwo, tab2);
                                    WriteLogFIle(freeLineLog);
                                    tableTime2.setVisibility(View.VISIBLE);
                                    final long time = getAllTime();

                                    tableStatus2.setText("Подготовка");
                                    new CountDownTimer(time, 1000) {
                                        long countTime = 0L;

                                        @Override
                                        public void onTick(long l) {

                                            if (tableStatus2.getText() != "Занят" && tableStatus2.getText() != "Свободен") {
                                                countTime += 1000;
                                                tableTime2.setText(String.format(Locale.getDefault(), "%02d:%02d", (l / 60000), (l % 60000 / 1000)));
                                            } else {
                                                cancel();
                                                String readyLineLog = String.format(Locale.getDefault(), "%s | %s | %s | Статус:СТОЛ ЗАНЯТ | Время за которое сделан кальян %02d:%02d\n", getCurrentDateAndTime(), roomTwo, tab2, (countTime / 60000), (countTime % 60000 / 1000));
                                                WriteLogFIle(readyLineLog);
                                                tableTime2.setTextColor(Color.BLACK);
                                                Log.i("time", String.format(Locale.getDefault(), "Время за которое сделан кальян %02d:%02d", (countTime / 60000), (countTime % 60000 / 1000)));

                                            }

                                        }

                                        @Override
                                        public void onFinish() {
                                            new CountDownTimer(Integer.MAX_VALUE, 1000) {
                                                int s = 0;
                                                int m = 0;

                                                @Override
                                                public void onTick(long l) {
                                                    tableTime2.setTextColor(Color.RED);
                                                    if (tableStatus2.getText() != "Занят" && tableStatus2.getText() != "Свободен") {
                                                        countTime += 1000;
                                                        s += 1;
                                                        if (s > 59) {
                                                            m += 1;
                                                            s = 0;
                                                        }
                                                        tableTime2.setText(String.format(Locale.getDefault(), "%02d:%02d", m, s));
                                                    } else {
                                                        cancel();
                                                        String readyLineLog = String.format(Locale.getDefault(), "%s | %s | %s | Статус:СТОЛ ЗАНЯТ | Время за которое сделан кальян %02d:%02d\n", getCurrentDateAndTime(), roomTwo, tab2, (countTime / 60000), (countTime % 60000 / 1000));
                                                        WriteLogFIle(readyLineLog);
                                                        tableTime2.setTextColor(Color.BLACK);
                                                        Log.i("time", String.format(Locale.getDefault(), "Время за которое сделан кальян %02d:%02d", (countTime / 60000), (countTime % 60000 / 1000)));

                                                    }


                                                }

                                                @Override
                                                public void onFinish() {

                                                }
                                            }.start();
                                        }
                                    }.start();


                                    break;
                                case "Подготовка":
                                    AlertDialog.Builder builder2 = new AlertDialog.Builder(getContext());
                                    builder2.setTitle("Стол 2")
                                            .setMessage("Кальян готов?")
                                            .setCancelable(false)
                                            .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                    tableStatus2.setText("Занят");
                                                    tableTime2.setVisibility(View.INVISIBLE);
                                                }
                                            })
                                            .setNegativeButton("Нет",
                                                    new DialogInterface.OnClickListener() {
                                                        public void onClick(DialogInterface dialog, int id) {
                                                            dialog.cancel();
                                                        }
                                                    });
                                    AlertDialog alert2 = builder2.create();
                                    alert2.show();


                                    break;
                                case "Занят":
                                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                    builder.setTitle("Стол 2")
                                            .setMessage("Стол свободен?")
                                            .setCancelable(false)
                                            .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                    tableStatus2.setText("Свободен");
                                                    tableTime2.setVisibility(View.INVISIBLE);
                                                    if (tableStatus2.getText().toString().equals("Свободен")) {
                                                        String LineLog = String.format("%s | %s | %s | Статус:СТОЛ СВОБОДЕН\n", getCurrentDateAndTime(), roomTwo, tab2);
                                                        WriteLogFIle(LineLog);
                                                    }
                                                }
                                            })
                                            .setNegativeButton("Нет",
                                                    new DialogInterface.OnClickListener() {
                                                        public void onClick(DialogInterface dialog, int id) {
                                                            dialog.cancel();
                                                        }
                                                    });
                                    AlertDialog alert = builder.create();
                                    alert.show();

                                    break;
                            }
                            break;
                        case R.id.table3:
                            switch (tableStatus3.getText().toString()) {

                                case "Свободен":
                                    String freeLineLog = String.format("%s | %s | %s | Статус:ПОДГОТОВКА КАЛЬЯНА\n", getCurrentDateAndTime(), roomTwo, tab3);
                                    WriteLogFIle(freeLineLog);
                                    tableTime3.setVisibility(View.VISIBLE);
                                    final long time = getAllTime();

                                    tableStatus3.setText("Подготовка");
                                    new CountDownTimer(time, 1000) {
                                        long countTime = 0L;

                                        @Override
                                        public void onTick(long l) {

                                            if (tableStatus3.getText() != "Занят" && tableStatus3.getText() != "Свободен") {
                                                countTime += 1000;
                                                tableTime3.setText(String.format(Locale.getDefault(), "%02d:%02d", (l / 60000), (l % 60000 / 1000)));
                                            } else {
                                                cancel();
                                                String readyLineLog = String.format(Locale.getDefault(), "%s | %s | %s | Статус:СТОЛ ЗАНЯТ | Время за которое сделан кальян %02d:%02d\n", getCurrentDateAndTime(), roomTwo, tab3, (countTime / 60000), (countTime % 60000 / 1000));
                                                WriteLogFIle(readyLineLog);
                                                tableTime3.setTextColor(Color.BLACK);
                                                Log.i("time", String.format(Locale.getDefault(), "Время за которое сделан кальян %02d:%02d", (countTime / 60000), (countTime % 60000 / 1000)));

                                            }

                                        }

                                        @Override
                                        public void onFinish() {
                                            new CountDownTimer(Integer.MAX_VALUE, 1000) {
                                                int s = 0;
                                                int m = 0;

                                                @Override
                                                public void onTick(long l) {
                                                    tableTime3.setTextColor(Color.RED);
                                                    if (tableStatus3.getText() != "Занят" && tableStatus3.getText() != "Свободен") {
                                                        countTime += 1000;
                                                        s += 1;
                                                        if (s > 59) {
                                                            m += 1;
                                                            s = 0;
                                                        }
                                                        tableTime3.setText(String.format(Locale.getDefault(), "%02d:%02d", m, s));
                                                    } else {
                                                        cancel();
                                                        String readyLineLog = String.format(Locale.getDefault(), "%s | %s | %s | Статус:СТОЛ ЗАНЯТ | Время за которое сделан кальян %02d:%02d\n", getCurrentDateAndTime(), roomTwo, tab3, (countTime / 60000), (countTime % 60000 / 1000));
                                                        WriteLogFIle(readyLineLog);
                                                        tableTime3.setTextColor(Color.BLACK);
                                                        Log.i("time", String.format(Locale.getDefault(), "Время за которое сделан кальян %02d:%02d", (countTime / 60000), (countTime % 60000 / 1000)));

                                                    }


                                                }

                                                @Override
                                                public void onFinish() {

                                                }
                                            }.start();
                                        }
                                    }.start();


                                    break;
                                case "Подготовка":
                                    AlertDialog.Builder builder2 = new AlertDialog.Builder(getContext());
                                    builder2.setTitle("Стол 3")
                                            .setMessage("Кальян готов?")
                                            .setCancelable(false)
                                            .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                    tableStatus3.setText("Занят");
                                                    tableTime3.setVisibility(View.INVISIBLE);
                                                }
                                            })
                                            .setNegativeButton("Нет",
                                                    new DialogInterface.OnClickListener() {
                                                        public void onClick(DialogInterface dialog, int id) {
                                                            dialog.cancel();
                                                        }
                                                    });
                                    AlertDialog alert2 = builder2.create();
                                    alert2.show();


                                    break;
                                case "Занят":
                                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                    builder.setTitle("Стол 3")
                                            .setMessage("Стол свободен?")
                                            .setCancelable(false)
                                            .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                    tableStatus3.setText("Свободен");
                                                    tableTime3.setVisibility(View.INVISIBLE);
                                                    if (tableStatus3.getText().toString().equals("Свободен")) {
                                                        String LineLog = String.format("%s | %s | %s | Статус:СТОЛ СВОБОДЕН\n", getCurrentDateAndTime(), roomTwo, tab3);
                                                        WriteLogFIle(LineLog);
                                                    }
                                                }
                                            })
                                            .setNegativeButton("Нет",
                                                    new DialogInterface.OnClickListener() {
                                                        public void onClick(DialogInterface dialog, int id) {
                                                            dialog.cancel();
                                                        }
                                                    });
                                    AlertDialog alert = builder.create();
                                    alert.show();

                                    break;
                            }
                            break;
                        case R.id.table4:
                            switch (tableStatus4.getText().toString()) {

                                case "Свободен":
                                    String freeLineLog = String.format("%s | %s | %s | Статус:ПОДГОТОВКА КАЛЬЯНА\n", getCurrentDateAndTime(), roomTwo, tab4);
                                    WriteLogFIle(freeLineLog);
                                    tableTime4.setVisibility(View.VISIBLE);
                                    final long time = getAllTime();

                                    tableStatus4.setText("Подготовка");
                                    new CountDownTimer(time, 1000) {
                                        long countTime = 0L;

                                        @Override
                                        public void onTick(long l) {

                                            if (tableStatus4.getText() != "Занят" && tableStatus4.getText() != "Свободен") {
                                                countTime += 1000;
                                                tableTime4.setText(String.format(Locale.getDefault(), "%02d:%02d", (l / 60000), (l % 60000 / 1000)));
                                            } else {
                                                cancel();
                                                String readyLineLog = String.format(Locale.getDefault(), "%s | %s | %s | Статус:СТОЛ ЗАНЯТ | Время за которое сделан кальян %02d:%02d\n", getCurrentDateAndTime(), roomTwo, tab4, (countTime / 60000), (countTime % 60000 / 1000));
                                                WriteLogFIle(readyLineLog);
                                                tableTime4.setTextColor(Color.BLACK);
                                                Log.i("time", String.format(Locale.getDefault(), "Время за которое сделан кальян %02d:%02d", (countTime / 60000), (countTime % 60000 / 1000)));

                                            }

                                        }

                                        @Override
                                        public void onFinish() {
                                            new CountDownTimer(Integer.MAX_VALUE, 1000) {
                                                int s = 0;
                                                int m = 0;

                                                @Override
                                                public void onTick(long l) {
                                                    tableTime4.setTextColor(Color.RED);
                                                    if (tableStatus4.getText() != "Занят" && tableStatus4.getText() != "Свободен") {
                                                        countTime += 1000;
                                                        s += 1;
                                                        if (s > 59) {
                                                            m += 1;
                                                            s = 0;
                                                        }
                                                        tableTime4.setText(String.format(Locale.getDefault(), "%02d:%02d", m, s));
                                                    } else {
                                                        cancel();
                                                        String readyLineLog = String.format(Locale.getDefault(), "%s | %s | %s | Статус:СТОЛ ЗАНЯТ | Время за которое сделан кальян %02d:%02d\n", getCurrentDateAndTime(), roomTwo, tab4, (countTime / 60000), (countTime % 60000 / 1000));
                                                        WriteLogFIle(readyLineLog);
                                                        tableTime4.setTextColor(Color.BLACK);
                                                        Log.i("time", String.format(Locale.getDefault(), "Время за которое сделан кальян %02d:%02d", (countTime / 60000), (countTime % 60000 / 1000)));

                                                    }


                                                }

                                                @Override
                                                public void onFinish() {

                                                }
                                            }.start();
                                        }
                                    }.start();


                                    break;
                                case "Подготовка":
                                    AlertDialog.Builder builder2 = new AlertDialog.Builder(getContext());
                                    builder2.setTitle("Стол 4")
                                            .setMessage("Кальян готов?")
                                            .setCancelable(false)
                                            .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                    tableStatus4.setText("Занят");
                                                    tableTime4.setVisibility(View.INVISIBLE);
                                                }
                                            })
                                            .setNegativeButton("Нет",
                                                    new DialogInterface.OnClickListener() {
                                                        public void onClick(DialogInterface dialog, int id) {
                                                            dialog.cancel();
                                                        }
                                                    });
                                    AlertDialog alert2 = builder2.create();
                                    alert2.show();


                                    break;
                                case "Занят":
                                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                    builder.setTitle("Стол 4")
                                            .setMessage("Стол свободен?")
                                            .setCancelable(false)
                                            .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                    tableStatus4.setText("Свободен");
                                                    tableTime4.setVisibility(View.INVISIBLE);
                                                    if (tableStatus4.getText().toString().equals("Свободен")) {
                                                        String LineLog = String.format("%s | %s | %s | Статус:СТОЛ СВОБОДЕН\n", getCurrentDateAndTime(), roomTwo, tab4);
                                                        WriteLogFIle(LineLog);
                                                    }
                                                }
                                            })
                                            .setNegativeButton("Нет",
                                                    new DialogInterface.OnClickListener() {
                                                        public void onClick(DialogInterface dialog, int id) {
                                                            dialog.cancel();
                                                        }
                                                    });
                                    AlertDialog alert = builder.create();
                                    alert.show();

                                    break;
                            }
                            break;
                        case R.id.table5:
                            switch (tableStatus5.getText().toString()) {

                                case "Свободен":
                                    String freeLineLog = String.format("%s | %s | %s | Статус:ПОДГОТОВКА КАЛЬЯНА\n", getCurrentDateAndTime(), roomTwo, tab5);
                                    WriteLogFIle(freeLineLog);
                                    tableTime5.setVisibility(View.VISIBLE);
                                    final long time = getAllTime();

                                    tableStatus5.setText("Подготовка");
                                    new CountDownTimer(time, 1000) {
                                        long countTime = 0L;

                                        @Override
                                        public void onTick(long l) {

                                            if (tableStatus5.getText() != "Занят" && tableStatus5.getText() != "Свободен") {
                                                countTime += 1000;
                                                tableTime5.setText(String.format(Locale.getDefault(), "%02d:%02d", (l / 60000), (l % 60000 / 1000)));
                                            } else {
                                                cancel();
                                                String readyLineLog = String.format(Locale.getDefault(), "%s | %s | %s | Статус:СТОЛ ЗАНЯТ | Время за которое сделан кальян %02d:%02d\n", getCurrentDateAndTime(), roomTwo, tab5, (countTime / 60000), (countTime % 60000 / 1000));
                                                WriteLogFIle(readyLineLog);
                                                tableTime5.setTextColor(Color.BLACK);
                                                Log.i("time", String.format(Locale.getDefault(), "Время за которое сделан кальян %02d:%02d", (countTime / 60000), (countTime % 60000 / 1000)));

                                            }

                                        }

                                        @Override
                                        public void onFinish() {
                                            new CountDownTimer(Integer.MAX_VALUE, 1000) {
                                                int s = 0;
                                                int m = 0;

                                                @Override
                                                public void onTick(long l) {
                                                    tableTime5.setTextColor(Color.RED);
                                                    if (tableStatus5.getText() != "Занят" && tableStatus5.getText() != "Свободен") {
                                                        countTime += 1000;
                                                        s += 1;
                                                        if (s > 59) {
                                                            m += 1;
                                                            s = 0;
                                                        }
                                                        tableTime5.setText(String.format(Locale.getDefault(), "%02d:%02d", m, s));
                                                    } else {
                                                        cancel();
                                                        String readyLineLog = String.format(Locale.getDefault(), "%s | %s | %s | Статус:СТОЛ ЗАНЯТ | Время за которое сделан кальян %02d:%02d\n", getCurrentDateAndTime(), roomTwo, tab5, (countTime / 60000), (countTime % 60000 / 1000));
                                                        WriteLogFIle(readyLineLog);
                                                        tableTime5.setTextColor(Color.BLACK);
                                                        Log.i("time", String.format(Locale.getDefault(), "Время за которое сделан кальян %02d:%02d", (countTime / 60000), (countTime % 60000 / 1000)));

                                                    }


                                                }

                                                @Override
                                                public void onFinish() {

                                                }
                                            }.start();
                                        }
                                    }.start();


                                    break;
                                case "Подготовка":
                                    AlertDialog.Builder builder2 = new AlertDialog.Builder(getContext());
                                    builder2.setTitle("Стол 5")
                                            .setMessage("Кальян готов?")
                                            .setCancelable(false)
                                            .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                    tableStatus5.setText("Занят");
                                                    tableTime5.setVisibility(View.INVISIBLE);
                                                }
                                            })
                                            .setNegativeButton("Нет",
                                                    new DialogInterface.OnClickListener() {
                                                        public void onClick(DialogInterface dialog, int id) {
                                                            dialog.cancel();
                                                        }
                                                    });
                                    AlertDialog alert2 = builder2.create();
                                    alert2.show();


                                    break;
                                case "Занят":
                                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                    builder.setTitle("Стол 5")
                                            .setMessage("Стол свободен?")
                                            .setCancelable(false)
                                            .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                    tableStatus5.setText("Свободен");
                                                    tableTime5.setVisibility(View.INVISIBLE);
                                                    if (tableStatus5.getText().toString().equals("Свободен")) {
                                                        String LineLog = String.format("%s | %s | %s | Статус:СТОЛ СВОБОДЕН\n", getCurrentDateAndTime(), roomTwo, tab5);
                                                        WriteLogFIle(LineLog);
                                                    }
                                                }
                                            })
                                            .setNegativeButton("Нет",
                                                    new DialogInterface.OnClickListener() {
                                                        public void onClick(DialogInterface dialog, int id) {
                                                            dialog.cancel();
                                                        }
                                                    });
                                    AlertDialog alert = builder.create();
                                    alert.show();

                                    break;
                            }
                            break;
                        case R.id.table6:
                            switch (tableStatus6.getText().toString()) {

                                case "Свободен":
                                    String freeLineLog = String.format("%s | %s | %s | Статус:ПОДГОТОВКА КАЛЬЯНА\n", getCurrentDateAndTime(), roomTwo, tab6);
                                    WriteLogFIle(freeLineLog);
                                    tableTime6.setVisibility(View.VISIBLE);
                                    final long time = getAllTime();

                                    tableStatus6.setText("Подготовка");
                                    new CountDownTimer(time, 1000) {
                                        long countTime = 0L;

                                        @Override
                                        public void onTick(long l) {

                                            if (tableStatus6.getText() != "Занят" && tableStatus6.getText() != "Свободен") {
                                                countTime += 1000;
                                                tableTime6.setText(String.format(Locale.getDefault(), "%02d:%02d", (l / 60000), (l % 60000 / 1000)));
                                            } else {
                                                cancel();
                                                String readyLineLog = String.format(Locale.getDefault(), "%s | %s | %s | Статус:СТОЛ ЗАНЯТ | Время за которое сделан кальян %02d:%02d\n", getCurrentDateAndTime(), roomTwo, tab6, (countTime / 60000), (countTime % 60000 / 1000));
                                                WriteLogFIle(readyLineLog);
                                                tableTime6.setTextColor(Color.BLACK);
                                                Log.i("time", String.format(Locale.getDefault(), "Время за которое сделан кальян %02d:%02d", (countTime / 60000), (countTime % 60000 / 1000)));

                                            }

                                        }

                                        @Override
                                        public void onFinish() {
                                            new CountDownTimer(Integer.MAX_VALUE, 1000) {
                                                int s = 0;
                                                int m = 0;

                                                @Override
                                                public void onTick(long l) {
                                                    tableTime6.setTextColor(Color.RED);
                                                    if (tableStatus6.getText() != "Занят" && tableStatus6.getText() != "Свободен") {
                                                        countTime += 1000;
                                                        s += 1;
                                                        if (s > 59) {
                                                            m += 1;
                                                            s = 0;
                                                        }
                                                        tableTime6.setText(String.format(Locale.getDefault(), "%02d:%02d", m, s));
                                                    } else {
                                                        cancel();
                                                        String readyLineLog = String.format(Locale.getDefault(), "%s | %s | %s | Статус:СТОЛ ЗАНЯТ | Время за которое сделан кальян %02d:%02d\n", getCurrentDateAndTime(), roomTwo, tab6, (countTime / 60000), (countTime % 60000 / 1000));
                                                        WriteLogFIle(readyLineLog);
                                                        tableTime6.setTextColor(Color.BLACK);
                                                        Log.i("time", String.format(Locale.getDefault(), "Время за которое сделан кальян %02d:%02d", (countTime / 60000), (countTime % 60000 / 1000)));

                                                    }


                                                }

                                                @Override
                                                public void onFinish() {

                                                }
                                            }.start();
                                        }
                                    }.start();


                                    break;
                                case "Подготовка":
                                    AlertDialog.Builder builder2 = new AlertDialog.Builder(getContext());
                                    builder2.setTitle("Стол 6")
                                            .setMessage("Кальян готов?")
                                            .setCancelable(false)
                                            .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                    tableStatus6.setText("Занят");
                                                    tableTime6.setVisibility(View.INVISIBLE);
                                                }
                                            })
                                            .setNegativeButton("Нет",
                                                    new DialogInterface.OnClickListener() {
                                                        public void onClick(DialogInterface dialog, int id) {
                                                            dialog.cancel();
                                                        }
                                                    });
                                    AlertDialog alert2 = builder2.create();
                                    alert2.show();


                                    break;
                                case "Занят":
                                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                    builder.setTitle("Стол 6")
                                            .setMessage("Стол свободен?")
                                            .setCancelable(false)
                                            .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                    tableStatus6.setText("Свободен");
                                                    tableTime6.setVisibility(View.INVISIBLE);
                                                    if (tableStatus6.getText().toString().equals("Свободен")) {
                                                        String LineLog = String.format("%s | %s | %s | Статус:СТОЛ СВОБОДЕН\n", getCurrentDateAndTime(), roomTwo, tab6);
                                                        WriteLogFIle(LineLog);
                                                    }
                                                }
                                            })
                                            .setNegativeButton("Нет",
                                                    new DialogInterface.OnClickListener() {
                                                        public void onClick(DialogInterface dialog, int id) {
                                                            dialog.cancel();
                                                        }
                                                    });
                                    AlertDialog alert = builder.create();
                                    alert.show();

                                    break;
                            }
                            break;
                        case R.id.table7:
                            switch (tableStatus7.getText().toString()) {

                                case "Свободен":
                                    String freeLineLog = String.format("%s | %s | %s | Статус:ПОДГОТОВКА КАЛЬЯНА\n", getCurrentDateAndTime(), roomTwo, tab7);
                                    WriteLogFIle(freeLineLog);
                                    tableTime7.setVisibility(View.VISIBLE);
                                    final long time = getAllTime();

                                    tableStatus7.setText("Подготовка");
                                    new CountDownTimer(time, 1000) {
                                        long countTime = 0L;

                                        @Override
                                        public void onTick(long l) {

                                            if (tableStatus7.getText() != "Занят" && tableStatus7.getText() != "Свободен") {
                                                countTime += 1000;
                                                tableTime7.setText(String.format(Locale.getDefault(), "%02d:%02d", (l / 60000), (l % 60000 / 1000)));
                                            } else {
                                                cancel();
                                                String readyLineLog = String.format(Locale.getDefault(), "%s | %s | %s | Статус:СТОЛ ЗАНЯТ | Время за которое сделан кальян %02d:%02d\n", getCurrentDateAndTime(), roomTwo, tab7, (countTime / 60000), (countTime % 60000 / 1000));
                                                WriteLogFIle(readyLineLog);
                                                tableTime7.setTextColor(Color.BLACK);
                                                Log.i("time", String.format(Locale.getDefault(), "Время за которое сделан кальян %02d:%02d", (countTime / 60000), (countTime % 60000 / 1000)));

                                            }

                                        }

                                        @Override
                                        public void onFinish() {
                                            new CountDownTimer(Integer.MAX_VALUE, 1000) {
                                                int s = 0;
                                                int m = 0;

                                                @Override
                                                public void onTick(long l) {
                                                    tableTime7.setTextColor(Color.RED);
                                                    if (tableStatus7.getText() != "Занят" && tableStatus7.getText() != "Свободен") {
                                                        countTime += 1000;
                                                        s += 1;
                                                        if (s > 59) {
                                                            m += 1;
                                                            s = 0;
                                                        }
                                                        tableTime7.setText(String.format(Locale.getDefault(), "%02d:%02d", m, s));
                                                    } else {
                                                        cancel();
                                                        String readyLineLog = String.format(Locale.getDefault(), "%s | %s | %s | Статус:СТОЛ ЗАНЯТ | Время за которое сделан кальян %02d:%02d\n", getCurrentDateAndTime(), roomTwo, tab7, (countTime / 60000), (countTime % 60000 / 1000));
                                                        WriteLogFIle(readyLineLog);
                                                        tableTime7.setTextColor(Color.BLACK);
                                                        Log.i("time", String.format(Locale.getDefault(), "Время за которое сделан кальян %02d:%02d", (countTime / 60000), (countTime % 60000 / 1000)));

                                                    }


                                                }

                                                @Override
                                                public void onFinish() {

                                                }
                                            }.start();
                                        }
                                    }.start();


                                    break;
                                case "Подготовка":
                                    AlertDialog.Builder builder2 = new AlertDialog.Builder(getContext());
                                    builder2.setTitle("Стол 7")
                                            .setMessage("Кальян готов?")
                                            .setCancelable(false)
                                            .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                    tableStatus7.setText("Занят");
                                                    tableTime7.setVisibility(View.INVISIBLE);
                                                }
                                            })
                                            .setNegativeButton("Нет",
                                                    new DialogInterface.OnClickListener() {
                                                        public void onClick(DialogInterface dialog, int id) {
                                                            dialog.cancel();
                                                        }
                                                    });
                                    AlertDialog alert2 = builder2.create();
                                    alert2.show();


                                    break;
                                case "Занят":
                                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                    builder.setTitle("Стол 7")
                                            .setMessage("Стол свободен?")
                                            .setCancelable(false)
                                            .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                    tableStatus7.setText("Свободен");
                                                    tableTime7.setVisibility(View.INVISIBLE);
                                                    if (tableStatus7.getText().toString().equals("Свободен")) {
                                                        String LineLog = String.format("%s | %s | %s | Статус:СТОЛ СВОБОДЕН\n", getCurrentDateAndTime(), roomTwo, tab7);
                                                        WriteLogFIle(LineLog);
                                                    }
                                                }
                                            })
                                            .setNegativeButton("Нет",
                                                    new DialogInterface.OnClickListener() {
                                                        public void onClick(DialogInterface dialog, int id) {
                                                            dialog.cancel();
                                                        }
                                                    });
                                    AlertDialog alert = builder.create();
                                    alert.show();

                                    break;
                            }
                            break;
                        case R.id.table8:
                            switch (tableStatus8.getText().toString()) {

                                case "Свободен":
                                    String freeLineLog = String.format("%s | %s | %s | Статус:ПОДГОТОВКА КАЛЬЯНА\n", getCurrentDateAndTime(), roomTwo, tab8);
                                    WriteLogFIle(freeLineLog);
                                    tableTime8.setVisibility(View.VISIBLE);
                                    final long time = getAllTime();

                                    tableStatus8.setText("Подготовка");
                                    new CountDownTimer(time, 1000) {
                                        long countTime = 0L;

                                        @Override
                                        public void onTick(long l) {

                                            if (tableStatus8.getText() != "Занят" && tableStatus8.getText() != "Свободен") {
                                                countTime += 1000;
                                                tableTime8.setText(String.format(Locale.getDefault(), "%02d:%02d", (l / 60000), (l % 60000 / 1000)));
                                            } else {
                                                cancel();
                                                String readyLineLog = String.format(Locale.getDefault(), "%s | %s | %s | Статус:СТОЛ ЗАНЯТ | Время за которое сделан кальян %02d:%02d\n", getCurrentDateAndTime(), roomTwo, tab8, (countTime / 60000), (countTime % 60000 / 1000));
                                                WriteLogFIle(readyLineLog);
                                                tableTime8.setTextColor(Color.BLACK);
                                                Log.i("time", String.format(Locale.getDefault(), "Время за которое сделан кальян %02d:%02d", (countTime / 60000), (countTime % 60000 / 1000)));

                                            }

                                        }

                                        @Override
                                        public void onFinish() {
                                            new CountDownTimer(Integer.MAX_VALUE, 1000) {
                                                int s = 0;
                                                int m = 0;

                                                @Override
                                                public void onTick(long l) {
                                                    tableTime8.setTextColor(Color.RED);
                                                    if (tableStatus8.getText() != "Занят" && tableStatus8.getText() != "Свободен") {
                                                        countTime += 1000;
                                                        s += 1;
                                                        if (s > 59) {
                                                            m += 1;
                                                            s = 0;
                                                        }
                                                        tableTime8.setText(String.format(Locale.getDefault(), "%02d:%02d", m, s));
                                                    } else {
                                                        cancel();
                                                        String readyLineLog = String.format(Locale.getDefault(), "%s | %s | %s | Статус:СТОЛ ЗАНЯТ | Время за которое сделан кальян %02d:%02d\n", getCurrentDateAndTime(), roomTwo, tab8, (countTime / 60000), (countTime % 60000 / 1000));
                                                        WriteLogFIle(readyLineLog);
                                                        tableTime8.setTextColor(Color.BLACK);
                                                        Log.i("time", String.format(Locale.getDefault(), "Время за которое сделан кальян %02d:%02d", (countTime / 60000), (countTime % 60000 / 1000)));

                                                    }


                                                }

                                                @Override
                                                public void onFinish() {

                                                }
                                            }.start();
                                        }
                                    }.start();


                                    break;
                                case "Подготовка":
                                    AlertDialog.Builder builder2 = new AlertDialog.Builder(getContext());
                                    builder2.setTitle("Стол 8")
                                            .setMessage("Кальян готов?")
                                            .setCancelable(false)
                                            .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                    tableStatus8.setText("Занят");
                                                    tableTime8.setVisibility(View.INVISIBLE);
                                                }
                                            })
                                            .setNegativeButton("Нет",
                                                    new DialogInterface.OnClickListener() {
                                                        public void onClick(DialogInterface dialog, int id) {
                                                            dialog.cancel();
                                                        }
                                                    });
                                    AlertDialog alert2 = builder2.create();
                                    alert2.show();


                                    break;
                                case "Занят":
                                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                    builder.setTitle("Стол 8")
                                            .setMessage("Стол свободен?")
                                            .setCancelable(false)
                                            .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                    tableStatus8.setText("Свободен");
                                                    tableTime8.setVisibility(View.INVISIBLE);
                                                    if (tableStatus8.getText().toString().equals("Свободен")) {
                                                        String LineLog = String.format("%s | %s | %s | Статус:СТОЛ СВОБОДЕН\n", getCurrentDateAndTime(), roomTwo, tab8);
                                                        WriteLogFIle(LineLog);
                                                    }
                                                }
                                            })
                                            .setNegativeButton("Нет",
                                                    new DialogInterface.OnClickListener() {
                                                        public void onClick(DialogInterface dialog, int id) {
                                                            dialog.cancel();
                                                        }
                                                    });
                                    AlertDialog alert = builder.create();
                                    alert.show();

                                    break;
                            }
                            break;
                        case R.id.table9:
                            switch (tableStatus9.getText().toString()) {

                                case "Свободен":
                                    String freeLineLog = String.format("%s | %s | %s | Статус:ПОДГОТОВКА КАЛЬЯНА\n", getCurrentDateAndTime(), roomTwo, tab9);
                                    WriteLogFIle(freeLineLog);
                                    tableTime9.setVisibility(View.VISIBLE);
                                    final long time = getAllTime();

                                    tableStatus9.setText("Подготовка");
                                    new CountDownTimer(time, 1000) {
                                        long countTime = 0L;

                                        @Override
                                        public void onTick(long l) {

                                            if (tableStatus9.getText() != "Занят" && tableStatus9.getText() != "Свободен") {
                                                countTime += 1000;
                                                tableTime9.setText(String.format(Locale.getDefault(), "%02d:%02d", (l / 60000), (l % 60000 / 1000)));
                                            } else {
                                                cancel();
                                                String readyLineLog = String.format(Locale.getDefault(), "%s | %s | %s | Статус:СТОЛ ЗАНЯТ | Время за которое сделан кальян %02d:%02d\n", getCurrentDateAndTime(), roomTwo, tab9, (countTime / 60000), (countTime % 60000 / 1000));
                                                WriteLogFIle(readyLineLog);
                                                tableTime9.setTextColor(Color.BLACK);
                                                Log.i("time", String.format(Locale.getDefault(), "Время за которое сделан кальян %02d:%02d", (countTime / 60000), (countTime % 60000 / 1000)));

                                            }

                                        }

                                        @Override
                                        public void onFinish() {
                                            new CountDownTimer(Integer.MAX_VALUE, 1000) {
                                                int s = 0;
                                                int m = 0;

                                                @Override
                                                public void onTick(long l) {
                                                    tableTime9.setTextColor(Color.RED);
                                                    if (tableStatus9.getText() != "Занят" && tableStatus9.getText() != "Свободен") {
                                                        countTime += 1000;
                                                        s += 1;
                                                        if (s > 59) {
                                                            m += 1;
                                                            s = 0;
                                                        }
                                                        tableTime9.setText(String.format(Locale.getDefault(), "%02d:%02d", m, s));
                                                    } else {
                                                        cancel();
                                                        String readyLineLog = String.format(Locale.getDefault(), "%s | %s | %s | Статус:СТОЛ ЗАНЯТ | Время за которое сделан кальян %02d:%02d\n", getCurrentDateAndTime(), roomTwo, tab9, (countTime / 60000), (countTime % 60000 / 1000));
                                                        WriteLogFIle(readyLineLog);
                                                        tableTime9.setTextColor(Color.BLACK);
                                                        Log.i("time", String.format(Locale.getDefault(), "Время за которое сделан кальян %02d:%02d", (countTime / 60000), (countTime % 60000 / 1000)));

                                                    }


                                                }

                                                @Override
                                                public void onFinish() {

                                                }
                                            }.start();
                                        }
                                    }.start();


                                    break;
                                case "Подготовка":
                                    AlertDialog.Builder builder2 = new AlertDialog.Builder(getContext());
                                    builder2.setTitle("Стол 9")
                                            .setMessage("Кальян готов?")
                                            .setCancelable(false)
                                            .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                    tableStatus9.setText("Занят");
                                                    tableTime9.setVisibility(View.INVISIBLE);
                                                }
                                            })
                                            .setNegativeButton("Нет",
                                                    new DialogInterface.OnClickListener() {
                                                        public void onClick(DialogInterface dialog, int id) {
                                                            dialog.cancel();
                                                        }
                                                    });
                                    AlertDialog alert2 = builder2.create();
                                    alert2.show();


                                    break;
                                case "Занят":
                                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                    builder.setTitle("Стол 9")
                                            .setMessage("Стол свободен?")
                                            .setCancelable(false)
                                            .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                    tableStatus9.setText("Свободен");
                                                    tableTime9.setVisibility(View.INVISIBLE);
                                                    if (tableStatus9.getText().toString().equals("Свободен")) {
                                                        String LineLog = String.format("%s | %s | %s | Статус:СТОЛ СВОБОДЕН\n", getCurrentDateAndTime(), roomTwo, tab9);
                                                        WriteLogFIle(LineLog);
                                                    }
                                                }
                                            })
                                            .setNegativeButton("Нет",
                                                    new DialogInterface.OnClickListener() {
                                                        public void onClick(DialogInterface dialog, int id) {
                                                            dialog.cancel();
                                                        }
                                                    });
                                    AlertDialog alert = builder.create();
                                    alert.show();

                                    break;
                            }
                            break;
                        case R.id.table10:
                            switch (tableStatus10.getText().toString()) {

                                case "Свободен":
                                    String freeLineLog = String.format("%s | %s | %s | Статус:ПОДГОТОВКА КАЛЬЯНА\n", getCurrentDateAndTime(), roomTwo, tab10);
                                    WriteLogFIle(freeLineLog);
                                    tableTime10.setVisibility(View.VISIBLE);
                                    final long time = getAllTime();

                                    tableStatus10.setText("Подготовка");
                                    new CountDownTimer(time, 1000) {
                                        long countTime = 0L;

                                        @Override
                                        public void onTick(long l) {

                                            if (tableStatus10.getText() != "Занят" && tableStatus10.getText() != "Свободен") {
                                                countTime += 1000;
                                                tableTime10.setText(String.format(Locale.getDefault(), "%02d:%02d", (l / 60000), (l % 60000 / 1000)));
                                            } else {
                                                cancel();
                                                Context context;
                                                String readyLineLog = String.format(Locale.getDefault(), "%s | %s | %s | Статус:СТОЛ ЗАНЯТ | Время за которое сделан кальян %02d:%02d\n", getCurrentDateAndTime(), roomTwo, tab10, (countTime / 60000), (countTime % 60000 / 1000));
                                                WriteLogFIle(readyLineLog);
                                                tableTime10.setTextColor(Color.BLACK);
                                                Log.i("time", String.format(Locale.getDefault(), "Время за которое сделан кальян %02d:%02d", (countTime / 60000), (countTime % 60000 / 1000)));

                                            }

                                        }

                                        @Override
                                        public void onFinish() {
                                            new CountDownTimer(Integer.MAX_VALUE, 1000) {
                                                int s = 0;
                                                int m = 0;

                                                @Override
                                                public void onTick(long l) {
                                                    tableTime10.setTextColor(Color.RED);
                                                    if (tableStatus10.getText() != "Занят" && tableStatus10.getText() != "Свободен") {
                                                        countTime += 1000;
                                                        s += 1;
                                                        if (s > 59) {
                                                            m += 1;
                                                            s = 0;
                                                        }
                                                        tableTime10.setText(String.format(Locale.getDefault(), "%02d:%02d", m, s));
                                                    } else {
                                                        cancel();
                                                        String readyLineLog = String.format(Locale.getDefault(), "%s | %s | %s | Статус:СТОЛ ЗАНЯТ | Время за которое сделан кальян %02d:%02d\n", getCurrentDateAndTime(), roomTwo, tab10, (countTime / 60000), (countTime % 60000 / 1000));
                                                        WriteLogFIle(readyLineLog);
                                                        tableTime10.setTextColor(Color.BLACK);
                                                        Log.i("time", String.format(Locale.getDefault(), "Время за которое сделан кальян %02d:%02d", (countTime / 60000), (countTime % 60000 / 1000)));

                                                    }


                                                }

                                                @Override
                                                public void onFinish() {

                                                }
                                            }.start();
                                        }
                                    }.start();


                                    break;
                                case "Подготовка":
                                    AlertDialog.Builder builder2 = new AlertDialog.Builder(getContext());
                                    builder2.setTitle("Стол 10")
                                            .setMessage("Кальян готов?")
                                            .setCancelable(false)
                                            .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                    tableStatus10.setText("Занят");
                                                    tableTime10.setVisibility(View.INVISIBLE);
                                                }
                                            })
                                            .setNegativeButton("Нет",
                                                    new DialogInterface.OnClickListener() {
                                                        public void onClick(DialogInterface dialog, int id) {
                                                            dialog.cancel();
                                                        }
                                                    });
                                    AlertDialog alert2 = builder2.create();
                                    alert2.show();


                                    break;
                                case "Занят":
                                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                    builder.setTitle("Стол 10")
                                            .setMessage("Стол свободен?")
                                            .setCancelable(false)
                                            .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                    tableStatus10.setText("Свободен");
                                                    tableTime10.setVisibility(View.INVISIBLE);
                                                    if (tableStatus10.getText().toString().equals("Свободен")) {
                                                        String LineLog = String.format("%s | %s | %s | Статус:СТОЛ СВОБОДЕН\n", getCurrentDateAndTime(), roomTwo, tab10);
                                                        WriteLogFIle(LineLog);
                                                    }
                                                }
                                            })
                                            .setNegativeButton("Нет",
                                                    new DialogInterface.OnClickListener() {
                                                        public void onClick(DialogInterface dialog, int id) {
                                                            dialog.cancel();
                                                        }
                                                    });
                                    AlertDialog alert = builder.create();
                                    alert.show();

                                    break;
                            }
                            break;
                    }
                }
            });
        }

        return rootView;
    }

    private String getCurrentDateAndTime() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat dateformat = new SimpleDateFormat("dd.MM.yyyy | HH:mm:ss",Locale.getDefault());
        return dateformat.format(c.getTime());
    }

    private void WriteLogFIle(String datetime) {
        try {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(getActivity().openFileOutput("log.txt", Context.MODE_APPEND)));
            bw.write(datetime);
            // закрываем поток
            bw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
