package com.example.clockjava.adapter;

public interface ClockAdapterContract {

    interface View {
        void changeAlarmTime();
    }

    interface Presenter {
        void switchChanged(long index, boolean aSwitch, String time);

        void runChangeTimeActivity();
    }
}

