package ru.alexanderdolinsky.rounddate;

/**
 * Created by Alexsvet on 04.06.2017.
 * Класс Группа событий
 */

class EventGroup {
    // Идентификатор Группы событий и БД
    // -1 - значение не определено
    private long id;
    // наименования Группы событий
    private String name;
    // источник настроек отслеживания
    private int sourceTrackSettings;
    // константы источников настроек отслеживания
    static final int SOURCE_TRACK_SETTINGS_APP = 0;
    static final int SOURCE_TRACK_SETTINGS_GROUP = 1;
    // индивидуальные настройки отслеживания Группы событий
    private TrackSettings trackSettings;

    // конструктор по умолчанию
    EventGroup() {
        this.id = -1;
        this.name = "Мои события";
        this.sourceTrackSettings = SOURCE_TRACK_SETTINGS_APP;
        this.trackSettings = new TrackSettings(0,0,0,0,0,0,0);
    }
    // конструктор при создании новой Группы событий
    EventGroup(long id, String name, int sourceTrackSettings, TrackSettings trackSettings) {
        this.id = id;
        this.name = name;
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

    int getSourceTrackSettings() {
        return sourceTrackSettings;
    }

    void setSourceTrackSettings(int sourceTrackSettings) {
        this.sourceTrackSettings = sourceTrackSettings;
    }

    TrackSettings getTrackSettings() {
        return trackSettings;
    }

    void setTrackSettings(TrackSettings trackSettings) {
        this.trackSettings = trackSettings;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
