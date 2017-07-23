package ru.alexanderdolinsky.rounddate;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Alexsvet on 15.07.2017.
 * Настройки уведомлений
 */

class NotifySettings {
    private int veryImportantRdMonth,
            veryImportantRdWeek,
            veryImportantRdDay,
            importantRdMonth,
            importantRdWeek,
            importantRdDay,
            standartRdMonth,
            standartRdWeek,
            standartRdDay,
            smallImportantRdMonth,
            smallImportantRdWeek,
            smallImportantRdDay;

    private static final String MY_SETTINGS_VERY_IMPORTANT_RD_MONTH = "very_important_rd_month";
    private static final String MY_SETTINGS_VERY_IMPORTANT_RD_WEEK = "very_important_rd_week";
    private static final String MY_SETTINGS_VERY_IMPORTANT_RD_DAY = "very_important_rd_day";
    private static final String MY_SETTINGS_IMPORTANT_RD_MONTH = "important_rd_month";
    private static final String MY_SETTINGS_IMPORTANT_RD_WEEK = "important_rd_week";
    private static final String MY_SETTINGS_IMPORTANT_RD_DAY = "important_rd_day";
    private static final String MY_SETTINGS_STANDART_RD_MONTH = "standart_rd_month";
    private static final String MY_SETTINGS_STANDART_RD_WEEK = "standart_rd_week";
    private static final String MY_SETTINGS_STANDART_RD_DAY = "standart_rd_day";
    private static final String MY_SETTINGS_SMALL_IMPORTANT_RD_MONTH = "small_important_rd_month";
    private static final String MY_SETTINGS_SMALL_IMPORTANT_RD_WEEK = "small_important_rd_week";
    private static final String MY_SETTINGS_SMALL_IMPORTANT_RD_DAY = "small_important_rd_day";


    NotifySettings(Context context) {
        SharedPreferences mSettings = context.getSharedPreferences(TrackSettings.MY_SETTINGS, MODE_PRIVATE);
        this.veryImportantRdMonth = mSettings.getInt(MY_SETTINGS_VERY_IMPORTANT_RD_MONTH, Integer.valueOf(context.getString(R.string.very_important_rd_month)));
        this.veryImportantRdWeek = mSettings.getInt(MY_SETTINGS_VERY_IMPORTANT_RD_WEEK, Integer.valueOf(context.getString(R.string.very_important_rd_week)));
        this.veryImportantRdDay = mSettings.getInt(MY_SETTINGS_VERY_IMPORTANT_RD_DAY, Integer.valueOf(context.getString(R.string.very_important_rd_day)));
        this.importantRdMonth = mSettings.getInt(MY_SETTINGS_IMPORTANT_RD_MONTH, Integer.valueOf(context.getString(R.string.important_rd_month)));
        this.importantRdWeek = mSettings.getInt(MY_SETTINGS_IMPORTANT_RD_WEEK, Integer.valueOf(context.getString(R.string.important_rd_week)));
        this.importantRdDay = mSettings.getInt(MY_SETTINGS_IMPORTANT_RD_DAY, Integer.valueOf(context.getString(R.string.important_rd_day)));
        this.standartRdMonth = mSettings.getInt(MY_SETTINGS_STANDART_RD_MONTH, Integer.valueOf(context.getString(R.string.standart_rd_month)));
        this.standartRdWeek = mSettings.getInt(MY_SETTINGS_STANDART_RD_WEEK, Integer.valueOf(context.getString(R.string.standart_rd_week)));
        this.standartRdDay = mSettings.getInt(MY_SETTINGS_STANDART_RD_DAY, Integer.valueOf(context.getString(R.string.standart_rd_day)));
        this.smallImportantRdMonth = mSettings.getInt(MY_SETTINGS_SMALL_IMPORTANT_RD_MONTH, Integer.valueOf(context.getString(R.string.small_important_rd_month)));
        this.smallImportantRdWeek = mSettings.getInt(MY_SETTINGS_SMALL_IMPORTANT_RD_WEEK, Integer.valueOf(context.getString(R.string.small_important_rd_week)));
        this.smallImportantRdDay = mSettings.getInt(MY_SETTINGS_SMALL_IMPORTANT_RD_DAY, Integer.valueOf(context.getString(R.string.small_important_rd_day)));
    }

    void save(Context context) {

        SharedPreferences mSettings = context.getSharedPreferences(TrackSettings.MY_SETTINGS, MODE_PRIVATE);
        SharedPreferences.Editor editor = mSettings.edit();
        editor.putInt(MY_SETTINGS_VERY_IMPORTANT_RD_MONTH, this.getVeryImportantRdMonth());
        editor.putInt(MY_SETTINGS_VERY_IMPORTANT_RD_WEEK, this.getVeryImportantRdWeek());
        editor.putInt(MY_SETTINGS_VERY_IMPORTANT_RD_DAY, this.getVeryImportantRdDay());
        editor.putInt(MY_SETTINGS_IMPORTANT_RD_MONTH, this.getImportantRdMonth());
        editor.putInt(MY_SETTINGS_IMPORTANT_RD_WEEK, this.getImportantRdWeek());
        editor.putInt(MY_SETTINGS_IMPORTANT_RD_DAY, this.getImportantRdDay());
        editor.putInt(MY_SETTINGS_STANDART_RD_MONTH, this.getStandartRdMonth());
        editor.putInt(MY_SETTINGS_STANDART_RD_WEEK, this.getStandartRdWeek());
        editor.putInt(MY_SETTINGS_STANDART_RD_DAY, this.getStandartRdDay());
        editor.putInt(MY_SETTINGS_SMALL_IMPORTANT_RD_MONTH, this.getSmallImportantRdMonth());
        editor.putInt(MY_SETTINGS_SMALL_IMPORTANT_RD_WEEK, this.getSmallImportantRdWeek());
        editor.putInt(MY_SETTINGS_SMALL_IMPORTANT_RD_DAY, this.getSmallImportantRdDay());
        editor.apply();

    }


    boolean comparedWith(NotifySettings notifySettings) {
        return (this.getVeryImportantRdDay() == notifySettings.getVeryImportantRdDay() &&
                this.getVeryImportantRdWeek() == notifySettings.getVeryImportantRdWeek() &&
                this.getVeryImportantRdMonth() == notifySettings.getVeryImportantRdMonth() &&
                this.getImportantRdDay() == notifySettings.getImportantRdDay() &&
                this.getImportantRdWeek() == notifySettings.getImportantRdWeek() &&
                this.getImportantRdMonth() == notifySettings.getImportantRdMonth() &&
                this.getStandartRdDay() == notifySettings.getStandartRdDay() &&
                this.getStandartRdWeek() == notifySettings.getStandartRdWeek() &&
                this.getStandartRdMonth() == notifySettings.getStandartRdMonth() &&
                this.getSmallImportantRdDay() == notifySettings.getSmallImportantRdDay() &&
                this.getSmallImportantRdWeek() == notifySettings.getSmallImportantRdWeek() &&
                this.getSmallImportantRdMonth() == notifySettings.getSmallImportantRdMonth());
    }

    int getVeryImportantRdMonth() {
        return veryImportantRdMonth;
    }

    void setVeryImportantRdMonth(int veryImportantRdMonth) {
        this.veryImportantRdMonth = veryImportantRdMonth;
    }

    int getVeryImportantRdWeek() {
        return veryImportantRdWeek;
    }

    void setVeryImportantRdWeek(int veryImportantRdWeek) {
        this.veryImportantRdWeek = veryImportantRdWeek;
    }

    int getVeryImportantRdDay() {
        return veryImportantRdDay;
    }

    void setVeryImportantRdDay(int veryImportantRdDay) {
        this.veryImportantRdDay = veryImportantRdDay;
    }

    int getImportantRdMonth() {
        return importantRdMonth;
    }

    void setImportantRdMonth(int importantRdMonth) {
        this.importantRdMonth = importantRdMonth;
    }

    int getImportantRdWeek() {
        return importantRdWeek;
    }

    void setImportantRdWeek(int importantRdWeek) {
        this.importantRdWeek = importantRdWeek;
    }

    int getImportantRdDay() {
        return importantRdDay;
    }

    void setImportantRdDay(int importantRdDay) {
        this.importantRdDay = importantRdDay;
    }

    int getStandartRdMonth() {
        return standartRdMonth;
    }

    void setStandartRdMonth(int standartRdMonth) {
        this.standartRdMonth = standartRdMonth;
    }

    int getStandartRdWeek() {
        return standartRdWeek;
    }

    void setStandartRdWeek(int standartRdWeek) {
        this.standartRdWeek = standartRdWeek;
    }

    int getStandartRdDay() {
        return standartRdDay;
    }

    void setStandartRdDay(int standartRdDay) {
        this.standartRdDay = standartRdDay;
    }

    int getSmallImportantRdMonth() {
        return smallImportantRdMonth;
    }

    void setSmallImportantRdMonth(int smallImportantRdMonth) {
        this.smallImportantRdMonth = smallImportantRdMonth;
    }

    int getSmallImportantRdWeek() {
        return smallImportantRdWeek;
    }

    void setSmallImportantRdWeek(int smallImportantRdWeek) {
        this.smallImportantRdWeek = smallImportantRdWeek;
    }

    int getSmallImportantRdDay() {
        return smallImportantRdDay;
    }

    void setSmallImportantRdDay(int smallImportantRdDay) {
        this.smallImportantRdDay = smallImportantRdDay;
    }
}
