package com.example.jujojazbase;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

public class Notification extends Service {
    public static boolean restart;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        restart = intent.getBooleanExtra("RESTART", true);
        Log.d("Restart", "NotificationService : " + restart);
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("Restart", "NotificationService : " + restart);
        if (restart) {
            Intent broadcastIntent = new Intent(this, BroadcastNotification.class);
            sendBroadcast(broadcastIntent);
        }
    }
}
