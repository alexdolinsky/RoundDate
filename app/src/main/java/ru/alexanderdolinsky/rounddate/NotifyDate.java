package ru.alexanderdolinsky.rounddate;

/**
 * Created by Alexsvet on 16.07.2017.
 * класс Уведомления
 */

class NotifyDate extends RoundDate {
    public static final long NOT_NOTIFY = -1;
    private long id;
    private long notifyDateAndTime;
    private long idRoundDate;

    NotifyDate(long id, long notifyDateAndTime, RoundDate roundDate) {
        this.id = id;
        this.valueOf = roundDate.valueOf;
        this.unit = roundDate.unit;
        this.dateAndTime = roundDate.dateAndTime;
        this.notifyDateAndTime = notifyDateAndTime;
        this.idEvent = roundDate.idEvent;
        this.nameEvent = roundDate.nameEvent;
        this.rare = roundDate.rare;
        this.important = roundDate.important;
    }

    NotifyDate() {
        super();
        this.id = NOT_NOTIFY;
        this.notifyDateAndTime = 0;
        this.idRoundDate = -1;
    }

    long getNotifyDateAndTime() {
        return notifyDateAndTime;
    }

    public void setNotifyDateAndTime(long notifyDateAndTime) {
        this.notifyDateAndTime = notifyDateAndTime;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    public long getIdRoundDate() {
        return idRoundDate;
    }

    public void setIdRoundDate(long idRoundDate) {
        this.idRoundDate = idRoundDate;
    }
}
