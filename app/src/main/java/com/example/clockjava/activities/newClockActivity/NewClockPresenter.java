package com.example.clockjava.activities.newClockActivity;

import com.example.clockjava.database.LocalDataBase;
import com.example.clockjava.alarmManger.ClockAlarmsManger;

public class NewClockPresenter implements NewClockContract.Presenter {

    private NewClockContract.View view;

    public NewClockPresenter(NewClockContract.View view) {
        this.view = view;
    }

    /**
     * Method adds new clock to database, shows {@link android.widget.Toast} message and returns
     * main activity.
     *
     * @param time time in witch clock will starts
     */
    @Override
    public void addNewClockBackMainActivity(String time) {
        long newClockIndex = addNewClockDataBase(time);
        addNewAlarmManger(time, newClockIndex);
        view.showMessage(time);
        finishActivity();
    }

    /**
     * When user clicks close button this code runs and closes current activity.
     */
    @Override
    public void finishActivity() {
        view.finishActivity();
    }

    /**
     * Method adds new clock to database.
     *
     * @param time time when starts clock alarm, should lock like this HH:mm for example 15:05 or 02:34
     *             using 24-hours format with leading zeroes
     * @return index of new added clock from database, this index auto increasing
     */
    private long addNewClockDataBase(String time) {
        LocalDataBase localDataBase = LocalDataBase.getInstance();
        return localDataBase.addClock(time);
    }

    /**
     * Method creates new {@link android.app.AlarmManager} for {@code time}.
     *
     * @param time  time when starts clock alarm, should lock like this HH:mm for example 15:05 or 02:34
     *              using 24-hours format with leading zeroes
     * @param index index of new added clock in database
     */
    private void addNewAlarmManger(String time, long index) {
        ClockAlarmsManger clockAlarmsManger = new ClockAlarmsManger();
        clockAlarmsManger.addAlarmSignal(time, index);
    }
}
