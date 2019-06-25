package com.example.clockjava.fragments.clockListFragment;

import com.example.clockjava.database.Alarm;

import java.util.ArrayList;

interface ClockListContract {

    interface View {

    }

    interface Presenter {
        ArrayList<Alarm> getClockData();
    }
}
