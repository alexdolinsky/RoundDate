package ru.alexanderdolinsky.rounddate;


import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

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

    public static final int SOURCE_TRACK_SETTINGS_APP = 0;
    public static final int SOURCE_TRACK_SETTINGS_GROUP = 1;
    public static final int SOURCE_TRACK_SETTINGS_EVENT = 2;

    public static final int YEAR = 0;
    public static final int MONTH = 1;
    public static final int WEEK = 2;
    public static final int DAY = 3;
    public static final int HOUR = 4;
    public static final int MINUTE = 5;
    public static final int SEC = 6;

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

    public List<RoundDate> getRoundDates(int unit, int value) {

        ArrayList<RoundDate> roundDates = new ArrayList<>();

        int i;

        switch (unit) {
            case YEAR:

                switch (value) {
                    case TrackSettings.STANDART:
                        i = 1;
                        while (i * HidSet.MULT_STANDART_RD_IN_YEARS <= HidSet.PERIOD_IN_YEARS) {
                            long valueOf = i * HidSet.MULT_STANDART_RD_IN_YEARS;
                            Calendar dateAndTime = new GregorianCalendar();
                            dateAndTime.setTimeInMillis(this.getDateAndTime().getTimeInMillis());
                            dateAndTime.add(Calendar.YEAR, (int) valueOf);
                            int rare = RoundDate.STANDART;
                            if (valueOf % HidSet.MULT_RARE_RD_IN_YEARS == 0) {
                                rare = RoundDate.RARE;
                            }
                            if (valueOf % HidSet.MULT_VERY_RARE_RD_IN_YEARS == 0) {
                                rare = RoundDate.VERY_RARE;
                            }

                            Log.d("MyLog", " valueOf = " + valueOf +
                                    " dateAndTime = " + dateAndTime.getTimeInMillis() / 31557600000L
                            );

                            roundDates.add(new RoundDate(-1, valueOf, RoundDate.UNIT_YEARS, dateAndTime, this.getId(), this.getName(), rare, rare));
                            i++;
                        }


                    case TrackSettings.RARE:
                        i = 1;
                        while (i * HidSet.MULT_RARE_RD_IN_YEARS <= HidSet.PERIOD_IN_YEARS) {
                            long valueOf = i * HidSet.MULT_RARE_RD_IN_YEARS;
                            Calendar dateAndTime = new GregorianCalendar();
                            dateAndTime.setTimeInMillis(this.getDateAndTime().getTimeInMillis());
                            dateAndTime.add(Calendar.YEAR, (int) valueOf);
                            int rare = RoundDate.RARE;
                            if (valueOf % HidSet.MULT_VERY_RARE_RD_IN_YEARS == 0) {
                                rare = RoundDate.VERY_RARE;
                            }
                            roundDates.add(new RoundDate(-1, valueOf, RoundDate.UNIT_YEARS, dateAndTime, this.getId(), this.getName(), rare, rare));
                            i++;
                        }

                    case TrackSettings.VERY_RARE:
                        i = 1;
                        while (i * HidSet.MULT_VERY_RARE_RD_IN_YEARS <= HidSet.PERIOD_IN_YEARS) {
                            long valueOf = i * HidSet.MULT_VERY_RARE_RD_IN_YEARS;
                            Calendar dateAndTime = new GregorianCalendar();
                            dateAndTime.setTimeInMillis(this.getDateAndTime().getTimeInMillis());
                            dateAndTime.add(Calendar.YEAR, (int) valueOf);
                            int rare = RoundDate.VERY_RARE;
                            roundDates.add(new RoundDate(-1, valueOf, RoundDate.UNIT_YEARS, dateAndTime, this.getId(), this.getName(), rare, rare));
                            i++;
                        }

                }

                return roundDates;


            case MONTH:
                switch (value) {
                    case TrackSettings.STANDART:
                        i = 1;
                        while (i * HidSet.MULT_STANDART_RD_IN_MONTHS <= HidSet.PERIOD_IN_MONTHS) {
                            long valueOf = i * HidSet.MULT_STANDART_RD_IN_MONTHS;
                            Calendar dateAndTime = new GregorianCalendar();
                            dateAndTime.setTimeInMillis(this.getDateAndTime().getTimeInMillis());
                            dateAndTime.add(Calendar.MONTH, (int) valueOf);
                            int rare = RoundDate.STANDART;
                            if (valueOf % HidSet.MULT_RARE_RD_IN_MONTHS == 0) {
                                rare = RoundDate.RARE;
                            }
                            if (valueOf % HidSet.MULT_VERY_RARE_RD_IN_MONTHS == 0) {
                                rare = RoundDate.VERY_RARE;
                            }
                            roundDates.add(new RoundDate(-1, valueOf, RoundDate.UNIT_MONTHS, dateAndTime, this.getId(), this.getName(), rare, rare));
                            i++;
                        }

                    case TrackSettings.RARE:
                        i = 1;
                        while (i * HidSet.MULT_RARE_RD_IN_MONTHS <= HidSet.PERIOD_IN_MONTHS) {
                            long valueOf = i * HidSet.MULT_RARE_RD_IN_MONTHS;
                            Calendar dateAndTime = new GregorianCalendar();
                            dateAndTime.setTimeInMillis(this.getDateAndTime().getTimeInMillis());
                            dateAndTime.add(Calendar.MONTH, (int) valueOf);
                            int rare = RoundDate.RARE;
                            if (valueOf % HidSet.MULT_VERY_RARE_RD_IN_MONTHS == 0) {
                                rare = RoundDate.VERY_RARE;
                            }
                            roundDates.add(new RoundDate(-1, valueOf, RoundDate.UNIT_MONTHS, dateAndTime, this.getId(), this.getName(), rare, rare));
                            i++;
                        }

                    case TrackSettings.VERY_RARE:
                        i = 1;
                        while (i * HidSet.MULT_VERY_RARE_RD_IN_MONTHS <= HidSet.PERIOD_IN_MONTHS) {
                            long valueOf = i * HidSet.MULT_VERY_RARE_RD_IN_MONTHS;
                            Calendar dateAndTime = new GregorianCalendar();
                            dateAndTime.setTimeInMillis(this.getDateAndTime().getTimeInMillis());
                            dateAndTime.add(Calendar.MONTH, (int) valueOf);
                            int rare = RoundDate.VERY_RARE;
                            roundDates.add(new RoundDate(-1, valueOf, RoundDate.UNIT_MONTHS, dateAndTime, this.getId(), this.getName(), rare, rare));
                            i++;
                        }

                }

                return roundDates;


            case WEEK:
                switch (value) {
                    case TrackSettings.STANDART:
                        i = 1;
                        while (i * HidSet.MULT_STANDART_RD_IN_WEEKS <= HidSet.PERIOD_IN_WEEKS) {
                            long valueOf = i * HidSet.MULT_STANDART_RD_IN_WEEKS;
                            Calendar dateAndTime = new GregorianCalendar();
                            dateAndTime.setTimeInMillis(this.getDateAndTime().getTimeInMillis());
                            dateAndTime.add(Calendar.WEEK_OF_MONTH, (int) valueOf);
                            int rare = RoundDate.STANDART;
                            if (valueOf % HidSet.MULT_RARE_RD_IN_WEEKS == 0) {
                                rare = RoundDate.RARE;
                            }
                            if (valueOf % HidSet.MULT_VERY_RARE_RD_IN_WEEKS == 0) {
                                rare = RoundDate.VERY_RARE;
                            }
                            roundDates.add(new RoundDate(-1, valueOf, RoundDate.UNIT_WEEKS, dateAndTime, this.getId(), this.getName(), rare, rare));
                            i++;
                        }

                    case TrackSettings.RARE:
                        i = 1;
                        while (i * HidSet.MULT_RARE_RD_IN_WEEKS <= HidSet.PERIOD_IN_WEEKS) {
                            long valueOf = i * HidSet.MULT_RARE_RD_IN_WEEKS;
                            Calendar dateAndTime = new GregorianCalendar();
                            dateAndTime.setTimeInMillis(this.getDateAndTime().getTimeInMillis());
                            dateAndTime.add(Calendar.WEEK_OF_MONTH, (int) valueOf);
                            int rare = RoundDate.RARE;
                            if (valueOf % HidSet.MULT_VERY_RARE_RD_IN_WEEKS == 0) {
                                rare = RoundDate.VERY_RARE;
                            }
                            roundDates.add(new RoundDate(-1, valueOf, RoundDate.UNIT_WEEKS, dateAndTime, this.getId(), this.getName(), rare, rare));
                            i++;
                        }

                    case TrackSettings.VERY_RARE:
                        i = 1;
                        while (i * HidSet.MULT_VERY_RARE_RD_IN_WEEKS <= HidSet.PERIOD_IN_WEEKS) {
                            long valueOf = i * HidSet.MULT_VERY_RARE_RD_IN_WEEKS;
                            Calendar dateAndTime = new GregorianCalendar();
                            dateAndTime.setTimeInMillis(this.getDateAndTime().getTimeInMillis());
                            dateAndTime.add(Calendar.WEEK_OF_MONTH, (int) valueOf);
                            int rare = RoundDate.VERY_RARE;
                            roundDates.add(new RoundDate(-1, valueOf, RoundDate.UNIT_WEEKS, dateAndTime, this.getId(), this.getName(), rare, rare));
                            i++;
                        }

                }

                return roundDates;


            case DAY:
                switch (value) {
                    case TrackSettings.STANDART:
                        i = 1;
                        while (i * HidSet.MULT_STANDART_RD_IN_DAYS <= HidSet.PERIOD_IN_DAYS) {
                            long valueOf = i * HidSet.MULT_STANDART_RD_IN_DAYS;
                            Calendar dateAndTime = new GregorianCalendar();
                            dateAndTime.setTimeInMillis(this.getDateAndTime().getTimeInMillis());
                            dateAndTime.add(Calendar.DAY_OF_MONTH, (int) valueOf);
                            int rare = RoundDate.STANDART;
                            if (valueOf % HidSet.MULT_RARE_RD_IN_DAYS == 0) {
                                rare = RoundDate.RARE;
                            }
                            if (valueOf % HidSet.MULT_VERY_RARE_RD_IN_DAYS == 0) {
                                rare = RoundDate.VERY_RARE;
                            }
                            roundDates.add(new RoundDate(-1, valueOf, RoundDate.UNIT_DAYS, dateAndTime, this.getId(), this.getName(), rare, rare));
                            i++;
                        }

                    case TrackSettings.RARE:
                        i = 1;
                        while (i * HidSet.MULT_RARE_RD_IN_DAYS <= HidSet.PERIOD_IN_DAYS) {
                            long valueOf = i * HidSet.MULT_RARE_RD_IN_DAYS;
                            Calendar dateAndTime = new GregorianCalendar();
                            dateAndTime.setTimeInMillis(this.getDateAndTime().getTimeInMillis());
                            dateAndTime.add(Calendar.DAY_OF_MONTH, (int) valueOf);
                            int rare = RoundDate.RARE;
                            if (valueOf % HidSet.MULT_VERY_RARE_RD_IN_DAYS == 0) {
                                rare = RoundDate.VERY_RARE;
                            }
                            roundDates.add(new RoundDate(-1, valueOf, RoundDate.UNIT_DAYS, dateAndTime, this.getId(), this.getName(), rare, rare));
                            i++;
                        }

                    case TrackSettings.VERY_RARE:
                        i = 1;
                        while (i * HidSet.MULT_VERY_RARE_RD_IN_DAYS <= HidSet.PERIOD_IN_DAYS) {
                            long valueOf = i * HidSet.MULT_VERY_RARE_RD_IN_DAYS;
                            Calendar dateAndTime = new GregorianCalendar();
                            dateAndTime.setTimeInMillis(this.getDateAndTime().getTimeInMillis());
                            dateAndTime.add(Calendar.DAY_OF_MONTH, (int) valueOf);
                            int rare = RoundDate.VERY_RARE;
                            roundDates.add(new RoundDate(-1, valueOf, RoundDate.UNIT_DAYS, dateAndTime, this.getId(), this.getName(), rare, rare));
                            i++;
                        }

                }

                return roundDates;


            case HOUR:
                switch (value) {
                    case TrackSettings.STANDART:
                        i = 1;
                        while (i * HidSet.MULT_STANDART_RD_IN_HOURS <= HidSet.PERIOD_IN_HOURS) {
                            long valueOf = i * HidSet.MULT_STANDART_RD_IN_HOURS;
                            Calendar dateAndTime = new GregorianCalendar();
                            dateAndTime.setTimeInMillis(this.getDateAndTime().getTimeInMillis() + valueOf * 3600000);
                            int rare = RoundDate.STANDART;
                            if (valueOf % HidSet.MULT_RARE_RD_IN_HOURS == 0) {
                                rare = RoundDate.RARE;
                            }
                            if (valueOf % HidSet.MULT_VERY_RARE_RD_IN_HOURS == 0) {
                                rare = RoundDate.VERY_RARE;
                            }
                            roundDates.add(new RoundDate(-1, valueOf, RoundDate.UNIT_HOURS, dateAndTime, this.getId(), this.getName(), rare, rare));
                            i++;
                        }

                    case TrackSettings.RARE:
                        i = 1;
                        while (i * HidSet.MULT_RARE_RD_IN_HOURS <= HidSet.PERIOD_IN_HOURS) {
                            long valueOf = i * HidSet.MULT_RARE_RD_IN_HOURS;
                            Calendar dateAndTime = new GregorianCalendar();
                            dateAndTime.setTimeInMillis(this.getDateAndTime().getTimeInMillis() + valueOf * 3600000);
                            int rare = RoundDate.RARE;
                            if (valueOf % HidSet.MULT_VERY_RARE_RD_IN_HOURS == 0) {
                                rare = RoundDate.VERY_RARE;
                            }
                            roundDates.add(new RoundDate(-1, valueOf, RoundDate.UNIT_HOURS, dateAndTime, this.getId(), this.getName(), rare, rare));
                            i++;
                        }

                    case TrackSettings.VERY_RARE:
                        i = 1;
                        while (i * HidSet.MULT_VERY_RARE_RD_IN_HOURS <= HidSet.PERIOD_IN_HOURS) {
                            long valueOf = i * HidSet.MULT_VERY_RARE_RD_IN_HOURS;
                            Calendar dateAndTime = new GregorianCalendar();
                            dateAndTime.setTimeInMillis(this.getDateAndTime().getTimeInMillis() + valueOf * 3600000);
                            int rare = RoundDate.VERY_RARE;
                            roundDates.add(new RoundDate(-1, valueOf, RoundDate.UNIT_HOURS, dateAndTime, this.getId(), this.getName(), rare, rare));
                            i++;
                        }

                }

                return roundDates;


            case MINUTE:
                switch (value) {
                    case TrackSettings.STANDART:
                        i = 1;
                        while (i * HidSet.MULT_STANDART_RD_IN_MINUTES <= HidSet.PERIOD_IN_MINUTES) {
                            long valueOf = i * HidSet.MULT_STANDART_RD_IN_MINUTES;
                            Calendar dateAndTime = new GregorianCalendar();
                            dateAndTime.setTimeInMillis(this.getDateAndTime().getTimeInMillis() + valueOf * 60000);
                            int rare = RoundDate.STANDART;
                            if (valueOf % HidSet.MULT_RARE_RD_IN_MINUTES == 0) {
                                rare = RoundDate.RARE;
                            }
                            if (valueOf % HidSet.MULT_VERY_RARE_RD_IN_MINUTES == 0) {
                                rare = RoundDate.VERY_RARE;
                            }
                            roundDates.add(new RoundDate(-1, valueOf, RoundDate.UNIT_MINUTES, dateAndTime, this.getId(), this.getName(), rare, rare));
                            i++;
                        }

                    case TrackSettings.RARE:
                        i = 1;
                        while (i * HidSet.MULT_RARE_RD_IN_MINUTES <= HidSet.PERIOD_IN_MINUTES) {
                            long valueOf = i * HidSet.MULT_RARE_RD_IN_MINUTES;
                            Calendar dateAndTime = new GregorianCalendar();
                            dateAndTime.setTimeInMillis(this.getDateAndTime().getTimeInMillis() + valueOf * 60000);
                            int rare = RoundDate.RARE;
                            if (valueOf % HidSet.MULT_VERY_RARE_RD_IN_MINUTES == 0) {
                                rare = RoundDate.VERY_RARE;
                            }
                            roundDates.add(new RoundDate(-1, valueOf, RoundDate.UNIT_MINUTES, dateAndTime, this.getId(), this.getName(), rare, rare));
                            i++;
                        }

                    case TrackSettings.VERY_RARE:
                        i = 1;
                        while (i * HidSet.MULT_VERY_RARE_RD_IN_MINUTES <= HidSet.PERIOD_IN_MINUTES) {
                            long valueOf = i * HidSet.MULT_VERY_RARE_RD_IN_MINUTES;
                            Calendar dateAndTime = new GregorianCalendar();
                            dateAndTime.setTimeInMillis(this.getDateAndTime().getTimeInMillis() + valueOf * 60000);
                            int rare = RoundDate.VERY_RARE;
                            roundDates.add(new RoundDate(-1, valueOf, RoundDate.UNIT_MINUTES, dateAndTime, this.getId(), this.getName(), rare, rare));
                            i++;
                        }

                }

                return roundDates;


            case SEC:
                switch (value) {
                    case TrackSettings.STANDART:
                        i = 1;
                        while (i * HidSet.MULT_STANDART_RD_IN_SECS <= HidSet.PERIOD_IN_SECS) {
                            long valueOf = i * HidSet.MULT_STANDART_RD_IN_SECS;
                            Calendar dateAndTime = this.getDateAndTime();
                            dateAndTime.setTimeInMillis(dateAndTime.getTimeInMillis() + valueOf * 1000);
                            int rare = RoundDate.STANDART;
                            if (valueOf % HidSet.MULT_RARE_RD_IN_SECS == 0) {
                                rare = RoundDate.RARE;
                            }
                            if (valueOf % HidSet.MULT_VERY_RARE_RD_IN_SECS == 0) {
                                rare = RoundDate.VERY_RARE;
                            }
                            roundDates.add(new RoundDate(-1, valueOf, RoundDate.UNIT_SECS, dateAndTime, this.getId(), this.getName(), rare, rare));
                            i++;
                        }

                    case TrackSettings.RARE:
                        i = 1;
                        while (i * HidSet.MULT_RARE_RD_IN_SECS <= HidSet.PERIOD_IN_SECS) {
                            long valueOf = i * HidSet.MULT_RARE_RD_IN_SECS;
                            Calendar dateAndTime = this.getDateAndTime();
                            dateAndTime.setTimeInMillis(dateAndTime.getTimeInMillis() + valueOf * 1000);
                            int rare = RoundDate.RARE;
                            if (valueOf % HidSet.MULT_VERY_RARE_RD_IN_SECS == 0) {
                                rare = RoundDate.VERY_RARE;
                            }
                            roundDates.add(new RoundDate(-1, valueOf, RoundDate.UNIT_SECS, dateAndTime, this.getId(), this.getName(), rare, rare));
                            i++;
                        }

                    case TrackSettings.VERY_RARE:
                        i = 1;
                        while (i * HidSet.MULT_VERY_RARE_RD_IN_SECS <= HidSet.PERIOD_IN_SECS) {
                            long valueOf = i * HidSet.MULT_VERY_RARE_RD_IN_SECS;
                            Calendar dateAndTime = this.getDateAndTime();
                            dateAndTime.setTimeInMillis(dateAndTime.getTimeInMillis() + valueOf * 1000);
                            int rare = RoundDate.VERY_RARE;
                            roundDates.add(new RoundDate(-1, valueOf, RoundDate.UNIT_SECS, dateAndTime, this.getId(), this.getName(), rare, rare));
                            i++;
                        }

                }

                return roundDates;


            default:
                return null;
        }

    }
}
