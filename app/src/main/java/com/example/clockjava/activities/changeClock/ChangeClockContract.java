package com.example.clockjava.activities.changeClock;

import android.widget.TimePicker;

interface ChangeClockContract {

    interface View {
        void closeActivity();
        void showDeleteAlertWindow();
        void showAlertWindowSaveData();
    }

    interface Presenter {
        void setAlarmTimeTimePicker(String time, TimePicker timePicker);
        void applyButtonClicked(boolean timeChanged, String newTime, String oldTime, long id);
        void discardButtonClicked(boolean timeChanged);
        void deleteButtonClicked();
        void deleteClockPositiveButtonClicked(long id, String time);
        void deleteClockNegativeButtonClicked();
        boolean userChangesTime();
        void negativeAlertButtonClicked();
        void positiveAlertButtonClicked(boolean timeChanged, String newTime, String oldTime, long id);
    }

}
