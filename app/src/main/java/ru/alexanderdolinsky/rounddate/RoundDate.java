package ru.alexanderdolinsky.rounddate;

import android.content.Context;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by Alexsvet on 04.06.2017.
 * Класс Круглая дата
 */

class RoundDate {
    // id Круглой даты
    private long id;
    // значение Круглой даты
    private long valueOf;
    // размерность Круглой даты
    private int unit;
    // дата/время Круглой даты
    private Calendar dateAndTime;
    // id События
    private long idEvent;
    // название События
    private String nameEvent;
    /* редкость Круглой даты
    0 - обычная Курглая дата
    1 - редкая Круглая дата
    2 - очень редкая Круглая дата
    */
    private int rare;
    /* важность Круглой даты
    0 - обычная Круглая дата
    1 - важная Круглой даты
    2 - очень важная Круглая дата
    3 - неважная Круглая дат
     */
    private int important;

    // константы размерностей Круглой даты
    public final static int UNIT_YEARS = 1;
    public final static int UNIT_MONTHS = 2;
    public final static int UNIT_WEEKS = 3;
    public final static int UNIT_DAYS = 4;
    public final static int UNIT_HOURS = 5;
    public final static int UNIT_MINUTES = 6;
    public final static int UNIT_SECS = 7;



    RoundDate() {
        this.id = -1;
        this.valueOf = 1;
        this.unit = 3;
        this.dateAndTime = new GregorianCalendar();
        this.idEvent = -1;
        this.nameEvent = "Standart Event";
        this.rare = 0;
        this.important = 0;
    }

    RoundDate(long id,
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

}
