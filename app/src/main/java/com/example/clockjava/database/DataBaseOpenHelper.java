package com.example.clockjava.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.clockjava.logger.Logger;

/**
 * Class that provides database open helper.
 * It helps creating and working with {@link LocalDataBase} which hold all alarms clocks.
 */
public class DataBaseOpenHelper extends SQLiteOpenHelper {

    private static String dbName;
    private static final String KEY_ID = "_id";
    private static final String TIME = "_time";
    private static final String SWITCH = "_switch";


    DataBaseOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        dbName = name;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Logger.log("Create database");

        db.execSQL("create table " + dbName + " ("
                + KEY_ID + " integer primary key autoincrement,"
                + TIME + " text,"
                + SWITCH + " bollean" + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
