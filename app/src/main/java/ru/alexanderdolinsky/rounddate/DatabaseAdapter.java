package ru.alexanderdolinsky.rounddate;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alexsvet on 06.06.2017.
 * Класс DatabaseAdapter
 */

public class DatabaseAdapter {
    private DatabaseHelper dbHelper;
    private SQLiteDatabase database;
    private Context context;

    public DatabaseAdapter (Context context) {
        dbHelper = new DatabaseHelper(context.getApplicationContext());
        this.context = context;
    }

    public DatabaseAdapter open() {
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }


    public List<EventGroup> getEventGroups() {
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

        Cursor cursor = database.query(DatabaseHelper.TABLE_EVENT_GROUPS, columns, null, null, null, null, DatabaseHelper.COLUMN_EVENTGROUPS_ID);

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
            eventGroups.add(new EventGroup(-1, getContext().getString(R.string.new_event_group), 1, new TrackSettings(0, 0, 0, 0, 0, 0, 0)));
        }
        cursor.close();
        return eventGroups;
    }

    public Context getContext() {
        return context;
    }

    public long addEventsGroup(EventGroup eventGroup) {

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

    public long addEvent(Event event) {

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

    public boolean isEventGroupExists(String newEventsGroupName) {
        Cursor cursor = database.query(DatabaseHelper.TABLE_EVENT_GROUPS,
                new String[]{DatabaseHelper.COLUMN_EVENTGROUPS_NAME},
                DatabaseHelper.COLUMN_EVENTGROUPS_NAME + "= '" + newEventsGroupName + "'", null, null, null, null);
        boolean b;
        if (cursor.getCount() == 0) {
            b = false;
        } else {
            b = true;
        }
        cursor.close();
        return b;
    }

    public boolean isEventExists(String eventName) {
        Cursor cursor = database.query(DatabaseHelper.TABLE_EVENTS,
                new String[]{DatabaseHelper.COLUMN_EVENTS_NAME},
                DatabaseHelper.COLUMN_EVENTS_NAME + "= '" + eventName + "'", null, null, null, null);
        boolean b;
        if (cursor.getCount() == 0) {
            b = false;
        } else {
            b = true;
        }
        cursor.close();
        return b;
    }


}
