package ru.alexanderdolinsky.rounddate;

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
    private String unit;
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



    RoundDate() {
        this.id = -1;
        this.valueOf = 1;
        this.unit = "days";
        this.dateAndTime = new GregorianCalendar();
        this.idEvent = -1;
        this.nameEvent = "Standart Event";
        this.rare = 0;
        this.important = 0;
    }

    RoundDate(long id,
              long valueOf,
              String unit,
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

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
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

}
