package ru.alexanderdolinsky.rounddate;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by Alexsvet on 06.06.2017.
 * Класс DatabaseAdapter
 */

public class DatabaseAdapter {
    final static int FIRST_ELEMENT_ID = -2;
    final static int LAST_ELEMENT_ID = -1;
    private DatabaseHelper dbHelper;
    private SQLiteDatabase database;
    private Context context;

    private static final long PERIOD_MONTH = 30 * 24 * 3600 * 1000L;
    private static final long PERIOD_WEEK = 7 * 24 * 3600 * 1000L;
    private static final long PERIOD_DAY = 24 * 3600 * 1000L;

    public DatabaseAdapter(Context context) {
        dbHelper = new DatabaseHelper(context.getApplicationContext());
        this.context = context;
    }

    public DatabaseAdapter open() {
        database = dbHelper.getWritableDatabase();
        database.execSQL("PRAGMA foreign_keys=on;");
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    void beginTransaction() {
        database.beginTransaction();
    }

    void endTransaction() {
        database.endTransaction();
    }

    void setTransactionSuccessful() {
        database.setTransactionSuccessful();
    }


    List<EventGroup> getEventGroups(boolean firstElement, boolean lastElement) {
        ArrayList<EventGroup> eventGroups = new ArrayList<>();
        String[] columns = new String[]{DatabaseHelper.COLUMN_EVENTGROUPS_ID,
                DatabaseHelper.COLUMN_EVENTGROUPS_NAME,
                DatabaseHelper.COLUMN_EVENTGROUPS_SOURCETRACKSETTINGS,
                DatabaseHelper.COLUMN_EVENTGROUPS_RDINYEARS,
                DatabaseHelper.COLUMN_EVENTGROUPS_RDINMONTHS,
                DatabaseHelper.COLUMN_EVENTGROUPS_RDINWEEKS,
                DatabaseHelper.COLUMN_EVENTGROUPS_RDINDAYS,
                DatabaseHelper.COLUMN_EVENTGROUPS_RDINHOURS,
                DatabaseHelper.COLUMN_EVENTGROUPS_RDINMINUTES,
                DatabaseHelper.COLUMN_EVENTGROUPS_RDINSECS};

        //добавляем первый элемент "Все события"
        if (firstElement) {
            eventGroups.add(new EventGroup(DatabaseAdapter.FIRST_ELEMENT_ID, getContext().getString(R.string.all_events), EventGroup.SOURCE_TRACK_SETTINGS_APP, new TrackSettings(getContext())));
        }

        Cursor cursor = database.query(DatabaseHelper.TABLE_EVENT_GROUPS, columns, null, null, null, null, DatabaseHelper.COLUMN_EVENTGROUPS_ID);
        // TODO: 17.06.2017 сделать сортировку по алфавиту, при этом первым должна идти группа Мои события
        if (cursor.moveToFirst()) {
            do {
                long id = cursor.getLong(cursor.getColumnIndex(DatabaseHelper.COLUMN_EVENTGROUPS_ID));
                String name = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_EVENTGROUPS_NAME));
                int sourceTrackSettings = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_EVENTGROUPS_SOURCETRACKSETTINGS));
                int rdInYears = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_EVENTGROUPS_RDINYEARS));
                int rdInMonths = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_EVENTGROUPS_RDINMONTHS));
                int rdInWeeks = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_EVENTGROUPS_RDINWEEKS));
                int rdInDays = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_EVENTGROUPS_RDINDAYS));
                int rdInHours = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_EVENTGROUPS_RDINHOURS));
                int rdInMinutes = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_EVENTGROUPS_RDINMINUTES));
                int rdInSecs = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_EVENTGROUPS_RDINSECS));
                eventGroups.add(new EventGroup(id, name, sourceTrackSettings, new TrackSettings(rdInYears, rdInMonths, rdInWeeks, rdInDays, rdInHours, rdInMinutes, rdInSecs)));
            }
            while (cursor.moveToNext());
            //добавляем последний элемент "Новая группа событий"
            if (lastElement) {
                eventGroups.add(new EventGroup(DatabaseAdapter.LAST_ELEMENT_ID, getContext().getString(R.string.new_event_group), EventGroup.SOURCE_TRACK_SETTINGS_APP, new TrackSettings(getContext())));
            }
        }
        cursor.close();
        return eventGroups;
    }

    public Context getContext() {
        return context;
    }

    long addEventsGroup(EventGroup eventGroup) {

        ContentValues cv = new ContentValues();
        cv.put(DatabaseHelper.COLUMN_EVENTGROUPS_NAME, eventGroup.getName());
        cv.put(DatabaseHelper.COLUMN_EVENTGROUPS_SOURCETRACKSETTINGS, eventGroup.getSourceTrackSettings());
        cv.put(DatabaseHelper.COLUMN_EVENTGROUPS_RDINYEARS, eventGroup.getTrackSettings().getRdInYears());
        cv.put(DatabaseHelper.COLUMN_EVENTGROUPS_RDINMONTHS, eventGroup.getTrackSettings().getRdInMonths());
        cv.put(DatabaseHelper.COLUMN_EVENTGROUPS_RDINWEEKS, eventGroup.getTrackSettings().getRdInWeeks());
        cv.put(DatabaseHelper.COLUMN_EVENTGROUPS_RDINDAYS, eventGroup.getTrackSettings().getRdInDays());
        cv.put(DatabaseHelper.COLUMN_EVENTGROUPS_RDINHOURS, eventGroup.getTrackSettings().getRdInHours());
        cv.put(DatabaseHelper.COLUMN_EVENTGROUPS_RDINMINUTES, eventGroup.getTrackSettings().getRdInMinutes());
        cv.put(DatabaseHelper.COLUMN_EVENTGROUPS_RDINSECS, eventGroup.getTrackSettings().getRdInSecs());

        return database.insert(DatabaseHelper.TABLE_EVENT_GROUPS, null, cv);
    }

    long addEvent(Event event) {

        ContentValues cv = new ContentValues();
        cv.put(DatabaseHelper.COLUMN_EVENTS_NAME, event.getName());
        cv.put(DatabaseHelper.COLUMN_EVENTS_COMMENT, event.getComment());
        cv.put(DatabaseHelper.COLUMN_EVENTS_ID_EVENTGROUP, event.getIdEventGroup());
        cv.put(DatabaseHelper.COLUMN_EVENTS_DATEANDTIME, event.getDateAndTime().getTimeInMillis());
        cv.put(DatabaseHelper.COLUMN_EVENTS_SOURCETRACKSETTINGS, event.getSourceTrackSettings());
        cv.put(DatabaseHelper.COLUMN_EVENTS_RDINYEARS, event.getTrackSettings().getRdInYears());
        cv.put(DatabaseHelper.COLUMN_EVENTS_RDINMONTHS, event.getTrackSettings().getRdInMonths());
        cv.put(DatabaseHelper.COLUMN_EVENTS_RDINWEEKS, event.getTrackSettings().getRdInWeeks());
        cv.put(DatabaseHelper.COLUMN_EVENTS_RDINDAYS, event.getTrackSettings().getRdInDays());
        cv.put(DatabaseHelper.COLUMN_EVENTS_RDINHOURS, event.getTrackSettings().getRdInHours());
        cv.put(DatabaseHelper.COLUMN_EVENTS_RDINMINUTES, event.getTrackSettings().getRdInMinutes());
        cv.put(DatabaseHelper.COLUMN_EVENTS_RDINSECS, event.getTrackSettings().getRdInSecs());

        return database.insert(DatabaseHelper.TABLE_EVENTS, null, cv);

    }

    boolean isEventGroupExists(String eventsGroupName, long id) {

        String selection = DatabaseHelper.COLUMN_EVENTGROUPS_NAME + "= '" + eventsGroupName + "' AND " +
                DatabaseHelper.COLUMN_EVENTGROUPS_ID + "<>" + id;

        Cursor cursor = database.query(DatabaseHelper.TABLE_EVENT_GROUPS,
                new String[]{DatabaseHelper.COLUMN_EVENTGROUPS_NAME}, selection, null, null, null, null);
        boolean b;
        if (cursor.getCount() == 0) {
            b = false;
        } else {
            b = true;
        }
        cursor.close();
        return b;
    }

    boolean isEventExists(String eventName, long id) {

        String selection = DatabaseHelper.COLUMN_EVENTS_NAME + "= '" + eventName + "' AND " +
                DatabaseHelper.COLUMN_EVENTS_ID + "<>" + id;

        Cursor cursor = database.query(DatabaseHelper.TABLE_EVENTS,
                new String[]{DatabaseHelper.COLUMN_EVENTS_NAME},
                selection, null, null, null, null);
        boolean b;
        if (cursor.getCount() == 0) {
            b = false;
        } else {
            b = true;
        }
        cursor.close();
        return b;
    }


    List<Event> getAllEvents() {
        ArrayList<Event> events = new ArrayList<>();

        Cursor cursor = database.query(DatabaseHelper.VIEW_EVENTS, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                long id = cursor.getLong(cursor.getColumnIndex(DatabaseHelper.COLUMN_VIEWEVENTS_ID));
                String name = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_VIEWEVENTS_NAME));
                String comment = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_VIEWEVENTS_COMMENT));
                long idEventGroup = cursor.getLong(cursor.getColumnIndex(DatabaseHelper.COLUMN_VIEWEVENTS_ID_EVENTGROUP));
                String nameEventGroup = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_VIEWEVENTS_EVENTGROUPNAME));
                Calendar dateAndTime = new GregorianCalendar();
                dateAndTime.setTimeInMillis(cursor.getLong(cursor.getColumnIndex(DatabaseHelper.COLUMN_VIEWEVENTS_DATEANDTIME)));
                int sourceTrackSettings = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_VIEWEVENTS_SOURCETRACKSETTINGS));
                int rdInYears = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_VIEWEVENTS_RDINYEARS));
                int rdInMonths = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_VIEWEVENTS_RDINMONTHS));
                int rdInWeeks = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_VIEWEVENTS_RDINWEEKS));
                int rdInDays = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_VIEWEVENTS_RDINDAYS));
                int rdInHours = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_VIEWEVENTS_RDINHOURS));
                int rdInMinutes = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_VIEWEVENTS_RDINMINUTES));
                int rdInSecs = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_VIEWEVENTS_RDINSECS));
                events.add(new Event(id, name, comment, idEventGroup, nameEventGroup, dateAndTime, sourceTrackSettings,
                        new TrackSettings(rdInYears, rdInMonths, rdInWeeks, rdInDays, rdInHours, rdInMinutes, rdInSecs)));
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return events;
    }

    List<Event> getEventsByEventGroupId(long idEventsGroup) {

        ArrayList<Event> events = new ArrayList<>();

        String selection = "" + DatabaseHelper.COLUMN_VIEWEVENTS_ID_EVENTGROUP + "=" + idEventsGroup;

        Cursor cursor = database.query(DatabaseHelper.VIEW_EVENTS, null, selection, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                long id = cursor.getLong(cursor.getColumnIndex(DatabaseHelper.COLUMN_VIEWEVENTS_ID));
                String name = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_VIEWEVENTS_NAME));
                String comment = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_VIEWEVENTS_COMMENT));
                long idEventGroup = cursor.getLong(cursor.getColumnIndex(DatabaseHelper.COLUMN_VIEWEVENTS_ID_EVENTGROUP));
                String nameEventGroup = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_VIEWEVENTS_EVENTGROUPNAME));
                Calendar dateAndTime = new GregorianCalendar();
                dateAndTime.setTimeInMillis(cursor.getLong(cursor.getColumnIndex(DatabaseHelper.COLUMN_VIEWEVENTS_DATEANDTIME)));
                int sourceTrackSettings = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_VIEWEVENTS_SOURCETRACKSETTINGS));
                int rdInYears = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_VIEWEVENTS_RDINYEARS));
                int rdInMonths = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_VIEWEVENTS_RDINMONTHS));
                int rdInWeeks = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_VIEWEVENTS_RDINWEEKS));
                int rdInDays = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_VIEWEVENTS_RDINDAYS));
                int rdInHours = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_VIEWEVENTS_RDINHOURS));
                int rdInMinutes = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_VIEWEVENTS_RDINMINUTES));
                int rdInSecs = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_VIEWEVENTS_RDINSECS));
                events.add(new Event(id, name, comment, idEventGroup, nameEventGroup, dateAndTime, sourceTrackSettings,
                        new TrackSettings(rdInYears, rdInMonths, rdInWeeks, rdInDays, rdInHours, rdInMinutes, rdInSecs)));
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return events;
    }

    Event getEventById(long idEvent) {
        Event event = new Event();
        String selection = "" + DatabaseHelper.COLUMN_VIEWEVENTS_ID + "=" + idEvent;

        Cursor cursor = database.query(DatabaseHelper.VIEW_EVENTS, null, selection, null, null, null, null);

        if (cursor.moveToFirst()) {
            event.setId(cursor.getLong(cursor.getColumnIndex(DatabaseHelper.COLUMN_VIEWEVENTS_ID)));
            event.setName(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_VIEWEVENTS_NAME)));
            event.setComment(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_VIEWEVENTS_COMMENT)));
            event.setIdEventGroup(cursor.getLong(cursor.getColumnIndex(DatabaseHelper.COLUMN_VIEWEVENTS_ID_EVENTGROUP)));
            event.setNameEventGroup(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_VIEWEVENTS_EVENTGROUPNAME)));
            Calendar dateAndTime = new GregorianCalendar();
            dateAndTime.setTimeInMillis(cursor.getLong(cursor.getColumnIndex(DatabaseHelper.COLUMN_VIEWEVENTS_DATEANDTIME)));
            event.setDateAndTime(dateAndTime);
            event.setSourceTrackSettings(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_VIEWEVENTS_SOURCETRACKSETTINGS)));
            event.setTrackSettings(new TrackSettings(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_VIEWEVENTS_RDINYEARS)),
                    cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_VIEWEVENTS_RDINMONTHS)),
                    cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_VIEWEVENTS_RDINWEEKS)),
                    cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_VIEWEVENTS_RDINDAYS)),
                    cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_VIEWEVENTS_RDINHOURS)),
                    cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_VIEWEVENTS_RDINMINUTES)),
                    cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_VIEWEVENTS_RDINSECS))));
        }
        cursor.close();
        return event;
    }

    List<Event> getEventsByEventGroupIdAndGroupTrackSettings(long idEG, int sourceTrackSettingsGroup) {

        ArrayList<Event> events = new ArrayList<>();

        String selection = DatabaseHelper.COLUMN_VIEWEVENTS_ID_EVENTGROUP + "=" + idEG + " AND " +
                DatabaseHelper.COLUMN_VIEWEVENTS_SOURCETRACKSETTINGS + "=" + sourceTrackSettingsGroup;

        Cursor cursor = database.query(DatabaseHelper.VIEW_EVENTS, null, selection, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                long id = cursor.getLong(cursor.getColumnIndex(DatabaseHelper.COLUMN_VIEWEVENTS_ID));
                String name = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_VIEWEVENTS_NAME));
                String comment = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_VIEWEVENTS_COMMENT));
                long idEventGroup = cursor.getLong(cursor.getColumnIndex(DatabaseHelper.COLUMN_VIEWEVENTS_ID_EVENTGROUP));
                String nameEventGroup = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_VIEWEVENTS_EVENTGROUPNAME));
                Calendar dateAndTime = new GregorianCalendar();
                dateAndTime.setTimeInMillis(cursor.getLong(cursor.getColumnIndex(DatabaseHelper.COLUMN_VIEWEVENTS_DATEANDTIME)));
                int sourceTrackSettings = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_VIEWEVENTS_SOURCETRACKSETTINGS));
                int rdInYears = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_VIEWEVENTS_RDINYEARS));
                int rdInMonths = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_VIEWEVENTS_RDINMONTHS));
                int rdInWeeks = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_VIEWEVENTS_RDINWEEKS));
                int rdInDays = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_VIEWEVENTS_RDINDAYS));
                int rdInHours = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_VIEWEVENTS_RDINHOURS));
                int rdInMinutes = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_VIEWEVENTS_RDINMINUTES));
                int rdInSecs = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_VIEWEVENTS_RDINSECS));
                events.add(new Event(id, name, comment, idEventGroup, nameEventGroup, dateAndTime, sourceTrackSettings,
                        new TrackSettings(rdInYears, rdInMonths, rdInWeeks, rdInDays, rdInHours, rdInMinutes, rdInSecs)));
            }
            while (cursor.moveToNext());
        }

        cursor.close();
        return events;
    }

    List<Event> getEventsWithAppTrackSettings() {
        ArrayList<Event> events = new ArrayList<>();

        String selection = DatabaseHelper.COLUMN_VIEWEVENTS_SOURCETRACKSETTINGS + "=" + Event.SOURCE_TRACK_SETTINGS_APP + " OR (" +
                DatabaseHelper.COLUMN_VIEWEVENTS_EVENTGROUPSOURCETRACKSETTINGS + "=" + Event.SOURCE_TRACK_SETTINGS_APP + " AND " +
                DatabaseHelper.COLUMN_VIEWEVENTS_SOURCETRACKSETTINGS + "=" + Event.SOURCE_TRACK_SETTINGS_GROUP + ")";

        Cursor cursor = database.query(DatabaseHelper.VIEW_EVENTS, null, selection, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                long id = cursor.getLong(cursor.getColumnIndex(DatabaseHelper.COLUMN_VIEWEVENTS_ID));
                String name = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_VIEWEVENTS_NAME));
                String comment = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_VIEWEVENTS_COMMENT));
                long idEventGroup = cursor.getLong(cursor.getColumnIndex(DatabaseHelper.COLUMN_VIEWEVENTS_ID_EVENTGROUP));
                String nameEventGroup = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_VIEWEVENTS_EVENTGROUPNAME));
                Calendar dateAndTime = new GregorianCalendar();
                dateAndTime.setTimeInMillis(cursor.getLong(cursor.getColumnIndex(DatabaseHelper.COLUMN_VIEWEVENTS_DATEANDTIME)));
                int sourceTrackSettings = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_VIEWEVENTS_SOURCETRACKSETTINGS));
                int rdInYears = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_VIEWEVENTS_RDINYEARS));
                int rdInMonths = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_VIEWEVENTS_RDINMONTHS));
                int rdInWeeks = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_VIEWEVENTS_RDINWEEKS));
                int rdInDays = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_VIEWEVENTS_RDINDAYS));
                int rdInHours = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_VIEWEVENTS_RDINHOURS));
                int rdInMinutes = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_VIEWEVENTS_RDINMINUTES));
                int rdInSecs = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_VIEWEVENTS_RDINSECS));
                events.add(new Event(id, name, comment, idEventGroup, nameEventGroup, dateAndTime, sourceTrackSettings,
                        new TrackSettings(rdInYears, rdInMonths, rdInWeeks, rdInDays, rdInHours, rdInMinutes, rdInSecs)));
            }
            while (cursor.moveToNext());
        }

        cursor.close();
        return events;
    }


    TrackSettings getGroupTrackSettingsById(long idEventGroup) {
        TrackSettings trackSettings;
        String selection = DatabaseHelper.COLUMN_EVENTGROUPS_ID + "=" + idEventGroup;

        Cursor cursor = database.query(DatabaseHelper.TABLE_EVENT_GROUPS, null, selection, null, null, null, null);

        cursor.moveToFirst();
        trackSettings = new TrackSettings(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_EVENTGROUPS_RDINYEARS)),
                cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_EVENTGROUPS_RDINMONTHS)),
                cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_EVENTGROUPS_RDINWEEKS)),
                cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_EVENTGROUPS_RDINDAYS)),
                cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_EVENTGROUPS_RDINHOURS)),
                cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_EVENTGROUPS_RDINMINUTES)),
                cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_EVENTGROUPS_RDINSECS)));
        cursor.close();
        return trackSettings;
    }

    List<RoundDate> addRoundDates(List<RoundDate> roundDates) {

        if (!roundDates.isEmpty()) {

            for (RoundDate roundDate : roundDates) {
                ContentValues cv = new ContentValues();
                cv.put(DatabaseHelper.COLUMN_ROUNDDATES_VALUE, roundDate.getValueOf());
                cv.put(DatabaseHelper.COLUMN_ROUNDDATES_UNIT, roundDate.getUnit());
                cv.put(DatabaseHelper.COLUMN_ROUNDDATES_DATEANDTIME, roundDate.getDateAndTime().getTimeInMillis());
                cv.put(DatabaseHelper.COLUMN_ROUNDDATES_ID_EVENT, roundDate.getIdEvent());
                cv.put(DatabaseHelper.COLUMN_ROUNDDATES_RARE, roundDate.getRare());
                cv.put(DatabaseHelper.COLUMN_ROUNDDATES_IMPORTANT, roundDate.getImportant());
                roundDate.setId(database.insert(DatabaseHelper.TABLE_ROUNDDATES, null, cv));
            }

            return roundDates;
        } else return null;
    }

    void addNotifyDates(List<RoundDate> roundDates) {

        if (!roundDates.isEmpty()) {

            NotifySettings notifySettings = new NotifySettings(getContext());

            for (RoundDate roundDate : roundDates) {
                switch (roundDate.getImportant()) {
                    case RoundDate.VERY_IMPORTANT:
                        if ((notifySettings.getVeryImportantRdMonth() == 1) && (roundDate.getDateAndTime().getTimeInMillis() - PERIOD_MONTH > System.currentTimeMillis())) {
                            ContentValues cv = new ContentValues();
                            cv.put(DatabaseHelper.COLUMN_NOTIFYDATES_ID_ROUNDDATE, roundDate.getId());
                            cv.put(DatabaseHelper.COLUMN_NOTIFYDATES_NOTIFYDATEANDTIME, roundDate.getDateAndTime().getTimeInMillis() - PERIOD_MONTH);
                            database.insert(DatabaseHelper.TABLE_NOTIFYDATES, null, cv);
                        }
                        if ((notifySettings.getVeryImportantRdWeek() == 1) && (roundDate.getDateAndTime().getTimeInMillis() - PERIOD_WEEK > System.currentTimeMillis())) {
                            ContentValues cv = new ContentValues();
                            cv.put(DatabaseHelper.COLUMN_NOTIFYDATES_ID_ROUNDDATE, roundDate.getId());
                            cv.put(DatabaseHelper.COLUMN_NOTIFYDATES_NOTIFYDATEANDTIME, roundDate.getDateAndTime().getTimeInMillis() - PERIOD_WEEK);
                            database.insert(DatabaseHelper.TABLE_NOTIFYDATES, null, cv);
                        }
                        if ((notifySettings.getVeryImportantRdDay() == 1) && (roundDate.getDateAndTime().getTimeInMillis() - PERIOD_DAY > System.currentTimeMillis())) {
                            ContentValues cv = new ContentValues();
                            cv.put(DatabaseHelper.COLUMN_NOTIFYDATES_ID_ROUNDDATE, roundDate.getId());
                            cv.put(DatabaseHelper.COLUMN_NOTIFYDATES_NOTIFYDATEANDTIME, roundDate.getDateAndTime().getTimeInMillis() - PERIOD_DAY);
                            database.insert(DatabaseHelper.TABLE_NOTIFYDATES, null, cv);
                        }
                        break;
                    case RoundDate.IMPORTANT:
                        if ((notifySettings.getImportantRdMonth() == 1) && (roundDate.getDateAndTime().getTimeInMillis() - PERIOD_MONTH > System.currentTimeMillis())) {
                            ContentValues cv = new ContentValues();
                            cv.put(DatabaseHelper.COLUMN_NOTIFYDATES_ID_ROUNDDATE, roundDate.getId());
                            cv.put(DatabaseHelper.COLUMN_NOTIFYDATES_NOTIFYDATEANDTIME, roundDate.getDateAndTime().getTimeInMillis() - PERIOD_MONTH);
                            database.insert(DatabaseHelper.TABLE_NOTIFYDATES, null, cv);
                        }
                        if ((notifySettings.getImportantRdWeek() == 1) && (roundDate.getDateAndTime().getTimeInMillis() - PERIOD_WEEK > System.currentTimeMillis())) {
                            ContentValues cv = new ContentValues();
                            cv.put(DatabaseHelper.COLUMN_NOTIFYDATES_ID_ROUNDDATE, roundDate.getId());
                            cv.put(DatabaseHelper.COLUMN_NOTIFYDATES_NOTIFYDATEANDTIME, roundDate.getDateAndTime().getTimeInMillis() - PERIOD_WEEK);
                            database.insert(DatabaseHelper.TABLE_NOTIFYDATES, null, cv);
                        }
                        if ((notifySettings.getImportantRdDay() == 1) && (roundDate.getDateAndTime().getTimeInMillis() - PERIOD_DAY > System.currentTimeMillis())) {
                            ContentValues cv = new ContentValues();
                            cv.put(DatabaseHelper.COLUMN_NOTIFYDATES_ID_ROUNDDATE, roundDate.getId());
                            cv.put(DatabaseHelper.COLUMN_NOTIFYDATES_NOTIFYDATEANDTIME, roundDate.getDateAndTime().getTimeInMillis() - PERIOD_DAY);
                            database.insert(DatabaseHelper.TABLE_NOTIFYDATES, null, cv);
                        }
                        break;
                    case RoundDate.STANDART:
                        if ((notifySettings.getStandartRdMonth() == 1) && (roundDate.getDateAndTime().getTimeInMillis() - PERIOD_MONTH > System.currentTimeMillis())) {
                            ContentValues cv = new ContentValues();
                            cv.put(DatabaseHelper.COLUMN_NOTIFYDATES_ID_ROUNDDATE, roundDate.getId());
                            cv.put(DatabaseHelper.COLUMN_NOTIFYDATES_NOTIFYDATEANDTIME, roundDate.getDateAndTime().getTimeInMillis() - PERIOD_MONTH);
                            database.insert(DatabaseHelper.TABLE_NOTIFYDATES, null, cv);
                        }
                        if ((notifySettings.getStandartRdWeek() == 1) && (roundDate.getDateAndTime().getTimeInMillis() - PERIOD_WEEK > System.currentTimeMillis())) {
                            ContentValues cv = new ContentValues();
                            cv.put(DatabaseHelper.COLUMN_NOTIFYDATES_ID_ROUNDDATE, roundDate.getId());
                            cv.put(DatabaseHelper.COLUMN_NOTIFYDATES_NOTIFYDATEANDTIME, roundDate.getDateAndTime().getTimeInMillis() - PERIOD_WEEK);
                            database.insert(DatabaseHelper.TABLE_NOTIFYDATES, null, cv);
                        }
                        if ((notifySettings.getStandartRdDay() == 1) && (roundDate.getDateAndTime().getTimeInMillis() - PERIOD_DAY > System.currentTimeMillis())) {
                            ContentValues cv = new ContentValues();
                            cv.put(DatabaseHelper.COLUMN_NOTIFYDATES_ID_ROUNDDATE, roundDate.getId());
                            cv.put(DatabaseHelper.COLUMN_NOTIFYDATES_NOTIFYDATEANDTIME, roundDate.getDateAndTime().getTimeInMillis() - PERIOD_DAY);
                            database.insert(DatabaseHelper.TABLE_NOTIFYDATES, null, cv);
                        }
                        break;
                    case RoundDate.NOT_IMPORTANT:
                        if ((notifySettings.getSmallImportantRdMonth() == 1) && (roundDate.getDateAndTime().getTimeInMillis() - PERIOD_MONTH > System.currentTimeMillis())) {
                            ContentValues cv = new ContentValues();
                            cv.put(DatabaseHelper.COLUMN_NOTIFYDATES_ID_ROUNDDATE, roundDate.getId());
                            cv.put(DatabaseHelper.COLUMN_NOTIFYDATES_NOTIFYDATEANDTIME, roundDate.getDateAndTime().getTimeInMillis() - PERIOD_MONTH);
                            database.insert(DatabaseHelper.TABLE_NOTIFYDATES, null, cv);
                        }
                        if ((notifySettings.getSmallImportantRdWeek() == 1) && (roundDate.getDateAndTime().getTimeInMillis() - PERIOD_WEEK > System.currentTimeMillis())) {
                            ContentValues cv = new ContentValues();
                            cv.put(DatabaseHelper.COLUMN_NOTIFYDATES_ID_ROUNDDATE, roundDate.getId());
                            cv.put(DatabaseHelper.COLUMN_NOTIFYDATES_NOTIFYDATEANDTIME, roundDate.getDateAndTime().getTimeInMillis() - PERIOD_WEEK);
                            database.insert(DatabaseHelper.TABLE_NOTIFYDATES, null, cv);
                        }
                        if ((notifySettings.getSmallImportantRdDay() == 1) && (roundDate.getDateAndTime().getTimeInMillis() - PERIOD_DAY > System.currentTimeMillis())) {
                            ContentValues cv = new ContentValues();
                            cv.put(DatabaseHelper.COLUMN_NOTIFYDATES_ID_ROUNDDATE, roundDate.getId());
                            cv.put(DatabaseHelper.COLUMN_NOTIFYDATES_NOTIFYDATEANDTIME, roundDate.getDateAndTime().getTimeInMillis() - PERIOD_DAY);
                            database.insert(DatabaseHelper.TABLE_NOTIFYDATES, null, cv);
                        }
                        break;
                }
                }
        }
    }


    List<RoundDate> getRoundDates() {
        ArrayList<RoundDate> roundDates = new ArrayList<>();

        Calendar currentDateAndTime = new GregorianCalendar();
        String selection = DatabaseHelper.COLUMN_VIEWROUNDDATES_DATEANDTIME + ">" + currentDateAndTime.getTimeInMillis();
        String order = DatabaseHelper.COLUMN_VIEWROUNDDATES_DATEANDTIME;

        Cursor cursor = database.query(DatabaseHelper.VIEW_ROUNDDATES, null, selection, null, null, null, order);

        if (cursor.moveToFirst()) {
            do {
                long id = cursor.getLong(cursor.getColumnIndex(DatabaseHelper.COLUMN_VIEWROUNDDATES_ID));
                long value = cursor.getLong(cursor.getColumnIndex(DatabaseHelper.COLUMN_VIEWROUNDDATES_VALUE));
                int unit = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_VIEWROUNDDATES_UNIT));
                Calendar dateAndTime = new GregorianCalendar();
                dateAndTime.setTimeInMillis(cursor.getLong(cursor.getColumnIndex(DatabaseHelper.COLUMN_VIEWROUNDDATES_DATEANDTIME)));
                long idEvent = cursor.getLong(cursor.getColumnIndex(DatabaseHelper.COLUMN_VIEWROUNDDATES_ID_EVENT));
                String eventName = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_VIEWROUNDDATES_EVENTNAME));
                int rare = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_VIEWROUNDDATES_RARE));
                int important = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_VIEWROUNDDATES_IMPORTANT));
                roundDates.add(new RoundDate(id, value, unit, dateAndTime, idEvent, eventName, rare, important));
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return roundDates;
    }

    List<RoundDate> getRoundDates(int i, long id) {
        ArrayList<RoundDate> roundDates = new ArrayList<>();

        Calendar currentDateAndTime = new GregorianCalendar();
        String selection = DatabaseHelper.COLUMN_VIEWROUNDDATES_DATEANDTIME + ">" + currentDateAndTime.getTimeInMillis();
        switch (i) {
            case RoundDateListActivity.EVENT:
                selection = selection + " AND " + DatabaseHelper.COLUMN_VIEWROUNDDATES_ID_EVENT + "=" + id;
                break;
            case RoundDateListActivity.EVENTSGROUP:
                selection = selection + " AND " + DatabaseHelper.COLUMN_VIEWROUNDDATES_ID_EVENTSGROUP + "=" + id;
                break;
        }
        String order = DatabaseHelper.COLUMN_VIEWROUNDDATES_DATEANDTIME;

        Cursor cursor = database.query(DatabaseHelper.VIEW_ROUNDDATES, null, selection, null, null, null, order);

        if (cursor.moveToFirst()) {
            do {
                long idRd = cursor.getLong(cursor.getColumnIndex(DatabaseHelper.COLUMN_VIEWROUNDDATES_ID));
                long value = cursor.getLong(cursor.getColumnIndex(DatabaseHelper.COLUMN_VIEWROUNDDATES_VALUE));
                int unit = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_VIEWROUNDDATES_UNIT));
                Calendar dateAndTime = new GregorianCalendar();
                dateAndTime.setTimeInMillis(cursor.getLong(cursor.getColumnIndex(DatabaseHelper.COLUMN_VIEWROUNDDATES_DATEANDTIME)));
                long idEvent = cursor.getLong(cursor.getColumnIndex(DatabaseHelper.COLUMN_VIEWROUNDDATES_ID_EVENT));
                String eventName = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_VIEWROUNDDATES_EVENTNAME));
                int rare = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_VIEWROUNDDATES_RARE));
                int important = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_VIEWROUNDDATES_IMPORTANT));
                roundDates.add(new RoundDate(idRd, value, unit, dateAndTime, idEvent, eventName, rare, important));
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return roundDates;
    }


    List<RoundDate> getAllRoundDates() {
        ArrayList<RoundDate> roundDates = new ArrayList<>();

        Cursor cursor = database.query(DatabaseHelper.TABLE_ROUNDDATES, null, null, null, null, null, null);
        Log.d("MyLog", "Количество круглых дат = " + cursor.getCount());

        cursor.close();
        return roundDates;
    }

    void deleteNotifyDate(long id) {
        String table = DatabaseHelper.TABLE_NOTIFYDATES;
        String where = DatabaseHelper.COLUMN_NOTIFYDATES_ID + "=" + id;
        database.delete(table, where, null);
    }

    NotifyDate getNextNotifyDate() {
        NotifyDate notifyDate = new NotifyDate();

        Cursor cursor = database.query(DatabaseHelper.VIEW_NOTIFYDATES, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            notifyDate.setId(cursor.getLong(cursor.getColumnIndex(DatabaseHelper.COLUMN_VIEWNOTIFYDATES_ID)));
            notifyDate.setValueOf(cursor.getLong(cursor.getColumnIndex(DatabaseHelper.COLUMN_VIEWNOTIFYDATES_VALUE)));
            notifyDate.setUnit(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_VIEWNOTIFYDATES_UNIT)));
            Calendar dateAndTime = new GregorianCalendar();
            dateAndTime.setTimeInMillis(cursor.getLong(cursor.getColumnIndex(DatabaseHelper.COLUMN_VIEWNOTIFYDATES_DATEANDTIME)));
            notifyDate.setDateAndTime(dateAndTime);
            notifyDate.setNotifyDateAndTime(cursor.getLong(cursor.getColumnIndex(DatabaseHelper.COLUMN_VIEWNOTIFYDATES_NOTIFYDATEANDTIME)));
            notifyDate.setIdRoundDate(cursor.getLong(cursor.getColumnIndex(DatabaseHelper.COLUMN_VIEWNOTIFYDATES_ID_ROUNDDATE)));
            notifyDate.setNameEvent(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_VIEWNOTIFYDATES_EVENTNAME)));
            notifyDate.setRare(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_VIEWNOTIFYDATES_RARE)));
            notifyDate.setImportant(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_VIEWNOTIFYDATES_IMPORTANT)));
        }

        cursor.close();
        if (notifyDate.getId() != NotifyDate.NOT_NOTIFY) {
            return notifyDate;
        } else {
            return null;
        }
    }

    void updateNotifyDate(RoundDate roundDate) {

        NotifySettings notifySettings = new NotifySettings(getContext());
        // удаялем старые уведомления
        String table = DatabaseHelper.TABLE_NOTIFYDATES;
        String where = DatabaseHelper.COLUMN_NOTIFYDATES_ID_ROUNDDATE + "=" + roundDate.getId();
        database.delete(table, where, null);
        // добавляем новые уведомления
        switch (roundDate.getImportant()) {
            case RoundDate.VERY_IMPORTANT:
                if ((notifySettings.getVeryImportantRdMonth() == 1) && (roundDate.getDateAndTime().getTimeInMillis() - PERIOD_MONTH > System.currentTimeMillis())) {
                    ContentValues cv = new ContentValues();
                    cv.put(DatabaseHelper.COLUMN_NOTIFYDATES_ID_ROUNDDATE, roundDate.getId());
                    cv.put(DatabaseHelper.COLUMN_NOTIFYDATES_NOTIFYDATEANDTIME, roundDate.getDateAndTime().getTimeInMillis() - PERIOD_MONTH);
                    database.insert(DatabaseHelper.TABLE_NOTIFYDATES, null, cv);
                }
                if ((notifySettings.getVeryImportantRdWeek() == 1) && (roundDate.getDateAndTime().getTimeInMillis() - PERIOD_WEEK > System.currentTimeMillis())) {
                    ContentValues cv = new ContentValues();
                    cv.put(DatabaseHelper.COLUMN_NOTIFYDATES_ID_ROUNDDATE, roundDate.getId());
                    cv.put(DatabaseHelper.COLUMN_NOTIFYDATES_NOTIFYDATEANDTIME, roundDate.getDateAndTime().getTimeInMillis() - PERIOD_WEEK);
                    database.insert(DatabaseHelper.TABLE_NOTIFYDATES, null, cv);
                }
                if ((notifySettings.getVeryImportantRdDay() == 1) && (roundDate.getDateAndTime().getTimeInMillis() - PERIOD_DAY > System.currentTimeMillis())) {
                    ContentValues cv = new ContentValues();
                    cv.put(DatabaseHelper.COLUMN_NOTIFYDATES_ID_ROUNDDATE, roundDate.getId());
                    cv.put(DatabaseHelper.COLUMN_NOTIFYDATES_NOTIFYDATEANDTIME, roundDate.getDateAndTime().getTimeInMillis() - PERIOD_DAY);
                    database.insert(DatabaseHelper.TABLE_NOTIFYDATES, null, cv);
                }
                break;
            case RoundDate.IMPORTANT:
                if ((notifySettings.getImportantRdMonth() == 1) && (roundDate.getDateAndTime().getTimeInMillis() - PERIOD_MONTH > System.currentTimeMillis())) {
                    ContentValues cv = new ContentValues();
                    cv.put(DatabaseHelper.COLUMN_NOTIFYDATES_ID_ROUNDDATE, roundDate.getId());
                    cv.put(DatabaseHelper.COLUMN_NOTIFYDATES_NOTIFYDATEANDTIME, roundDate.getDateAndTime().getTimeInMillis() - PERIOD_MONTH);
                    database.insert(DatabaseHelper.TABLE_NOTIFYDATES, null, cv);
                }
                if ((notifySettings.getImportantRdWeek() == 1) && (roundDate.getDateAndTime().getTimeInMillis() - PERIOD_WEEK > System.currentTimeMillis())) {
                    ContentValues cv = new ContentValues();
                    cv.put(DatabaseHelper.COLUMN_NOTIFYDATES_ID_ROUNDDATE, roundDate.getId());
                    cv.put(DatabaseHelper.COLUMN_NOTIFYDATES_NOTIFYDATEANDTIME, roundDate.getDateAndTime().getTimeInMillis() - PERIOD_WEEK);
                    database.insert(DatabaseHelper.TABLE_NOTIFYDATES, null, cv);
                }
                if ((notifySettings.getImportantRdDay() == 1) && (roundDate.getDateAndTime().getTimeInMillis() - PERIOD_DAY > System.currentTimeMillis())) {
                    ContentValues cv = new ContentValues();
                    cv.put(DatabaseHelper.COLUMN_NOTIFYDATES_ID_ROUNDDATE, roundDate.getId());
                    cv.put(DatabaseHelper.COLUMN_NOTIFYDATES_NOTIFYDATEANDTIME, roundDate.getDateAndTime().getTimeInMillis() - PERIOD_DAY);
                    database.insert(DatabaseHelper.TABLE_NOTIFYDATES, null, cv);
                }
                break;
            case RoundDate.STANDART:
                if ((notifySettings.getStandartRdMonth() == 1) && (roundDate.getDateAndTime().getTimeInMillis() - PERIOD_MONTH > System.currentTimeMillis())) {
                    ContentValues cv = new ContentValues();
                    cv.put(DatabaseHelper.COLUMN_NOTIFYDATES_ID_ROUNDDATE, roundDate.getId());
                    cv.put(DatabaseHelper.COLUMN_NOTIFYDATES_NOTIFYDATEANDTIME, roundDate.getDateAndTime().getTimeInMillis() - PERIOD_MONTH);
                    database.insert(DatabaseHelper.TABLE_NOTIFYDATES, null, cv);
                }
                if ((notifySettings.getStandartRdWeek() == 1) && (roundDate.getDateAndTime().getTimeInMillis() - PERIOD_WEEK > System.currentTimeMillis())) {
                    ContentValues cv = new ContentValues();
                    cv.put(DatabaseHelper.COLUMN_NOTIFYDATES_ID_ROUNDDATE, roundDate.getId());
                    cv.put(DatabaseHelper.COLUMN_NOTIFYDATES_NOTIFYDATEANDTIME, roundDate.getDateAndTime().getTimeInMillis() - PERIOD_WEEK);
                    database.insert(DatabaseHelper.TABLE_NOTIFYDATES, null, cv);
                }
                if ((notifySettings.getStandartRdDay() == 1) && (roundDate.getDateAndTime().getTimeInMillis() - PERIOD_DAY > System.currentTimeMillis())) {
                    ContentValues cv = new ContentValues();
                    cv.put(DatabaseHelper.COLUMN_NOTIFYDATES_ID_ROUNDDATE, roundDate.getId());
                    cv.put(DatabaseHelper.COLUMN_NOTIFYDATES_NOTIFYDATEANDTIME, roundDate.getDateAndTime().getTimeInMillis() - PERIOD_DAY);
                    database.insert(DatabaseHelper.TABLE_NOTIFYDATES, null, cv);
                }
                break;
            case RoundDate.NOT_IMPORTANT:
                if ((notifySettings.getSmallImportantRdMonth() == 1) && (roundDate.getDateAndTime().getTimeInMillis() - PERIOD_MONTH > System.currentTimeMillis())) {
                    ContentValues cv = new ContentValues();
                    cv.put(DatabaseHelper.COLUMN_NOTIFYDATES_ID_ROUNDDATE, roundDate.getId());
                    cv.put(DatabaseHelper.COLUMN_NOTIFYDATES_NOTIFYDATEANDTIME, roundDate.getDateAndTime().getTimeInMillis() - PERIOD_MONTH);
                    database.insert(DatabaseHelper.TABLE_NOTIFYDATES, null, cv);
                }
                if ((notifySettings.getSmallImportantRdWeek() == 1) && (roundDate.getDateAndTime().getTimeInMillis() - PERIOD_WEEK > System.currentTimeMillis())) {
                    ContentValues cv = new ContentValues();
                    cv.put(DatabaseHelper.COLUMN_NOTIFYDATES_ID_ROUNDDATE, roundDate.getId());
                    cv.put(DatabaseHelper.COLUMN_NOTIFYDATES_NOTIFYDATEANDTIME, roundDate.getDateAndTime().getTimeInMillis() - PERIOD_WEEK);
                    database.insert(DatabaseHelper.TABLE_NOTIFYDATES, null, cv);
                }
                if ((notifySettings.getSmallImportantRdDay() == 1) && (roundDate.getDateAndTime().getTimeInMillis() - PERIOD_DAY > System.currentTimeMillis())) {
                    ContentValues cv = new ContentValues();
                    cv.put(DatabaseHelper.COLUMN_NOTIFYDATES_ID_ROUNDDATE, roundDate.getId());
                    cv.put(DatabaseHelper.COLUMN_NOTIFYDATES_NOTIFYDATEANDTIME, roundDate.getDateAndTime().getTimeInMillis() - PERIOD_DAY);
                    database.insert(DatabaseHelper.TABLE_NOTIFYDATES, null, cv);
                }
                break;
        }
    }


    int updateEvent(Event event) {
        ContentValues cv = new ContentValues();
        cv.put(DatabaseHelper.COLUMN_EVENTS_NAME, event.getName());
        cv.put(DatabaseHelper.COLUMN_EVENTS_COMMENT, event.getComment());
        cv.put(DatabaseHelper.COLUMN_EVENTS_ID_EVENTGROUP, event.getIdEventGroup());
        cv.put(DatabaseHelper.COLUMN_EVENTS_DATEANDTIME, event.getDateAndTime().getTimeInMillis());
        cv.put(DatabaseHelper.COLUMN_EVENTS_SOURCETRACKSETTINGS, event.getSourceTrackSettings());
        cv.put(DatabaseHelper.COLUMN_EVENTS_RDINYEARS, event.getTrackSettings().getRdInYears());
        cv.put(DatabaseHelper.COLUMN_EVENTS_RDINMONTHS, event.getTrackSettings().getRdInMonths());
        cv.put(DatabaseHelper.COLUMN_EVENTS_RDINWEEKS, event.getTrackSettings().getRdInWeeks());
        cv.put(DatabaseHelper.COLUMN_EVENTS_RDINDAYS, event.getTrackSettings().getRdInDays());
        cv.put(DatabaseHelper.COLUMN_EVENTS_RDINHOURS, event.getTrackSettings().getRdInHours());
        cv.put(DatabaseHelper.COLUMN_EVENTS_RDINMINUTES, event.getTrackSettings().getRdInMinutes());
        cv.put(DatabaseHelper.COLUMN_EVENTS_RDINSECS, event.getTrackSettings().getRdInSecs());
        String where = DatabaseHelper.COLUMN_EVENTS_ID + "=" + event.getId();
        return database.update(DatabaseHelper.TABLE_EVENTS, cv, where, null);
    }

    void deleteAllNotifyDates() {
        String table = DatabaseHelper.TABLE_NOTIFYDATES;
        database.delete(table, null, null);
    }

    void deleteRoundDates(long id, int unit) {
        String table = DatabaseHelper.TABLE_ROUNDDATES;
        String where = DatabaseHelper.COLUMN_ROUNDDATES_ID_EVENT + "=" + id + " AND " +
                DatabaseHelper.COLUMN_ROUNDDATES_UNIT + "=" + unit;
        database.delete(table, where, null);
    }

    void deleteEvent(long id) {
        String table = DatabaseHelper.TABLE_EVENTS;
        String where = DatabaseHelper.COLUMN_EVENTS_ID + "=" + id;
        database.delete(table, where, null);
    }

    void deleteEventsGroup(long id) {
        String table = DatabaseHelper.TABLE_EVENT_GROUPS;
        String where = DatabaseHelper.COLUMN_EVENTGROUPS_ID + "=" + id;
        database.delete(table, where, null);
    }

    EventGroup getEventGroupById(long id) {

        String selection = DatabaseHelper.COLUMN_EVENTGROUPS_ID + "=" + id;
        Cursor cursor = database.query(DatabaseHelper.TABLE_EVENT_GROUPS, null, selection, null, null, null, null);
        if (cursor.moveToFirst()) {
            EventGroup eventGroup = new EventGroup(id,
                    cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_EVENTGROUPS_NAME)),
                    cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_EVENTGROUPS_SOURCETRACKSETTINGS)),
                    new TrackSettings(
                            cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_EVENTGROUPS_RDINYEARS)),
                            cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_EVENTGROUPS_RDINMONTHS)),
                            cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_EVENTGROUPS_RDINWEEKS)),
                            cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_EVENTGROUPS_RDINDAYS)),
                            cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_EVENTGROUPS_RDINHOURS)),
                            cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_EVENTGROUPS_RDINMINUTES)),
                            cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_EVENTGROUPS_RDINSECS))));
            cursor.close();
            return eventGroup;
        } else {
            return new EventGroup();
        }
    }

    int updateEventGroup(EventGroup eventGroup) {
        ContentValues cv = new ContentValues();
        cv.put(DatabaseHelper.COLUMN_EVENTGROUPS_NAME, eventGroup.getName());
        cv.put(DatabaseHelper.COLUMN_EVENTGROUPS_SOURCETRACKSETTINGS, eventGroup.getSourceTrackSettings());
        cv.put(DatabaseHelper.COLUMN_EVENTGROUPS_RDINYEARS, eventGroup.getTrackSettings().getRdInYears());
        cv.put(DatabaseHelper.COLUMN_EVENTGROUPS_RDINMONTHS, eventGroup.getTrackSettings().getRdInMonths());
        cv.put(DatabaseHelper.COLUMN_EVENTGROUPS_RDINWEEKS, eventGroup.getTrackSettings().getRdInWeeks());
        cv.put(DatabaseHelper.COLUMN_EVENTGROUPS_RDINDAYS, eventGroup.getTrackSettings().getRdInDays());
        cv.put(DatabaseHelper.COLUMN_EVENTGROUPS_RDINHOURS, eventGroup.getTrackSettings().getRdInHours());
        cv.put(DatabaseHelper.COLUMN_EVENTGROUPS_RDINMINUTES, eventGroup.getTrackSettings().getRdInMinutes());
        cv.put(DatabaseHelper.COLUMN_EVENTGROUPS_RDINSECS, eventGroup.getTrackSettings().getRdInSecs());
        String where = DatabaseHelper.COLUMN_EVENTGROUPS_ID + "=" + eventGroup.getId();
        return database.update(DatabaseHelper.TABLE_EVENT_GROUPS, cv, where, null);
    }

    int updateRoundDateImportant(long id, int important) {
        ContentValues cv = new ContentValues();
        cv.put(DatabaseHelper.COLUMN_ROUNDDATES_IMPORTANT, important);
        String where = DatabaseHelper.COLUMN_ROUNDDATES_ID + "=" + id;
        return database.update(DatabaseHelper.TABLE_ROUNDDATES, cv, where, null);
    }

    void getAllNotify() {
        Cursor cursor = database.query(DatabaseHelper.TABLE_NOTIFYDATES, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            Log.d("MyLog", "Количество уведомлений = " + cursor.getCount());
        }
        cursor.close();
    }

}
