package com.example.clockjava.adapter;

import com.example.clockjava.database.LocalDataBase;
import com.example.clockjava.alarmManger.ClockAlarmsManger;

public class ClockListPresenter implements ClockAdapterContract.Presenter {

    private ClockAdapterContract.View alarmHolder;

    ClockListPresenter(ClockAdapterContract.View alarmHolder) {
        this.alarmHolder = alarmHolder;
    }

    /**
     * Code changes switch value in database and changes AlarmManger signal
     *
     * @param index   index of element that need change
     * @param aSwitch new switch value
     * @param time time when clock must start working
     */
    @Override
    public void switchPressed(long index, boolean aSwitch, String time) {
        changeDataBaseValue(index, aSwitch);
        changeAlarmManager(index, aSwitch, time);
    }

    /**
     * User clicks time
     */
    @Override
    public void textViewPressed() {
        alarmHolder.changeAlarmTime();
    }

    /**
     * Method changes switch value in database
     *
     * @param index   index of element that need change
     * @param aSwitch new switch value
     */
    private void changeDataBaseValue(long index, boolean aSwitch) {
        LocalDataBase.getInstance().changeSwitch(index, aSwitch);
    }

    /**
     * Method that changes {@link android.app.AlarmManager} removes or makes it active.
     *
     * @param index   index of element that need change
     * @param aSwitch future switch value
     * @param time time for clock that need change
     */
    private void changeAlarmManager(long index, boolean aSwitch, String time) {
        new ClockAlarmsManger().onSwitchChanged(aSwitch, index, time);
    }

}
