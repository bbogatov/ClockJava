package com.example.clockjava;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 *
 */
public class CreateAlarm {

    public void addAlarmSignal(String time, long index) {
        Context context = App.getContext();

        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.setAction("ClockAlarm " + time);
        intent.putExtra("time", time);
        intent.putExtra("index", index);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        long time2 = getClockTime(time);
        alarmManager.set(AlarmManager.RTC_WAKEUP, time2, pendingIntent);
        Logger.log("Create new alarmManager. Time = " + time + "; index = " + index);
    }

    //Todo: removeAlarm, changeAlarm, backAlarm

    private long getClockTime(String time) {

        Calendar calendarAlarmTime = Calendar.getInstance();
        calendarAlarmTime.set(Calendar.HOUR_OF_DAY, Integer.valueOf(time.substring(0, 2)));
        calendarAlarmTime.set(Calendar.MINUTE, Integer.valueOf(time.substring(3, 5)));
        calendarAlarmTime.set(Calendar.SECOND, 0);
        calendarAlarmTime.set(Calendar.MILLISECOND, 0);
        Date clockTime = calendarAlarmTime.getTime();

        Calendar calendarNow = Calendar.getInstance();
        Date currentTime = calendarNow.getTime();

        if (clockTime.getTime() > currentTime.getTime()) {
            return clockTime.getTime();
        } else {
            return clockTime.getTime() + TimeUnit.HOURS.toMillis(24);
        }
    }

}
