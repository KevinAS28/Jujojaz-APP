package com.example.jujojazbase;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class BroadcastNotification extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        boolean startService = intent.getBooleanExtra("STARTSERVICE", true);
        Intent notificationService = new Intent(context, Notification.class);
        if (startService) {
            context.startService(notificationService);
            Log.d("runningService", String.valueOf(isMyServiceRunning(context)));
        } else {
            context.stopService(notificationService);
            Log.d("runningService", String.valueOf(isMyServiceRunning(context)));
        }
    }

    public boolean isMyServiceRunning(Context context) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        assert manager != null;
        for (ActivityManager.RunningServiceInfo serviceInfo : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (Notification.class.getName().equals(serviceInfo.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}
