package com.example.clockjava;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * When alarms time starts, this code runs,
 * at first shows notification and next plays sound to wake up user.
 */
public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        long index = intent.getLongExtra("index", 0);
        String time = intent.getStringExtra("time");


        Notificator notificator = Notificator.init();
        notificator.createNotification(time);

        Player player = Player.init();
        player.playAlarmSong();

        //Change switch value in database
        LocalDataBase localDataBase = LocalDataBase.init();
        localDataBase.changeSwitch(index, false);
    }
}
