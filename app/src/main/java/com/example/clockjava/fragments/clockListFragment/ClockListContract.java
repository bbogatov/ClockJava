package com.example.clockjava.fragments.clockListFragment;

import com.example.clockjava.database.ClockAlarm;

import java.util.ArrayList;

interface ClockListContract {

    interface View {

    }

    interface Presenter {
        ArrayList<ClockAlarm> getClockData();
    }
}
