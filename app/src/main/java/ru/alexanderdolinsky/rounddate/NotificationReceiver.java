package ru.alexanderdolinsky.rounddate;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import static android.support.v4.content.ContextCompat.startForegroundService;


public class NotificationReceiver extends BroadcastReceiver {

    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("MyLog", "1");
        Intent service = new Intent(context, NotificationService.class);
        Log.d("MyLog", "2");
        //context.startService(service);
        startForegroundService(context, service);
    }
}
