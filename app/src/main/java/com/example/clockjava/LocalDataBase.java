package com.example.clockjava;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

public class LocalDataBase {

    private static SQLiteDatabase sqLiteDatabase;
    private static DataBaseOpenHelper dbHelper;
    private static final String DATA_BASE_NAME = "clock_alarms";

    private static void init() {
        dbHelper = new DataBaseOpenHelper(App.getContext(), DATA_BASE_NAME, null, 1);
        sqLiteDatabase = dbHelper.getWritableDatabase();
    }

    public static void addClock(String time) {
        if (sqLiteDatabase == null) {
            init();
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put("time", time);
        contentValues.put("switch", true);
        long index = sqLiteDatabase.insert(DATA_BASE_NAME, null, contentValues);
        Logger.log("Added new clock to data base: time = " + time + "; id = " + index);
    }

    public static void removeClock(long index) {
        if (sqLiteDatabase == null) {
            init();
        }
        sqLiteDatabase.delete(DATA_BASE_NAME, "id = " + index, null);
        Logger.log("Removed clock from data base:  " + index + " + index");
    }
}
