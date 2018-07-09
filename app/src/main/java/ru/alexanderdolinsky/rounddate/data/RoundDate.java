package ru.alexanderdolinsky.rounddate.data;


import android.content.Context;

import java.util.Calendar;
import java.util.GregorianCalendar;

import ru.alexanderdolinsky.rounddate.R;

/**
 * Created by Alexsvet on 04.06.2017.
 * Класс Круглая дата
 */

public class RoundDate {
    // id Круглой даты
    private long id;
    // значение Круглой даты
    long valueOf;
    // размерность Круглой даты
    int unit;
    // дата/время Круглой даты
    Calendar dateAndTime;
    // id События
    long idEvent;
    // название События
    String nameEvent;
    /* редкость Круглой даты
    0 - обычная Курглая дата
    1 - редкая Круглая дата
    2 - очень редкая Круглая дата
    */
    int rare;
    /* важность Круглой даты
    0 - обычная Круглая дата
    1 - важная Круглой даты
    2 - очень важная Круглая дата
    3 - неважная Круглая дат
     */
    int important;

    // константы размерностей Круглой даты
    public final static int UNIT_YEARS = 1;
    public final static int UNIT_MONTHS = 2;
    public final static int UNIT_WEEKS = 3;
    public final static int UNIT_DAYS = 4;
    public final static int UNIT_HOURS = 5;
    public final static int UNIT_MINUTES = 6;
    public final static int UNIT_SECS = 7;

    public final static int STANDART = 0;
    public final static int RARE = 1;
    public final static int VERY_RARE = 2;
    public final static int IMPORTANT = 1;
    public final static int VERY_IMPORTANT = 2;
    public final static int NOT_IMPORTANT = 3;


    public RoundDate() {
        this.id = -1;
        this.valueOf = 1;
        this.unit = 4;
        this.dateAndTime = new GregorianCalendar();
        this.idEvent = -1;
        this.nameEvent = "Standart Event";
        this.rare = 0;
        this.important = 0;
    }

    public RoundDate(long id,
              long valueOf,
              int unit,
              Calendar dateAndTime,
              long idEvent,
              String nameEvent,
              int rare,
              int important) {
        this.id = id;
        this.valueOf = valueOf;
        this.unit = unit;
        this.dateAndTime = dateAndTime;
        this.idEvent = idEvent;
        this.nameEvent = nameEvent;
        this.rare = rare;
        this.important = important;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getValueOf() {
        return valueOf;
    }

    public void setValueOf(long valueOf) {
        this.valueOf = valueOf;
    }

    public int getUnit() {
        return unit;
    }

    public void setUnit(int unit) {
        this.unit = unit;
    }

    public Calendar getDateAndTime() {
        return dateAndTime;
    }

    public void setDateAndTime(Calendar dateAndTime) {
        this.dateAndTime = dateAndTime;
    }

    public long getIdEvent() {
        return idEvent;
    }

    public void setIdEvent(long idEvent) {
        this.idEvent = idEvent;
    }

    public String getNameEvent() {
        return nameEvent;
    }

    public void setNameEvent(String nameEvent) {
        this.nameEvent = nameEvent;
    }

    public int getRare() {
        return rare;
    }

    public void setRare(int rare) {
        this.rare = rare;
    }

    public int getImportant() {
        return important;
    }

    public void setImportant(int important) {
        this.important = important;
    }

    public static String getUnit(Context context, long value, int unit) {

        int value10 = (int) value % 10;
        int value100 = (int) value % 100;

        switch (unit) {
            case UNIT_YEARS:
                if ((value10 == 1) && (value100 != 11)) {
                    return context.getString(R.string.years_1);
                } else if ((value10 > 1) && (value10 < 5) && ((value100 < 12) || (value100 > 14))) {
                    return context.getString(R.string.years_2);
                } else return context.getString(R.string.years_3);
            case UNIT_MONTHS:
                if ((value10 == 1) && (value100 != 11)) {
                    return context.getString(R.string.months_1);
                } else if ((value10 > 1) && (value10 < 5) && ((value100 < 12) || (value100 > 14))) {
                    return context.getString(R.string.months_2);
                } else return context.getString(R.string.months_3);
            case UNIT_WEEKS:
                if ((value10 == 1) && (value100 != 11)) {
                    return context.getString(R.string.weeks_1);
                } else if ((value10 > 1) && (value10 < 5) && ((value100 < 12) || (value100 > 14))) {
                    return context.getString(R.string.weeks_2);
                } else return context.getString(R.string.weeks_3);
            case UNIT_DAYS:
                if ((value10 == 1) && (value100 != 11)) {
                    return context.getString(R.string.days_1);
                } else if ((value10 > 1) && (value10 < 5) && ((value100 < 12) || (value100 > 14))) {
                    return context.getString(R.string.days_2);
                } else return context.getString(R.string.days_3);
            case UNIT_HOURS:
                if ((value10 == 1) && (value100 != 11)) {
                    return context.getString(R.string.hours_1);
                } else if ((value10 > 1) && (value10 < 5) && ((value100 < 12) || (value100 > 14))) {
                    return context.getString(R.string.hours_2);
                } else return context.getString(R.string.hours_3);
            case UNIT_MINUTES:
                if ((value10 == 1) && (value100 != 11)) {
                    return context.getString(R.string.minutes_1);
                } else if ((value10 > 1) && (value10 < 5) && ((value100 < 12) || (value100 > 14))) {
                    return context.getString(R.string.minutes_2);
                } else return context.getString(R.string.minutes_3);
            case UNIT_SECS:
                if ((value10 == 1) && (value100 != 11)) {
                    return context.getString(R.string.secs_1);
                } else if ((value10 > 1) && (value10 < 5) && ((value100 < 12) || (value100 > 14))) {
                    return context.getString(R.string.secs_2);
                } else return context.getString(R.string.secs_3);
            default:
                return "";
        }
    }

    public static String getTimeToWait(Context context, Calendar dateAndTime) {
        Calendar currentDateAndTime = new GregorianCalendar();
        long duration = dateAndTime.getTimeInMillis() - currentDateAndTime.getTimeInMillis();

        if (duration > 31557600000L) { //если разница более 1 года
            long value = duration / 31557600000L;
            return context.getString(R.string.over) + " " + value + " " + getGenitiveUnit(context, value, RoundDate.UNIT_YEARS);
        } else if (duration > 2629800000L) { //если разница более 1 месяца
            long value = duration / 2629800000L;
            return context.getString(R.string.over) + " " + value + " " + getGenitiveUnit(context, value, RoundDate.UNIT_MONTHS);
        } else if (duration > 86400000L) { //если разница более 1 дня
            long value = duration / 86400000L;
            return value + " " + getUnit(context, value, RoundDate.UNIT_DAYS);
        } else if (duration > 3600000L) { //если разница более 1 часа
            long value = duration / 3600000L;
            return value + " " + getUnit(context, value, RoundDate.UNIT_HOURS);
        } else if (duration > 0L) { //если разница менее 1 часа
            long value = 1L;
            return context.getString(R.string.less) + " " + value + " " + getGenitiveUnit(context, value, RoundDate.UNIT_HOURS);
        } else { //если разница отрицательная
            return context.getString(R.string.already_passed);
        }
    }

    private static String getGenitiveUnit(Context context, long value, int unit) {
        int value10 = (int) value % 10;
        int value100 = (int) value % 100;

        switch (unit) {
            case UNIT_YEARS:
                if ((value10 == 1) && (value100 != 11)) {
                    return context.getString(R.string.years_4);
                } else return context.getString(R.string.years_3);
            case UNIT_MONTHS:
                if ((value10 == 1) && (value100 != 11)) {
                    return context.getString(R.string.months_4);
                } else return context.getString(R.string.months_3);
            case UNIT_WEEKS:
                if ((value10 == 1) && (value100 != 11)) {
                    return context.getString(R.string.weeks_4);
                } else return context.getString(R.string.weeks_3);
            case UNIT_DAYS:
                if ((value10 == 1) && (value100 != 11)) {
                    return context.getString(R.string.days_4);
                } else return context.getString(R.string.days_3);
            case UNIT_HOURS:
                if ((value10 == 1) && (value100 != 11)) {
                    return context.getString(R.string.hours_4);
                } else return context.getString(R.string.hours_3);
            case UNIT_MINUTES:
                if ((value10 == 1) && (value100 != 11)) {
                    return context.getString(R.string.minutes_4);
                } else return context.getString(R.string.minutes_3);
            case UNIT_SECS:
                if ((value10 == 1) && (value100 != 11)) {
                    return context.getString(R.string.secs_4);
                } else return context.getString(R.string.secs_3);
            default:
                return "";
        }
    }
}
