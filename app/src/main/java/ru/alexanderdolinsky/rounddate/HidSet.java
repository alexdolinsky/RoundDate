package ru.alexanderdolinsky.rounddate;

/**
 * Created by Alexsvet on 06.06.2017.
 * Класс HidSet - скрытые настройки
 */

public  class HidSet {
    // периоды расчета в годах, месяцах, ...
    public static final long PERIOD_IN_YEARS = 100L;
    public static final long PERIOD_IN_MONTHS = PERIOD_IN_YEARS*12;
    public static final long PERIOD_IN_WEEKS = PERIOD_IN_YEARS*52;
    public static final long PERIOD_IN_DAYS = PERIOD_IN_WEEKS*7;
    public static final long PERIOD_IN_HOURS = PERIOD_IN_DAYS*24;
    public static final long PERIOD_IN_MINUTES = PERIOD_IN_HOURS*60;
    public static final long PERIOD_IN_SECS = PERIOD_IN_MINUTES*60;
    // количество круглых дат, выводимых на главной странице приложения
    public static final long NUMBER_RD_ON_PAGE = 100;
    // кратности круглых дат в зависимости от их редкости
    public static final long MULT_STANDART_RD_IN_YEARS = 1;
    public static final long MULT_RARE_RD_IN_YEARS = 5;
    public static final long MULT_VERY_RARE_RD_IN_YEARS = 25;

    public static final long MULT_STANDART_RD_IN_MONTHS = 10;
    public static final long MULT_RARE_RD_IN_MONTHS = 50;
    public static final long MULT_VERY_RARE_RD_IN_MONTHS = 100;

    public static final long MULT_STANDART_RD_IN_WEEKS = 50;
    public static final long MULT_RARE_RD_IN_WEEKS = 100;
    public static final long MULT_VERY_RARE_RD_IN_WEEKS = 500;

    public static final long MULT_STANDART_RD_IN_DAYS = 500;
    public static final long MULT_RARE_RD_IN_DAYS = 1000;
    public static final long MULT_VERY_RARE_RD_IN_DAYS = 5000;

    public static final long MULT_STANDART_RD_IN_HOURS = 5000;
    public static final long MULT_RARE_RD_IN_HOURS = 25000;
    public static final long MULT_VERY_RARE_RD_IN_HOURS = 100000;

    public static final long MULT_STANDART_RD_IN_MINUTES = 100000;
    public static final long MULT_RARE_RD_IN_MINUTES = 1000000;
    public static final long MULT_VERY_RARE_RD_IN_MINUTES = 5000000;

    public static final long MULT_STANDART_RD_IN_SECS = 10000000;
    public static final long MULT_RARE_RD_IN_SECS = 100000000;
    public static final long MULT_VERY_RARE_RD_IN_SECS = 500000000;



}
