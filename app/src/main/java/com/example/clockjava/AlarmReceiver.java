package com.example.clockjava;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

//TODO тут буду обрабатывать ноутификации которые сюдапопадают

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
