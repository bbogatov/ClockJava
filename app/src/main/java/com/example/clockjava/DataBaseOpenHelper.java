package com.example.clockjava;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseOpenHelper extends SQLiteOpenHelper {

    private static String dbName;

    DataBaseOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        dbName = name;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Logger.log("Create database");

        db.execSQL("create table " + dbName +  " ("
                + "id integer primary key autoincrement,"
                + "time text,"
                + "switch bollean" + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
