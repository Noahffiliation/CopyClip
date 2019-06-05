package com.example.copyclip;

import android.app.Service;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

public class MainService extends Service {
    public MainService(Context applicationContext) {
        super();
    }

    public MainService() {

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        final ClipboardManager tester = (ClipboardManager)getSystemService(CLIPBOARD_SERVICE);
        final ClipboardManager.OnPrimaryClipChangedListener mPrimaryChangeListener
                = new ClipboardManager.OnPrimaryClipChangedListener() {
            public void onPrimaryClipChanged() {
                Log.e("SERVICE COPY", tester.getText().toString());
                Shared shared = new Shared(getApplicationContext());
                shared.updateList(getApplicationContext(), shared.bufferTag, tester.getText().toString());
            }
        };

        tester.addPrimaryClipChangedListener(mPrimaryChangeListener);
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Intent broadcastIntent = new Intent("com.evergreen.ServiceRestart.RestartSensor");
        sendBroadcast(broadcastIntent);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}