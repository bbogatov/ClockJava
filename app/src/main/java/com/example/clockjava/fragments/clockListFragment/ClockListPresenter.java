package com.example.clockjava.fragments.clockListFragment;

import com.example.clockjava.database.Alarm;
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
    public ArrayList<Alarm> getClockData() {
        LocalDataBase localDataBase = LocalDataBase.init();
        return localDataBase.getAlarms();
    }
}
