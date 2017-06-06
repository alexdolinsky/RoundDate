package ru.alexanderdolinsky.rounddate;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Alexsvet on 06.06.2017.
 * Класс DatabaseAdapter
 */

public class DatabaseAdapter {
    private DatabaseHelper dbHelper;
    private SQLiteDatabase database;

    public DatabaseAdapter (Context context) {
        dbHelper = new DatabaseHelper(context.getApplicationContext());
    }

    public DatabaseAdapter open() {
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }


}
