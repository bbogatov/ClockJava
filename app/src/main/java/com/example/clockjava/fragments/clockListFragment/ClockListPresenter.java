package com.example.clockjava.fragments.clockListFragment;

import com.example.clockjava.database.ClockAlarm;
import com.example.clockjava.database.LocalDataBase;

import java.util.ArrayList;

public class ClockListPresenter implements ClockListContract.Presenter {

    private ClockListContract.View view;


    ClockListPresenter(ClockListContract.View view) {
        this.view = view;
    }

    /**
     * Method takes from database all clocks.
     *
     * @return array of clocks (active and inactive)
     */
    @Override
    public ArrayList<ClockAlarm> getClockData() {
        LocalDataBase localDataBase = LocalDataBase.getInstance();
        return localDataBase.getAlarms();
    }
}
