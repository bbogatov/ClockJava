package com.example.clockjava;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import com.example.clockjava.logger.Logger;
import com.example.clockjava.receivers.AlarmReceiver;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Class that provides pending alarm clocks.
 * To add clock use method {@link #addAlarmSignal(String, long)}
 * To remove clock use {@link #removeAlarm(String, long)}
 */
public class ClockAlarmsManger {
    private Context context;
    private Intent intent;
    private PendingIntent pendingIntent;
    private AlarmManager alarmManager;

    private void initializeIntentPenIntentAlarmManger(String time, long index) {
        context = App.getContext();

        intent = new Intent(context, AlarmReceiver.class);
        intent.setAction("ClockAlarm " + time);
        intent.putExtra("time", time);
        intent.putExtra("index", index);
        intent.addFlags(Intent.FLAG_RECEIVER_FOREGROUND);

        pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
    }

    public void addAlarmSignal(String time, long index) {
        initializeIntentPenIntentAlarmManger(time, index);
        long startAlarmTime = getClockTime(time);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, startAlarmTime, pendingIntent);
        } else {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, startAlarmTime, pendingIntent);
        }

        Logger.log("Create new alarmManager. Time = " + time + "; index = " + index);
    }


    /**
     * Method removes alarm signal
     *
     * @param time  time when starts clock alarm, should lock like this HH:mm for example 15:05 or 02:34
     *              using 24-hours format with leading zeroes
     * @param index index of alarm in database
     */
    public void removeAlarm(String time, long index) {
        initializeIntentPenIntentAlarmManger(time, index);

        pendingIntent.cancel();
        alarmManager.cancel(pendingIntent);
        Logger.log("Remove alarmManager. Time = " + time + "; index = " + index);
    }

    /**
     * If user change alarm time for clock this method removes old signal and add new one.
     * Index in database doesn't changes.
     *
     * @param oldTime alarm time that need change, should look like this HH:mm for example 15:05 or 02:34
     *                using 24-hours format with leading zeroes
     * @param newTime future alarm time, should look like this HH:mm for example 15:05 or 02:34
     *                using 24-hours format with leading zeroes
     * @param index   alarm clock index in database
     */
    public void changeAlarm(String oldTime, String newTime, long index) {
        removeAlarm(oldTime, index);
        addAlarmSignal(newTime, index);
        Logger.log("Change time for alarmManager. Index = " + index
                + "; Old time = " + oldTime
                + "; New time = " + newTime);
    }

    public void changeSwitch(boolean isChecked, long index, String time) {
        if (isChecked) {
            addAlarmSignal(time, index);
        } else {
            removeAlarm(time, index);
        }
        Logger.log("Change alarm manager switch. Time = " + time + "; index = " + index +
                "; switch old value " + !isChecked + "; switch new value " + isChecked);
    }

    /**
     * Method used for creating time for clock alarm returns time in milliseconds.
     * If time has already passed returns alarm time plus 24-hours.
     * For example if current time is 15:30 and alarm time must be 06:30 clock will work immediately,
     * that is why added 24 hours.
     * <p>
     * If alarm time must work in 15:30 and now 06:30 code wound added 24-hours.
     *
     * @param time time when starts clock alarm, should look like this HH:mm for example 15:05 or 02:34
     *             using 24-hours format with leading zeroes
     * @return
     */
    private long getClockTime(String time) {

        Calendar calendarAlarmTime = Calendar.getInstance();
        calendarAlarmTime.set(Calendar.HOUR_OF_DAY, Integer.valueOf(time.substring(0, 2)));
        calendarAlarmTime.set(Calendar.MINUTE, Integer.valueOf(time.substring(3, 5)));
        calendarAlarmTime.set(Calendar.SECOND, 0);
        calendarAlarmTime.set(Calendar.MILLISECOND, 0);
        Date alarmTime = calendarAlarmTime.getTime();

        Calendar calendarNow = Calendar.getInstance();
        Date currentTime = calendarNow.getTime();

        if (alarmTime.getTime() > currentTime.getTime()) {
            return alarmTime.getTime();
        } else {
            return alarmTime.getTime() + TimeUnit.HOURS.toMillis(24);
        }
    }

}
