package com.example.copyclip;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Shared shared;
    private MainService mSensorService;
    Intent mServiceIntent;
    Context ctx;

    public Context getCtx() {
        return ctx;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ctx = this;
        mSensorService = new MainService(getCtx());
        mServiceIntent = new Intent(getCtx(), mSensorService.getClass());

        if (!isMyServiceRunning(mSensorService.getClass())) {
            startService(mServiceIntent);
        }

        this.shared = new Shared(getApplicationContext());

        Toolbar toolbar = findViewById(R.id.my_toolbar);
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        ViewPager viewPager = findViewById(R.id.pager);

        if (toolbar != null) {
            setSupportActionBar(toolbar);
            toolbar.setTitle("Evergreen");
        }

        viewPager.setAdapter(new SectionPagerAdapter(getSupportFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);

        findViewById(R.id.settings).setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                Log.i("isMyServiceRunning?", true + "");
                return true;
            }
        }

        Log.i("isMyServiceRunning?", false + "");
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.settings:
                Intent settingsIntent = new Intent(this, Settings.class);
                startActivity(settingsIntent);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        Toolbar toolbar = findViewById(R.id.my_toolbar);
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        ViewPager viewPager = findViewById(R.id.pager);

        if (toolbar != null) {
            setSupportActionBar(toolbar);
            toolbar.setTitle("Evergreen");
        }

        viewPager.setAdapter(new SectionPagerAdapter(getSupportFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);

        findViewById(R.id.settings).setOnClickListener(this);
    }

    public class SectionPagerAdapter extends FragmentPagerAdapter {
        public SectionPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new fragment_buffer();
                default:
                    return new fragment_buffer();
            }
        }

        @Override
        public int getCount() {
            return 1;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Recent";
                default:
                    return "Recent";
            }
        }
    }
}
