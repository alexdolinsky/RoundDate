package ru.alexanderdolinsky.rounddate.notifications;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.text.DateFormat;
import java.util.Locale;
import java.util.Objects;

import ru.alexanderdolinsky.rounddate.R;
import ru.alexanderdolinsky.rounddate.activities.MainActivity;
import ru.alexanderdolinsky.rounddate.data.NotifyDate;
import ru.alexanderdolinsky.rounddate.data.RoundDate;
import ru.alexanderdolinsky.rounddate.db.DatabaseAdapter;


/**
 * Created by Alexsvet on 15.07.2017.
 * Сервис рассылки уведомлений
 */

public class NotificationService extends Service {

    public static final long MINUTE_30 = 30 * 60 * 1000L;
    private static final int NOTIFICATION_ID = 0;
    private static final String CHANNEL_ID = "My channel ID";


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }



    @Override
    public void onCreate() {
        super.onCreate();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String CHANNEL_ID = "foreground_channel";
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);

            ((NotificationManager) Objects.requireNonNull(getSystemService(Context.NOTIFICATION_SERVICE))).createNotificationChannel(channel);

            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setContentTitle("")
                    .setContentText("");
            Notification notification = builder.build();

            startForeground(1, notification);
        }


        // открываем подключение
        DatabaseAdapter adapter = new DatabaseAdapter(this);
        adapter.open();

        // получаем данные следующего уведомления

        NotifyDate notifyDate = adapter.getNextNotifyDate();

        if (notifyDate != null) {
            if ((System.currentTimeMillis() - notifyDate.getNotifyDateAndTime() < MINUTE_30) &&
                    (System.currentTimeMillis() - notifyDate.getNotifyDateAndTime() >= 0)) {
                setNotification(notifyDate);
                adapter.deleteNotifyDate(notifyDate.getId());
                notifyDate = adapter.getNextNotifyDate();
            }

            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            Intent i = new Intent(this, NotificationReceiver.class);

            PendingIntent pending = PendingIntent.getBroadcast(this, 0, i, PendingIntent.FLAG_CANCEL_CURRENT);
            long t = notifyDate.getNotifyDateAndTime();
            if (alarmManager != null) {
                alarmManager.set(AlarmManager.RTC_WAKEUP, t, pending);
            }
            Log.d("MyLog", "Следущий будильник будет - " + DateFormat.getDateInstance(DateFormat.LONG, Locale.getDefault()).format(notifyDate.getNotifyDateAndTime()) + " " // дата
                    + DateFormat.getTimeInstance(DateFormat.SHORT, Locale.getDefault()).format(notifyDate.getNotifyDateAndTime()));
        }
        adapter.close();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            stopForeground(true);
        }
        stopSelf();
    }



    private void setNotification(NotifyDate notifyDate) {
        Context context = getApplicationContext();
        Intent notificationIntent = new Intent(context, MainActivity.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent contentIntent = PendingIntent.getActivity(context,
                0, notificationIntent,
                PendingIntent.FLAG_CANCEL_CURRENT);

        NotificationCompat.Builder builder;
        NotificationManager notificationManager;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // для старших версий
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }

            builder = new NotificationCompat.Builder(context, CHANNEL_ID);


        } else {
            // для версий младше API 26
            notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            builder = new NotificationCompat.Builder(context);
        }



            builder.setContentIntent(contentIntent)
                    .setSmallIcon(R.drawable.ic_action_name)
                    // большая картинка
                    .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher_round))
                    .setWhen(System.currentTimeMillis())
                    .setAutoCancel(true)
                    .setTicker(getString(R.string.soon_round_date))
                    .setContentTitle(DateFormat.getDateInstance(DateFormat.SHORT, Locale.getDefault()).format(notifyDate.getDateAndTime().getTime()) +
                            " " + getString(R.string.will) + " " +
                            String.format(Locale.getDefault(), "%,d %s", notifyDate.getValueOf(), RoundDate.getUnit(context, notifyDate.getValueOf(), notifyDate.getUnit())))
                    .setContentText(getString(R.string.since_the_event) + ": " + notifyDate.getNameEvent()); // Текст уведомления

            Notification notification = builder.build();


            //startForeground(101, notification);

        if (notificationManager != null) {
            notificationManager.notify(NOTIFICATION_ID, notification);
        }

    }
}
