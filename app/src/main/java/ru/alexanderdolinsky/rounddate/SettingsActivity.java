package ru.alexanderdolinsky.rounddate;

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
    private TextView tvYears, tvMonths, tvWeeks, tvDays, tvHours, tvMinutes, tvSecs;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Считываем настройки из внутреннего файла с настройками, если его нет, то по умолчанию
        trackSettings = new TrackSettings(this);

        // Сохраняем начальные настройки отслеживания для последующего сравнения
        oldAppTrackSettings = new TrackSettings(
                trackSettings.getRdInYears(),
                trackSettings.getRdInMonths(),
                trackSettings.getRdInWeeks(),
                trackSettings.getRdInDays(),
                trackSettings.getRdInHours(),
                trackSettings.getRdInMinutes(),
                trackSettings.getRdInSecs());

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


    }


    public void onSaveSettings(View view) {
        // TODO: 31.05.2017 Сохраняем настройки
        SharedPreferences mSettings = getSharedPreferences(TrackSettings.MY_SETTINGS, MODE_PRIVATE);
        SharedPreferences.Editor editor = mSettings.edit();
        editor.putInt(TrackSettings.MY_SETTINGS_RD_IN_YEARS, trackSettings.getRdInYears());
        editor.putInt(TrackSettings.MY_SETTINGS_RD_IN_MONTHS, trackSettings.getRdInMonths());
        editor.putInt(TrackSettings.MY_SETTINGS_RD_IN_WEEKS, trackSettings.getRdInWeeks());
        editor.putInt(TrackSettings.MY_SETTINGS_RD_IN_DAYS, trackSettings.getRdInDays());
        editor.putInt(TrackSettings.MY_SETTINGS_RD_IN_HOURS, trackSettings.getRdInHours());
        editor.putInt(TrackSettings.MY_SETTINGS_RD_IN_MINUTES, trackSettings.getRdInMinutes());
        editor.putInt(TrackSettings.MY_SETTINGS_RD_IN_SECS, trackSettings.getRdInSecs());
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

                    // Удаление из БД текущих круглых дат - года
                    adapter.deleteRoundDates(event.getId(), RoundDate.UNIT_YEARS);
                    if (trackSettings.getRdInYears() != TrackSettings.NOT_TRACK) {
                        // Расчет Круглых дат - года
                        roundDates = event.getRoundDates(Event.YEAR, trackSettings.getRdInYears());
                        // Запись круглых дат в БД
                        adapter.addRoundDates(roundDates);
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
                        adapter.addRoundDates(roundDates);
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
                        adapter.addRoundDates(roundDates);
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
                        adapter.addRoundDates(roundDates);
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
                        adapter.addRoundDates(roundDates);
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
                        adapter.addRoundDates(roundDates);
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
                        adapter.addRoundDates(roundDates);
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

        final CharSequence[] rdVariants = getResources().getStringArray(R.array.rd_variants);

        tvYears.setText(rdVariants[trackSettings.getRdInYears()]);
        tvMonths.setText(rdVariants[trackSettings.getRdInMonths()]);
        tvWeeks.setText(rdVariants[trackSettings.getRdInWeeks()]);
        tvDays.setText(rdVariants[trackSettings.getRdInDays()]);
        tvHours.setText(rdVariants[trackSettings.getRdInHours()]);
        tvMinutes.setText(rdVariants[trackSettings.getRdInMinutes()]);
        tvSecs.setText(rdVariants[trackSettings.getRdInSecs()]);

    }


    public void onClick(View view) {
        DialogScreen ds;
        android.app.AlertDialog dialog;

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


        }
    }

    public TrackSettings getTrackSettings() {
        return trackSettings;
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
}
