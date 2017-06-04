package ru.alexanderdolinsky.rounddate;

import android.content.SharedPreferences;
import static android.content.Context.MODE_PRIVATE;
import static android.provider.Settings.Global.getString;

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
