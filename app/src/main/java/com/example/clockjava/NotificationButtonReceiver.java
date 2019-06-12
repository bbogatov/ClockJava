package com.example.clockjava;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class NotificationButtonReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Notificator notificator = Notificator.init();
        notificator.cancelNotificator();

        Player.init().stopPlayer();

    }
}
