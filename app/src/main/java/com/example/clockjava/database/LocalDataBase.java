package com.example.clockjava.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.clockjava.App;
import com.example.clockjava.logger.Logger;
import com.example.clockjava.R;
import com.example.clockjava.observerInterfaces.Observed;
import com.example.clockjava.observerInterfaces.Observer;

import java.util.ArrayList;
import java.util.List;

/**
 * Class represents local database. Before work with it you should initialize it.
 * For example:  LocalDataBase localDataBase = LocalDataBase.getInstance();
 * localDataBase.changeEnable(id);
 */
public class LocalDataBase implements Observed {

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

    private List<Observer> observersList;


    /**
     * Class constructor
     */
    private LocalDataBase() {
        DataBaseOpenHelper dbHelper = new DataBaseOpenHelper(App.getContext(), DATA_BASE_NAME, null, 1);
        sqLiteDatabase = dbHelper.getWritableDatabase();
        observersList = new ArrayList<>();
    }

    /**
     * Method that used to initialize database.
     *
     * @return static database
     */
    public static LocalDataBase getInstance() {
        if (localDataBase == null) {
            localDataBase = new LocalDataBase();
        }
        return localDataBase;
    }

    /**
     * Method adds new clock to database, and returns its index in database.
     *
     * @param time time when clock should start
     * @return index in database
     */
    public long addClock(String time) {
        long id = 0;
        if (sqLiteDatabase != null) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("time", time);
            contentValues.put("enable", true);
            id = sqLiteDatabase.insert(DATA_BASE_NAME, null, contentValues);
            Logger.log("Added new clock to data base: time = " + time + "; id = " + id);
        }
        notifyObservers();
        return id;
    }

    /**
     * Method that removes clock from database by its index.
     *
     * @param id index in database
     */
    public void removeClock(long id) {
        if (sqLiteDatabase != null) {
            sqLiteDatabase.delete(DATA_BASE_NAME, "id = " + id, null);
            Logger.log("Removed clock from data base:  " + id + " + Id");
        }
        notifyObservers();
    }

    /**
     * Method that changes time and switch values in data base
     *
     * @param id   id of element in database that need change
     * @param time new time in database
     */
    public void changeTime(long id, String time) {
        if (sqLiteDatabase != null) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("time", time);
            sqLiteDatabase.update(DATA_BASE_NAME, contentValues, "id = ?", new String[]{String.valueOf(id)});
            Logger.log("Updated time in data base:"
                    + " time = " + time
                    + "; id = " + id);
        }
        notifyObservers();
    }

    /**
     * Returns all clocks from database and its values as arrayList
     *
     * @return arrayList of active and inactive clocks
     */
    public ArrayList<ClockAlarm> getAlarms() {
        ArrayList<ClockAlarm> clockAlarms = new ArrayList<>();

        Cursor cursor = sqLiteDatabase.query(DATA_BASE_NAME, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {

            int indexColumn = cursor.getColumnIndex("id");
            int timeColumn = cursor.getColumnIndex("time");
            int enableColumn = cursor.getColumnIndex("enable");

            do {
                clockAlarms.add(new ClockAlarm(cursor.getLong(indexColumn),
                        cursor.getString(timeColumn),
                        cursor.getInt(enableColumn) > 0));
            } while (cursor.moveToNext());

        }
        cursor.close();
        return clockAlarms;
    }

    /**
     * Change switch value in data base.
     *
     * @param id        index of element in database
     * @param isChecked new switch value
     */
    public void changeSwitch(long id, boolean isChecked) {
        if (sqLiteDatabase != null) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("enable", isChecked);

            sqLiteDatabase.update(DATA_BASE_NAME, contentValues, "id = ?", new String[]{String.valueOf(id)});

            Logger.log("Updated switch in data base:"
                    + "; id = " + id
                    + "; enable = " + isChecked);
        }
        notifyObservers();
    }

    /**
     * Method adds new observer that using this database for drawing all clocks.
     *
     * @param observer observer that used this database
     */
    @Override
    public void addObserver(Observer observer) {
        this.observersList.add(observer);
    }

    /**
     * Method removes observer that don't need this database.
     *
     * @param observer observer that do not use this database
     */
    @Override
    public void removeObserver(Observer observer) {
        this.observersList.remove(observer);
    }

    /**
     * Method notifies all observers to redraw recyclerView, that contains all clock alarms.
     */
    @Override
    public void notifyObservers() {
        for (Observer observer : observersList) {
            observer.handleEvent(getAlarms());
        }
    }
}
