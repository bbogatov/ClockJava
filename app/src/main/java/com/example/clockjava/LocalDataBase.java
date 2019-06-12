package com.example.clockjava;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Class represents local database. Before work with it you should initialize it.
 * For example:  LocalDataBase localDataBase = LocalDataBase.init();
 * localDataBase.changeEnable(index);
 */
public class LocalDataBase {

    private static LocalDataBase localDataBase;
    private static SQLiteDatabase sqLiteDatabase;
    private static final String DATA_BASE_NAME = "clock_alarms";

    private LocalDataBase() {
        DataBaseOpenHelper dbHelper = new DataBaseOpenHelper(App.getContext(), DATA_BASE_NAME, null, 1);
        sqLiteDatabase = dbHelper.getWritableDatabase();
    }

    public static LocalDataBase init() {
        if (localDataBase == null) {
            localDataBase = new LocalDataBase();
        }
        return localDataBase;
    }

    long addClock(String time) {
        if (sqLiteDatabase != null) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("time", time);
            contentValues.put("switch", true);
            long index = sqLiteDatabase.insert(DATA_BASE_NAME, null, contentValues);
            Logger.log("Added new clock to data base: time = " + time + "; id = " + index);
            return index;
        }
        return 0;
    }

    /**
     * Method that removes clock from database by its index.
     *
     * @param index index in database
     */
    void removeClock(long index) {
        if (sqLiteDatabase != null) {
            sqLiteDatabase.delete(DATA_BASE_NAME, "id = " + index, null);
            Logger.log("Removed clock from data base:  " + index + " + index");
        }
    }


    void changeTime(long index, String time) {
        if (sqLiteDatabase != null) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("time", time);
            contentValues.put("switch", true);
            sqLiteDatabase.update(DATA_BASE_NAME, contentValues, "id = ?", new String[]{String.valueOf(index)});
            Logger.log("Updated time in data base:"
                    + " time = " + time
                    + "; id = " + index
                    + "; switch = " + true);
        }
    }

    ArrayList<Alarm> getAlarms() {
        ArrayList<Alarm> alarms = new ArrayList<>();

        Cursor cursor = sqLiteDatabase.query(DATA_BASE_NAME, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {

            int indexColumn = cursor.getColumnIndex("id");
            int timeColumn = cursor.getColumnIndex("time");
            int enableColumn = cursor.getColumnIndex("switch");

            do {
                alarms.add(new Alarm(cursor.getLong(indexColumn),
                        cursor.getString(timeColumn),
                        cursor.getInt(enableColumn) > 0));
            } while (cursor.moveToNext());

        }
        cursor.close();
        return alarms;
    }

    void changeSwitch(long index, boolean isEnabled) {
        if (sqLiteDatabase != null) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("switch", isEnabled);

            sqLiteDatabase.update(DATA_BASE_NAME, contentValues, "id = ?", new String[]{String.valueOf(index)});

            Logger.log("Updated switch in data base:"
                    + "; id = " + index
                    + "; switch = " + true);


        }
    }
}
