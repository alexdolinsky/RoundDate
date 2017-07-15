package ru.alexanderdolinsky.rounddate;

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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by Alexsvet on 15.07.2017.
 * Сервис рассылки уведомлений
 */

public class NotificationService extends Service {

    public static final long PERIOD = 45 * 24 * 3600 * 1000L;
    //public static final long PERIOD_MONTH = 30*24*3600*1000L;
    //public static final long PERIOD_WEEK = 7*24*3600*1000L;
    //public static final long PERIOD_DAY = 24*3600*1000L;
    public static final long PERIOD_MONTH = 30 * 24 * 3600 * 1000L;
    public static final long PERIOD_WEEK = 30 * 24 * 3600 * 1000L - 2000;
    public static final long PERIOD_DAY = 30 * 3600 * 1000L - 4000;

    //public static final long WEEK = 604800000L;
    public static final long WEEK = 60000L;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Log.d("MyLog", "Сервис запущен");

        // Получение списка событий в промежутке с текущего момента и 45 дней вперед
        DatabaseAdapter adapter = new DatabaseAdapter(this);
        adapter.open();

        Calendar currentTime = new GregorianCalendar();

        List<RoundDate> roundDates = adapter.getRoundDates(currentTime.getTimeInMillis(), currentTime.getTimeInMillis() + PERIOD);
        adapter.close();

        List<RoundDate> notifyDates = new ArrayList<>();

        NotifySettings notifySettings = new NotifySettings(this);

        Calendar notifyTime;

        // Формирование списка событий-уведомлений
        for (RoundDate roundDate : roundDates) {
            switch (roundDate.getImportant()) {
                case RoundDate.VERY_IMPORTANT:
                    if ((notifySettings.getVeryImportantRdMonth() == 1) && (roundDate.getDateAndTime().getTimeInMillis() - PERIOD_MONTH > currentTime.getTimeInMillis())) {
                        notifyTime = new GregorianCalendar();
                        notifyTime.setTimeInMillis(roundDate.getDateAndTime().getTimeInMillis() - PERIOD_MONTH);
                        notifyDates.add(new RoundDate(
                                roundDate.getId(),
                                roundDate.getValueOf(),
                                roundDate.getUnit(),
                                notifyTime,
                                roundDate.getIdEvent(),
                                roundDate.getNameEvent(),
                                roundDate.getRare(),
                                roundDate.getImportant()
                        ));
                    }
                    if ((notifySettings.getVeryImportantRdWeek() == 1) && (roundDate.getDateAndTime().getTimeInMillis() - PERIOD_WEEK > currentTime.getTimeInMillis())) {
                        notifyTime = new GregorianCalendar();
                        notifyTime.setTimeInMillis(roundDate.getDateAndTime().getTimeInMillis() - PERIOD_WEEK);
                        notifyDates.add(new RoundDate(
                                roundDate.getId(),
                                roundDate.getValueOf(),
                                roundDate.getUnit(),
                                notifyTime,
                                roundDate.getIdEvent(),
                                roundDate.getNameEvent(),
                                roundDate.getRare(),
                                roundDate.getImportant()
                        ));
                    }
                    if ((notifySettings.getVeryImportantRdDay() == 1) && (roundDate.getDateAndTime().getTimeInMillis() - PERIOD_DAY > currentTime.getTimeInMillis())) {
                        notifyTime = new GregorianCalendar();
                        notifyTime.setTimeInMillis(roundDate.getDateAndTime().getTimeInMillis() - PERIOD_DAY);
                        notifyDates.add(new RoundDate(
                                roundDate.getId(),
                                roundDate.getValueOf(),
                                roundDate.getUnit(),
                                notifyTime,
                                roundDate.getIdEvent(),
                                roundDate.getNameEvent(),
                                roundDate.getRare(),
                                roundDate.getImportant()
                        ));
                    }
                    break;
                case RoundDate.IMPORTANT:
                    if ((notifySettings.getImportantRdMonth() == 1) && (roundDate.getDateAndTime().getTimeInMillis() - PERIOD_MONTH > currentTime.getTimeInMillis())) {
                        notifyTime = new GregorianCalendar();
                        notifyTime.setTimeInMillis(roundDate.getDateAndTime().getTimeInMillis() - PERIOD_MONTH);
                        notifyDates.add(new RoundDate(
                                roundDate.getId(),
                                roundDate.getValueOf(),
                                roundDate.getUnit(),
                                notifyTime,
                                roundDate.getIdEvent(),
                                roundDate.getNameEvent(),
                                roundDate.getRare(),
                                roundDate.getImportant()
                        ));
                    }
                    if ((notifySettings.getImportantRdWeek() == 1) && (roundDate.getDateAndTime().getTimeInMillis() - PERIOD_WEEK > currentTime.getTimeInMillis())) {
                        notifyTime = new GregorianCalendar();
                        notifyTime.setTimeInMillis(roundDate.getDateAndTime().getTimeInMillis() - PERIOD_WEEK);
                        notifyDates.add(new RoundDate(
                                roundDate.getId(),
                                roundDate.getValueOf(),
                                roundDate.getUnit(),
                                notifyTime,
                                roundDate.getIdEvent(),
                                roundDate.getNameEvent(),
                                roundDate.getRare(),
                                roundDate.getImportant()
                        ));
                    }
                    if ((notifySettings.getImportantRdDay() == 1) && (roundDate.getDateAndTime().getTimeInMillis() - PERIOD_DAY > currentTime.getTimeInMillis())) {
                        notifyTime = new GregorianCalendar();
                        notifyTime.setTimeInMillis(roundDate.getDateAndTime().getTimeInMillis() - PERIOD_DAY);
                        notifyDates.add(new RoundDate(
                                roundDate.getId(),
                                roundDate.getValueOf(),
                                roundDate.getUnit(),
                                notifyTime,
                                roundDate.getIdEvent(),
                                roundDate.getNameEvent(),
                                roundDate.getRare(),
                                roundDate.getImportant()
                        ));
                    }
                    break;
                case RoundDate.STANDART:
                    if ((notifySettings.getStandartRdMonth() == 1) && (roundDate.getDateAndTime().getTimeInMillis() - PERIOD_MONTH > currentTime.getTimeInMillis())) {
                        notifyTime = new GregorianCalendar();
                        notifyTime.setTimeInMillis(roundDate.getDateAndTime().getTimeInMillis() - PERIOD_MONTH);
                        notifyDates.add(new RoundDate(
                                roundDate.getId(),
                                roundDate.getValueOf(),
                                roundDate.getUnit(),
                                notifyTime,
                                roundDate.getIdEvent(),
                                roundDate.getNameEvent(),
                                roundDate.getRare(),
                                roundDate.getImportant()
                        ));
                    }
                    if ((notifySettings.getStandartRdWeek() == 1) && (roundDate.getDateAndTime().getTimeInMillis() - PERIOD_WEEK > currentTime.getTimeInMillis())) {
                        notifyTime = new GregorianCalendar();
                        notifyTime.setTimeInMillis(roundDate.getDateAndTime().getTimeInMillis() - PERIOD_WEEK);
                        notifyDates.add(new RoundDate(
                                roundDate.getId(),
                                roundDate.getValueOf(),
                                roundDate.getUnit(),
                                notifyTime,
                                roundDate.getIdEvent(),
                                roundDate.getNameEvent(),
                                roundDate.getRare(),
                                roundDate.getImportant()
                        ));
                    }
                    if ((notifySettings.getStandartRdDay() == 1) && (roundDate.getDateAndTime().getTimeInMillis() - PERIOD_DAY > currentTime.getTimeInMillis())) {
                        notifyTime = new GregorianCalendar();
                        notifyTime.setTimeInMillis(roundDate.getDateAndTime().getTimeInMillis() - PERIOD_DAY);
                        notifyDates.add(new RoundDate(
                                roundDate.getId(),
                                roundDate.getValueOf(),
                                roundDate.getUnit(),
                                notifyTime,
                                roundDate.getIdEvent(),
                                roundDate.getNameEvent(),
                                roundDate.getRare(),
                                roundDate.getImportant()
                        ));
                    }
                    break;
                case RoundDate.NOT_IMPORTANT:
                    if ((notifySettings.getSmallImportantRdMonth() == 1) && (roundDate.getDateAndTime().getTimeInMillis() - PERIOD_MONTH > currentTime.getTimeInMillis())) {
                        notifyTime = new GregorianCalendar();
                        notifyTime.setTimeInMillis(roundDate.getDateAndTime().getTimeInMillis() - PERIOD_MONTH);
                        notifyDates.add(new RoundDate(
                                roundDate.getId(),
                                roundDate.getValueOf(),
                                roundDate.getUnit(),
                                notifyTime,
                                roundDate.getIdEvent(),
                                roundDate.getNameEvent(),
                                roundDate.getRare(),
                                roundDate.getImportant()
                        ));
                    }
                    if ((notifySettings.getSmallImportantRdWeek() == 1) && (roundDate.getDateAndTime().getTimeInMillis() - PERIOD_WEEK > currentTime.getTimeInMillis())) {
                        notifyTime = new GregorianCalendar();
                        notifyTime.setTimeInMillis(roundDate.getDateAndTime().getTimeInMillis() - PERIOD_WEEK);
                        notifyDates.add(new RoundDate(
                                roundDate.getId(),
                                roundDate.getValueOf(),
                                roundDate.getUnit(),
                                notifyTime,
                                roundDate.getIdEvent(),
                                roundDate.getNameEvent(),
                                roundDate.getRare(),
                                roundDate.getImportant()
                        ));
                    }
                    if ((notifySettings.getSmallImportantRdDay() == 1) && (roundDate.getDateAndTime().getTimeInMillis() - PERIOD_DAY > currentTime.getTimeInMillis())) {
                        notifyTime = new GregorianCalendar();
                        notifyTime.setTimeInMillis(roundDate.getDateAndTime().getTimeInMillis() - PERIOD_DAY);
                        notifyDates.add(new RoundDate(
                                roundDate.getId(),
                                roundDate.getValueOf(),
                                roundDate.getUnit(),
                                notifyTime,
                                roundDate.getIdEvent(),
                                roundDate.getNameEvent(),
                                roundDate.getRare(),
                                roundDate.getImportant()
                        ));
                    }
                    break;
            }

        }


        // Удаление текущих уведомлений


        // Добавление новых уведомлений
        for (RoundDate notifyDate : notifyDates) {
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
                    .setContentTitle("Напоминание")
                    //.setContentText(res.getString(R.string.notifytext))
                    .setContentText("Пора покормить кота"); // Текст уведомления

            // Notification notification = builder.getNotification(); // до API 16
            Notification notification = builder.build();

            NotificationManager notificationManager = (NotificationManager) context
                    .getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(0, notification);
        }


        stopSelf();

        Log.d("MyLog", "Сервис остановлен");
    }

}
