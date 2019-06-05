package com.example.copyclip;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity
    implements View.OnClickListener, fragment_buffer.OnFragmentInteractionListener, fragment_pinned.OnFragmentInteractionListener {

    private Shared shared;
    public final static int REQUEST_CODE = -1010101;

    Intent mServiceIntent;
    private MainService mSensorService;
    Context ctx;

    public Context getCtx() {
        return ctx;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        displayNotification("CopyClip", "Display Buffer");

        ctx = this;
//        setContentView(R.layout.activity_main);
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

        Log.i ("isMyServiceRunning?", false + "");
        return false;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.settings:
                Intent settingsIntent = new Intent(this, Settings.class);
                startActivity(settingsIntent);
        }
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
                case 1:
                    return new fragment_pinned();
                default:
                    return new fragment_buffer();
            }
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Recent";
                case 1:
                default:
                    return "Pinned";
            }
        }
    }

    private void getPermissionOverlay() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!android.provider.Settings.canDrawOverlays(this)) {
                Intent intent = new Intent(android.provider.Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, 101);
            } else {
                startService(new Intent(getApplicationContext(), BufferHeadService.class));
            }
        } else {
            startService(new Intent(getApplicationContext(), BufferHeadService.class));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 101: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.i("Permission", "Granted");
                    startService(new Intent(getApplicationContext(), BufferHeadService.class));
                } else {
                    Log.i("Permission", "Denied");
                }
            }
        }
    }

    public void displayNotification(String title, String message) {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_pin)
                .setContentTitle(title)
                .setContentText(message);

        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Notification n = mBuilder.build();
        n.flags |= Notification.FLAG_NO_CLEAR | Notification.FLAG_ONGOING_EVENT;
        mNotificationManager.notify(001, n);
    }
}
