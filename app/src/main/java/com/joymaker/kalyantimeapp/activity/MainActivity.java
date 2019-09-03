package com.joymaker.kalyantimeapp.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.joymaker.kalyantimeapp.R;
import com.joymaker.kalyantimeapp.adapter.ViewPagerAdapter;
import com.joymaker.kalyantimeapp.dialog.DialogCreatePassword;
import com.joymaker.kalyantimeapp.dialog.DialogPassword;
import com.joymaker.kalyantimeapp.dialog.DialogPasswordSetTime;
import com.joymaker.kalyantimeapp.fragmentsroom.FirstRoomTablesFragment;
import com.joymaker.kalyantimeapp.fragmentsroom.SecondRoomTablesFragment;

public class MainActivity extends AppCompatActivity implements Toolbar.OnMenuItemClickListener {

    private Context context;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.menu_main2);
        toolbar.setOnMenuItemClickListener(this);
        context = getApplicationContext();
        viewPager = (ViewPager) findViewById(R.id.container);
        setupViewPager(viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        sharedPreferences.edit().putString("password", "").apply();
        password = sharedPreferences.getString("password", "");

        if (password.equals("")) {
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
        getMenuInflater().inflate(R.menu.menu_main2, menu);
        return true;
    }


    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                DialogPassword dialogPassword = new DialogPassword();
                dialogPassword.show(getSupportFragmentManager(), "dsd");
                return true;
            case R.id.setTime:
                DialogPasswordSetTime dialogPasswordSetTime = new DialogPasswordSetTime();
                dialogPasswordSetTime.show(getSupportFragmentManager(), "fd");

                return true;
        }
        return false;
    }


}
