package com.example.clockjava.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.clockjava.notificator.AlarmPlayer;
import com.example.clockjava.notificator.Notificator;

/**
 * Class that provides removing notification and stops playing music when user clicks button "I woke up"
 * fro {@link Notificator} class.
 */
public class NotificationButtonReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Notificator notificator = Notificator.getInstance();
        notificator.cancelNotificator();

        AlarmPlayer.getInstance().stopPlayer();
    }
}
