package ru.alexanderdolinsky.rounddate;

import android.app.AlarmManager;
import android.app.PendingIntent;
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


        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(context, NotificationReceiver.class);

        PendingIntent pending = PendingIntent.getBroadcast(context, 0, i, PendingIntent.FLAG_CANCEL_CURRENT);
        long t = System.currentTimeMillis() + WEEK;
        alarmManager.set(AlarmManager.RTC_WAKEUP, t, pending);


    }
}
