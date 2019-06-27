package com.example.clockjava.adapter;

public interface ClockAdapterContract {

    interface View {
        void changeAlarmTime();
    }

    interface Presenter {
        void switchPressed(long index, boolean aSwitch, String time);
        void textViewPressed();
    }
}

