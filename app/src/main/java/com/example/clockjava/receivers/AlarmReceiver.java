package com.example.clockjava.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.clockjava.database.LocalDataBase;
import com.example.clockjava.notificator.Notificator;
import com.example.clockjava.notificator.AlarmPlayer;

/**
 * When alarms time starts, this code runs,
 * at first shows notification and next plays sound to wake up user.
 */
public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        long index = intent.getLongExtra("index", 0);
        String time = intent.getStringExtra("time");


        Notificator.getInstance().createNotification(time);

        AlarmPlayer.getInstance().playAlarmSong();

        //Change switch value in database
        LocalDataBase localDataBase = LocalDataBase.init();
        localDataBase.changeSwitch(index, false);
    }
}
