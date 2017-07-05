package ru.alexanderdolinsky.rounddate;

/**
 * Created by Alexsvet on 06.06.2017.
 * Класс HidSet - скрытые настройки
 */

public  class HidSet {
    // периоды расчета в годах, месяцах, ...
    static final long PERIOD_IN_YEARS = 100L;
    static final long PERIOD_IN_MONTHS = PERIOD_IN_YEARS * 12;
    static final long PERIOD_IN_WEEKS = PERIOD_IN_YEARS * 52;
    static final long PERIOD_IN_DAYS = PERIOD_IN_WEEKS * 7;
    static final long PERIOD_IN_HOURS = PERIOD_IN_DAYS * 24;
    static final long PERIOD_IN_MINUTES = PERIOD_IN_HOURS * 60;
    static final long PERIOD_IN_SECS = PERIOD_IN_MINUTES * 60;
    // количество круглых дат, выводимых на главной странице приложения
    static final long NUMBER_RD_ON_PAGE = 100;
    // кратности круглых дат в зависимости от их редкости
    static final long MULT_STANDART_RD_IN_YEARS = 1;
    static final long MULT_RARE_RD_IN_YEARS = 5; // должно быть кратно MULT_STANDART_RD_IN_YEARS
    static final long MULT_VERY_RARE_RD_IN_YEARS = 25;// должно быть кратно

    static final long MULT_STANDART_RD_IN_MONTHS = 10;
    static final long MULT_RARE_RD_IN_MONTHS = 100;// должно быть кратно MULT_STANDART_RD_IN_MONTHS
    static final long MULT_VERY_RARE_RD_IN_MONTHS = 500;// должно быть кратно MULT_RARE_RD_IN_MONTHS

    static final long MULT_STANDART_RD_IN_WEEKS = 50;
    static final long MULT_RARE_RD_IN_WEEKS = 500;// должно быть кратно MULT_STANDART_RD_IN_WEEKS
    static final long MULT_VERY_RARE_RD_IN_WEEKS = 1000;// должно быть кратно MULT_RARE_RD_IN_WEEKS

    static final long MULT_STANDART_RD_IN_DAYS = 500;
    static final long MULT_RARE_RD_IN_DAYS = 1000;// должно быть кратно MULT_STANDART_RD_IN_DAYS
    static final long MULT_VERY_RARE_RD_IN_DAYS = 5000;// должно быть кратно MULT_RARE_RD_IN_DAYS

    static final long MULT_STANDART_RD_IN_HOURS = 5000;
    static final long MULT_RARE_RD_IN_HOURS = 25000;// должно быть кратно MULT_STANDART_RD_IN_HOURS
    static final long MULT_VERY_RARE_RD_IN_HOURS = 100000;// должно быть кратно MULT_RARE_RD_IN_HOURS

    static final long MULT_STANDART_RD_IN_MINUTES = 100000;
    static final long MULT_RARE_RD_IN_MINUTES = 1000000;// должно быть кратно MULT_STANDART_RD_IN_MINUTES
    static final long MULT_VERY_RARE_RD_IN_MINUTES = 5000000;// должно быть кратно MULT_RARE_RD_IN_MINUTES

    static final long MULT_STANDART_RD_IN_SECS = 10000000;
    static final long MULT_RARE_RD_IN_SECS = 100000000;// должно быть кратно MULT_STANDART_RD_IN_SECS
    static final long MULT_VERY_RARE_RD_IN_SECS = 500000000;// должно быть кратно MULT_RARE_RD_IN_SECS



}
