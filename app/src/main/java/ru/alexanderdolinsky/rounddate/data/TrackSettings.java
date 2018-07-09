package ru.alexanderdolinsky.rounddate.data;

import android.content.Context;
import android.content.SharedPreferences;

import ru.alexanderdolinsky.rounddate.R;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Alexsvet on 04.06.2017.
 * Класс Настройки отслеживания
 */

public class TrackSettings {
    /* настройки отслеживания
    0 - стандартный набор
    1 - только редкие
    2 - только очень редкие
    3 - не отслеживать
    */
    private int rdInYears,
                rdInMonths,
                rdInWeeks,
                rdInDays,
                rdInHours,
                rdInMinutes,
                rdInSecs;

    public static final String MY_SETTINGS = "my_settings";
    private static final String MY_SETTINGS_RD_IN_YEARS = "rd_in_years",
            MY_SETTINGS_RD_IN_MONTHS = "rd_in_months",
            MY_SETTINGS_RD_IN_WEEKS = "rd_in_weeks",
            MY_SETTINGS_RD_IN_DAYS = "rd_in_days",
            MY_SETTINGS_RD_IN_HOURS = "rd_in_hours",
            MY_SETTINGS_RD_IN_MINUTES = "rd_in_minutes",
            MY_SETTINGS_RD_IN_SECS = "rd_in_secs";

    public static final int STANDART = 0;
    public static final int RARE = 1;
    public static final int VERY_RARE = 2;
    public static final int NOT_TRACK = 3;


    // конструкторы
    public TrackSettings(int rdInYears,
                  int rdInMonths,
                  int rdInWeeks,
                  int rdInDays,
                  int rdInHours,
                  int rdInMinutes,
                  int rdInSecs ){
        this.rdInYears = rdInYears;
        this.rdInMonths = rdInMonths;
        this.rdInWeeks = rdInWeeks;
        this.rdInDays = rdInDays;
        this.rdInHours = rdInHours;
        this.rdInMinutes = rdInMinutes;
        this.rdInSecs = rdInSecs;
    }

    public TrackSettings(Context context) {
        SharedPreferences mSettings = context.getSharedPreferences(MY_SETTINGS, MODE_PRIVATE);
        this.rdInYears = mSettings.getInt(MY_SETTINGS_RD_IN_YEARS, Integer.valueOf(context.getString(R.string.rd_in_years)));
        this.rdInMonths = mSettings.getInt(MY_SETTINGS_RD_IN_MONTHS, Integer.valueOf(context.getString(R.string.rd_in_months)));
        this.rdInWeeks = mSettings.getInt(MY_SETTINGS_RD_IN_WEEKS, Integer.valueOf(context.getString(R.string.rd_in_weeks)));
        this.rdInDays = mSettings.getInt(MY_SETTINGS_RD_IN_DAYS, Integer.valueOf(context.getString(R.string.rd_in_days)));
        this.rdInHours = mSettings.getInt(MY_SETTINGS_RD_IN_HOURS, Integer.valueOf(context.getString(R.string.rd_in_hours)));
        this.rdInMinutes = mSettings.getInt(MY_SETTINGS_RD_IN_MINUTES, Integer.valueOf(context.getString(R.string.rd_in_minutes)));
        this.rdInSecs = mSettings.getInt(MY_SETTINGS_RD_IN_SECS, Integer.valueOf(context.getString(R.string.rd_in_secs)));

    }

    public void save(Context context) {

        SharedPreferences mSettings = context.getSharedPreferences(TrackSettings.MY_SETTINGS, MODE_PRIVATE);
        SharedPreferences.Editor editor = mSettings.edit();
        editor.putInt(TrackSettings.MY_SETTINGS_RD_IN_YEARS, this.getRdInYears());
        editor.putInt(TrackSettings.MY_SETTINGS_RD_IN_MONTHS, this.getRdInMonths());
        editor.putInt(TrackSettings.MY_SETTINGS_RD_IN_WEEKS, this.getRdInWeeks());
        editor.putInt(TrackSettings.MY_SETTINGS_RD_IN_DAYS, this.getRdInDays());
        editor.putInt(TrackSettings.MY_SETTINGS_RD_IN_HOURS, this.getRdInHours());
        editor.putInt(TrackSettings.MY_SETTINGS_RD_IN_MINUTES, this.getRdInMinutes());
        editor.putInt(TrackSettings.MY_SETTINGS_RD_IN_SECS, this.getRdInSecs());
        editor.apply();
    }

    public int getRdInYears() {
        return rdInYears;
    }

    public void setRdInYears(int rdInYears) {
        this.rdInYears = rdInYears;
    }

    public int getRdInMonths() {
        return rdInMonths;
    }

    public void setRdInMonths(int rdInMonths) {
        this.rdInMonths = rdInMonths;
    }

    public int getRdInWeeks() {
        return rdInWeeks;
    }

    public void setRdInWeeks(int rdInWeeks) {
        this.rdInWeeks = rdInWeeks;
    }

    public int getRdInDays() {
        return rdInDays;
    }

    public void setRdInDays(int rdInDays) {
        this.rdInDays = rdInDays;
    }

    public int getRdInHours() {
        return rdInHours;
    }

    public void setRdInHours(int rdInHours) {
        this.rdInHours = rdInHours;
    }

    public int getRdInMinutes() {
        return rdInMinutes;
    }

    public void setRdInMinutes(int rdInMinutes) {
        this.rdInMinutes = rdInMinutes;
    }

    public int getRdInSecs() {
        return rdInSecs;
    }

    public void setRdInSecs(int rdInSecs) {
        this.rdInSecs = rdInSecs;
    }
}
