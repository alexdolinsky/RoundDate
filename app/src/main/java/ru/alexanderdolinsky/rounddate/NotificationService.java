package ru.alexanderdolinsky.rounddate;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.text.DateFormat;
import java.util.Locale;


/**
 * Created by Alexsvet on 15.07.2017.
 * Сервис рассылки уведомлений
 */

public class NotificationService extends Service {

    static final long MINUTE_30 = 30 * 60 * 1000L;
    private static final int NOTIFICATION_ID = 0;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Log.d("MyLog", "Сервис запущен");

        // открываем подключение
        DatabaseAdapter adapter = new DatabaseAdapter(this);
        adapter.open();


        // получаем данные следующего уведомления

        NotifyDate notifyDate = adapter.getNextNotifyDate();

        if (notifyDate != null) {
            if ((System.currentTimeMillis() - notifyDate.getNotifyDateAndTime() < MINUTE_30) &&
                    (System.currentTimeMillis() - notifyDate.getNotifyDateAndTime() >= 0)) {
                setNotification(notifyDate);
                Log.d("MyLog", "id = " + notifyDate.getId());
                adapter.deleteNotifyDate(notifyDate.getId());
                notifyDate = adapter.getNextNotifyDate();
                Log.d("MyLog", "id = " + notifyDate.getId());
            }

            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            Intent i = new Intent(this, NotificationReceiver.class);

            PendingIntent pending = PendingIntent.getBroadcast(this, 0, i, PendingIntent.FLAG_CANCEL_CURRENT);
            long t = notifyDate.getNotifyDateAndTime();
            alarmManager.set(AlarmManager.RTC_WAKEUP, t, pending);
            Log.d("MyLog", "Следущий будильник будет - " + DateFormat.getDateInstance(DateFormat.LONG, Locale.getDefault()).format(notifyDate.getNotifyDateAndTime()) + " " // дата
                    + DateFormat.getTimeInstance(DateFormat.SHORT, Locale.getDefault()).format(notifyDate.getNotifyDateAndTime()));

        }

        adapter.close();

        stopSelf();

        Log.d("MyLog", "Сервис остановлен");
    }

    private void setNotification(NotifyDate notifyDate) {
        Context context = getApplicationContext();
        Intent notificationIntent = new Intent(context, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(context,
                0, notificationIntent,
                PendingIntent.FLAG_CANCEL_CURRENT);

        Resources res = context.getResources();
        Notification.Builder builder = new Notification.Builder(context);

        builder.setContentIntent(contentIntent)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                // большая картинка
                .setLargeIcon(BitmapFactory.decodeResource(res, R.mipmap.ic_launcher_round))
                .setWhen(System.currentTimeMillis())
                .setAutoCancel(true)
                //.setContentTitle(res.getString(R.string.notifytitle)) // Заголовок уведомления
                .setContentTitle("29/01/2018 будет 1 000 000 000 секунд")
                //.setContentText(res.getString(R.string.notifytext))
                .setContentText("с момента события: День рождения Лизы"); // Текст уведомления

        // Notification notification = builder.getNotification(); // до API 16
        Notification notification = builder.build();

        NotificationManager notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID, notification);
    }

}
