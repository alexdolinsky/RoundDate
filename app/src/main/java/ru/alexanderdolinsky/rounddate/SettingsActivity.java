package ru.alexanderdolinsky.rounddate;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.util.List;

public class SettingsActivity extends AppCompatActivity {

    //Объявляем названия файла настроек и сами настройки
    /*public static final String
                                MY_SETTINGS_VERY_IMORTANT_RD_MONTH = "very_important_rd_month",
                                MY_SETTINGS_VERY_IMORTANT_RD_WEEK = "very_important_rd_week",
                                MY_SETTINGS_VERY_IMORTANT_RD_DAY = "very_important_rd_day",
                                MY_SETTINGS_VERY_IMORTANT_RD_HOUR = "very_important_rd_hour",
                                MY_SETTINGS_IMORTANT_RD_MONTH = "important_rd_month",
                                MY_SETTINGS_IMORTANT_RD_WEEK = "important_rd_week",
                                MY_SETTINGS_IMORTANT_RD_DAY = "important_rd_day",
                                MY_SETTINGS_IMORTANT_RD_HOUR = "important_rd_hour",
                                MY_SETTINGS_STANDART_RD_MONTH = "standart_rd_month",
                                MY_SETTINGS_STANDART_RD_WEEK = "standart_rd_week",
                                MY_SETTINGS_STANDART_RD_DAY = "standart_rd_day",
                                MY_SETTINGS_STANDART_RD_HOUR = "standart_rd_hour",
                                MY_SETTINGS_SMALL_IMPORTANT_RD_MONTH = "small_important_rd_month",
                                MY_SETTINGS_SMALL_IMPORTANT_RD_WEEK = "small_important_rd_week",
                                MY_SETTINGS_SMALL_IMPORTANT_RD_DAY = "small_important_rd_day",
                                MY_SETTINGS_SMALL_IMPORTANT_RD_HOUR = "small_important_rd_hour";*/
    private SharedPreferences mSettings, mFilter;
    private TrackSettings trackSettings, oldAppTrackSettings;
    private NotifySettings notifySettings, oldNotifySettings;
    private TextView tvYears, tvMonths, tvWeeks, tvDays, tvHours, tvMinutes, tvSecs,
            tvVeryImportantRd, tvImportantRd, tvStandartRd, tvSmallImportantRd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Считываем настройки отслеживания из внутреннего файла с настройками, если его нет, то по умолчанию
        trackSettings = new TrackSettings(this);
        // Считываем настройки уведомлений из внутреннего файла с настройками, если его нет, то по умолчанию
        notifySettings = new NotifySettings(this);

        // Сохраняем начальные настройки отслеживания для последующего сравнения
        oldAppTrackSettings = new TrackSettings(this);

        // Сохраняем начальные настройки уведомлений для последующего сравнения
        oldNotifySettings = new NotifySettings(this);

        // Выводим значения настроек отслеживания
        final CharSequence[] rdVariants = getResources().getStringArray(R.array.rd_variants);

        tvYears = (TextView) findViewById(R.id.tvRoundDateInYears);
        tvMonths = (TextView) findViewById(R.id.tvRoundDateInMonths);
        tvWeeks = (TextView) findViewById(R.id.tvRoundDateInWeeks);
        tvDays = (TextView) findViewById(R.id.tvRoundDateInDays);
        tvHours = (TextView) findViewById(R.id.tvRoundDateInHours);
        tvMinutes = (TextView) findViewById(R.id.tvRoundDateInMinutes);
        tvSecs = (TextView) findViewById(R.id.tvRoundDateInSecs);

        tvYears.setText(rdVariants[trackSettings.getRdInYears()]);
        tvMonths.setText(rdVariants[trackSettings.getRdInMonths()]);
        tvWeeks.setText(rdVariants[trackSettings.getRdInWeeks()]);
        tvDays.setText(rdVariants[trackSettings.getRdInDays()]);
        tvHours.setText(rdVariants[trackSettings.getRdInHours()]);
        tvMinutes.setText(rdVariants[trackSettings.getRdInMinutes()]);
        tvSecs.setText(rdVariants[trackSettings.getRdInSecs()]);

        // TODO: 22.07.2017  Выводим значения настроек уведомлений
        tvVeryImportantRd = (TextView) findViewById(R.id.tvVeryImportantRoundDate);
        tvImportantRd = (TextView) findViewById(R.id.tvImportantRoundDate);
        tvStandartRd = (TextView) findViewById(R.id.tvStandartRoundDate);
        tvSmallImportantRd = (TextView) findViewById(R.id.tvSmallImportantRoundDate);

        tvVeryImportantRd.setText(getNotificationsVariants(notifySettings.getVeryImportantRdDay(),
                notifySettings.getVeryImportantRdWeek(),
                notifySettings.getVeryImportantRdMonth()));

        tvImportantRd.setText(getNotificationsVariants(notifySettings.getImportantRdDay(),
                notifySettings.getImportantRdWeek(),
                notifySettings.getImportantRdMonth()));

        tvStandartRd.setText(getNotificationsVariants(notifySettings.getStandartRdDay(),
                notifySettings.getStandartRdWeek(),
                notifySettings.getStandartRdMonth()));

        tvSmallImportantRd.setText(getNotificationsVariants(notifySettings.getSmallImportantRdDay(),
                notifySettings.getSmallImportantRdWeek(),
                notifySettings.getSmallImportantRdMonth()));

    }


    public void onSaveSettings(View view) {
        // Сохраняем настройки отслеживания и уведомлений
        SharedPreferences mSettings = getSharedPreferences(TrackSettings.MY_SETTINGS, MODE_PRIVATE);
        SharedPreferences.Editor editor = mSettings.edit();
        editor.putInt(TrackSettings.MY_SETTINGS_RD_IN_YEARS, trackSettings.getRdInYears());
        editor.putInt(TrackSettings.MY_SETTINGS_RD_IN_MONTHS, trackSettings.getRdInMonths());
        editor.putInt(TrackSettings.MY_SETTINGS_RD_IN_WEEKS, trackSettings.getRdInWeeks());
        editor.putInt(TrackSettings.MY_SETTINGS_RD_IN_DAYS, trackSettings.getRdInDays());
        editor.putInt(TrackSettings.MY_SETTINGS_RD_IN_HOURS, trackSettings.getRdInHours());
        editor.putInt(TrackSettings.MY_SETTINGS_RD_IN_MINUTES, trackSettings.getRdInMinutes());
        editor.putInt(TrackSettings.MY_SETTINGS_RD_IN_SECS, trackSettings.getRdInSecs());

        editor.putInt(NotifySettings.MY_SETTINGS_VERY_IMPORTANT_RD_MONTH, notifySettings.getVeryImportantRdMonth());
        editor.putInt(NotifySettings.MY_SETTINGS_VERY_IMPORTANT_RD_WEEK, notifySettings.getVeryImportantRdWeek());
        editor.putInt(NotifySettings.MY_SETTINGS_VERY_IMPORTANT_RD_DAY, notifySettings.getVeryImportantRdDay());
        editor.putInt(NotifySettings.MY_SETTINGS_IMPORTANT_RD_MONTH, notifySettings.getImportantRdMonth());
        editor.putInt(NotifySettings.MY_SETTINGS_IMPORTANT_RD_WEEK, notifySettings.getImportantRdWeek());
        editor.putInt(NotifySettings.MY_SETTINGS_IMPORTANT_RD_DAY, notifySettings.getImportantRdDay());
        editor.putInt(NotifySettings.MY_SETTINGS_STANDART_RD_MONTH, notifySettings.getStandartRdMonth());
        editor.putInt(NotifySettings.MY_SETTINGS_STANDART_RD_WEEK, notifySettings.getStandartRdWeek());
        editor.putInt(NotifySettings.MY_SETTINGS_STANDART_RD_DAY, notifySettings.getStandartRdDay());
        editor.putInt(NotifySettings.MY_SETTINGS_SMALL_IMPORTANT_RD_MONTH, notifySettings.getSmallImportantRdMonth());
        editor.putInt(NotifySettings.MY_SETTINGS_SMALL_IMPORTANT_RD_WEEK, notifySettings.getSmallImportantRdWeek());
        editor.putInt(NotifySettings.MY_SETTINGS_SMALL_IMPORTANT_RD_DAY, notifySettings.getSmallImportantRdDay());

        editor.apply();


        // Связь с БД и ее открытие
        DatabaseAdapter adapter = new DatabaseAdapter(this);
        adapter.open();


        // начинаем транзакцию
        adapter.beginTransaction();
        try {


            // Получаем список событий данной группы
            List<Event> events = adapter.getEventsWithAppTrackSettings();

            List<RoundDate> roundDates;
            // Перерасчет круглых дат в случае если изменились настройки отслеживания
            //long start = System.nanoTime();
            if (trackSettings.getRdInYears() != getOldAppTrackSettings().getRdInYears()) {

                for (Event event : events) {

                    // Лог
                    adapter.getAllRoundDates();
                    adapter.getAllNotify();

                    // Удаление из БД текущих круглых дат - года
                    adapter.deleteRoundDates(event.getId(), RoundDate.UNIT_YEARS);
                    //adapter.deleteRoundDate(event.getId());

                    // Лог
                    adapter.getAllRoundDates();
                    adapter.getAllNotify();

                    if (trackSettings.getRdInYears() != TrackSettings.NOT_TRACK) {
                        // Расчет Круглых дат - года
                        roundDates = event.getRoundDates(Event.YEAR, trackSettings.getRdInYears());
                        // Запись круглых дат в БД
                        roundDates = adapter.addRoundDates(roundDates);
                        adapter.addNotifyDates(roundDates);
                    }
                }
            }

            if (trackSettings.getRdInMonths() != getOldAppTrackSettings().getRdInMonths()) {
                for (Event event : events) {
                    // Удаление из БД текущих круглых дат - месяцы
                    adapter.deleteRoundDates(event.getId(), RoundDate.UNIT_MONTHS);
                    if (trackSettings.getRdInMonths() != TrackSettings.NOT_TRACK) {
                        // Расчет Круглых дат - месяцы
                        roundDates = event.getRoundDates(Event.MONTH, trackSettings.getRdInMonths());
                        // Запись круглых дат в БД
                        roundDates = adapter.addRoundDates(roundDates);
                        adapter.addNotifyDates(roundDates);
                    }
                }
            }

            if (trackSettings.getRdInWeeks() != getOldAppTrackSettings().getRdInWeeks()) {
                for (Event event : events) {
                    // Удаление из БД текущих круглых дат - недели
                    adapter.deleteRoundDates(event.getId(), RoundDate.UNIT_WEEKS);
                    if (trackSettings.getRdInWeeks() != TrackSettings.NOT_TRACK) {
                        // Расчет Круглых дат - недели
                        roundDates = event.getRoundDates(Event.WEEK, trackSettings.getRdInWeeks());
                        // Запись круглых дат в БД
                        roundDates = adapter.addRoundDates(roundDates);
                        adapter.addNotifyDates(roundDates);
                    }
                }
            }

            if (trackSettings.getRdInDays() != getOldAppTrackSettings().getRdInDays()) {
                for (Event event : events) {
                    // Удаление из БД текущих круглых дат - дни
                    adapter.deleteRoundDates(event.getId(), RoundDate.UNIT_DAYS);
                    if (trackSettings.getRdInDays() != TrackSettings.NOT_TRACK) {
                        // Расчет Круглых дат - дни
                        roundDates = event.getRoundDates(Event.DAY, trackSettings.getRdInDays());
                        // Запись круглых дат в БД
                        roundDates = adapter.addRoundDates(roundDates);
                        adapter.addNotifyDates(roundDates);
                    }
                }
            }

            if (trackSettings.getRdInHours() != getOldAppTrackSettings().getRdInHours()) {
                for (Event event : events) {
                    // Удаление из БД текущих круглых дат - часы
                    adapter.deleteRoundDates(event.getId(), RoundDate.UNIT_HOURS);
                    if (trackSettings.getRdInHours() != TrackSettings.NOT_TRACK) {
                        // Расчет Круглых дат - часы
                        roundDates = event.getRoundDates(Event.HOUR, trackSettings.getRdInHours());
                        // Запись круглых дат в БД
                        roundDates = adapter.addRoundDates(roundDates);
                        adapter.addNotifyDates(roundDates);
                    }
                }
            }

            if (trackSettings.getRdInMinutes() != getOldAppTrackSettings().getRdInMinutes()) {
                for (Event event : events) {
                    // Удаление из БД текущих круглых дат - минуты
                    adapter.deleteRoundDates(event.getId(), RoundDate.UNIT_MINUTES);
                    if (trackSettings.getRdInMinutes() != TrackSettings.NOT_TRACK) {
                        // Расчет Круглых дат - минуты
                        roundDates = event.getRoundDates(Event.MINUTE, trackSettings.getRdInMinutes());
                        // Запись круглых дат в БД
                        roundDates = adapter.addRoundDates(roundDates);
                        adapter.addNotifyDates(roundDates);
                    }
                }
            }

            if (trackSettings.getRdInSecs() != getOldAppTrackSettings().getRdInSecs()) {
                for (Event event : events) {
                    // Удаление из БД текущих круглых дат - секунды
                    adapter.deleteRoundDates(event.getId(), RoundDate.UNIT_SECS);
                    if (trackSettings.getRdInSecs() != TrackSettings.NOT_TRACK) {
                        // Расчет Круглых дат - секунды
                        roundDates = event.getRoundDates(Event.SEC, trackSettings.getRdInSecs());
                        // Запись круглых дат в БД
                        roundDates = adapter.addRoundDates(roundDates);
                        adapter.addNotifyDates(roundDates);
                    }
                }
            }

            //long finish = System.nanoTime();
            //long duration = finish - start;
            //Log.d("MyLog", "Время выполнения = " + duration);

            adapter.setTransactionSuccessful();
        } finally {
            adapter.endTransaction();
        }

        // Закрытие соединения с БД
        adapter.close();

        Intent intent = new Intent();
        intent.setAction(AddEditEventActivity.NOTIFICATION_ACTION);
        sendBroadcast(intent);

        setResult(RESULT_OK);
        finish();


    }

    public void onCancel(View view) {
        setResult(RESULT_CANCELED);
        finish();
    }

    public void onLoadDefaultSettings(View view) {
        SharedPreferences mSettings = getSharedPreferences(TrackSettings.MY_SETTINGS, MODE_PRIVATE);
        SharedPreferences.Editor editor = mSettings.edit();
        editor.clear();
        editor.apply();
        trackSettings = new TrackSettings(this);
        notifySettings = new NotifySettings(this);

        final CharSequence[] rdVariants = getResources().getStringArray(R.array.rd_variants);

        tvYears.setText(rdVariants[trackSettings.getRdInYears()]);
        tvMonths.setText(rdVariants[trackSettings.getRdInMonths()]);
        tvWeeks.setText(rdVariants[trackSettings.getRdInWeeks()]);
        tvDays.setText(rdVariants[trackSettings.getRdInDays()]);
        tvHours.setText(rdVariants[trackSettings.getRdInHours()]);
        tvMinutes.setText(rdVariants[trackSettings.getRdInMinutes()]);
        tvSecs.setText(rdVariants[trackSettings.getRdInSecs()]);

        tvVeryImportantRd.setText(getNotificationsVariants(notifySettings.getVeryImportantRdDay(),
                notifySettings.getVeryImportantRdWeek(),
                notifySettings.getVeryImportantRdMonth()));

        tvImportantRd.setText(getNotificationsVariants(notifySettings.getImportantRdDay(),
                notifySettings.getImportantRdWeek(),
                notifySettings.getImportantRdMonth()));

        tvStandartRd.setText(getNotificationsVariants(notifySettings.getStandartRdDay(),
                notifySettings.getStandartRdWeek(),
                notifySettings.getStandartRdMonth()));

        tvSmallImportantRd.setText(getNotificationsVariants(notifySettings.getSmallImportantRdDay(),
                notifySettings.getSmallImportantRdWeek(),
                notifySettings.getSmallImportantRdMonth()));

    }


    public void onClick(View view) {
        DialogScreen ds;
        android.support.v7.app.AlertDialog dialog;

        switch (view.getId()) {
            case R.id.llRoundDateYear:
                ds = new DialogScreen(DialogScreen.IDD_RD_YEARS_3);
                dialog = ds.getDialog(this);
                dialog.show();
                break;
            case R.id.llRoundDateMonth:
                ds = new DialogScreen(DialogScreen.IDD_RD_MONTHS_3);
                dialog = ds.getDialog(this);
                dialog.show();
                break;
            case R.id.llRoundDateWeek:
                ds = new DialogScreen(DialogScreen.IDD_RD_WEEKS_3);
                dialog = ds.getDialog(this);
                dialog.show();
                break;
            case R.id.llRoundDateDay:
                ds = new DialogScreen(DialogScreen.IDD_RD_DAYS_3);
                dialog = ds.getDialog(this);
                dialog.show();
                break;
            case R.id.llRoundDateHour:
                ds = new DialogScreen(DialogScreen.IDD_RD_HOURS_3);
                dialog = ds.getDialog(this);
                dialog.show();
                break;
            case R.id.llRoundDateMinute:
                ds = new DialogScreen(DialogScreen.IDD_RD_MINUTES_3);
                dialog = ds.getDialog(this);
                dialog.show();
                break;
            case R.id.llRoundDateSec:
                ds = new DialogScreen(DialogScreen.IDD_RD_SECS_3);
                dialog = ds.getDialog(this);
                dialog.show();
                break;
            case R.id.llVeryImportantRoundDate:
                ds = new DialogScreen(DialogScreen.IDD_VERY_IMPORTANT_ROUNDDATE);
                dialog = ds.getDialog(this);
                dialog.show();
                break;
            case R.id.llImportantRoundDate:
                ds = new DialogScreen(DialogScreen.IDD_IMPORTANT_ROUNDDATE);
                dialog = ds.getDialog(this);
                dialog.show();
                break;
            case R.id.llStandartRoundDate:
                ds = new DialogScreen(DialogScreen.IDD_STANDART_ROUNDDATE);
                dialog = ds.getDialog(this);
                dialog.show();
                break;
            case R.id.llSmallImportantRoundDate:
                ds = new DialogScreen(DialogScreen.IDD_SMALL_IMPORTANT_ROUNDDATE);
                dialog = ds.getDialog(this);
                dialog.show();
                break;
        }
    }

    public TrackSettings getTrackSettings() {
        return trackSettings;
    }

    public NotifySettings getNotifySettings() {
        return notifySettings;
    }

    public void setTrackSettings(TrackSettings trackSettings) {
        this.trackSettings = trackSettings;
    }

    public TrackSettings getOldAppTrackSettings() {
        return oldAppTrackSettings;
    }

    public void setOldAppTrackSettings(TrackSettings oldAppTrackSettings) {
        this.oldAppTrackSettings = oldAppTrackSettings;
    }

    public void setTvYears(CharSequence rdVariant) {
        this.tvYears.setText(rdVariant);
    }

    public void setTvMonths(CharSequence rdVariant) {
        this.tvMonths.setText(rdVariant);
    }

    public void setTvWeeks(CharSequence rdVariant) {
        this.tvWeeks.setText(rdVariant);
    }

    public void setTvDays(CharSequence rdVariant) {
        this.tvDays.setText(rdVariant);
    }

    public void setTvHours(CharSequence rdVariant) {
        this.tvHours.setText(rdVariant);
    }

    public void setTvMinutes(CharSequence rdVariant) {
        this.tvMinutes.setText(rdVariant);
    }

    public void setTvSecs(CharSequence rdVariant) {
        this.tvSecs.setText(rdVariant);
    }

    public void setTvVeryImportantRd(CharSequence notificationsVariants) {
        this.tvVeryImportantRd.setText(notificationsVariants);
    }

    public void setTvImportantRd(CharSequence notificationsVariants) {
        this.tvImportantRd.setText(notificationsVariants);
    }

    public void setTvStandartRd(CharSequence notificationsVariants) {
        this.tvStandartRd.setText(notificationsVariants);
    }

    public void setTvSmallImportantRd(CharSequence notificationsVariants) {
        this.tvSmallImportantRd.setText(notificationsVariants);
    }


    public String getNotificationsVariants(int rdDay, int rdWeek, int rdMonth) {
        String s = "";
        if (rdDay == 1) {
            s = getString(R.string.one_day);
        }
        if (rdWeek == 1) {
            if (s.isEmpty()) {
                s = getString(R.string.one_week);
            } else {
                s = s + ", " + getString(R.string.one_week);
            }
        }
        if (rdMonth == 1) {
            if (s.isEmpty()) {
                s = getString(R.string.one_month);
            } else {
                s = s + ", " + getString(R.string.one_month);
            }
        }
        if (s.isEmpty()) {
            s = getString(R.string.not_notify);
        } else {
            s = getString(R.string.notice_for) + " " + s;
        }
        return s;
    }
}
