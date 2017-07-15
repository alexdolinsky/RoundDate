package ru.alexanderdolinsky.rounddate;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Alexsvet on 04.06.2017.
 * Класс Настройки отслеживания
 */

class TrackSettings {
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

    static final String MY_SETTINGS = "my_settings",
            MY_SETTINGS_RD_IN_YEARS = "rd_in_years",
            MY_SETTINGS_RD_IN_MONTHS = "rd_in_months",
            MY_SETTINGS_RD_IN_WEEKS = "rd_in_weeks",
            MY_SETTINGS_RD_IN_DAYS = "rd_in_days",
            MY_SETTINGS_RD_IN_HOURS = "rd_in_hours",
            MY_SETTINGS_RD_IN_MINUTES = "rd_in_minutes",
            MY_SETTINGS_RD_IN_SECS = "rd_in_secs";

    static final int STANDART = 0,
            RARE = 1,
            VERY_RARE = 2,
            NOT_TRACK = 3;


    // конструкторы
    TrackSettings(int rdInYears,
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

    TrackSettings(Context context) {
        SharedPreferences mSettings = context.getSharedPreferences(MY_SETTINGS, MODE_PRIVATE);
        this.rdInYears = mSettings.getInt(MY_SETTINGS_RD_IN_YEARS, Integer.valueOf(context.getString(R.string.rd_in_years)));
        this.rdInMonths = mSettings.getInt(MY_SETTINGS_RD_IN_MONTHS, Integer.valueOf(context.getString(R.string.rd_in_months)));
        this.rdInWeeks = mSettings.getInt(MY_SETTINGS_RD_IN_WEEKS, Integer.valueOf(context.getString(R.string.rd_in_weeks)));
        this.rdInDays = mSettings.getInt(MY_SETTINGS_RD_IN_DAYS, Integer.valueOf(context.getString(R.string.rd_in_days)));
        this.rdInHours = mSettings.getInt(MY_SETTINGS_RD_IN_HOURS, Integer.valueOf(context.getString(R.string.rd_in_hours)));
        this.rdInMinutes = mSettings.getInt(MY_SETTINGS_RD_IN_MINUTES, Integer.valueOf(context.getString(R.string.rd_in_minutes)));
        this.rdInSecs = mSettings.getInt(MY_SETTINGS_RD_IN_SECS, Integer.valueOf(context.getString(R.string.rd_in_secs)));

    }

    int getRdInYears() {
        return rdInYears;
    }

    void setRdInYears(int rdInYears) {
        this.rdInYears = rdInYears;
    }

    int getRdInMonths() {
        return rdInMonths;
    }

    void setRdInMonths(int rdInMonths) {
        this.rdInMonths = rdInMonths;
    }

    int getRdInWeeks() {
        return rdInWeeks;
    }

    void setRdInWeeks(int rdInWeeks) {
        this.rdInWeeks = rdInWeeks;
    }

    int getRdInDays() {
        return rdInDays;
    }

    void setRdInDays(int rdInDays) {
        this.rdInDays = rdInDays;
    }

    int getRdInHours() {
        return rdInHours;
    }

    void setRdInHours(int rdInHours) {
        this.rdInHours = rdInHours;
    }

    int getRdInMinutes() {
        return rdInMinutes;
    }

    void setRdInMinutes(int rdInMinutes) {
        this.rdInMinutes = rdInMinutes;
    }

    int getRdInSecs() {
        return rdInSecs;
    }

    void setRdInSecs(int rdInSecs) {
        this.rdInSecs = rdInSecs;
    }
}
