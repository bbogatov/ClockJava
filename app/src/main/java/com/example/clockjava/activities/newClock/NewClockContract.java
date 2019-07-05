package com.example.clockjava.activities.newClock;

public interface NewClockContract {
    interface View {
        void finishActivity();
        void showMessage(String time);
    }

    interface Presenter {
        void applyButtonPressed(String  time);
        void closeButtonPressed();
    }

}
