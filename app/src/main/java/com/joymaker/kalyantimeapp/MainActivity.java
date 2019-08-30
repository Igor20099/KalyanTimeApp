package com.joymaker.kalyantimeapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.joymaker.kalyantimeapp.fragments.FirstRoomTablesFragment;
import com.joymaker.kalyantimeapp.fragments.SecondRoomTablesFragment;

public class MainActivity extends AppCompatActivity implements Toolbar.OnMenuItemClickListener{


    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private String password;
    private boolean passbool;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.menu_main2);
        toolbar.setOnMenuItemClickListener(this);

        viewPager = (ViewPager) findViewById(R.id.container);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        Context context;
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        sharedPreferences.edit().putString("password","").apply();
        password = sharedPreferences.getString("password","");

        if(password.equals("")) {
            DialogCreatePassword dialogCreatePassword = new DialogCreatePassword();
            dialogCreatePassword.show(getSupportFragmentManager(), "createpass");
        }


    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new FirstRoomTablesFragment(), "ЗАЛ 1");
        adapter.addFragment(new SecondRoomTablesFragment(), "ЗАЛ 2");

        viewPager.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main2, menu);
        return true;
    }


    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                DialogPassword dialogPassword = new DialogPassword();
                dialogPassword.show(getSupportFragmentManager(),"dsd");
//                Intent intent = new Intent(this,LogActivity.class);
//                startActivity(intent);
                return true;
            case R.id.setTime:
                Context context;
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                String key;
               DialogPasswordSetTime dialogPasswordSetTime = new DialogPasswordSetTime();
               dialogPasswordSetTime.show(getSupportFragmentManager(),"fd");

                return true;
        }
        return false;
    }


}
