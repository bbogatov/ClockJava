package com.example.clockjava;


import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

public class Notificator {
    private static Notificator notificator;
    private static NotificationManager notificationManager;


    private Notificator() {
    }

    public static Notificator init() {
        if (notificator == null) {
            notificator = new Notificator();
        }
        return notificator;
    }

    public void createNotification(String time) {
        Context context = App.getContext();

        Intent intent = new Intent(context, NotificationButtonReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(App.getContext());
        builder.setSmallIcon(R.drawable.clock_img);
        builder.setContentTitle("Alarm " + time);
        builder.setContentText("Wake up");
        builder.setOngoing(true);
        builder.setPriority(NotificationCompat.PRIORITY_HIGH);
        builder.addAction(R.drawable.clock_img, "I woke up", pendingIntent);

        notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationChannel notificationChannel =
                    new NotificationChannel("10002",
                            "Alarm Clock Alarm",
                            NotificationManager.IMPORTANCE_HIGH);


            builder.setChannelId("10002");
            notificationManager.createNotificationChannel(notificationChannel);
        }

        notificationManager.notify(0, builder.build());
    }

    public void cancelNotificator() {
        notificationManager.cancel(0);
    }

}
