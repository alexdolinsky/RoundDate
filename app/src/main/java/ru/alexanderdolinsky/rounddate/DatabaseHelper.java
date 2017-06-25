package ru.alexanderdolinsky.rounddate;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;





/**
 * Created by Alexsvet on 04.06.2017.
 * Класс DatabaseHelper
 */

class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "RoundDate.db"; // название базы данных
    private static final int SCHEMA = 1; // версия базы данных
    static final String TABLE_EVENT_GROUPS = "event_groups"; // название таблицы Групп событий
    // столбцы таблицы "event groups"
    static final String COLUMN_EVENTGROUPS_ID = "_id";
    static final String COLUMN_EVENTGROUPS_NAME = "name";
    static final String COLUMN_EVENTGROUPS_SOURCETRACKSETTINGS = "source_track_settings";
    static final String COLUMN_EVENTGROUPS_RDINYEARS = "rd_in_years";
    static final String COLUMN_EVENTGROUPS_RDINMONTHS = "rd_in_months";
    static final String COLUMN_EVENTGROUPS_RDINWEEKS = "rd_in_weeks";
    static final String COLUMN_EVENTGROUPS_RDINDAYS = "rd_in_days";
    static final String COLUMN_EVENTGROUPS_RDINHOURS = "rd_in_hours";
    static final String COLUMN_EVENTGROUPS_RDINMINUTES = "rd_in_minutes";
    static final String COLUMN_EVENTGROUPS_RDINSECS = "rd_in_secs";

    static final String TABLE_EVENTS = "events"; // название таблицы Событий
    // столбцы таблицы "events"
    static final String COLUMN_EVENTS_ID = "_id";
    static final String COLUMN_EVENTS_NAME = "name";
    static final String COLUMN_EVENTS_COMMENT = "comment";
    static final String COLUMN_EVENTS_ID_EVENTGROUP = "id_event_group";
    static final String COLUMN_EVENTS_DATEANDTIME = "date_and_time";
    static final String COLUMN_EVENTS_SOURCETRACKSETTINGS = "source_track_settings";
    static final String COLUMN_EVENTS_RDINYEARS = "rd_in_years";
    static final String COLUMN_EVENTS_RDINMONTHS = "rd_in_months";
    static final String COLUMN_EVENTS_RDINWEEKS = "rd_in_weeks";
    static final String COLUMN_EVENTS_RDINDAYS = "rd_in_days";
    static final String COLUMN_EVENTS_RDINHOURS = "rd_in_hours";
    static final String COLUMN_EVENTS_RDINMINUTES = "rd_in_minutes";
    static final String COLUMN_EVENTS_RDINSECS = "rd_in_secs";

    static final String TABLE_ROUNDDATES = "round_dates"; //название таблицы Круглые даты
    // столбцы таблицы "round dates"
    private static final String COLUMN_ROUNDDATES_ID = "_id";
    static final String COLUMN_ROUNDDATES_VALUE = "value";
    static final String COLUMN_ROUNDDATES_UNIT = "unit"; //1 - года, 2 - месяцы, 3 - недели, 4 - дни, 5 - часы, 6 - минуты, 7 - секунды
    static final String COLUMN_ROUNDDATES_DATEANDTIME = "date_and_time";
    static final String COLUMN_ROUNDDATES_ID_EVENT = "id_event";
    static final String COLUMN_ROUNDDATES_RARE = "rare";
    static final String COLUMN_ROUNDDATES_IMPORTANT = "important";

    static final String VIEW_EVENTS = "view_events"; // название представления Событий
    // столбцы-алиасы представления событий
    static final String COLUMN_VIEWEVENTS_ID = "_id";
    static final String COLUMN_VIEWEVENTS_NAME = "name";
    static final String COLUMN_VIEWEVENTS_COMMENT = "comment";
    static final String COLUMN_VIEWEVENTS_ID_EVENTGROUP = "id_event_group";
    static final String COLUMN_VIEWEVENTS_EVENTGROUPNAME = "event_group_name";
    static final String COLUMN_VIEWEVENTS_DATEANDTIME = "date_and_time";
    static final String COLUMN_VIEWEVENTS_SOURCETRACKSETTINGS = "source_track_settings";
    static final String COLUMN_VIEWEVENTS_RDINYEARS = "rd_in_years";
    static final String COLUMN_VIEWEVENTS_RDINMONTHS = "rd_in_months";
    static final String COLUMN_VIEWEVENTS_RDINWEEKS = "rd_in_weeks";
    static final String COLUMN_VIEWEVENTS_RDINDAYS = "rd_in_days";
    static final String COLUMN_VIEWEVENTS_RDINHOURS = "rd_in_hours";
    static final String COLUMN_VIEWEVENTS_RDINMINUTES = "rd_in_minutes";
    static final String COLUMN_VIEWEVENTS_RDINSECS = "rd_in_secs";


    static final String VIEW_ROUNDDATES = "view_round_dates"; // название представления Круглые даты
    // столбцы-алиасы представления событий
    static final String COLUMN_VIEWROUNDDATES_ID = "_id";
    static final String COLUMN_VIEWROUNDDATES_VALUE = "value";
    static final String COLUMN_VIEWROUNDDATES_UNIT = "unit"; //1 - года, 2 - месяцы, 3 - недели, 4 - дни, 5 - часы, 6 - минуты, 7 - секунды
    static final String COLUMN_VIEWROUNDDATES_DATEANDTIME = "date_and_time";
    static final String COLUMN_VIEWROUNDDATES_ID_EVENT = "id_event";
    static final String COLUMN_VIEWROUNDDATES_EVENTNAME = "event_name";
    static final String COLUMN_VIEWROUNDDATES_RARE = "rare";
    static final String COLUMN_VIEWROUNDDATES_IMPORTANT = "important";

    private static String myEvents;


    DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, SCHEMA);
        myEvents = context.getString(R.string.my_events);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {


        db.execSQL("CREATE TABLE " + TABLE_EVENT_GROUPS + " ("
                + COLUMN_EVENTGROUPS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_EVENTGROUPS_NAME + " TEXT UNIQUE, "
                + COLUMN_EVENTGROUPS_SOURCETRACKSETTINGS + " INTEGER NOT NULL, "
                + COLUMN_EVENTGROUPS_RDINYEARS + " INTEGER NOT NULL, "
                + COLUMN_EVENTGROUPS_RDINMONTHS + " INTEGER NOT NULL, "
                + COLUMN_EVENTGROUPS_RDINWEEKS + " INTEGER NOT NULL, "
                + COLUMN_EVENTGROUPS_RDINDAYS + " INTEGER NOT NULL, "
                + COLUMN_EVENTGROUPS_RDINHOURS + " INTEGER NOT NULL, "
                + COLUMN_EVENTGROUPS_RDINMINUTES + " INTEGER NOT NULL, "
                + COLUMN_EVENTGROUPS_RDINSECS + " INTEGER NOT NULL);"
        );

        db.execSQL("CREATE TABLE " + TABLE_EVENTS + " ("
                + COLUMN_EVENTS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_EVENTS_NAME + " TEXT UNIQUE, "
                + COLUMN_EVENTS_COMMENT + " TEXT NOT NULL, "
                + COLUMN_EVENTS_ID_EVENTGROUP + " INTEGER REFERENCES " + TABLE_EVENT_GROUPS + "("
                        + COLUMN_EVENTGROUPS_ID + ") ON DELETE CASCADE, "
                + COLUMN_EVENTS_DATEANDTIME + " INTEGER NOT NULL, "
                + COLUMN_EVENTS_SOURCETRACKSETTINGS + " INTEGER NOT NULL, "
                + COLUMN_EVENTS_RDINYEARS + " INTEGER NOT NULL, "
                + COLUMN_EVENTS_RDINMONTHS + " INTEGER NOT NULL, "
                + COLUMN_EVENTS_RDINWEEKS + " INTEGER NOT NULL, "
                + COLUMN_EVENTS_RDINDAYS + " INTEGER NOT NULL, "
                + COLUMN_EVENTS_RDINHOURS + " INTEGER NOT NULL, "
                + COLUMN_EVENTS_RDINMINUTES + " INTEGER NOT NULL, "
                + COLUMN_EVENTS_RDINSECS + " INTEGER NOT NULL);"
        );

        db.execSQL("CREATE TABLE " + TABLE_ROUNDDATES + " ("
                + COLUMN_ROUNDDATES_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_ROUNDDATES_VALUE + " INTEGER NOT NULL, "
                + COLUMN_ROUNDDATES_UNIT + " INTEGER NOT NULL, "
                + COLUMN_ROUNDDATES_DATEANDTIME + " INTEGER NOT NULL, "
                + COLUMN_ROUNDDATES_ID_EVENT + " INTEGER REFERENCES " + TABLE_EVENTS + "("
                    + COLUMN_EVENTS_ID + ") ON DELETE CASCADE, "
                + COLUMN_ROUNDDATES_RARE + " INTEGER NOT NULL, "
                + COLUMN_ROUNDDATES_IMPORTANT + " INTEGER NOT NULL);");


        db.execSQL("INSERT INTO " + TABLE_EVENT_GROUPS + " ("
                + COLUMN_EVENTGROUPS_NAME + ", "
                + COLUMN_EVENTGROUPS_SOURCETRACKSETTINGS + ", "
                + COLUMN_EVENTGROUPS_RDINYEARS + ", "
                + COLUMN_EVENTGROUPS_RDINMONTHS + ", "
                + COLUMN_EVENTGROUPS_RDINWEEKS + ", "
                + COLUMN_EVENTGROUPS_RDINDAYS + ", "
                + COLUMN_EVENTGROUPS_RDINHOURS + ", "
                + COLUMN_EVENTGROUPS_RDINMINUTES + ", "
                + COLUMN_EVENTGROUPS_RDINSECS + ") VALUES ('"
                + myEvents + "', 0, 0, 0, 0, 0, 0, 0, 0);"
        );

        db.execSQL("CREATE VIEW " + VIEW_EVENTS + " AS SELECT "
                + TABLE_EVENTS + "." + COLUMN_EVENTS_ID + " AS " + COLUMN_VIEWEVENTS_ID + ", "
                + TABLE_EVENTS + "." + COLUMN_EVENTS_NAME + " AS " + COLUMN_VIEWEVENTS_NAME + ", "
                + TABLE_EVENTS + "." + COLUMN_EVENTS_COMMENT + " AS " + COLUMN_VIEWEVENTS_COMMENT + ", "
                + TABLE_EVENTS + "." + COLUMN_EVENTS_ID_EVENTGROUP + " AS " + COLUMN_VIEWEVENTS_ID_EVENTGROUP + ", "
                + TABLE_EVENT_GROUPS + "." + COLUMN_EVENTGROUPS_NAME + " AS " + COLUMN_VIEWEVENTS_EVENTGROUPNAME + ", "
                + TABLE_EVENTS + "." + COLUMN_EVENTS_DATEANDTIME + " AS " + COLUMN_VIEWEVENTS_DATEANDTIME + ", "
                + TABLE_EVENTS + "." + COLUMN_EVENTS_SOURCETRACKSETTINGS + " AS " + COLUMN_VIEWEVENTS_SOURCETRACKSETTINGS + ", "
                + TABLE_EVENTS + "." + COLUMN_EVENTS_RDINYEARS + " AS " + COLUMN_VIEWEVENTS_RDINYEARS + ", "
                + TABLE_EVENTS + "." + COLUMN_EVENTS_RDINMONTHS + " AS " + COLUMN_VIEWEVENTS_RDINMONTHS + ", "
                + TABLE_EVENTS + "." + COLUMN_EVENTS_RDINWEEKS + " AS " + COLUMN_VIEWEVENTS_RDINWEEKS + ", "
                + TABLE_EVENTS + "." + COLUMN_EVENTS_RDINDAYS + " AS " + COLUMN_VIEWEVENTS_RDINDAYS + ", "
                + TABLE_EVENTS + "." + COLUMN_EVENTS_RDINHOURS + " AS " + COLUMN_VIEWEVENTS_RDINHOURS + ", "
                + TABLE_EVENTS + "." + COLUMN_EVENTS_RDINMINUTES + " AS " + COLUMN_VIEWEVENTS_RDINMINUTES + ", "
                + TABLE_EVENTS + "." + COLUMN_EVENTS_RDINSECS + " AS " + COLUMN_VIEWEVENTS_RDINSECS +
                " FROM " + TABLE_EVENTS + " INNER JOIN " + TABLE_EVENT_GROUPS +
                " ON " + TABLE_EVENTS + "." + COLUMN_EVENTS_ID_EVENTGROUP + "="
                + TABLE_EVENT_GROUPS + "." + COLUMN_EVENTGROUPS_ID + ";"
        );

        db.execSQL("CREATE VIEW " + VIEW_ROUNDDATES + " AS SELECT DISTINCT "
                + TABLE_ROUNDDATES + "." + COLUMN_ROUNDDATES_ID + " AS " + COLUMN_VIEWROUNDDATES_ID + ", "
                + TABLE_ROUNDDATES + "." + COLUMN_ROUNDDATES_VALUE + " AS " + COLUMN_VIEWROUNDDATES_VALUE + ", "
                + TABLE_ROUNDDATES + "." + COLUMN_ROUNDDATES_UNIT + " AS " + COLUMN_VIEWROUNDDATES_UNIT + ", "
                + TABLE_ROUNDDATES + "." + COLUMN_ROUNDDATES_DATEANDTIME + " AS " + COLUMN_VIEWROUNDDATES_DATEANDTIME + ", "
                + TABLE_ROUNDDATES + "." + COLUMN_ROUNDDATES_ID_EVENT + " AS " + COLUMN_VIEWROUNDDATES_ID_EVENT + ", "
                + TABLE_EVENTS + "." + COLUMN_EVENTS_NAME + " AS " + COLUMN_VIEWROUNDDATES_EVENTNAME + ", "
                + TABLE_ROUNDDATES + "." + COLUMN_ROUNDDATES_RARE + " AS " + COLUMN_VIEWROUNDDATES_RARE + ", "
                + TABLE_ROUNDDATES + "." + COLUMN_ROUNDDATES_IMPORTANT + " AS " + COLUMN_VIEWROUNDDATES_IMPORTANT +
                " FROM " + TABLE_ROUNDDATES + " INNER JOIN " + TABLE_EVENTS +
                " ON " + TABLE_ROUNDDATES + "." + COLUMN_ROUNDDATES_ID_EVENT + "="
                + TABLE_EVENTS + "." + COLUMN_EVENTS_ID + ";"
        );

        // включаем внешние ключи
        db.execSQL("PRAGMA foreign_keys=on;");



    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EVENT_GROUPS + ";");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EVENTS + ";");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ROUNDDATES + ";");
        db.execSQL("DROP VIEW IF EXISTS " + VIEW_EVENTS + ";");
        onCreate(db);
    }
}
