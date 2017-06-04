package ru.alexanderdolinsky.rounddate;

/**
 * Created by Alexsvet on 04.06.2017.
 */

class EventGroup {
    // Идентификатор Группы событий и БД
    // -1 - значение не определено
    private long idEventGroup;
    // наименования Группы событий
    private String nameEventGroup;
    // источник настроек отслеживания
    // 0 - общие настройки отслеживания приложения
    // 1 - индивидуальные настройки отслеживания Группы событий
    private int sourceTrackSettings;
    // индивидуальные настройки отслеживания Группы событий
    private TrackSettings eventGroupTrackSettings;


    // конструктор по умолчанию
    EventGroup() {
        this.idEventGroup = -1;
        this.nameEventGroup = "Мои события";
        this.sourceTrackSettings = 0;
        this.eventGroupTrackSettings = new TrackSettings(0,0,0,0,0,0,0);
    }
    // конструктор при создании новой Группы событий
    EventGroup(long idEventGroup, String nameEventGroup, int sourceTrackSettings, TrackSettings eventGroupTrackSettings) {
        this.idEventGroup = idEventGroup;
        this.nameEventGroup = nameEventGroup;
        this.sourceTrackSettings = sourceTrackSettings;
        this.eventGroupTrackSettings = eventGroupTrackSettings;
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

    public int getSourceTrackSettings() {
        return sourceTrackSettings;
    }

    public void setSourceTrackSettings(int sourceTrackSettings) {
        this.sourceTrackSettings = sourceTrackSettings;
    }

    public TrackSettings getEventGroupTrackSettings() {
        return eventGroupTrackSettings;
    }

    public void setEventGroupTrackSettings(TrackSettings eventGroupTrackSettings) {
        this.eventGroupTrackSettings = eventGroupTrackSettings;
    }
}
