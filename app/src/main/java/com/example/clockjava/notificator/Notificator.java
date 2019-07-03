package com.example.clockjava.notificator;


import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import com.example.clockjava.App;
import com.example.clockjava.R;
import com.example.clockjava.receivers.NotificationButtonReceiver;

/**
 * This class used for creating notification. Implements singleton pattern,
 * to initialize class object use {@link #getInstance()} method.
 */
public class Notificator {
    private static Notificator instance;
    private static NotificationManager notificationManager;

    /**
     * Class constructor
     */
    private Notificator() {
    }

    /**
     * Method that initializing static class object and returns it back.
     *
     * @return static class object
     */
    public static Notificator getInstance() {
        if (instance == null) {
            instance = new Notificator();
            notificationManager =
                    (NotificationManager) App.getContext().getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return instance;
    }

    /**
     * Method that creates notification get
     *
     * @param time this time that shows on screen, must be that time when notification appears
     */
    public void createNotification(String time) {
        Context context = App.getContext();

        Intent intent = new Intent(context, NotificationButtonReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(App.getContext());
        builder.setSmallIcon(R.drawable.clock_img);
        builder.setContentTitle("ClockAlarm " + time);
        builder.setContentText("Wake up");
        builder.setOngoing(true);
        builder.setSound(null);
        builder.setPriority(NotificationCompat.PRIORITY_MAX);
        builder.addAction(R.drawable.clock_img, "I woke up", pendingIntent);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationChannel notificationChannel =
                    new NotificationChannel("10002",
                            "ClockAlarm Clock ClockAlarm",
                            NotificationManager.IMPORTANCE_HIGH);

            notificationChannel.setSound(null, null);
            builder.setChannelId("10002");
            notificationManager.createNotificationChannel(notificationChannel);
        }


        notificationManager.notify(0, builder.build());
    }

    /**
     * Use when user click "I woke up" button on notification, this used to remove notification from notification panel.
     */
    public void cancelNotificator() {
        notificationManager.cancel(0);
    }

}
