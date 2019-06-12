package com.example.clockjava;


import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

/**
 * This class used for creating notification. Implements singleton pattern,
 * to initialize class object use {@link #init()} method.
 */
class Notificator {
    private static Notificator notificator;
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
    static Notificator init() {
        if (notificator == null) {
            notificator = new Notificator();
        }
        return notificator;
    }

    /**
     * Method that creates notification get
     *
     * @param time this time that shows on screen, must be that time when notification appears
     */
    void createNotification(String time) {
        Context context = App.getContext();

        Intent intent = new Intent(context, NotificationButtonReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(App.getContext());
        builder.setSmallIcon(R.drawable.clock_img);
        builder.setContentTitle("Alarm " + time);
        builder.setContentText("Wake up");
        builder.setOngoing(true);
        builder.setSound(null);
        builder.setPriority(NotificationCompat.PRIORITY_HIGH);
        builder.addAction(R.drawable.clock_img, "I woke up", pendingIntent);

        notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationChannel notificationChannel =
                    new NotificationChannel("10002",
                            "Alarm Clock Alarm",
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
    void cancelNotificator() {
        notificationManager.cancel(0);
    }

}
