package ru.alexanderdolinsky.rounddate;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by Alexsvet on 04.06.2017.
 * Класс Событие
 */

class Event {
    // идентификатор События в БД
    // - 1 - значение не определено
    private long id;
    // наименование события
    private String name;
    // комментарий к событию
    private String comment;
    // id связанной Группы событий
    private long idEventGroup;
    // наименование связанной группы событий
    private String nameEventGroup;
    // Дата/время события
    private Calendar dateAndTime;
    // источник настроек отслеживания
    // 0 - общие настройки отслеживания приложения
    // 1 - настройки отслеживания Группы событий
    // 2 - индивидуальные настройки отслеживания События
    private int sourceTrackSettings;
    // индивидуальные настройки отслеживания События
    private TrackSettings trackSettings;

    // конструктор по умолчанию
    Event() {
        this.id = -1; // - 1 - значение не определено
        this.name = "Событие по умолчанию";
        this.comment = "Комментарий по умолчанию";
        this.idEventGroup = -1; // - 1 - значение не определено
        this.nameEventGroup = "Группа событий по умолчанию";
        this.dateAndTime = new GregorianCalendar();
        this.sourceTrackSettings = 1;
        this.trackSettings = new TrackSettings(0,0,0,0,0,0,0);
    }

    // конструктор при создании нового События
    Event(long id,
          String name,
          String comment,
          long idEventGroup,
          String nameEventGroup,
          Calendar dateAndTime,
          int sourceTrackSettings,
          TrackSettings trackSettings) {
        this.id = id;
        this.name = name;
        this.comment = comment;
        this.idEventGroup = idEventGroup;
        this.nameEventGroup = nameEventGroup;
        this.dateAndTime = dateAndTime;
        this.sourceTrackSettings = sourceTrackSettings;
        this.trackSettings = trackSettings;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
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

    public Calendar getDateAndTime() {
        return dateAndTime;
    }

    public void setDateAndTime(Calendar dateAndTime) {
        this.dateAndTime = dateAndTime;
    }

    public int getSourceTrackSettings() {
        return sourceTrackSettings;
    }

    public void setSourceTrackSettings(int sourceTrackSettings) {
        this.sourceTrackSettings = sourceTrackSettings;
    }

    public TrackSettings getTrackSettings() {
        return trackSettings;
    }

    public void setTrackSettings(TrackSettings trackSettings) {
        this.trackSettings = trackSettings;
    }

    public String getDate() {

        SimpleDateFormat sdfDate = new SimpleDateFormat("dd/MM/yyyy");
        return sdfDate.format(this.getDateAndTime().getTime());
    }

    public String getTime() {
        SimpleDateFormat sdfTime = new SimpleDateFormat("kk:mm");
        return sdfTime.format(this.getDateAndTime().getTime());
    }
}
