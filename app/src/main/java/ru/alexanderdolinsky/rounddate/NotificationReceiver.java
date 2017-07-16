package ru.alexanderdolinsky.rounddate;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class NotificationReceiver extends BroadcastReceiver {

    //public static final long WEEK = 604800000L;
    public static final long WEEK = 60000L;

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent service = new Intent(context, NotificationService.class);
        context.startService(service);


    }
}
