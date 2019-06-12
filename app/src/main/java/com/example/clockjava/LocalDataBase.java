package com.example.clockjava;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Class represents local database. Before work with it you should initialize it.
 * For example:  LocalDataBase localDataBase = LocalDataBase.init();
 * localDataBase.changeEnable(index);
 */
class LocalDataBase {

    /**
     * Static class object
     */
    private static LocalDataBase localDataBase;

    /**
     * Database of alarms objects
     */
    private static SQLiteDatabase sqLiteDatabase;

    /**
     * Database name that stores all clock alarms
     */
    private static final String DATA_BASE_NAME =
            App.getContext().getResources().getString(R.string.alarms_data_base_name);

    /**
     * Class constructor
     */
    private LocalDataBase() {
        DataBaseOpenHelper dbHelper = new DataBaseOpenHelper(App.getContext(), DATA_BASE_NAME, null, 1);
        sqLiteDatabase = dbHelper.getWritableDatabase();
    }

    /**
     * Method that used to initialize database.
     *
     * @return static database
     */
    static LocalDataBase init() {
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

    /**
     * Method that changes time and switch values in data base
     *
     * @param index index of element in database that need change
     * @param time  new time in database
     */
    void changeTime(long index, String time) {
        if (sqLiteDatabase != null) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("time", time);
            sqLiteDatabase.update(DATA_BASE_NAME, contentValues, "id = ?", new String[]{String.valueOf(index)});
            Logger.log("Updated time in data base:"
                    + " time = " + time
                    + "; id = " + index
                    + "; switch = " + true);
        }
    }

    /**
     * Returns all clocks from database and its values as arrayList
     *
     * @return arrayList of active and inactive clocks
     */
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

    /**
     * Change switch value in data base.
     *
     * @param index     index of element in database
     * @param isChecked new switch value
     */
    void changeSwitch(long index, boolean isChecked) {
        if (sqLiteDatabase != null) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("switch", isChecked);

            sqLiteDatabase.update(DATA_BASE_NAME, contentValues, "id = ?", new String[]{String.valueOf(index)});

            Logger.log("Updated switch in data base:"
                    + "; id = " + index
                    + "; switch = " + true);
        }
    }
}
