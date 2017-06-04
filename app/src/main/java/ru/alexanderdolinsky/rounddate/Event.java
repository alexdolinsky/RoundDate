package ru.alexanderdolinsky.rounddate;


import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by Alexsvet on 04.06.2017.
 */

class Event {
    // идентификатор События в БД
    // - 1 - значение не определено
    private long idEvent;
    // наименование события
    private String nameEvent;
    // комментарий к событию
    private String commentEvent;
    // id связанной Группы событий
    private long idEventGroup;
    // наименование связанной группы событий
    private String nameEventGroup;
    // Дата/время события
    private Calendar dtEvent;
    // источник настроек отслеживания
    // 0 - общие настройки отслеживания приложения
    // 1 - настройки отслеживания Группы событий
    // 2 - индивидуальные настройки отслеживания События
    private int sourceTrackSettings;
    // индивидуальные настройки отслеживания События
    private TrackSettings eventTrackSettings;

    // конструктор по умолчанию
    Event() {
        this.idEvent = -1; // - 1 - значение не определено
        this.nameEvent = "Событие по умолчанию";
        this.commentEvent = "Комментарий по умолчанию";
        this.idEventGroup = -1; // - 1 - значение не определено
        this.nameEventGroup = "Группа событий по умолчанию";
        this.dtEvent = new GregorianCalendar();
        this.sourceTrackSettings = 1;
        this.eventTrackSettings = new TrackSettings(0,0,0,0,0,0,0);
    }

    // конструктор при создании нового События
    Event(long idEvent,
          String nameEvent,
          String commentEvent,
          long idEventGroup,
          String nameEventGroup,
          Calendar dtEvent,
          int sourceTrackSettings,
          TrackSettings eventTrackSettings) {
        this.idEvent = idEvent;
        this.nameEvent = nameEvent;
        this.commentEvent = commentEvent;
        this.idEventGroup = idEventGroup;
        this.nameEventGroup = nameEventGroup;
        this.dtEvent = dtEvent;
        this.sourceTrackSettings = sourceTrackSettings;
        this.eventTrackSettings = eventTrackSettings;
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

    public String getCommentEvent() {
        return commentEvent;
    }

    public void setCommentEvent(String commentEvent) {
        this.commentEvent = commentEvent;
    }

    public long getIdEventGroup() {
        return idEventGroup;
    }

    public void setIdEventGroup(long idEventGroup) {
        this.idEventGroup = idEventGroup;
    }

    public String getNameEventGroup() {
        return nameEventGroup;
    }

    public void setNameEventGroup(String nameEventGroup) {
        this.nameEventGroup = nameEventGroup;
    }

    public Calendar getDtEvent() {
        return dtEvent;
    }

    public void setDtEvent(Calendar dtEvent) {
        this.dtEvent = dtEvent;
    }

    public int getSourceTrackSettings() {
        return sourceTrackSettings;
    }

    public void setSourceTrackSettings(int sourceTrackSettings) {
        this.sourceTrackSettings = sourceTrackSettings;
    }

    public TrackSettings getEventTrackSettings() {
        return eventTrackSettings;
    }

    public void setEventTrackSettings(TrackSettings eventTrackSettings) {
        this.eventTrackSettings = eventTrackSettings;
    }
}
