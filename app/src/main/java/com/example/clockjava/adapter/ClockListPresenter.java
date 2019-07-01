package com.example.clockjava.adapter;

import com.example.clockjava.database.LocalDataBase;
import com.example.clockjava.alarmManger.ClockAlarmsManger;

public class ClockListPresenter implements ClockAdapterContract.Presenter {

    ClockAdapterContract.View alarmHolder;

    ClockListPresenter(ClockAdapterContract.View alarmHolder) {
        this.alarmHolder = alarmHolder;
    }

    /**
     * Code changes switch value in database and changes AlarmManger signal
     *
     * @param index   index of element that need change
     * @param aSwitch new switch value
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
     * Code changes switch value in database
     *
     * @param index   index of element that need change
     * @param aSwitch new switch value
     */
    private void changeDataBaseValue(long index, boolean aSwitch) {
        LocalDataBase localDataBase = LocalDataBase.getInstance();
        localDataBase.changeSwitch(index, aSwitch);
    }

    /**
     * Method that changes {@link android.app.AlarmManager} removes or makes it active.
     *
     * @param index   index of element that need change
     * @param aSwitch future switch value
     * @param time
     */
    private void changeAlarmManager(long index, boolean aSwitch, String time) {
        ClockAlarmsManger clockAlarmsManger = new ClockAlarmsManger();
        clockAlarmsManger.onSwitchChanged(aSwitch, index, time);
    }

}
