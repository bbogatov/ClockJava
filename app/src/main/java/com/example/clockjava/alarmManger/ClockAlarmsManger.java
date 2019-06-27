package com.example.clockjava.alarmManger;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import com.example.clockjava.App;
import com.example.clockjava.logger.Logger;
import com.example.clockjava.receivers.AlarmReceiver;

import org.jetbrains.annotations.NotNull;

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

    /**
     * Method that initializes and returns context
     */
    private Context getContext() {
        return App.getContext();
    }

    /**
     * Method creates intent that used for pending intent and alarm manager.
     *
     * @param time time when alarm manager must start. Must have a look like this HH:MM
     *             for example 01:05 or 23:45
     * @param id   id of clock in database
     * @return returns intent that
     */
    private Intent getIntent(String time, long id, Context context) {
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.setAction("ClockAlarm " + time);
        intent.putExtra("time", time);
        intent.putExtra("id", id);
        intent.addFlags(Intent.FLAG_RECEIVER_FOREGROUND);
        return intent;
    }

    /**
     * Method returns pending intent for clock.
     *
     * @param intent alarm intent that will be pending
     * @return pending intent
     */
    private PendingIntent getPendingIntent(Context context, Intent intent) {
        return PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
    }

    /**
     *
     * @param time
     * @param id
     */
    public void addAlarmSignal(String time, long id) {
        context = getContext();
        intent = getIntent(time, id, context);
        pendingIntent = getPendingIntent(context, intent);
        alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);


        long startAlarmTime = getClockTime(time);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, startAlarmTime, pendingIntent);
        } else {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, startAlarmTime, pendingIntent);
        }

        Logger.log("Create new alarmManager. Time = " + time + "; id = " + id);
    }


    /**
     * Method removes alarm signal
     *
     * @param time time when starts clock alarm, should lock like this HH:mm for example 15:05 or 02:34
     *             using 24-hours format with leading zeroes
     * @param id   id of alarm in database
     */
    public void removeAlarm(String time, long id) {
        context = getContext();
        intent = getIntent(time, id, context);
        pendingIntent = getPendingIntent(context, intent);
        alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        pendingIntent.cancel();
        alarmManager.cancel(pendingIntent);
        Logger.log("Remove alarmManager. Time = " + time + "; Id = " + id);
    }

    /**
     * If user change alarm time for clock this method removes old signal and add new one.
     * ID in database doesn't changes.
     *
     * @param oldTime alarm time that need change, should look like this HH:mm for example 15:05 or 02:34
     *                using 24-hours format with leading zeroes
     * @param newTime future alarm time, should look like this HH:mm for example 15:05 or 02:34
     *                using 24-hours format with leading zeroes
     * @param id      alarm clock id in database
     */
    public void changeAlarm(String oldTime, String newTime, long id) {
        removeAlarm(oldTime, id);
        addAlarmSignal(newTime, id);
        Logger.log("Change time for alarmManager. Id = " + id
                + "; Old time = " + oldTime
                + "; New time = " + newTime);
    }

    /**
     * When switch changes its value code removes or add new alarm signal.
     *
     * @param isChecked switch value, if true it activates clock, if false it inactivates clock
     * @param id        id of element that need change
     * @param time      alarm time
     */
    public void onSwitchChanged(boolean isChecked, long id, String time) {
        if (isChecked) {
            addAlarmSignal(time, id);
        } else {
            removeAlarm(time, id);
        }
        Logger.log("Change alarm manager switch. Time = " + time + "; id = " + id +
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
    private static long getClockTime(String time) {

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
