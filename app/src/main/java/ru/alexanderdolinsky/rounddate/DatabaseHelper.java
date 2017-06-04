package ru.alexanderdolinsky.rounddate;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Alexsvet on 04.06.2017.
 * Класс DatabaseHelper
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "RoundDate.db"; // название базы данных
    private static final int SCHEMA = 1; // версия базы данных
    static final String TABLE_EVENT_GROUPS = "event groups"; // название таблицы Групп событий
    // столбцы таблицы "event groups"
    public static final String COLUMN_EVENTGROUPS_ID = "_id";
    public static final String COLUMN_EVENTGROUPS_NAME = "name of event group";
    public static final String COLUMN_EVENTGROUPS_SOURCETRACKSETTINGS = "source of track settings event group";
    public static final String COLUMN_EVENTGROUPS_RDINYEARS = "rd in years event group";
    public static final String COLUMN_EVENTGROUPS_RDINMONTHS = "rd in months event group";
    public static final String COLUMN_EVENTGROUPS_RDINWEEKS = "rd in weeks event group";
    public static final String COLUMN_EVENTGROUPS_RDINDAYS = "rd in days event group";
    public static final String COLUMN_EVENTGROUPS_RDINHOURS = "rd in hours event group";
    public static final String COLUMN_EVENTGROUPS_RDINMINUTES = "rd in minutes event group";
    public static final String COLUMN_EVENTGROUPS_RDINSECS = "rd in secs event group";

    static final String TABLE_EVENTS = "events"; // название таблицы Событий
    // столбцы таблицы "events"
    public static final String COLUMN_EVENTS_ID = "_id";
    public static final String COLUMN_EVENTS_NAME = "name of event";
    public static final String COLUMN_EVENTS_COMMENT = "comment of event";
    public static final String COLUMN_EVENTS_ID_EVENTGROUP = "id event group";
    public static final String COLUMN_EVENTS_DATEANDTIME = "date and time of event";
    public static final String COLUMN_EVENTS_SOURCETRACKSETTINGS = "source of track settings event";
    public static final String COLUMN_EVENTS_RDINYEARS = "rd in years event";
    public static final String COLUMN_EVENTS_RDINMONTHS = "rd in months event";
    public static final String COLUMN_EVENTS_RDINWEEKS = "rd in weeks event";
    public static final String COLUMN_EVENTS_RDINDAYS = "rd in days event";
    public static final String COLUMN_EVENTS_RDINHOURS = "rd in hours event";
    public static final String COLUMN_EVENTS_RDINMINUTES = "rd in minutes event";
    public static final String COLUMN_EVENTS_RDINSECS = "rd in secs event";

    static final String TABLE_ROUNDDATES = "round dates"; //название таблицы Круглые даты
    // столбцы таблицы "round dates"
    public static final String COLUMN_ROUNDDATES_ID = "_id";
    public static final String COLUMN_ROUNDDATES_VALUE = "value of round date";
    public static final String COLUMN_ROUNDDATES_UNIT = "unit of round date"; //0 - года, 1 - месяцы, 2 - недели, 3 - дни, 4 - часы, 5 - минуты, 6 - секунды
    public static final String COLUMN_ROUNDDATES_DATEANDTIME = "date and time of round date";
    public static final String COLUMN_ROUNDDATES_ID_EVENT = "id event";
    public static final String COLUMN_ROUNDDATES_RARE = "rare of round date";
    public static final String COLUMN_ROUNDDATES_IMPORTANT = "important of round date";



    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, SCHEMA);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_EVENT_GROUPS + " ("
                + COLUMN_EVENTGROUPS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_EVENTGROUPS_NAME + " TEXT, "
                + COLUMN_EVENTGROUPS_SOURCETRACKSETTINGS + " INTEGER, "
                + COLUMN_EVENTGROUPS_RDINYEARS + " INTEGER, "
                + COLUMN_EVENTGROUPS_RDINMONTHS + " INTEGER, "
                + COLUMN_EVENTGROUPS_RDINWEEKS + " INTEGER, "
                + COLUMN_EVENTGROUPS_RDINDAYS + " INTEGER, "
                + COLUMN_EVENTGROUPS_RDINHOURS + " INTEGER, "
                + COLUMN_EVENTGROUPS_RDINMINUTES + " INTEGER, "
                + COLUMN_EVENTGROUPS_RDINSECS + " INTEGER);"
        );

        db.execSQL("CREATE TABLE " + TABLE_EVENTS + " ("
                + COLUMN_EVENTS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_EVENTS_NAME + " TEXT, "
                + COLUMN_EVENTS_COMMENT + " TEXT, "
                + COLUMN_EVENTS_ID_EVENTGROUP + " INTEGER, "
                + COLUMN_EVENTS_DATEANDTIME + " INTEGER, "
                + COLUMN_EVENTS_SOURCETRACKSETTINGS + " INTEGER, "
                + COLUMN_EVENTS_RDINYEARS + " INTEGER, "
                + COLUMN_EVENTS_RDINMONTHS + " INTEGER, "
                + COLUMN_EVENTS_RDINWEEKS + " INTEGER, "
                + COLUMN_EVENTS_RDINDAYS + " INTEGER, "
                + COLUMN_EVENTS_RDINHOURS + " INTEGER, "
                + COLUMN_EVENTS_RDINMINUTES + " INTEGER, "
                + COLUMN_EVENTS_RDINSECS + " INTEGER);"
        );

        db.execSQL("CREATE TABLE " + TABLE_ROUNDDATES + " ("
                + COLUMN_ROUNDDATES_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_ROUNDDATES_VALUE + " INTEGER, "
                + COLUMN_ROUNDDATES_UNIT + " INTEGER, "
                + COLUMN_ROUNDDATES_DATEANDTIME + " INTEGER, "
                + COLUMN_ROUNDDATES_ID_EVENT + " INTEGER, "
                + COLUMN_ROUNDDATES_RARE + " INTEGER, "
                + COLUMN_ROUNDDATES_IMPORTANT + " INTEGER);");

        db.execSQL("INSERT INTO " + TABLE_EVENT_GROUPS + " ("
                + COLUMN_EVENTGROUPS_NAME + ", "
                + COLUMN_EVENTGROUPS_SOURCETRACKSETTINGS + ", "
                + COLUMN_EVENTGROUPS_RDINYEARS + ", "
                + COLUMN_EVENTGROUPS_RDINMONTHS + ", "
                + COLUMN_EVENTGROUPS_RDINWEEKS + ", "
                + COLUMN_EVENTGROUPS_RDINDAYS + ", "
                + COLUMN_EVENTGROUPS_RDINHOURS + ", "
                + COLUMN_EVENTGROUPS_RDINMINUTES + ", "
                + COLUMN_EVENTGROUPS_RDINSECS + ") VALUES (" +
                "'Мои события', 0, 0, 0, 0, 0, 0, 0, 0);"
        );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
