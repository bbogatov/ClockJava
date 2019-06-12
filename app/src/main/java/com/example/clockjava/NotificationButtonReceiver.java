package com.example.clockjava;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Class that provides removing notification and stops playing music when user clicks button "I woke up"
 * fro {@link Notificator} class.
 */
public class NotificationButtonReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Notificator notificator = Notificator.init();
        notificator.cancelNotificator();

        Player.init().stopPlayer();
    }
}
